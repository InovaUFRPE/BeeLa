package com.beela.beela.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.AdapterPersoUsuario;
import com.beela.beela.List.adapterPersonalizadoAmigos;
import com.beela.beela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConvitesActivity extends AppCompatActivity {

    private Sessao preferencias;
    private AdapterPersoUsuario adapterPersoUsuario;
    private ListView listViewCUU;
    private adapterPersonalizadoAmigos arrayadapterCu;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> conviteslista = new ArrayList<>();
    private ArrayList<Usuario> listaAmigosListview = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convites);
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewCUU = findViewById(R.id.listviewConvites);
        carregarConvites();



    }

    private void carregarConvites() {


        databaseReference = FirebaseDatabase.getInstance().getReference("convite").child(Codificador.codificador(preferencias.getUsuario().getEmail()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()) {

                   String conta = data.getValue().toString();

                   conviteslista.add(conta);

                }

                for (String conta2: conviteslista) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("conta").child(conta2);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario amigo = new Usuario();
                            amigo.setNome(dataSnapshot.child("nome").getValue().toString());
                            amigo.setEmail(dataSnapshot.child("email").getValue().toString());
                            amigo.setSexo(dataSnapshot.child("sexo").getValue().toString());
                            amigo.setUrlFoto(dataSnapshot.child("urlFoto").getValue().toString());

                            listaAmigosListview.add(amigo);



                            adapterPersoUsuario = new AdapterPersoUsuario(listaAmigosListview,ConvitesActivity.this);
                            //arrayadapterCu = new adapterPersonalizadoAmigos(listaAmigosListview,ConvitesActivity.this);
                            listViewCUU.setAdapter(adapterPersoUsuario);

                            listViewCUU.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                    Usuario s = (Usuario) parent.getAdapter().getItem(position);

                                    adicionarAbigo(s);

                                    return true;

                                }
                            });


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }






                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









    }

    private void adicionarAbigo(final Usuario s) {


        String emaildoamiginho = s.getEmail();
        final String emaiamiguinhocodificado = Codificador.codificador(emaildoamiginho);


        final CharSequence[] escolha = {"Sim", "Não"};
        AlertDialog.Builder alerta = new AlertDialog.Builder(ConvitesActivity.this);
        alerta.setItems(escolha, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String opcao = (String) escolha[i];
                if (opcao.equals(("Sim"))) {

                    //TODO Tratar o dadaSnapshot deveria ser o email do cara
                    databaseReference = FirebaseDatabase.getInstance().getReference("amigo").child(Codificador.codificador(preferencias.getUsuario().getEmail())).child(emaiamiguinhocodificado);
                    databaseReference.setValue(emaiamiguinhocodificado);
                    databaseReference = FirebaseDatabase.getInstance().getReference("amigo").child(emaiamiguinhocodificado).child(Codificador.codificador(preferencias.getUsuario().getEmail()));
                    databaseReference.setValue(Codificador.codificador(preferencias.getUsuario().getEmail()));
                    Toast.makeText(getApplicationContext(),"Abigo Adicionado", Toast.LENGTH_SHORT).show();




                } else if (opcao.equals(("Não"))) {
                    dialogInterface.cancel();
                }
            }
        });
        alerta.setTitle("Adicionar Amigo ?");
        AlertDialog aviso = alerta.create();
        aviso.show();




    }
}
