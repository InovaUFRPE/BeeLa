package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AmigoDetalhesActivity extends AppCompatActivity {
    private Sessao preferencias;

    private DatabaseReference databaseReference;
    private TextView nomeAmigo,emailAmigo,sexoAmigo,dataNiverAmigo;
    private ImageView fotoAmigo;
    private Button buttonExcluir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo_detalhes);

        Intent intent = getIntent();
        final Usuario abigo = (Usuario)  intent.getSerializableExtra("amiguinho");

        preferencias = Sessao.getInstancia(this.getApplicationContext());

        nomeAmigo = findViewById(R.id.textViewAmigoNome);
        nomeAmigo.setText(abigo.getNome());

        emailAmigo = findViewById(R.id.textViewAmigoEmail);
        sexoAmigo = findViewById(R.id.textViewAmigoSexo);
        dataNiverAmigo = findViewById(R.id.textViewAmigoData);

        emailAmigo.setText(abigo.getEmail());
        sexoAmigo.setText(abigo.getSexo());
        dataNiverAmigo.setText(abigo.getDataAniversario());


        fotoAmigo = findViewById(R.id.imageViewFotoAmigo);

        Picasso.get().load(abigo.getUrlFoto()).into(fotoAmigo);


        buttonExcluir = findViewById(R.id.buttonExluirAmigo);

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference("amigos").child(Codificador.codificador(preferencias.getEmail())).child(Codificador.codificador(abigo.getEmail()));
                databaseReference.removeValue();
                finish();
                Toast.makeText(getApplicationContext(),"Amiguinho Excluido",Toast.LENGTH_LONG).show();


            }
        });


    }









}
