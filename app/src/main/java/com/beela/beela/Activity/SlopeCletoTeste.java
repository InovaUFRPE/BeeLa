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
import com.beela.beela.Entidades.Lugar;
import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.adapterLugares;
import com.beela.beela.Lugar.SlopeCleto;
import com.beela.beela.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_slope_cleto);
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewLugaresindicados  = findViewById(R.id.listViewRecomenda);

        slopeCleto = new SlopeCleto(preferencias.getUsuario());
        slopeCleto.deixaPegarFogoUsuarios();







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




        String emailcodificado = Codificador.codificador(preferencias.getUsuario().getEmail());
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

                                            //lugarGoogle.setLocaliza(new LatLng(lat, lng));

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


}


