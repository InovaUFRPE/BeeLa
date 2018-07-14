package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.AdapterPersoUsuario;
import com.beela.beela.List.adapterPersonalizadoAmigos;
import com.beela.beela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaDeAmigosActivity extends AppCompatActivity {


    private Sessao preferencias;
    private AdapterPersoUsuario adapterPersoUsuario;
    private ListView listViewGrupos;
    private adapterPersonalizadoAmigos arrayadapterCu;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> listaamigosID = new ArrayList<>();
    private ArrayList<Usuario> listaAmigosListview = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_amigos);

        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewGrupos = findViewById(R.id.listViewCriarGrupoLista);
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



                                adapterPersoUsuario = new AdapterPersoUsuario(listaAmigosListview,ListaDeAmigosActivity.this);
                                //arrayadapterCu = new adapterPersonalizadoAmigos(listaAmigosListview,ConvitesActivity.this);
                                listViewGrupos.setAdapter(adapterPersoUsuario);




                                listViewGrupos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                        Usuario UsuarinhoDetalhes = (Usuario) parent.getAdapter().getItem(position);

                                        Usuario s = (Usuario) parent.getAdapter().getItem(position);

                                        Intent intent = new Intent(getApplicationContext(),AmigoDetalhesActivity.class);
                                        intent.putExtra("usuarinho",s);
                                        startActivity(intent);
                                        return true;

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




    }

