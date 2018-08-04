package com.beela.beela.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Entidades.PreferenciasPerfil;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.adapterLugares;
import com.beela.beela.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonTesteLugaresAct extends AppCompatActivity {

    private com.android.volley.RequestQueue mQueue;
    private Sessao preferencias;
    private Button buttonJson;
    private DatabaseReference databaseReference;
    private EditText palavraChave;
    private com.beela.beela.List.adapterLugares adapterLugares;
    private ListView listViewLugares;
    private ArrayList<LugarGoogle> lugarGoogles = new ArrayList<>();
    private ArrayList<String> palavras = new ArrayList<>();
    private LocationManager locationManager;
    private GoogleMap mMap;
    private static final int REQUEST_FINE_LOCATION = 1;
    private Double latt;
    private Double langg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste2_json);
        mQueue = Volley.newRequestQueue(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        preferencias = Sessao.getInstancia(this.getApplicationContext());

        listViewLugares = findViewById(R.id.listViewPreferenciasJson);
//        palavraChave = findViewById(R.id.editTextPalavra);
        //buttonJson = findViewById(R.id.buttonJson);


        updateLocation();
        pegarInteresses();
        imprimirListView();
        Toast.makeText(getApplicationContext(),"Carregando...",Toast.LENGTH_LONG).show();

//
//        buttonJson.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!palavraChave.getText().toString().equals("")){
//
//                    updateLocation();
//                    pegarInteresses();
//                    imprimirListView();
//                    Toast.makeText(getApplicationContext(),"Wait",Toast.LENGTH_LONG).show();
//
//                }
//                else {
//
//                    Toast.makeText(getApplicationContext(),
//                            "Digite Algo Amibiguinho",
//                            Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });


    }


    private void pegarInteresses(){

        String emailcod = Codificador.codificador(preferencias.getUsuario().getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference("perfil").
                child(emailcod);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (!data.getValue().toString().equals("null") && !data.getKey().equals("id")) {

//                        PreferenciasPerfil p = new PreferenciasPerfil();
//                        p.setValor(data.getValue().toString());
//                        p.setChave(data.getKey().toString());
                        palavras.add(data.getValue().toString());

                    }
                }


                jsonParse(palavras);





                    }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    private void jsonParse(ArrayList<String> palavras) {

        lugarGoogles.clear();

        for(String palavra:palavras){

            chamadasJson(palavra);

        }

    }

    private void chamadasJson(String palavra) {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location="+latt.toString()+",%20"+langg.toString()+
                "&radius=15000&" +
                "type=restaurante&" +
                "keyword="+ palavra.toString() +"&" +
                "key=AIzaSyAI1bjrxWDnoBGDtMJumHon73xZjLcNwmg";

        final JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.
                Method.GET, url, null, new
                com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                LugarGoogle lugarGoogle = new LugarGoogle();
                                JSONObject lugaJson = jsonArray.getJSONObject(i);
                                lugarGoogle.setNome(lugaJson.getString("name"));
                                lugarGoogle.setIdGoogle(lugaJson.getString("place_id"));
                                lugarGoogle.setEndereco(lugaJson.getString("vicinity"));
                                try{
                                lugarGoogle.setAbertoagora(Boolean.valueOf(lugaJson.getJSONObject("opening_hours").getString("open_now")));

                                }catch (JSONException e){

                                    e.printStackTrace();
                                }
                                lugarGoogle.setNota(lugaJson.getDouble("rating"));

                                double lat = lugaJson.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                double lng = lugaJson.getJSONObject("geometry").getJSONObject("location").getDouble("lng");



                                lugarGoogle.setLat(lat);

                                lugarGoogle.setLng(lng);




                                lugarGoogles.add(lugarGoogle);
                                adapterLugares.notifyDataSetChanged();

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });

        mQueue.add(request);
    }


    public void imprimirListView(){


        adapterLugares = new adapterLugares(lugarGoogles, JsonTesteLugaresAct.this);
        listViewLugares.setAdapter(adapterLugares);

        listViewLugares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarGoogle lugarzinhoGoogle;
                        ;
                lugarzinhoGoogle = (LugarGoogle) parent.getAdapter().getItem(position);

                Intent intent = new Intent(JsonTesteLugaresAct.this,LugarDetalhesActivity.class);
                intent.putExtra("lugarzinho",lugarzinhoGoogle);

                startActivity(intent);


            }
        });

    }



    //Localizacao

    public void updateLocation() {
        checkPermission();
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED /*&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
                return;
            }
            goToCurrentLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mMap.setMyLocationEnabled(true);
            goToCurrentLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    public void goToCurrentLocation(Location location){
        if (location!=null){
            latt = location.getLatitude();
            langg = location.getLongitude();
            LatLng latLng = new LatLng(latt, langg);
        }
    }




    private void checkPermission() {
        boolean permissionFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
        boolean permissionCoarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;

        if (permissionFineLocation && permissionCoarseLocation) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }
    }


}
