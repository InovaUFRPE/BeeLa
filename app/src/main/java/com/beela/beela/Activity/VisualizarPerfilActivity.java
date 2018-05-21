package com.beela.beela.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.beela.beela.Helper.Preferencias;
import com.beela.beela.R;

public class VisualizarPerfilActivity extends AppCompatActivity {
    private TextView interesse1;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_perfil);

        preferencias = new Preferencias(VisualizarPerfilActivity.this);

        interesse1 = findViewById(R.id.textViewMostrarInteresses);
        interesse1.setText(preferencias.getInteresse1());

    }
}
