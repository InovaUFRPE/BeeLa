package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beela.beela.Entidades.Grupo;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CriarGrupoP2Activity extends AppCompatActivity {
    private Sessao preferencias;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button criarGrupo;
    private EditText editTextNomeGrupo;
    private String nomedogrupo;
    private ArrayList<Usuario> abigos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_grupo_p2);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

        abigos = (ArrayList<Usuario>) getIntent().getSerializableExtra("listadeAbiginhosSelecionados");

        editTextNomeGrupo = findViewById(R.id.editTextNomedogrupo);

        criarGrupo = findViewById(R.id.buttonCriarGrupoFinal);
        criarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CriaGrupinho();


            }
        });

    }

    private void CriaGrupinho() {

        Grupo grupinho = new Grupo();

        if (!editTextNomeGrupo.getText().toString().equals("")){

            nomedogrupo = editTextNomeGrupo.getText().toString();
            grupinho.setNome(nomedogrupo);




        String codificadorUsuarioLogado = Codificador.codificador(preferencias.getUsuario().getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference("grupo").child(nomedogrupo);


        databaseReference.child("admin").setValue(codificadorUsuarioLogado);
        databaseReference.child("integrantes").child(codificadorUsuarioLogado).setValue(codificadorUsuarioLogado);

        for(Usuario u :abigos){

            String codificador = Codificador.codificador(u.getEmail());
            databaseReference.child("integrantes").child(codificador).setValue(codificador);

        }
        databaseReference = FirebaseDatabase.getInstance().getReference("conta").child(codificadorUsuarioLogado).child("grupo").child(nomedogrupo);
        databaseReference.setValue(nomedogrupo);

        for(Usuario usuarios:abigos) {

            String codificador = Codificador.codificador(usuarios.getEmail());

            databaseReference = FirebaseDatabase.getInstance().getReference("conta").child(codificador).child("grupo").child(nomedogrupo);
            databaseReference.setValue(nomedogrupo);

        }



            Intent intent = new Intent(CriarGrupoP2Activity.this,GruposActivity.class);
            startActivity(intent);

        finish();


        }
        else {

            Toast.makeText(getApplicationContext(),"Digite um Nome para o Grupo",Toast.LENGTH_LONG).show();

        }


}


    @Override
    public void onBackPressed() {

        finish();

    }


}
