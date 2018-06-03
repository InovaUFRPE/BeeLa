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
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;

import java.util.ArrayList;

public class CategoriaEsporteActivity extends AppCompatActivity {
    private CheckBox checkBoxCaminhada;
    private CheckBox checkBoxSurf;
    private CheckBox checkBoxSkate;
    private CheckBox checkBoxFutebol;
    private CheckBox checkBoxAcademia;
    private CheckBox checkBoxArtesMarciais;
    private CheckBox checkBoxNatação;
    private CheckBox checkBoxEsporteOutro;
    private Button buttonAdicionarInteresseCategoria;


    private ArrayList<CheckBox> checkboxes;
    private ArrayList<String> interessesEsporte;

    private Sessao preferencias;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_esporte);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

        checkBoxCaminhada = (CheckBox) findViewById(R.id.checkBoxCaminhada);
        checkBoxSurf = (CheckBox) findViewById(R.id.checkBoxSurf);
        checkBoxSkate = (CheckBox) findViewById(R.id.checkBoxSkate);
        checkBoxFutebol = (CheckBox) findViewById(R.id.checkBoxFutebol);
        checkBoxAcademia = (CheckBox) findViewById(R.id.checkBoxAcademia);
        checkBoxArtesMarciais = (CheckBox) findViewById(R.id.checkBoxArtesMarciais);
        checkBoxNatação = (CheckBox) findViewById(R.id.checkBoxNatação);
        checkBoxEsporteOutro = (CheckBox) findViewById(R.id.checkBoxEsporteOutro);
        buttonAdicionarInteresseCategoria = (Button) findViewById(R.id.buttonAdicionarInteresseCategoria);

        checkboxes = new ArrayList<CheckBox>();
        interessesEsporte = new ArrayList<String>();

        checkboxes.add(checkBoxAcademia);
        checkboxes.add(checkBoxArtesMarciais);
        checkboxes.add(checkBoxSkate);
        checkboxes.add(checkBoxSurf);
        checkboxes.add(checkBoxNatação);
        checkboxes.add(checkBoxFutebol);
        checkboxes.add(checkBoxCaminhada);
        checkboxes.add(checkBoxEsporteOutro);

        buttonAdicionarInteresseCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaCheckboxes(checkboxes, interessesEsporte);
                criarPerfil();
                redirecionarPrincipal();
            }
        });


    }

    public void verificaCheckboxes(ArrayList<CheckBox> checkboxes, ArrayList<String> interesses) {
        for (CheckBox checkbox : checkboxes) {

            if (checkbox.isChecked()) {
                Toast.makeText(CategoriaEsporteActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                interesses.add(checkbox.getText().toString());
            }
        }
    }

    public void adicionaInteressePreferencias() {
        for (String interesse : interessesEsporte) {
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

        perfil.setInteresse1(interessesEsporte.get(0));
        preferencias.setInteresse1(identificador, interessesEsporte.get(0));

        adicionaInteressePreferencias();
        preferencias.setPerfil(perfil);


        //updatar child de perfil no firebase

        Toast.makeText(CategoriaEsporteActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

    }

    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaEsporteActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }

}


