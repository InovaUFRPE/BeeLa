package com.beela.beela.Lugar;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Lugar;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class SlopeCleto {

    private Sessao preferencias;
    private Usuario usuarioQuepegafogo = new Usuario();
    private ArrayList<Lugar> arrayListLugaresHistorico = new ArrayList<>();
    private ArrayList<Lugar> arrayListLugaresUsuariosSimilares = new ArrayList<>();
    private ArrayList<Lugar> arrayListFinalCleto = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> listaPreferenciasUsuario = new ArrayList<>();
    private ArrayList<String> listaDeUsuarios = new ArrayList<>();
    private ArrayList<String> preferenciasdoUsuarioX;
    private ArrayList<String> usuariosSimilares = new ArrayList<>();
    private ArrayList<String> listaidLocalGoogle = new ArrayList<>();





    public SlopeCleto(Usuario usuario){

        usuarioQuepegafogo = usuario;


    }


    public ArrayList<Lugar> deixaPegarFogoHistorico(){






        return arrayListLugaresHistorico;
    }


    public ArrayList<String> deixaPegarFogoUsuarios(){

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Brazil/East"));
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH); // O mês vai de 0 a 11.
        int semana = calendar.get(Calendar.WEEK_OF_MONTH);
        final int dia = calendar.get(Calendar.DAY_OF_MONTH);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minuto = calendar.get(Calendar.MINUTE);
        final int segundo = calendar.get(Calendar.SECOND);



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
