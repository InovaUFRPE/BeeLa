package com.beela.beela.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.AlimentandoLugares;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;

//import com.beela.beela.Helper.Preferencias;
import com.beela.beela.Lugar.lugarTeste;
import com.beela.beela.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.beela.beela.Entidades.Perfil;

public class LoginActivity extends AppCompatActivity {

    private LoginFragment loginFrament;
    private Login2Fragment login2Fragment;

    private FirebaseAuth autenticacao;
    private DatabaseReference referencia;

    private Usuario usuario;
    private Perfil perfil;
    private Sessao preferencias;

    private EditText editTextEmailLogin;
    private EditText editTextSenhaLogin;
    private AlimentandoLugares alimentandoLugares = new AlimentandoLugares();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login2Fragment = new Login2Fragment();
        loginFrament = new LoginFragment();


        //setFragment(loginFrament);


        preferencias = Sessao.getInstancia(this.getApplicationContext());

        editTextEmailLogin = (EditText) findViewById(R.id.editTextEmailLogin2);
        editTextSenhaLogin = (EditText) findViewById(R.id.editTextSenhaLogin2);

        alimentandoLugares.gerandoLugares(this);


        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        TextView edittextCriarConta = findViewById(R.id.textViewCriarConta);




        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextEmailLogin.getText().toString().equals("") && !editTextSenhaLogin.getText().toString().equals("")) {

//                    usuario.setEmail(editTextEmailLogin.getText().toString());
//                    usuario.setSenha(editTextSenhaLogin.getText().toString());

                    usuario = new Usuario();
                    perfil = new Perfil();

                    validarLogin();

                } else {
                    Toast.makeText(LoginActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                    //setFragment(login2Fragment);

                }
            }
        });

        edittextCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecionarCriarConta();
            }
        });

    }

    private void validarLogin() {
        autenticacao = Firebase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(editTextEmailLogin.getText().toString(), editTextSenhaLogin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();

                    preferencias.iniciarSessao();
                    dadosConta();
                    //usuario.setAutenticacao(autenticacao);

                    abrirPerfil();
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário e/ou senha incorretos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void dadosConta() {
        final Query nome, email, data, genero, interesse1, interesse2, interesse3, interesse4, interesse5, interesse6, interesse7, interesse8, interesse9, interesse10;

        usuario = new Usuario();

        referencia = FirebaseDatabase.getInstance().getReference();

        nome = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("nome");
        nome.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                preferencias.salvarNome(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                usuario.setNome(preferencias.getNome());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        email = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("email");
        email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                preferencias.salvarEmail(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                usuario.setEmail(preferencias.getEmail());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        data = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("dataAniversario");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                preferencias.salvarDataAniversario(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                usuario.setDataAniversario(preferencias.getDataAniversario());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        genero = referencia.child("conta").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("sexo");
        genero.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                preferencias.salvarGenero(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                usuario.setSexo(preferencias.getGenero());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse1 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse1");
        interesse1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_1")) {
                    preferencias.setInteresse1(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse1(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse1());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse2 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse2");
        interesse2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_2")) {
                    preferencias.setInteresse2(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse2(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse2());
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse3 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse3");
        interesse3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_3")) {
                    preferencias.setInteresse3(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse3(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse3());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse4 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse4");
        interesse4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_4")) {
                    preferencias.setInteresse4(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse4(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse4());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse5 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse5");
        interesse5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_5")) {
                    preferencias.setInteresse5(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse5(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse5());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse6 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse6");
        interesse6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_6")) {
                    preferencias.setInteresse6(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse6(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse6());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse7 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse7");
        interesse7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_7")) {
                    preferencias.setInteresse7(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse7(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse7());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse8 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse8");
        interesse8.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_8")) {
                    preferencias.setInteresse8(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse8(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse8());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse9 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse9");
        interesse9.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_9")) {
                    preferencias.setInteresse9(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse9(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse9());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        interesse10 = referencia.child("perfil").child(Codificador.codificador(autenticacao.getCurrentUser().getEmail())).child("interesse10");
        interesse10.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.getValue().toString().equals("CHAVE_INTERESSE_10")) {
                    preferencias.setInteresse10(Codificador.codificador(autenticacao.getCurrentUser().getEmail()), dataSnapshot.getValue().toString());
                    perfil.setInteresse10(dataSnapshot.getValue().toString());
                    perfil.addInteresse(perfil.getInteresse10());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        preferencias.setPerfil(perfil);
        preferencias.setUsuario(usuario);

    }

    public void abrirPerfil() {
        Intent abrirPerfil = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(abrirPerfil);
    }

    public void redirecionarCriarConta() {
        Intent criarConta = new Intent(LoginActivity.this, CriarContaP1Activity.class);
        startActivity(criarConta);
    }


    private void setFragment(android.support.v4.app.Fragment fragment) {

//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.login_frame, fragment);
//        fragmentTransaction.commit();


    }




}
