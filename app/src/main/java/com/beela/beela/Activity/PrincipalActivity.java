package com.beela.beela.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Preferencias;
import com.beela.beela.R;

public class PrincipalActivity extends AppCompatActivity {
    private Preferencias preferencias;
    private Button gerenciarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        preferencias = new Preferencias(PrincipalActivity.this);

        gerenciarPerfil = (Button) findViewById(R.id.buttonGerenciarPerfil);

        gerenciarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarGerenciarPerfil();
            }
        });

        Toast.makeText(PrincipalActivity.this, "Seu interesse 1 é " + preferencias.getInteresse1(), Toast.LENGTH_SHORT).show();

        Toast.makeText(PrincipalActivity.this, "Seu nome é " + preferencias.getNome() + ".", Toast.LENGTH_SHORT).show();
        Toast.makeText(PrincipalActivity.this, "Seu e-mail é " + preferencias.getEmail() + ".", Toast.LENGTH_SHORT).show();
        Toast.makeText(PrincipalActivity.this, "Sua data de aniversário é " + preferencias.getDataAniversario() + ".", Toast.LENGTH_SHORT).show();
        Toast.makeText(PrincipalActivity.this, "Seu gênero é " + preferencias.getGenero() + ".", Toast.LENGTH_SHORT).show();



    }

    public void redirecionarGerenciarPerfil() {
        Intent abrirGerenciarPerfil = new Intent(PrincipalActivity.this, VisualizarPerfilActivity.class);
        startActivity(abrirGerenciarPerfil);
    }
}
