package com.beela.beela.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import com.beela.beela.Helper.Preferencias;
import com.beela.beela.R;

public class VisualizarPerfilActivity extends AppCompatActivity {
    private TextView interesse1;
    private Preferencias preferencias;
    private android.widget.Button adicionarInteresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil);

        preferencias = Preferencias.getInstancia(this.getApplicationContext());

        interesse1 = findViewById(R.id.textViewMostrarInteresses);
        interesse1.setText(preferencias.getInteresse1());

        adicionarInteresse = (android.widget.Button) findViewById(R.id.buttonAdicionarInteressesVisualizar);

        adicionarInteresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarAdicionarInteresse();
            }
        });

    }

    public void redirecionarAdicionarInteresse() {
        android.content.Intent abrirPerfil = new android.content.Intent(VisualizarPerfilActivity.this, AdicionarInteresseActivity.class);
        startActivity(abrirPerfil);
        finish();
    }

}
