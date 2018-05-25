package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.beela.beela.Entidades.Perfil;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Preferencias;
import com.beela.beela.R;

import java.util.ArrayList;

public class CategoriaMusicaActivity extends AppCompatActivity {
    private CheckBox checkBoxForro;
    private CheckBox checkBoxSamba;
    private CheckBox checkBoxFunk;
    private CheckBox checkBoxSertanejo;
    private CheckBox checkBoxAxe;
    private CheckBox checkBoxRock;
    private CheckBox checkBoxEletronica;
    private CheckBox checkBoxMusicaOutro;
    private Button buttonAdicionarInteresseCategoria;


    private ArrayList<CheckBox> checkboxes;
    private ArrayList<String> interessesMusica;

    private Preferencias preferencias;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_musica);

        preferencias = Preferencias.getInstancia(this.getApplicationContext());

        checkBoxForro = (CheckBox) findViewById(R.id.checkBoxForro);
        checkBoxSamba = (CheckBox) findViewById(R.id.checkBoxSamba);
        checkBoxFunk = (CheckBox) findViewById(R.id.checkBoxFunk);
        checkBoxSertanejo = (CheckBox) findViewById(R.id.checkBoxSertanejo);
        checkBoxAxe = (CheckBox) findViewById(R.id.checkBoxAxe);
        checkBoxRock = (CheckBox) findViewById(R.id.checkBoxRock);
        checkBoxEletronica = (CheckBox) findViewById(R.id.checkBoxEletronica);
        checkBoxMusicaOutro = (CheckBox) findViewById(R.id.checkBoxMusicaOutro);
        buttonAdicionarInteresseCategoria = (Button) findViewById(R.id.buttonAdicionarInteresseCategoria);

        checkboxes = new ArrayList<CheckBox>();
        interessesMusica = new ArrayList<String>();

        checkboxes.add(checkBoxForro);
        checkboxes.add(checkBoxSamba);
        checkboxes.add(checkBoxFunk);
        checkboxes.add(checkBoxSertanejo);
        checkboxes.add(checkBoxAxe);
        checkboxes.add(checkBoxRock);
        checkboxes.add(checkBoxEletronica);
        checkboxes.add(checkBoxMusicaOutro);

        buttonAdicionarInteresseCategoria = (Button) findViewById(R.id.buttonAdicionarInteresseCategoria);

        buttonAdicionarInteresseCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaCheckboxes(checkboxes, interessesMusica);
                criarPerfil();
                redirecionarPrincipal();
            }
        });


    }

    public void verificaCheckboxes(ArrayList<CheckBox> checkboxes, ArrayList<String> interesses) {
        for (CheckBox checkbox : checkboxes) {

            if (checkbox.isChecked()) {
                Toast.makeText(CategoriaMusicaActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                interesses.add(checkbox.getText().toString());
            }
        }
    }

    public void adicionaInteressePreferencias() {
        for (String interesse : interessesMusica) {
            perfil.addInteresse(interesse);
        }
    }

    public void criarPerfil() {
        if (preferencias.getStatusSessao().equals("1")) {
            perfil = preferencias.getPerfil();
        } else {
            perfil = new Perfil();
        }

        String identificador = Codificador.codificador(preferencias.getEmail());
        perfil.setId(identificador);
        perfil.salvar();

        perfil.setInteresse1(interessesMusica.get(0));
        preferencias.setInteresse1(identificador, interessesMusica.get(0));

        adicionaInteressePreferencias();
        preferencias.setPerfil(perfil);

        //updatar child de perfil no firebase

        Toast.makeText(CategoriaMusicaActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

    }

    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaMusicaActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }

}
