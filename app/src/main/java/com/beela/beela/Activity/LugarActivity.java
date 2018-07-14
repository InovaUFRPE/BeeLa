package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beela.beela.R;

public class LugarActivity extends AppCompatActivity {


    private Button buttonPesquisaGoogle,PesquisaJson,recomenda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);


        recomenda = findViewById(R.id.buttonRecomendacao);
        buttonPesquisaGoogle = findViewById(R.id.buttongoogle);
        PesquisaJson = findViewById(R.id.buttonpesquisajson);


        recomenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LugarActivity.this,SlopeCletoTeste.class);
                startActivity(intent);



            }
        });


        buttonPesquisaGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LugarActivity.this,LugaresGoogleActivity.class);
                startActivity(intent);

            }
        });



        PesquisaJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LugarActivity.this,JsonTesteLugaresAct.class);
                startActivity(intent);

            }
        });







    }
}
