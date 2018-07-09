package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LugarDetalhesActivity extends AppCompatActivity {
    private Sessao preferencias;
    private TextView nomeLocal,endereco,aberto,nota,localiza,idgoogle;
    private Button buttonIr;
    private DatabaseReference databaseReference;
    private LugarGoogle lugarGoogless;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_detelhes);
        preferencias = Sessao.getInstancia(this.getApplicationContext());


        Intent intent = getIntent();
        lugarGoogless = (LugarGoogle)  intent.getSerializableExtra("lugarzinho");

        nomeLocal = findViewById(R.id.textViewNomeLugar);
        nomeLocal.setText(lugarGoogless.getNome().toString());

        endereco = findViewById(R.id.textViewEndereco);
        endereco.setText(lugarGoogless.getEndereco().toString());


        aberto = findViewById(R.id.textViewAberto);
        aberto.setText(lugarGoogless.getAbertoagora().toString());

        nota = findViewById(R.id.textViewNota);
        nota.setText(lugarGoogless.getNota().toString());


        localiza = findViewById(R.id.textViewLocaliza);
        //localiza.setText(lugarGoogle.getLocaliza().toString());



        buttonIr = findViewById(R.id.buttonirparaolocal);
        idgoogle = findViewById(R.id.textViewId);
        idgoogle.setText(lugarGoogless.getIdGoogle());


        buttonIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailCod = Codificador.codificador(preferencias.getUsuario().getEmail());

                databaseReference = FirebaseDatabase.getInstance().getReference("historico").child(emailCod).child(lugarGoogless.getIdGoogle());
                databaseReference.setValue(lugarGoogless.getIdGoogle());
                //databaseReference.setValue("Visitado");
                //TODO inserir quantidade de vezes que o usuario foi ao local
                //TODO se o usuario gostou



            }
        });








    }
}