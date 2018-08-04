package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;

import java.util.ArrayList;
import java.util.TreeMap;

public class LugarActivity extends AppCompatActivity {

    private Sessao preferencias;

    private Button buttonPesquisaGoogle,PesquisaJson,recomenda;
    private ImageButton btMaps,btRecomendacao,bthistorico,btPreferencias;

    private ArrayList<ArrayList<String>> prefs = new ArrayList<>();
    private TreeMap<String, Integer> map = new TreeMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

        btMaps = findViewById(R.id.imageButtonPesquisaGoogle);
        bthistorico = findViewById(R.id.imageButtonHistorico);
        btPreferencias = findViewById(R.id.imageButtonPreferencias);
        btRecomendacao = findViewById(R.id.imageButtonRecomendacao);



        //recomenda = findViewById(R.id.buttonRecomendacao);
        //buttonPesquisaGoogle = findViewById(R.id.buttongoogle);
        //PesquisaJson = findViewById(R.id.buttonpesquisajson);


        btRecomendacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(LugarActivity.this,SlopeCletoTeste.class);
                Usuario u = preferencias.getUsuario();
                u.setAutenticacao(null);
                intent.putExtra("usuarioMagico",u);
                startActivity(intent);



            }
        });


        btMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LugarActivity.this,LugaresGoogleActivity.class);
                startActivity(intent);

            }
        });



        btPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LugarActivity.this,JsonTesteLugaresAct.class);
                startActivity(intent);

            }
        });



        bthistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LugarActivity.this,HistoricoActivity.class);
                startActivity(intent);




            }
        });

    }

    @Override
    public void onBackPressed() {

        finish();

    }


}
