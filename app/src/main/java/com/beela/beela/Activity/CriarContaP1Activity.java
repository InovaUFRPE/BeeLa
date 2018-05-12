package com.beela.beela.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beela.beela.Entidades.Usuario;

import com.beela.beela.Helper.Preferencias;
import com.beela.beela.R;

public class CriarContaP1Activity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonContinuar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta_p1);

        editTextEmail = (EditText) findViewById(R.id.editTextEmailCriarConta);
        editTextSenha = (EditText) findViewById(R.id.editTextSenhaCriarConta);
        buttonContinuar = (Button) findViewById(R.id.buttonContinuar);

        buttonContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextEmail.getText().toString().equals("") && !editTextSenha.getText().toString().equals("")) {
                    usuario = new Usuario();
                    usuario.setEmail(editTextEmail.getText().toString());
                    usuario.setSenha(editTextSenha.getText().toString());

                    redirecionarCriarContaFinal();

                } else {
                    Toast.makeText(CriarContaP1Activity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void redirecionarCriarContaFinal() {
        Bundle bundle = new Bundle();
        bundle.putString("email", usuario.getEmail());
        bundle.putString("senha", usuario.getSenha());

        Intent intent = new Intent(CriarContaP1Activity.this, CriarContaP2Activity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
