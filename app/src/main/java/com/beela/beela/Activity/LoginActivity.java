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
import com.beela.beela.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmailLogin;
    private EditText editTextSenhaLogin;
    private Button buttonLogin;
    private Button buttonCriarConta;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailLogin = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextSenhaLogin = (EditText) findViewById(R.id.editTextSenhaLogin);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonCriarConta = (Button) findViewById(R.id.buttonCriarConta);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextEmailLogin.getText().toString().equals("") && !editTextSenhaLogin.getText().toString().equals("")) {

                    usuario = new Usuario();
                    usuario.setEmail(editTextEmailLogin.getText().toString());
                    usuario.setSenha(editTextSenhaLogin.getText().toString());

                    validarLogin();

                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarCriarConta();
            }
        });

    }

    private void validarLogin() {
        autenticacao = Firebase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    abrirPerfil();
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário e/ou senha incorretos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirPerfil() {
        Intent abrirPerfil = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(abrirPerfil);
    }

    public void redirecionarCriarConta() {
        Intent criarConta = new Intent(LoginActivity.this, CriarContaP1Activity.class);
        startActivity(criarConta);
    }

}
