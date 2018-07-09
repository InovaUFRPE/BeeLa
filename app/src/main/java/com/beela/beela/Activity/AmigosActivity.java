package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.beela.beela.R;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.adapterPersonalizadoAmigos;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AmigosActivity extends AppCompatActivity {

    private adapterPersonalizadoAmigos arrayadapterCu;


    private Sessao preferencias;
    private ListView listViewAmigos;
    private Button buttonAdicionar;
    private EditText editTextEmail;
    private Adapter adapterListaAmigos;
    private DatabaseReference databaseReference;

    private ArrayList<String> listaAmigos = new ArrayList<>();
    private String emailUsuarioCodi;
    private ArrayList<Usuario> listaAmigosListview = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewAmigos = findViewById(R.id.listviewAmigos);
        buttonAdicionar = findViewById(R.id.buttonAdcAmigos);
        editTextEmail = findViewById(R.id.editTextEmailAmigo);

        emailUsuarioCodi = Codificador.codificador(preferencias.getEmail());

        buttonAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextEmail.getText().toString().equals("")){
                    String emailCodificado = Codificador.codificador(editTextEmail.getText().toString());
                    //TODO Verificar se o email Existe e id do usuario

                    databaseReference = FirebaseDatabase.getInstance().getReference("amigos").child(emailUsuarioCodi);
                    Map<String, Object> postValues = new HashMap<String, Object>();
                    postValues.put(emailCodificado ,emailCodificado);

                    databaseReference.updateChildren(postValues);

                }
            }
        });



        databaseReference = FirebaseDatabase.getInstance().getReference("amigos").child(emailUsuarioCodi);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data: dataSnapshot.getChildren()){

                    String conta = data.getValue().toString();

                    listaAmigos.add(conta);

                }


                    for(String conta:listaAmigos) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("conta").child(conta);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            Usuario amigo = new Usuario();
                            amigo.setNome(dataSnapshot.child("nome").getValue().toString());
                            amigo.setEmail(dataSnapshot.child("email").getValue().toString());
                            amigo.setSexo(dataSnapshot.child("sexo").getValue().toString());
                            amigo.setUrlFoto(dataSnapshot.child("urlFoto").getValue().toString());

                            listaAmigosListview.add(amigo);

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }


                arrayadapterCu = new adapterPersonalizadoAmigos(listaAmigosListview,AmigosActivity.this);
                listViewAmigos.setAdapter(arrayadapterCu);

                listViewAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Usuario abiguinhoDetalhes = (Usuario) parent.getAdapter().getItem(position);


                        Intent intent = new Intent(getApplicationContext(),AmigoDetalhesActivity.class);
                        intent.putExtra("amiguinho",abiguinhoDetalhes);
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
