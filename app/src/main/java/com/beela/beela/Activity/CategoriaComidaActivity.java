package com.beela.beela.Activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.beela.beela.Entidades.Perfil;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Preferencias;
import com.beela.beela.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private DatabaseReference referencia;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_comida);

        preferencias = Preferencias.getInstancia(this.getApplicationContext());

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

    public void verificaCheckboxes(ArrayList<CheckBox> checkboxes, ArrayList<String> arrayInteresses) {
        for (CheckBox checkbox : checkboxes) {

            if (checkbox.isChecked()) {
                Toast.makeText(CategoriaComidaActivity.this, checkbox.getText().toString(), Toast.LENGTH_SHORT).show();
                arrayInteresses.add(checkbox.getText().toString());

            }
        }
    }

    public void adicionarInteressesPreferencias() {
        for (String interesse : interessesComida) {
            perfil.addInteresse(interesse);
        }
    }

    public void criarPerfil() {
        perfil = new Perfil();

        String id = Codificador.codificador(preferencias.getUsuario().getEmail());
        perfil.setId(id);
        perfil.salvar();

        adicionarInteressesPreferencias();
        atualizarInteresseFirebase();

        preferencias.setPerfil(perfil);

        //updatar child de perfil no firebase

        Toast.makeText(CategoriaComidaActivity.this, "Perfil criado com sucesso!", Toast.LENGTH_SHORT).show();

    }

    private void atualizarInteresseFirebase() {
        referencia = FirebaseDatabase.getInstance().getReference();
        //autenticacao = preferencias.getUsuario().getAutenticacao();

        referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> postValues = new HashMap<String, Object>();

                /**for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    postValues.put(snapshot.getKey(), snapshot.getValue());
                }
                 */

                for (int i = 0; i < preferencias.getPerfil().getInteresses().size(); i++) {
                    String chave = "interesse" + (i + 1);
                    postValues.put(chave, preferencias.getPerfil().getInteresses().get(i));
                }

                referencia.child("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail())).updateChildren(postValues);
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