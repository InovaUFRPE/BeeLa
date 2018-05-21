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
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CategoriaComidaActivity extends AppCompatActivity {
    private CheckBox checkBoxVegetariana;
    private CheckBox checkBoxDoce;
    private CheckBox checkBoxJaponesa;
    private CheckBox checkBoxItaliana;
    private CheckBox checkBoxMexicana;
    private CheckBox checkBoxMassa;
    private CheckBox checkBoxComidaNordestina;
    private CheckBox checkBoxComidaOutro;
    private Button buttonAdicionarInteresseCategoria;

    private ArrayList<CheckBox> checkboxes;
    private ArrayList<String> interessesComida;

    private Preferencias preferencias;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_comida);

        checkBoxVegetariana = (CheckBox) findViewById(R.id.checkBoxVegetariana);
        checkBoxDoce = (CheckBox) findViewById(R.id.checkBoxDoce);
        checkBoxJaponesa = (CheckBox) findViewById(R.id.checkBoxJaponesa);
        checkBoxItaliana = (CheckBox) findViewById(R.id.checkBoxItaliana);
        checkBoxMexicana = (CheckBox) findViewById(R.id.checkBoxMexicana);
        checkBoxMassa = (CheckBox) findViewById(R.id.checkBoxMassa);
        checkBoxComidaNordestina = (CheckBox) findViewById(R.id.checkBoxComidaNordestina);
        checkBoxComidaOutro = (CheckBox) findViewById(R.id.checkBoxComidaOutro);

        checkboxes = new ArrayList<CheckBox>();
        interessesComida = new ArrayList<String>();

        checkboxes.add(checkBoxVegetariana);
        checkboxes.add(checkBoxDoce);
        checkboxes.add(checkBoxJaponesa);
        checkboxes.add(checkBoxItaliana);
        checkboxes.add(checkBoxMexicana);
        checkboxes.add(checkBoxMassa);
        checkboxes.add(checkBoxComidaNordestina);
        checkboxes.add(checkBoxComidaOutro);

        buttonAdicionarInteresseCategoria = (Button) findViewById(R.id.buttonAdicionarInteresseCategoria);

        buttonAdicionarInteresseCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaCheckboxes(checkboxes, interessesComida);
                criarPerfil();
                redirecionarPrincipal();
            }
        });

    }

    public void verificaCheckboxes(ArrayList<CheckBox> checkboxes, ArrayList<String> interesses) {
        for (CheckBox checkbox : checkboxes) {

            if (checkbox.isChecked()) {
                Toast.makeText(CategoriaComidaActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                interesses.add(checkbox.getText().toString());
            }
        }
    }

    public void criarPerfil() {
        preferencias = new Preferencias(CategoriaComidaActivity.this);
        perfil = new Perfil();

        String identificador = Codificador.codificador(preferencias.getEmail());
        perfil.setId(identificador);
        perfil.salvar();

        perfil.setInteresse1(interessesComida.get(0));

        preferencias.setInteresse1(identificador, interessesComida.get(0));

        //updatar child de perfil no firebase

        Toast.makeText(CategoriaComidaActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

    }

    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaComidaActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }
}