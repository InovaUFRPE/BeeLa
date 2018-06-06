package com.beela.beela.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;

public class VisualizarPerfilActivity extends AppCompatActivity {
    private TextView interesse1;
    private Sessao preferencias;
    private android.widget.Button gerenciarInteresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

        interesse1 = findViewById(R.id.textViewMostrarInteresses);
        interesse1.setText(preferencias.getInteresse1());

        gerenciarInteresses = (android.widget.Button) findViewById(R.id.buttonGerenciarInteresses);

        gerenciarInteresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerenciarInteresses();
            }
        });


    }

    public void gerenciarInteresses() {
        android.content.Intent abrirPerfil = new android.content.Intent(VisualizarPerfilActivity.this, GerenciarInteressesActivity.class);


//    public void redirecionarAdicionarInteresse() {
//        android.content.Intent abrirPerfil = new android.content.Intent(VisualizarPerfilActivity.this, CriarPerfilActivity.class);
//        startActivity(abrirPerfil);
//        finish();
//    }

//    public void verificarQuantidadeInteresses() {
//        if (preferencias.getPerfil().getInteresses().size() < 10) {
//            gerenciarInteresses();
//        } else {
//            Toast.makeText(VisualizarPerfilActivity.this, "Você já possui 10 interesses em seu perfil!", Toast.LENGTH_SHORT).show();
//        }
//    }

    }
}