package com.beela.beela.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Entidades.Perfil;
import com.beela.beela.Entidades.PreferenciasPerfil;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.ListViewPreferencias;
import com.beela.beela.List.adapterPersonalizado;
import com.beela.beela.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GerenciarInteressesActivity extends AppCompatActivity {

    private adapterPersonalizado arrayadapterListaPreferencias;
    private Sessao preferencias;
    private DatabaseReference referencia;
    private Perfil perfil;
    private DatabaseReference databaseReference;
    private ArrayList<PreferenciasPerfil> preferenciasLista = new ArrayList<PreferenciasPerfil>();
    private Button adicInteresse;
    private ListView listView;
    private TextView quantidadeInteresses;
    private Button excluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_interesses);
        listView = findViewById(R.id.listviewPreferencias);
       // excluir = findViewById(R.id.button);
        adicInteresse = findViewById(R.id.buttonAdicionarInteresses);
        quantidadeInteresses = findViewById(R.id.quantidadeInteresses);
        chamarFirebase();
        ExibirPreferencias();

       /** excluir.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference("perfil").child(Codificador.codificador(preferencias.getUsuario().getEmail()));


                for(PreferenciasPerfil prefs:preferenciasLista){

                    databaseReference.child(prefs.getChave()).setValue("null");



               }

                Toast.makeText(getApplicationContext(), "Interesses Exluidos", Toast.LENGTH_SHORT).show();

                preferenciasLista.clear();
                arrayadapterListaPreferencias.notifyDataSetChanged();
                quantidadeInteresses.setText(preferenciasLista.size() +"/29");
                ExibirPreferencias();




            }

        });**/




        adicInteresse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirCategoriaPerfil = new Intent(GerenciarInteressesActivity.this, CriarPerfilActivity.class);
                startActivity(abrirCategoriaPerfil);
            }
        });
    }

    private void excluirTodos(final PreferenciasPerfil prefs) {




    }


    private void chamarFirebase() {
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        String identificador = Codificador.codificador(preferencias.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference("perfil").child(identificador);
    }

    private void ExibirPreferencias() {
        preferenciasLista.clear();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (!data.getValue().toString().equals("null") && !data.getKey().equals("id")) {

                        PreferenciasPerfil p = new PreferenciasPerfil();
                        p.setValor(data.getValue().toString());
                        p.setChave(data.getKey().toString());
                        //   if (!preferencias.getInteresse1().equals("null"));
                        preferenciasLista.add(p);

                        //  } else {

                    }
                }

                atualizarlistview();

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        PreferenciasPerfil preferenciaExcluir = (PreferenciasPerfil) parent.getAdapter().getItem(position);

                        exluirInteresse(preferenciaExcluir);

                        return true;
                    }
                });
            }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void atualizarlistview() {



        quantidadeInteresses.setText( preferenciasLista.size() + "/28");
        arrayadapterListaPreferencias = new adapterPersonalizado(preferenciasLista, GerenciarInteressesActivity.this);
        listView.setAdapter(arrayadapterListaPreferencias);



    }

    private void excluirPreferencias() {
        try {
            if (listView != null) {

                Adapter adapter = (Adapter) listView.getAdapter();
                for (int i = 0; i < adapter.getCount(); i++) {

                    PreferenciasPerfil a = (PreferenciasPerfil) adapter.getItem(i);
                    PreferenciasPerfil p = a;
                    if (p.getSelecionado()) {
                        Toast.makeText(GerenciarInteressesActivity.this, "excluido preferencia" ,Toast.LENGTH_SHORT).show();


                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void exluirInteresse(final PreferenciasPerfil interesse) {
        final CharSequence[] escolha = {"Sim", "Não"};
        AlertDialog.Builder alerta = new AlertDialog.Builder(GerenciarInteressesActivity.this);
        alerta.setItems(escolha, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String opcao = (String) escolha[i];
                if (opcao.equals(("Sim"))) {

                    //exluir do banco

                    databaseReference.child(interesse.getChave()).setValue("null");
                    Toast.makeText(getApplicationContext(), "Interesse " + interesse.getValor() + " Exluido", Toast.LENGTH_SHORT).show();


                    preferenciasLista.remove(interesse);

                    //excluirPreferencias();

                    arrayadapterListaPreferencias.notifyDataSetChanged();

                    quantidadeInteresses.setText(preferenciasLista.size() +"/28");

                    quantidadeInteresses.setText(preferenciasLista.size() +"/25");

                    ExibirPreferencias();


                } else if (opcao.equals(("Não"))) {
                    dialogInterface.cancel();
                }
            }
        });
        alerta.setTitle(("Excluir Preferencia" + " " + interesse.getValor() + "?"));
        AlertDialog aviso = alerta.create();
        aviso.show();
    }

    @Override
    public void onBackPressed() {

        finish();

    }


}