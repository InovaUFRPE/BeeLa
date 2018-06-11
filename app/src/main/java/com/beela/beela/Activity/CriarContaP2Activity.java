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
import com.beela.beela.Entidades.Perfil;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CriarContaP2Activity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSenha;

    private Sessao preferencias;

    private Button buttonCriarConta;

    private Usuario usuario = new Usuario();
    private Perfil perfil = new Perfil();

    private FirebaseAuth autenticacao;
    private String nome;
    private String data;
    private String genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta_pt2);

        final Bundle parametros = this.getIntent().getExtras();
        //
        // autenticacao.pass
        nome = parametros.getString("nome");
        data = parametros.getString("data");
        genero = parametros.getString("genero");

        editTextEmail = (EditText) findViewById(R.id.editTextEmailCriarConta);
        editTextSenha = (EditText) findViewById(R.id.editTextSenhaCriarConta);
        buttonCriarConta = (Button) findViewById(R.id.buttonCriarContaFinal);

        preferencias = Sessao.getInstancia(this.getApplicationContext());

        buttonCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextEmail.getText().toString().equals("") && !editTextSenha.getText().toString().equals("")) {

                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setDataAniversario(data);
                    usuario.setSexo(genero);
                    usuario.setUrlFoto("null");
                    usuario.setEmail(editTextEmail.getText().toString());
                    usuario.setSenha(editTextSenha.getText().toString());

                    criarConta();

                } else {
                    Toast.makeText(CriarContaP2Activity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criarConta() {
        autenticacao = Firebase.getAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CriarContaP2Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String identificador = Codificador.codificador(usuario.getEmail());
                    usuario.setId(identificador);
                    usuario.salvar();

                    Toast.makeText(CriarContaP2Activity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                    preferencias.salvarNome(identificador, usuario.getNome());
                    preferencias.salvarEmail(identificador, usuario.getEmail());
                    preferencias.salvarDataAniversario(identificador, usuario.getDataAniversario());
                    preferencias.salvarGenero(identificador, usuario.getSexo());
                    preferencias.salvarUrlFoto(identificador, usuario.getUrlFoto());

                    preferencias.setUsuario(usuario);
                    abrirCriarPerfil();

                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "digite uma senha mais forte!";

                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "o e-mail digitado é inválido!";
                    } catch(FirebaseAuthUserCollisionException e) {
                        erroExcecao = "o e-mail digitado pertence à outra conta!";
                    } catch(Exception e) {
                        erroExcecao = "erro ao criar conta!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CriarContaP2Activity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void abrirCriarPerfil() {
        Intent abrirPerfil = new Intent(CriarContaP2Activity.this, CriarPerfilActivity.class);
        startActivity(abrirPerfil);
        finish();
    }
}
