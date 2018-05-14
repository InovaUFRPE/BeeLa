package com.beela.beela.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.Calendar;

public class CriarContaP2Activity extends AppCompatActivity {

    private static final String TAG = "CriarContap2Activity";
    private EditText editTextNome;
    private EditText editTextData;
    private EditText editTextGenero;
    private Button buttonCriarConta;
    private Usuario usuario;
    private String email;
    private String senha;
    private FirebaseAuth autenticacao;


    private TextView displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta_p2);
        datadata();






        final Bundle parametros = this.getIntent().getExtras();

        email = parametros.getString("email");
        senha = parametros.getString("senha");

        editTextNome = (EditText) findViewById(R.id.editTextPedirNome);
        editTextData = (EditText) findViewById(R.id.editTextPedirData);
        editTextGenero = (EditText) findViewById(R.id.editTextPedirGenero);
        buttonCriarConta = (Button) findViewById(R.id.buttonCriarContaFinal);

        buttonCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextNome.getText().toString().equals("") && !editTextData.getText().toString().equals("") && !editTextGenero.getText().toString().equals("")) {
                    usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    usuario.setNome(editTextNome.getText().toString());
                    usuario.setDataAniversario(editTextData.getText().toString());
                    usuario.setSexo(editTextGenero.getText().toString());

                    criarConta();

                } else {
                    Toast.makeText(CriarContaP2Activity.this, "Preencha os campos de nome, data de nascimento e gênero!", Toast.LENGTH_SHORT).show();
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
                    FirebaseUser novaConta = task.getResult().getUser();
                    usuario.setId(identificador);
                    usuario.salvar();
                    Toast.makeText(CriarContaP2Activity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                    Preferencias preferencias = new Preferencias(CriarContaP2Activity.this);
                    preferencias.salvarPreferencias(identificador, usuario.getNome());

                    abrirPerfil();

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

    public void abrirPerfil() {
        Intent abrirPerfil = new Intent(CriarContaP2Activity.this, PrincipalActivity.class);
        startActivity(abrirPerfil);
        finish();
    }


    public void datadata(){
        displayDate = (TextView) findViewById(R.id.textViewPedirData);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int ano = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(
                        CriarContaP2Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        ano, mes, dia);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();


            }





        });


        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                Log.d(TAG, "dataSet: date:" + dayOfMonth + "/" + month + "/" + year  );
            }




        };



    }



}
