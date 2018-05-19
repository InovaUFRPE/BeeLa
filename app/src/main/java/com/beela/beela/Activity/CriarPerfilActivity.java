package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beela.beela.R;

public class CriarPerfilActivity extends AppCompatActivity {
    private Button buttonCategoriaComida;
    private Button buttonCategoriaMusica;
    private Button buttonCategoriaEsporte;
    private Button buttonCategoriaLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_perfil);

        buttonCategoriaComida = (Button) findViewById(R.id.buttonCategoriaComida);
        buttonCategoriaMusica = (Button) findViewById(R.id.buttonCategoriaMusica);
        buttonCategoriaEsporte = (Button) findViewById(R.id.buttonCategoriaEsporte);
        buttonCategoriaLugar = (Button) findViewById(R.id.buttonCategoriaLugar);

        buttonCategoriaComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarCategoriaComida();
            }
        });

        buttonCategoriaMusica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarCategoriaMusica();
            }
        });

        buttonCategoriaEsporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonCategoriaLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void redirecionarCategoriaComida() {
        Intent abrirCategoriaComida = new Intent(CriarPerfilActivity.this, CategoriaComidaActivity.class);
        startActivity(abrirCategoriaComida);
    }

    public void redirecionarCategoriaMusica() {
        Intent abrirCategoriaMusica = new Intent(CriarPerfilActivity.this, CategoriaMusicaActivity.class);
        startActivity(abrirCategoriaMusica);
    }
}