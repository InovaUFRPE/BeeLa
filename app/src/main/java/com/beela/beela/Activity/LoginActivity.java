package com.beela.beela.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private DatabaseReference referencia;
    private Usuario usuario;

    private EditText editTextEmailLogin;
    private EditText editTextSenhaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailLogin = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextSenhaLogin = (EditText) findViewById(R.id.editTextSenhaLogin);
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        Button buttonCriarConta = (Button) findViewById(R.id.buttonCriarConta);

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
                    dadosConta();
                    abrirPerfil();
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário e/ou senha incorretos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void dadosConta() {
        Query nome;
        Query email;
        Query data;
        Query genero;
        Query interesse1;

        referencia = FirebaseDatabase.getInstance().getReference();

        nome = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("nome");
        nome.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarNome(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        email = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("email");
        email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarEmail(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("dataAniversario");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarDataAniversario(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        genero = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("sexo");
        genero.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarGenero(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse1 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse1");
        interesse1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.setInteresse1(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
