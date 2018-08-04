package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.beela.beela.Entidades.Grupo;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GruposActivity extends AppCompatActivity {


    private ImageButton criarGrupo;
    private DatabaseReference databaseReference;
    private ArrayAdapter<String> adapter;
    private ListView listViewGruposNomes;
    private Grupo grupinho = new Grupo();
    private Sessao preferencias;

    private ArrayList<String> listadeGrupos = new ArrayList<>();
    private ArrayList<String> integrantes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewGruposNomes = findViewById(R.id.listViewGruposActGrupos);

    criarGrupo = findViewById(R.id.imageButtonCriarGrupo);
    //gerenciarGrupo = findViewById(R.id.buttonGerenciarGrupo);


    criarGrupo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(GruposActivity.this,CriarGrupoActivity.class);
            startActivity(intent);
            finish();

        }
    });

    carregarGrupos();


//    gerenciarGrupo.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//
//            Intent intent = new Intent(GruposActivity.this,GerenciarGruposActivity.class);
//            startActivity(intent);
//
//        }
//    });



    }


    private void carregarGrupos() {

        String cod = Codificador.codificador(preferencias.getUsuario().getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference("conta").
                child(cod).child("grupo");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String grupo = data.getValue().toString();

                    listadeGrupos.add(grupo);
                }

                adapter = new ArrayAdapter<String>(GruposActivity.this, android.R.layout.simple_list_item_1, listadeGrupos);
                listViewGruposNomes.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                listViewGruposNomes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        //Excluir grupo

                        String nomegrupo = (String) parent.getAdapter().getItem(position);

                        excluirGrupo(nomegrupo);

                        return true;



                    }
                });


                listViewGruposNomes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        integrantes.clear();
                        grupinho = new Grupo();
                        final String idGrupo = (String) parent.getAdapter().getItem(position);


                        databaseReference = FirebaseDatabase.getInstance().getReference("grupo").
                                child(idGrupo).child("integrantes");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                                    integrantes.add(dataSnapshot1.getValue().toString());

                                }

                                grupinho.setIntegrantes(integrantes);
                                grupinho.setNome(idGrupo);
                                grupinho.setId(idGrupo);

                                Intent intent = new Intent(getApplicationContext(),GrupoDetalhesActivity.class);
                                intent.putExtra("grupinho",grupinho);
                                startActivity(intent);



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void excluirGrupo(String nomegrupo) {






    }



    @Override
    public void onBackPressed() {

        finish();

    }


}

