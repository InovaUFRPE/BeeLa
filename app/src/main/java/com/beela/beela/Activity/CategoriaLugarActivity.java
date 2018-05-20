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

public class CategoriaLugarActivity extends AppCompatActivity {

    private CheckBox checkBoxParqueDiversoes;
    private CheckBox checkBoxPraia;
    private CheckBox checkBoxClube;
    private CheckBox checkBoxBalada;
    private CheckBox checkBoxIgreja;
    private CheckBox checkBoxCinema;
    private CheckBox checkBoxShopping;
    private CheckBox checkBoxLugarOutro;
    private Button buttonAdicionarInteresseCategoria;

    private ArrayList<CheckBox> checkboxes;
    private ArrayList<String> interessesLugar;

    private Preferencias preferencias;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_lugar);

        checkBoxParqueDiversoes = (CheckBox) findViewById(R.id.checkBoxParqueDiversoes);
        checkBoxPraia = (CheckBox) findViewById(R.id.checkBoxPraia);
        checkBoxClube = (CheckBox) findViewById(R.id.checkBoxClube);
        checkBoxBalada = (CheckBox) findViewById(R.id.checkBoxBalada);
        checkBoxCinema = (CheckBox) findViewById(R.id.checkBoxCinema);
        checkBoxIgreja = (CheckBox) findViewById(R.id.checkBoxIgreja);
        checkBoxShopping = (CheckBox) findViewById(R.id.checkBoxShopping);
        checkBoxLugarOutro = (CheckBox) findViewById(R.id.checkBoxLugarOutro);
        buttonAdicionarInteresseCategoria = (Button) findViewById(R.id.buttonAdicionarInteresseCategoria);

        checkboxes = new ArrayList<CheckBox>();
        interessesLugar = new ArrayList<String>();

        checkboxes.add(checkBoxParqueDiversoes);
        checkboxes.add(checkBoxPraia);
        checkboxes.add(checkBoxBalada);
        checkboxes.add(checkBoxCinema);
        checkboxes.add(checkBoxClube);
        checkboxes.add(checkBoxIgreja);
        checkboxes.add(checkBoxShopping);
        checkboxes.add(checkBoxLugarOutro);

        buttonAdicionarInteresseCategoria = (Button) findViewById(R.id.buttonAdicionarInteresseCategoria);

        buttonAdicionarInteresseCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaCheckboxes(checkboxes, interessesLugar);
                criarPerfil();
                redirecionarPrincipal();
            }
        });


    }

    public void verificaCheckboxes(ArrayList<CheckBox> checkboxes, ArrayList<String> interesses) {
        for (CheckBox checkbox : checkboxes) {

            if (checkbox.isChecked()) {
                Toast.makeText(CategoriaLugarActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                interesses.add(checkbox.getText().toString());
            }
        }
    }

    public void criarPerfil() {
        preferencias = new Preferencias(CategoriaLugarActivity.this);
        perfil = new Perfil();

        String identificador = Codificador.codificador(preferencias.getEmail());
        perfil.setId(identificador);
        perfil.salvar();

        perfil.setInteresse1(interessesLugar.get(0));

        preferencias.setInteresse1(identificador, interessesLugar.get(0));

        //updatar child de perfil no firebase

        Toast.makeText(CategoriaLugarActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

    }

    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaLugarActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }

}
