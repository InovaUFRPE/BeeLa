package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.List.AdapterPersoUsuario;
import com.beela.beela.List.adapterPersonalizadoAmigos;
import com.beela.beela.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PesquisaActivity extends AppCompatActivity {


    private EditText editTextPesquisa;
    private ListView listViewNomes;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private AdapterPersoUsuario usuarioArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        inicializarcomponentes();
        inicializarfirebase();
        eventoEdit();

    }

    private void eventoEdit() {


        editTextPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String palavra = editTextPesquisa.getText().toString().trim();
                pesquisarpalavra(palavra);



            }
        });

    }

    private void pesquisarpalavra(String palavra) {

        Query query;
        if (palavra.equals("")){

            query = databaseReference.child("conta").orderByChild("nome");


        }
        else {

            query = databaseReference.child("conta").orderByChild("nome").startAt(palavra).endAt(palavra+"\uf8ff");

        }

        usuarios.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Usuario usuario = dataSnapshot1.getValue(Usuario.class);
                    usuarios.add(usuario);

                }

                usuarioArrayAdapter = new AdapterPersoUsuario(usuarios ,PesquisaActivity.this);
                listViewNomes.setAdapter(usuarioArrayAdapter);



                listViewNomes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Usuario UsuarinhoDetalhes = (Usuario) parent.getAdapter().getItem(position);


                        Intent intent = new Intent(getApplicationContext(),AmigoDetalhesActivity.class);
                        intent.putExtra("usuarinho",UsuarinhoDetalhes);
                        startActivity(intent);

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        
    }


    @Override
    protected void onResume() {
        super.onResume();
        pesquisarpalavra("");
    }

    private void inicializarcomponentes() {

        editTextPesquisa = findViewById(R.id.editTextPesquisaNome);
        listViewNomes = findViewById(R.id.listview_pesquisa);

    }

    private void inicializarfirebase() {


        FirebaseApp.initializeApp(PesquisaActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();



    }
}
