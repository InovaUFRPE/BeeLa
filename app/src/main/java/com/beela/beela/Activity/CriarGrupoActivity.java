package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.adapterAmigosCriarGrupo;
import com.beela.beela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CriarGrupoActivity extends AppCompatActivity {


    private Sessao preferencias;
    private adapterAmigosCriarGrupo adapterPersoUsuario;
    private ListView listViewlistadeGrupos;
    private adapterAmigosCriarGrupo arrayadapterCu;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> listaamigosID = new ArrayList<>();
    private ArrayList<Usuario> listaAmigosListview = new ArrayList<>();
    private Button avancar;
    private ArrayList<Usuario> listaAmigosSelecionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_grupo);

        avancar = findViewById(R.id.buttonCriarGrupoAvancar);
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewlistadeGrupos = findViewById(R.id.listViewCriarGrupoLista);
        carregarAmigos();


    }


    private void carregarAmigos() {


        databaseReference = FirebaseDatabase.getInstance().getReference("amigo").child(Codificador.codificador(preferencias.getUsuario().getEmail()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()) {

                    String conta = data.getValue().toString();

                    listaamigosID.add(conta);

                }



                for (String conta2: listaamigosID) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("conta").child(conta2);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario amigo = new Usuario();
                            amigo.setNome(dataSnapshot.child("nome").getValue().toString());
                            amigo.setEmail(dataSnapshot.child("email").getValue().toString());
                            amigo.setSexo(dataSnapshot.child("sexo").getValue().toString());
                            amigo.setUrlFoto(dataSnapshot.child("urlFoto").getValue().toString());

                            listaAmigosListview.add(amigo);


                            adapterPersoUsuario = new adapterAmigosCriarGrupo(CriarGrupoActivity.this, listaAmigosListview);

                            listViewlistadeGrupos.setAdapter(adapterPersoUsuario);


                            avancar.setOnClickListener(new View.OnClickListener() {
                                @Override

                                public void onClick(View v) {

                                    listaAmigosSelecionados = verificaSelecionados(listViewlistadeGrupos);


                                    if(listaAmigosSelecionados.size()>0) {

                                        Intent intent = new Intent(CriarGrupoActivity.this, CriarGrupoP2Activity.class);

                                        intent.putExtra("listadeAbiginhosSelecionados", listaAmigosSelecionados);

                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(CriarGrupoActivity.this, "Escolha pelo menos um contato", Toast.LENGTH_LONG).show();

                                    }

                                }
                            });


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


    private ArrayList<Usuario> verificaSelecionados(ListView listaVerifica) {

        ArrayList<Usuario> listaSelecionados = new ArrayList<>();

        try {
            if (listaVerifica != null) {
                Adapter adapter = (Adapter) listaVerifica.getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {

                    Usuario perfil = (Usuario) adapter.getItem(i);
                    if (perfil.getSelecionado()) {

                        listaSelecionados.add(perfil);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    return listaSelecionados;
    }





}



