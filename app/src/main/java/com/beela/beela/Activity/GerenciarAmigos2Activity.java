package com.beela.beela.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.AdapterPersoUsuario;
import com.beela.beela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GerenciarAmigos2Activity extends AppCompatActivity {
    private Sessao preferencias;

    private DatabaseReference databaseReference;
    private ArrayList<String> listaamigosID = new ArrayList<>();
    private ArrayList<Usuario> listaAmigosListview = new ArrayList<>();
    private AdapterPersoUsuario adapterPersoUsuario;
    private ListView listViewGrupos;
    private TextView textViewNaoTemAmigos;
    private ImageButton buttonpesquisarnome, buttonConvite, buttonAmigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_amigos2);
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        textViewNaoTemAmigos = findViewById(R.id.textViewNaotemAmigos);
        listViewGrupos = findViewById(R.id.listViewListaAmigosFinal);

        carregarAmigos();


        buttonpesquisarnome = findViewById(R.id.buttonNomePesquisa);
        buttonpesquisarnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GerenciarAmigos2Activity.this, PesquisaActivity.class);
                startActivity(intent);

            }
        });


        buttonConvite = findViewById(R.id.buttonConvites);
        buttonConvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //tela dos convites

                Intent intent = new Intent(GerenciarAmigos2Activity.this, ConvitesActivity.class);
                startActivity(intent);
            }
        });


//        buttonAmigos = findViewById(R.id.buttonAmigosLista);
//
//        buttonAmigos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(GerenciarAmigos2Activity.this,ListaDeAmigosActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//
//
//            }

    }


    private void carregarAmigos() {


        databaseReference = FirebaseDatabase.getInstance().getReference("amigo").child(Codificador.codificador(preferencias.getUsuario().getEmail()));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()) {

                    String conta = data.getValue().toString();

                    listaamigosID.add(conta);

                }

                if (listaamigosID.size()<1){

                    textViewNaoTemAmigos.setVisibility(View.VISIBLE);

                }



                for (String conta2: listaamigosID) {
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



                            adapterPersoUsuario = new AdapterPersoUsuario(listaAmigosListview,GerenciarAmigos2Activity.this);
                            listViewGrupos.setAdapter(adapterPersoUsuario);


                            listViewGrupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    Usuario s = (Usuario) parent.getAdapter().getItem(position);

                                    Intent intent = new Intent(getApplicationContext(),AmigoDetalhesActivity.class);
                                    intent.putExtra("usuarinho",s);
                                    startActivity(intent);


                                }
                            });

                            listViewGrupos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                    Usuario UsuarinhoDetalhes = (Usuario) parent.getAdapter().getItem(position);

                                    Usuario s = (Usuario) parent.getAdapter().getItem(position);


                                    excluirAmigo(s);

                                    //Intent intent = new Intent(getApplicationContext(),AmigoDetalhesActivity.class);
                                    // intent.putExtra("usuarinho",UsuarinhoDetalhes);
                                    // startActivity(intent);

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



    @Override
    public void onBackPressed() {

        finish();

    }


    private void excluirAmigo(final Usuario s) {

        String emaildoamiginho = s.getEmail();
        final String emaiamiguinhocodificado = Codificador.codificador(emaildoamiginho);

        final CharSequence[] escolha = {"Sim", "Não"};
        AlertDialog.Builder alerta = new AlertDialog.Builder(GerenciarAmigos2Activity.this);
        alerta.setItems(escolha, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String opcao = (String) escolha[i];
                if (opcao.equals(("Sim"))) {

                    databaseReference = FirebaseDatabase.getInstance().getReference("amigo").child(Codificador.codificador(preferencias.getEmail())).child(emaiamiguinhocodificado);
                    databaseReference.removeValue();
                    finish();
                    Toast.makeText(getApplicationContext(),"Amiguinho Excluido",Toast.LENGTH_LONG).show();

                } else if (opcao.equals(("Não"))) {
                    dialogInterface.cancel();
                }
            }
        });
        alerta.setTitle("Excluir Amigo ?");
        AlertDialog aviso = alerta.create();
        aviso.show();

    }





}




