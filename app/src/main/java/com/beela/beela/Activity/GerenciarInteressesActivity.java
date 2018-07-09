package com.beela.beela.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class GerenciarInteressesActivity extends AppCompatActivity {

    private adapterPersonalizado adapterPersonalizado;



    private Sessao preferencias;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String identificador;
    private ArrayList<PreferenciasPerfil> preferenciasLista = new ArrayList<PreferenciasPerfil>();
    ListViewPreferencias adapterPreferencias ;
    private HashMap<String, String> prefetenciasDicionario = new HashMap<String, String>();
    private ListView listView;
    private TextView quantidadeInteresses;
    private static PreferenciasPerfil preferenciasPerfil = new PreferenciasPerfil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_interesses);

        listView =  findViewById(R.id.listviewPreferencias);
        quantidadeInteresses = findViewById(R.id.quantidadeInteresses);
        chamarFirebase();
        ExibirPreferencias();


    }

    private void chamarFirebase() {
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        String identificador = Codificador.codificador(preferencias.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference("perfil").child(identificador);
    }

    private void ExibirPreferencias() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (!data.getValue().toString().equals("null") && !data.getKey().equals("id")) {

                        PreferenciasPerfil p = new PreferenciasPerfil();
                        p.setValor(data.getValue().toString());
                        p.setChave(data.getKey().toString());
                        preferenciasLista.add(p);

                    } else {

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



        quantidadeInteresses.setText( preferenciasLista.size() + "/10");
        adapterPersonalizado = new adapterPersonalizado(preferenciasLista, GerenciarInteressesActivity.this);
        listView.setAdapter(adapterPersonalizado);



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
                    adapterPersonalizado.notifyDataSetChanged();
                    quantidadeInteresses.setText(preferenciasLista.size() +"/10");


                } else if (opcao.equals(("Não"))) {
                    dialogInterface.cancel();
                }
            }
        });
        alerta.setTitle(("Excluir Preferencia" + " " + interesse.getValor() + "?"));
        AlertDialog aviso = alerta.create();
        aviso.show();
    }




}
