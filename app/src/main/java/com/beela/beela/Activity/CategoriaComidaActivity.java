package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.beela.beela.Entidades.PreferenciasPerfil;
import com.beela.beela.Entidades.Perfil;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private Sessao preferencias;
    private Perfil perfil;

    private DatabaseReference referencia;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_comida);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

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

    //Pegando os selescionados

    public void verificaCheckboxes(ArrayList<CheckBox> checkboxes, ArrayList<String> interesses) {
        for (CheckBox checkbox : checkboxes) {

            if (checkbox.isChecked()) {
                interesses.add(checkbox.getText().toString());

            }
        }
    }

  /** public void adicionarInteressesPreferencias() {
      for (String interesse : interessesComida) {
          perfil.addInteresseComidaP(interesse);
        }
    }**/

    public void criarPerfil() {
       if (preferencias.getStatusSessao().equals("1")) {
           perfil = preferencias.getPerfil();
       } else {
          perfil = new Perfil();
       }

        String identificador = Codificador.codificador(preferencias.getEmail());
        perfil.setId(identificador);
        perfil.salvar();

        if (interessesComida.size() > 3) {
            Toast.makeText(CategoriaComidaActivity.this, "Você não pode adicionar mais de 3 interesses!", Toast.LENGTH_SHORT).show();

        } else {
            adicionarInteresses(identificador);

        }
    }

    public void adicionarInteresses(String identificador) {


        //perfil = new Perfil();

        perfil.setInteresse1(interessesComida.get(0));
        preferencias.setInteresse1(identificador, interessesComida.get(0));
        preferencias.getPerfil().addInteresseComidaP(interessesComida.get(0));
        preferencias.getPerfil().addInteresse(interessesComida.get(0));
        preferencias.setPerfil(perfil);

        if (interessesComida.size() > 1){
            perfil.setInteresse2(interessesComida.get(1));
            preferencias.setInteresse2(identificador, interessesComida.get(1));
            preferencias.getPerfil().addInteresseComidaP(interessesComida.get(1));
            preferencias.getPerfil().addInteresse(interessesComida.get(1));
            preferencias.setPerfil(perfil);


        } else {
         }

        if (interessesComida.size() > 2){

            perfil.setInteresse3(interessesComida.get(2));
            preferencias.setInteresse3(identificador, interessesComida.get(2));
            preferencias.getPerfil().addInteresseComidaP(interessesComida.get(2));
            preferencias.getPerfil().addInteresse(interessesComida.get(2));
            preferencias.setPerfil(perfil);

        } else {
            }

        atualizarInteresseFirebase();
        Toast.makeText(CategoriaComidaActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

    }


    //updatar child de perfil no firebase
    private void atualizarInteresseFirebase() {

        referencia = FirebaseDatabase.getInstance().getReference();
        //autenticacao = preferencias.getUsuario().getAutenticacao();

        referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> postValues = new HashMap<String, Object>();

                int tam = preferencias.getPerfil().getInteressesComidaP().size();

                for (int i = 0; i < tam; i++) {
                    String chave = "interesse" + (i + 1);
                    postValues.put(chave, preferencias.getPerfil().getInteressesComidaP().get(0));
                    referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
                    preferencias.getPerfil().getInteressesComidaP().remove(0);

                }

               // referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void redirecionarPrincipal() {
        Intent abrirPrincipal = new Intent(CategoriaComidaActivity.this, PrincipalActivity.class);
        startActivity(abrirPrincipal);
    }

}