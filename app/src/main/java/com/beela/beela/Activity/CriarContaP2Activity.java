package com.beela.beela.Activity;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private TextView nomeAppConta;
    public ArrayList<Usuario> amigos = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta_pt2);

        final Bundle parametros = this.getIntent().getExtras();
        nomeAppConta = findViewById(R.id.textViewNomeAppContap2);
        Typeface fonte = Typeface.createFromAsset(getAssets(), "fonts/aqua.ttf");
        nomeAppConta.setTypeface(fonte);


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

                    if ( editTextSenha.getText().toString().length() > 8)
                        Toast.makeText(CriarContaP2Activity.this, "Digite senha até 8 digitos! ", Toast.LENGTH_SHORT).show();
                    else{

                        criarConta();}

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
                   // formarAmigos();
                    Toast.makeText(CriarContaP2Activity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                    preferencias.salvarNome(identificador, usuario.getNome());
                    preferencias.salvarEmail(identificador, usuario.getEmail());
                    preferencias.salvarDataAniversario(identificador, usuario.getDataAniversario());
                    preferencias.salvarGenero(identificador, usuario.getSexo());
                    preferencias.salvarUrlFoto(identificador, usuario.getUrlFoto());

                    preferencias.setUsuario(usuario);
                    preferencias.iniciarSessao();
                    abrirCriarPerfil();

                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
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

   /** public void formarAmigos() {

        databaseReference = FirebaseDatabase.getInstance().getReference("conta");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Usuario usuario = dataSnapshot1.getValue(Usuario.class);
                    amigos.add(usuario);

                }
                Toast.makeText(CriarContaP2Activity.this, "Lista: " + amigos, Toast.LENGTH_SHORT).show();
            }


                @Override
                public void onCancelled (DatabaseError databaseError){


            }
        });

    }**/



            public void abrirCriarPerfil() {
        Intent abrirPerfil = new Intent(CriarContaP2Activity.this,LoginActivity.class);
        startActivity(abrirPerfil);
        finish();
    }
}
