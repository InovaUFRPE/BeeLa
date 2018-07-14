package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class GerenciarGruposActivity extends AppCompatActivity {

    private Sessao preferencias;
    private ListView listViewGruposNomes;
    private ArrayAdapter<String> adapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> listadeGrupos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_grupos);
        listViewGruposNomes = findViewById(R.id.listViewGruposNomes);
        preferencias = Sessao.getInstancia(this.getApplicationContext());

        String cod = Codificador.codificador(preferencias.getUsuario().getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference("conta").
                child(cod).child("grupo");



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String grupo = data.getValue().toString();

                    listadeGrupos.add(grupo);
                }

                adapter = new ArrayAdapter<String>(GerenciarGruposActivity.this, android.R.layout.simple_list_item_1, listadeGrupos);
                listViewGruposNomes.setAdapter(adapter);
                listViewGruposNomes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Grupo grupinho = (Grupo) parent.getAdapter().getItem(position);


                        Intent intent = new Intent(getApplicationContext(),GrupoDetalhesActivity.class);
                        intent.putExtra("grupinho",grupinho);
                        startActivity(intent);





                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

}