package com.beela.beela.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Usuario;

import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Preferencias;
import com.beela.beela.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CriarContaP1Activity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextData;
    private EditText editTextGenero;
    private Button buttonContinuar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta_pt1);

        editTextNome = (EditText) findViewById(R.id.editTextPedirNome);
        editTextData = (EditText) findViewById(R.id.editTextPedirData);
        editTextGenero = (EditText) findViewById(R.id.editTextPedirGenero);
        buttonContinuar = (Button) findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextNome.getText().toString().equals("") && !editTextData.getText().toString().equals("") && !editTextGenero.getText().toString().equals("")) {
                    usuario = new Usuario();
                    usuario.setNome(editTextNome.getText().toString());
                    usuario.setDataAniversario(editTextData.getText().toString());
                    usuario.setSexo(editTextGenero.getText().toString());

                    redirecionarCriarContaFinal();

                } else {
                    Toast.makeText(CriarContaP1Activity.this, "Preencha os campos de nome, data de nascimento e gênero!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void redirecionarCriarContaFinal() {
        Bundle bundle = new Bundle();
        bundle.putString("nome", usuario.getNome());
        bundle.putString("data", usuario.getDataAniversario());
        bundle.putString("genero", usuario.getSexo());

        Intent intent = new Intent(CriarContaP1Activity.this, CriarContaP2Activity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

}
