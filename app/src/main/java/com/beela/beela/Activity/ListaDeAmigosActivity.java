package com.beela.beela.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaDeAmigosActivity extends AppCompatActivity {


    private Sessao preferencias;
    private AdapterPersoUsuario adapterPersoUsuario;
    private ListView listViewCUU;
    private adapterPersonalizadoAmigos arrayadapterCu;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> listaamigosID = new ArrayList<>();
    private ArrayList<Usuario> listaAmigosListview = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_amigos);

        preferencias = Sessao.getInstancia(this.getApplicationContext());
        listViewCUU = findViewById(R.id.listViewCriarGrupoLista);
        carregarAmigos();
       // ListaExcluir();

    }

     /**   private void ListaExcluir(){

            listViewCUU.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    //Usuario s = (Usuario) parent.getAdapter().getItem(position);
                   // adicionarAbigo(s);

                    final CharSequence[] escolha = {"Sim", "Não"};
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaDeAmigosActivity.this);
                    alerta.setItems(escolha, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String opcao = (String) escolha[i];
                            if (opcao.equals(("Sim"))) {

                                Toast.makeText(ListaDeAmigosActivity.this,"apagar",Toast.LENGTH_LONG).show();

                            } else if (opcao.equals(("Não"))) {
                                dialogInterface.cancel();
                            }
                        }
                    });
                    alerta.setTitle("Excluir Amigo ?");
                    AlertDialog aviso = alerta.create();
                    aviso.show();
                    return true;

                }
            });

        }**/

    private void adicionarAbigo(final Usuario s) {


        String emaildoamiginho = s.getEmail();
        final String emaiamiguinhocodificado = Codificador.codificador(emaildoamiginho);


        final CharSequence[] escolha = {"Sim", "Não"};
        AlertDialog.Builder alerta = new AlertDialog.Builder(ListaDeAmigosActivity.this);
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

        private void carregarAmigos() {


            databaseReference = FirebaseDatabase.getInstance().getReference("amigo").child(Codificador.codificador(preferencias.getUsuario().getEmail()));

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot data: dataSnapshot.getChildren()) {

                        String conta = data.getValue().toString();

                        listaamigosID.add(conta);

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



                                adapterPersoUsuario = new AdapterPersoUsuario(listaAmigosListview,ListaDeAmigosActivity.this);
                                //arrayadapterCu = new adapterPersonalizadoAmigos(listaAmigosListview,ConvitesActivity.this);
                                listViewCUU.setAdapter(adapterPersoUsuario);




                                listViewCUU.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                        Usuario UsuarinhoDetalhes = (Usuario) parent.getAdapter().getItem(position);

                                        Usuario s = (Usuario) parent.getAdapter().getItem(position);
                                        adicionarAbigo(s);

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




    }

