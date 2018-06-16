package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beela.beela.R;

public class GruposActivity extends AppCompatActivity {


    Button criarGrupo,gerenciarGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

    criarGrupo = findViewById(R.id.buttonCriarGrupo);
    gerenciarGrupo = findViewById(R.id.buttonGerenciarGrupo);


    criarGrupo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(GruposActivity.this,CriarGrupoActivity.class);
            startActivity(intent);
        }
    });


    gerenciarGrupo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent intent = new Intent(GruposActivity.this,GerenciarGruposActivity.class);
            startActivity(intent);

        }
    });








    }
}
