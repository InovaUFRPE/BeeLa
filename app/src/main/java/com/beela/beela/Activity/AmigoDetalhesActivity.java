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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AmigoDetalhesActivity extends AppCompatActivity {
    private Sessao preferencias;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private TextView nomeAmigo,emailAmigo,sexoAmigo,dataNiverAmigo;
    private ImageView fotoAmigo;
    private Button  buttonAdc;
    private String emailAbigocod;
    private String aux = "null";
    private ArrayList<DataSnapshot> amigos = new ArrayList<DataSnapshot>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo_detalhes);

        Intent intent = getIntent();
        final Usuario abigo = (Usuario) intent.getSerializableExtra("usuarinho");

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

        emailAbigocod = Codificador.codificador(abigo.getEmail());

        buttonAdc = findViewById(R.id.buttonAdcAmiguinho);
        buttonAdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //criar notificacao de convide no email do cara
                //TODO Criar objeto do tipo convite;

                databaseReference = FirebaseDatabase.getInstance().getReference("convite")
                        .child(emailAbigocod).child(Codificador.codificador(preferencias.getUsuario().getEmail()));
                databaseReference.setValue(Codificador.codificador(preferencias.getEmail()));
                //Toast.makeText(AmigoDetalhesActivity.this,"Convite Enviado",Toast.LENGTH_LONG).show();


                final String identificador = Codificador.codificador(preferencias.getEmail());
                databaseReference = FirebaseDatabase.getInstance().getReference("amigo").child(identificador);
                databaseReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            amigos.add(data);

                        }
                        for (DataSnapshot amigos1 : amigos) {
                            final String emailAmg = Codificador.decodificador(amigos1.getKey());
                            if (emailAmg.equals(abigo.getEmail())){
                                    aux = "Vocês já são amigos";
                                    Toast.makeText(AmigoDetalhesActivity.this, aux, Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(AmigoDetalhesActivity.this, "1 if da tela "+abigo.getEmail(), Toast.LENGTH_LONG).show();
                                    //Toast.makeText(AmigoDetalhesActivity.this, "1 if do fb " +emailAmg, Toast.LENGTH_LONG).show();

                                }else {

                            }

                        }
                        if (abigo.getEmail().equals(preferencias.getEmail())){
                            aux = "erro";

                            Toast.makeText(AmigoDetalhesActivity.this, "Você não pode enviar para si próprio", Toast.LENGTH_LONG).show();

                        }
                        if (aux.equals("null")){
                            databaseReference = FirebaseDatabase.getInstance().getReference("convite").child(emailAbigocod).child(Codificador.codificador(preferencias.getUsuario().getEmail()));
                            databaseReference.setValue(Codificador.codificador(preferencias.getEmail()));
                            Toast.makeText(AmigoDetalhesActivity.this, "Convite Enviado", Toast.LENGTH_LONG).show();
                            finish();}


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


    }}
