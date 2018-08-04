package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Perfil;
import com.beela.beela.Entidades.PreferenciasPerfil;
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
    private DatabaseReference databaseReference;


    private ArrayList<CheckBox> checkboxes;
    private ArrayList<String> interessesEsporte;

    private Sessao preferencias;
    private Perfil perfil;
    private DatabaseReference referencia;

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
              //  Toast.makeText(CategoriaEsporteActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                interesses.add(checkbox.getText().toString());
            }
        }
    }

    public void adicionaInteressePreferencias() {
        for (String interesse : interessesEsporte) {
            perfil.addInteresseEsporte(interesse);
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
        //perfil.salvar();
        adicionarInteresses(identificador);
    }
    //ADICIONANDO INTERESSES NAS PREFERERENCIA

    public void adicionarInteresses(String identificador) {

            perfil.setInteresse16(interessesEsporte.get(0));
            preferencias.setInteresse16(identificador, interessesEsporte.get(0));
            preferencias.getPerfil().addInteresseEsporte(interessesEsporte.get(0));
            preferencias.setPerfil(perfil);

            if (interessesEsporte.size() > 1) {
                perfil.setInteresse17(interessesEsporte.get(1));
                preferencias.setInteresse17(identificador, interessesEsporte.get(1));
                preferencias.getPerfil().addInteresseEsporte(interessesEsporte.get(1));
                preferencias.setPerfil(perfil);
             } else { }

            if (interessesEsporte.size() > 2) {
                perfil.setInteresse18(interessesEsporte.get(2));
                preferencias.setInteresse18(identificador, interessesEsporte.get(2));
                preferencias.getPerfil().addInteresseEsporte(interessesEsporte.get(2));
                preferencias.setPerfil(perfil);
            } else { }

            if (interessesEsporte.size() > 3) {
                perfil.setInteresse19(interessesEsporte.get(3));
                preferencias.setInteresse19(identificador, interessesEsporte.get(3));
                preferencias.getPerfil().addInteresseEsporte(interessesEsporte.get(3));
                preferencias.setPerfil(perfil);
            } else { }

            if (interessesEsporte.size() > 4) {
                perfil.setInteresse20(interessesEsporte.get(4));
                preferencias.setInteresse20(identificador, interessesEsporte.get(4));
                preferencias.getPerfil().addInteresseEsporte(interessesEsporte.get(4));
                preferencias.setPerfil(perfil);
            } else { }

            if (interessesEsporte.size() > 5) {
                perfil.setInteresse21(interessesEsporte.get(5));
                preferencias.setInteresse21(identificador, interessesEsporte.get(5));
                preferencias.getPerfil().addInteresseEsporte(interessesEsporte.get(5));
                preferencias.setPerfil(perfil);
            } else { }


        if (interessesEsporte.size() > 6) {
            perfil.setInteresse22(interessesEsporte.get(6));
            preferencias.setInteresse22(identificador, interessesEsporte.get(6));
            preferencias.getPerfil().addInteresseEsporte(interessesEsporte.get(6));
            preferencias.setPerfil(perfil);
        } else { }



        //updatar child de perfil no firebase
        //atualizarInteresseFirebase();
        //meuFirebase();
        Firebase.SalvarInteressesUsuario(interessesEsporte,preferencias.getUsuario());

        Toast.makeText(CategoriaEsporteActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

    }




                           //ATUAIZANDO O FIREBASE

    private void atualizarInteresseFirebase() {
        referencia = FirebaseDatabase.getInstance().getReference();

        referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> postValues = new HashMap<String, Object>();

                int tam = (preferencias.getPerfil().getInteressesEsportes().size());

                for (int i = 0; i < tam ; i++) {
                    String chave = "interesse" + (i + 14);
                    postValues.put(chave, preferencias.getPerfil().getInteressesEsportes().get(0));
                    referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
                    preferencias.getPerfil().getInteressesEsportes().remove(0);
                }

               // referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaEsporteActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }

}


