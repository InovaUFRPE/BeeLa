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
    private DatabaseReference referencia;


    private ArrayList<CheckBox> checkboxes;
    private ArrayList<String> interessesMusica;

    private Sessao preferencias;
    private Perfil perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_musica);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

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
              //  Toast.makeText(CategoriaMusicaActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                interesses.add(checkbox.getText().toString());
            }
        }
    }

    public void adicionaInteressePreferencias() {
        for (String interesse : interessesMusica) {
            perfil.addInteresseMusicaP(interesse);
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

        if (interessesMusica.size() > 3) {
            Toast.makeText(CategoriaMusicaActivity.this, "Você não pode adicionar mais de 3 interesses!", Toast.LENGTH_SHORT).show();

        } else {
            adicionarInteresses(identificador);

        }
    }

    public void adicionarInteresses(String identificador) {

        perfil.setInteresse4(interessesMusica.get(0));
        preferencias.setInteresse4(identificador, interessesMusica.get(0));
        preferencias.getPerfil().addInteresseMusicaP(interessesMusica.get(0));
        preferencias.setPerfil(perfil);

        if (interessesMusica.size() > 1){

            perfil.setInteresse5(interessesMusica.get(1));
            preferencias.setInteresse5(identificador, interessesMusica.get(1));
            preferencias.getPerfil().addInteresseMusicaP(interessesMusica.get(1));
            preferencias.setPerfil(perfil);

        } else { }

        if (interessesMusica.size() == 3){

            perfil.setInteresse6(interessesMusica.get(2));
            preferencias.setInteresse6(identificador, interessesMusica.get(2));
            preferencias.getPerfil().addInteresseMusicaP(interessesMusica.get(2));
            preferencias.setPerfil(perfil);

        } else { }

        //updatar child de perfil no firebase
        atualizarInteresseFirebase();
        Toast.makeText(CategoriaMusicaActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();


    }

    private void atualizarInteresseFirebase() {
        referencia = FirebaseDatabase.getInstance().getReference();
        //autenticacao = preferencias.getUsuario().getAutenticacao();

        referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> postValues = new HashMap<String, Object>();

                int tam = preferencias.getPerfil().getInteressesMusicaP().size();

                for (int i = 0; i < tam; i++) {
                    String chave = "interesse" + (i + 4);
                    postValues.put(chave, preferencias.getPerfil().getInteressesMusicaP().get(0));
                    referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
                    preferencias.getPerfil().getInteressesMusicaP().remove(0);

                }

                //referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaMusicaActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }

}
