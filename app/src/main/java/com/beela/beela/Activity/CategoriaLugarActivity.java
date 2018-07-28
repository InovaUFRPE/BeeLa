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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private DatabaseReference referencia;

    private ArrayList<CheckBox> checkboxes;
    private ArrayList<String> interessesLugar;

    private Sessao preferencias;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_lugar);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

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
               /// Toast.makeText(CategoriaLugarActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                interesses.add(checkbox.getText().toString());
            }
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
        adicionarInteresses(identificador);
    }
                //INSERINDO NAS PREFERENCIAS

    public void adicionarInteresses(String identificador) {

        perfil.setInteresse23(interessesLugar.get(0));
        preferencias.setInteresse23(identificador, interessesLugar.get(0));
        preferencias.getPerfil().addInteresseLugarP(interessesLugar.get(0));
        preferencias.setPerfil(perfil);

        if (interessesLugar.size() > 1){

            perfil.setInteresse24(interessesLugar.get(1));
            preferencias.setInteresse24(identificador, interessesLugar.get(1));
            preferencias.getPerfil().addInteresseLugarP(interessesLugar.get(1));
            preferencias.setPerfil(perfil);

        } else { }

        if (interessesLugar.size() > 2){

            perfil.setInteresse25(interessesLugar.get(2));
            preferencias.setInteresse25(identificador, interessesLugar.get(2));
            preferencias.getPerfil().addInteresseLugarP(interessesLugar.get(2));
            preferencias.setPerfil(perfil);

        } else { }

        if (interessesLugar.size() > 3){

            perfil.setInteresse26(interessesLugar.get(3));
            preferencias.setInteresse26(identificador, interessesLugar.get(3));
            preferencias.getPerfil().addInteresseLugarP(interessesLugar.get(3));
            preferencias.setPerfil(perfil);

        } else { }

        if (interessesLugar.size() > 4){

            perfil.setInteresse27(interessesLugar.get(4));
            preferencias.setInteresse27(identificador, interessesLugar.get(4));
            preferencias.getPerfil().addInteresseLugarP(interessesLugar.get(4));
            preferencias.setPerfil(perfil);

        } else { }

        if (interessesLugar.size() > 5){

            perfil.setInteresse28(interessesLugar.get(5));
            preferencias.setInteresse28(identificador, interessesLugar.get(5));
            preferencias.getPerfil().addInteresseLugarP(interessesLugar.get(5));
            preferencias.setPerfil(perfil);

        } else { }


        Toast.makeText(CategoriaLugarActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();
        atualizarInteresseFirebase();

    }

                    //ATUALIZANDO NO FIREBASE

    private void atualizarInteresseFirebase() {
        referencia = FirebaseDatabase.getInstance().getReference();
        //autenticacao = preferencias.getUsuario().getAutenticacao();

        referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> postValues = new HashMap<String, Object>();

                int tam = (preferencias.getPerfil().getInteressesLugarP().size());
                for (int i = 0; i < tam; i++) {
                    String chave = "interesse" + (i + 20);
                    postValues.put(chave, preferencias.getPerfil().getInteressesLugarP().get(0));
                    referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
                    preferencias.getPerfil().getInteressesLugarP().remove(0);
                }

               // referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaLugarActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }

}
