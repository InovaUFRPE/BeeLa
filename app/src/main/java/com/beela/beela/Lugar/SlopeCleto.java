package com.beela.beela.Lugar;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Lugar;
import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Entidades.PreferenciasPerfil;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
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
import java.util.Collections;
import java.util.TimeZone;

public class SlopeCleto {
    private com.android.volley.RequestQueue mQueue;


    private Usuario usuarioQuepegafogo = new Usuario();
    private ArrayList<Lugar> arrayListLugaresHistorico = new ArrayList<>();


    private DatabaseReference databaseReference;
    private ArrayList<String> listaPreferenciasUsuario = new ArrayList<>();
    private ArrayList<String> listaDeUsuarios = new ArrayList<>();
    private ArrayList<String> preferenciasdoUsuarioX;
    private ArrayList<String> usuariosSimilares = new ArrayList<>();
    private ArrayList<String> listaidLocalGoogle = new ArrayList<>();
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
    int ano = calendar.get(Calendar.YEAR);
    int mes = calendar.get(Calendar.MONTH); // O mês vai de 0 a 11.
    int semana = calendar.get(Calendar.WEEK_OF_MONTH);
    final int dia = calendar.get(Calendar.DAY_OF_MONTH);
    final int hora = calendar.get(Calendar.HOUR_OF_DAY);
    final int minuto = calendar.get(Calendar.MINUTE);
    final int segundo = calendar.get(Calendar.SECOND);





    public SlopeCleto(Usuario usuario){

        usuarioQuepegafogo = usuario;


    }


    public ArrayList<Lugar> deixaPegarFogoHistorico(Context context){






        return arrayListLugaresHistorico;
    }


    public void deixarPegarFogoPelasPreferencias(Context context, final Double latt, final Double langg){
        final String emailCodificado = Codificador.codificador(usuarioQuepegafogo.getEmail());

        final ArrayList<String> preferenciasLista = new ArrayList<>();
        final ArrayList<LugarGoogle> lugarGoogles = new ArrayList<>();
        mQueue = Volley.newRequestQueue(context);


        databaseReference = FirebaseDatabase.getInstance().
                getReference("perfil").child(emailCodificado);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {


                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(!data.getKey().equals("id")) {
                        if(!data.getValue().toString().equals("null")) {
                            preferenciasLista.add(data.getValue().toString());
                        }
                    }

                }

                //chamarJson

                for(String s:preferenciasLista){


                    String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                            "location="+latt.toString()+",%20"+langg.toString()+
                            "&radius=15000&" +
                            "keyword="+ s +"&" +
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
//                                            databaseReference = FirebaseDatabase.getInstance().getReference("recomendacao").
//                                                    child(emailCodificado).child(String.valueOf(dia)).child(String.valueOf(hora));
//
//                                            databaseReference.setValue(lugaJson.getString("place_id"));



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

                                        }


                                        Collections.sort(lugarGoogles);

                                        if(lugarGoogles.size()<=10){

                                            for(LugarGoogle lugarGoogle1:lugarGoogles){

                                                databaseReference = FirebaseDatabase.getInstance()
                                                        .getReference("recomendacao").child
                                                                (Codificador.codificador(usuarioQuepegafogo.getEmail())).
                                                                child(String.valueOf(dia)).child(String.valueOf(hora)).
                                                                child(lugarGoogle1.getIdGoogle());

                                                databaseReference.setValue(lugarGoogle1.getIdGoogle());


                                            }

                                        } else{

                                            lugarGoogles.subList(0,10);

                                            for(LugarGoogle lugarGoogle1:lugarGoogles){

                                                databaseReference = FirebaseDatabase.getInstance()
                                                        .getReference("recomendacao").child
                                                                (Codificador.codificador(usuarioQuepegafogo.getEmail())).
                                                                child(String.valueOf(dia)).child(String.valueOf(hora)).
                                                                child(lugarGoogle1.getIdGoogle());

                                                databaseReference.setValue(lugarGoogle1.getIdGoogle());


                                            }



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








            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }


    public ArrayList<String> deixaPegarFogoUsuarios(){

        String emailCodificado = Codificador.codificador(usuarioQuepegafogo.getEmail());


        databaseReference = FirebaseDatabase.getInstance().getReference("perfil").child(emailCodificado);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(!data.getKey().equals("id")) {
                        if(!data.getValue().toString().equals("null")) {
                            listaPreferenciasUsuario.add(data.getValue().toString());
                        }
                        }

                }


                //Pegar os gostos de Todos Usuarios do banco
                databaseReference = FirebaseDatabase.getInstance().getReference("conta");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()) {
                            String idUsuarios = data.child("id").getValue().toString();
                            //pegando id de todos os usuarios do banco menos eu

                            if(!data.child("id").getValue().equals(Codificador.codificador(usuarioQuepegafogo.getEmail()))) {

                                listaDeUsuarios.add(idUsuarios);
                            }

                        }


                    for(final String idUsuario: listaDeUsuarios){
                            preferenciasdoUsuarioX = new ArrayList<String>();
                            databaseReference = FirebaseDatabase.getInstance().getReference("perfil").child(idUsuario);


                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    preferenciasdoUsuarioX.clear();
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (!data.getKey().toString().equals("id")) {
                                            if (!data.getValue().toString().equals("null")) {
                                                preferenciasdoUsuarioX.add(data.getValue().toString());

                                            }
                                        }
                                    }

                                    //verificacaodeSimilaridade
                                    int qtd = 0 ;

                                    if(verificarTamanho(listaPreferenciasUsuario,preferenciasdoUsuarioX)){

                                        qtd = VerificarPreferenciasSimilares(qtd);
                                        if(qtd!=0 && (qtd - listaPreferenciasUsuario.size()) >= -2 ){
                                            usuariosSimilares.add(idUsuario);
                                            databaseReference = FirebaseDatabase.getInstance().getReference("historico").child(idUsuario);

                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    //String iddoLocalGoogle = dataSnapshot.getValue().toString();
                                                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                        String iddoLocalGoogle = dataSnapshot1.getValue().toString();


                                                        //listaidLocalGoogle.add(iddoLocalGoogle);
                                                        databaseReference = FirebaseDatabase.getInstance()
                                                                .getReference("recomendacao").child
                                                                        (Codificador.codificador(usuarioQuepegafogo.getEmail())).
                                                                        child(String.valueOf(dia)).child(String.valueOf(hora)).
                                                                        child(iddoLocalGoogle);
                                                        databaseReference.setValue(iddoLocalGoogle);
                                                    }

                                                }


                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });




                                        }
                                    }
                                    //PegarHistoricodosLugares

                                    //combase em usuarios similares todos os locais;
                                }






                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                    }




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    return listaidLocalGoogle;
    }

    private int VerificarPreferenciasSimilares(int qtd) {
        for(String prefereUser:listaPreferenciasUsuario){
            if(!prefereUser.equals("null")) {
                if (preferenciasdoUsuarioX.contains(prefereUser)) {
                    qtd ++;
                }
            }
        }
        return qtd;
    }



    private boolean verificarTamanho(ArrayList<String> usuarioLogado,ArrayList<String> usuarioCompara) {

        if(usuarioCompara.size() == 0){

            return false;
        }

        if((usuarioLogado.size()-usuarioCompara.size()) <= 2 &&
                (usuarioLogado.size() - usuarioCompara.size())>=-2  ){

            return true;
        }

        else {
            return false;
        }

    }


}
