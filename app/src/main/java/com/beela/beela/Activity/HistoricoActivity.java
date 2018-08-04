package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.adapterLugares;
import com.beela.beela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoricoActivity extends AppCompatActivity {
    private com.android.volley.RequestQueue mQueue;
    private com.beela.beela.List.adapterLugares adapterLugares;

    private Sessao preferencias;
    private DatabaseReference databaseReference;
    private ArrayList<String> idDoGoogle = new ArrayList<>();
    private ArrayList<LugarGoogle> lugarGoogles = new ArrayList<>();

    private ListView listViewwHistorico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        mQueue = Volley.newRequestQueue(this);

        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewwHistorico = findViewById(R.id.listViewHistorico);

        imprimirListView();
        Toast.makeText(getApplicationContext(),"Carregando...",Toast.LENGTH_LONG).show();


    }


    public void imprimirListView(){



        String emailcodificado = Codificador.codificador(preferencias.getUsuario().getEmail());
        databaseReference = FirebaseDatabase.getInstance().
                getReference("historico").
                child(emailcodificado);



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


                                            if(Boolean.valueOf(lugaJson.getJSONObject("opening_hours").getString("open_now")).equals(true)){

                                                lugarGoogle.setAbertoagora(2);

                                            }


                                            if(Boolean.valueOf(lugaJson.getJSONObject("opening_hours").getString("open_now")).equals(false)){

                                                lugarGoogle.setAbertoagora(1);

                                            }





                                        }catch (JSONException e){

                                            e.printStackTrace();
                                        }
                                        lugarGoogle.setNota(lugaJson.getDouble("rating"));

                                        double lat = lugaJson.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                        double lng = lugaJson.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                                        //lugarGoogle.setLocaliza(new LatLng(lat, lng));

                                        lugarGoogle.setLng(lng);
                                        lugarGoogle.setLat(lat);

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
        adapterLugares = new adapterLugares(lugarGoogles, HistoricoActivity.this);
        listViewwHistorico.setAdapter(adapterLugares);

        listViewwHistorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarGoogle lugarzinhoGoogle;

                lugarzinhoGoogle = (LugarGoogle) parent.getAdapter().getItem(position);

                Intent intent = new Intent(HistoricoActivity.this,LugarDetalhesActivity.class);
                intent.putExtra("lugarzinho",lugarzinhoGoogle);

                startActivity(intent);


            }
        });
    }


    @Override
    public void onBackPressed() {

        finish();

    }



}
