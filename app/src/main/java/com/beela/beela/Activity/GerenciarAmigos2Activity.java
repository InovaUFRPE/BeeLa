package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beela.beela.R;

public class GerenciarAmigos2Activity extends AppCompatActivity {


    Button buttonpesquisarnome, buttonConvite, buttonAmigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_amigos2);

        buttonpesquisarnome = findViewById(R.id.buttonNomePesquisa);
        buttonpesquisarnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GerenciarAmigos2Activity.this,PesquisaActivity.class);
                startActivity(intent);

                }
        });


        buttonConvite = findViewById(R.id.buttonConvites);
        buttonConvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //tela dos convites

                Intent intent = new Intent(GerenciarAmigos2Activity.this,ConvitesActivity.class);
                startActivity(intent);
            }
        });


        buttonAmigos = findViewById(R.id.buttonAmigosLista);

        buttonAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GerenciarAmigos2Activity.this,ListaDeAmigosActivity.class);
                startActivity(intent);

            }
        });








            }





    }




