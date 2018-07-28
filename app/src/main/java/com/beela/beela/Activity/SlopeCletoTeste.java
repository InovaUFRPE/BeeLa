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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beela.beela.Entidades.Lugar;
import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.adapterLugares;
import com.beela.beela.Lugar.SlopeCleto;
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
import java.util.Calendar;
import java.util.TimeZone;

public class SlopeCletoTeste extends AppCompatActivity {
    private com.android.volley.RequestQueue mQueue;

    private ListView listViewLugaresindicados;
    private Sessao preferencias;
    private SlopeCleto slopeCleto;
    private com.beela.beela.List.adapterLugares adapterLugares;
    private ArrayList<LugarGoogle> lugarGoogles = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ArrayList<String> idDoGoogle = new ArrayList<>();
    private Usuario usuarioMagico;

    private LocationManager locationManager;
    private GoogleMap mMap;
    private static final int REQUEST_FINE_LOCATION = 1;
    private Double latt;
    private Double langg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        usuarioMagico = (Usuario) intent.getSerializableExtra("usuarioMagico");


        setContentView(R.layout.activity_slope_cleto);

        listViewLugaresindicados  = findViewById(R.id.listViewRecomenda);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        updateLocation();

        slopeCleto = new SlopeCleto(usuarioMagico);
        slopeCleto.deixaPegarFogoUsuarios();
        slopeCleto.deixarPegarFogoPelasPreferencias(getApplicationContext(),latt,langg);






        imprimirListView();



    }



    public void imprimirListView(){

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH); // O mês vai de 0 a 11.
        int semana = calendar.get(Calendar.WEEK_OF_MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = calendar.get(Calendar.MINUTE);
        final int segundo = calendar.get(Calendar.SECOND);




        String emailcodificado = Codificador.codificador(usuarioMagico.getEmail());
        databaseReference = FirebaseDatabase.getInstance().
                getReference("recomendacao").
                child(emailcodificado).
                child(String.valueOf(dia)).
                child(String.valueOf(hora));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    idDoGoogle.add(dataSnapshot1.getValue().toString());

                }



                for(String s:idDoGoogle){


                    String  url = "https://maps.googleapis.com/maps/api/place/details/json?" +
                            "placeid="+ s +"&" +
                            "key=AIzaSyCs4g8KU95xizS77El9HbxhTZcBfiaJk7A";

                    final JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.
                            Method.GET, url, null, new
                            com.android.volley.Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        JSONObject lugaJson = response.getJSONObject("result");


                                            LugarGoogle lugarGoogle = new LugarGoogle();



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

                imprimir();







            }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void imprimir() {
        adapterLugares = new adapterLugares(lugarGoogles, SlopeCletoTeste.this);
        listViewLugaresindicados.setAdapter(adapterLugares);

        listViewLugaresindicados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarGoogle lugarzinhoGoogle;

                lugarzinhoGoogle = (LugarGoogle) parent.getAdapter().getItem(position);

                Intent intent = new Intent(SlopeCletoTeste.this,LugarDetalhesActivity.class);
                intent.putExtra("lugarzinho",lugarzinhoGoogle);

                startActivity(intent);


            }
        });
    }



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













