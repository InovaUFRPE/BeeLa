package com.beela.beela.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.Entidades.CletoLino;
import com.beela.beela.Entidades.Grupo;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Cletus;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class GrupoDetalhesActivity extends AppCompatActivity {
    private Sessao preferencias;
    private DatabaseReference databaseReference;
    private Grupo grupo;
    private ArrayList<String> preferenciasdoUsuarioX;
    private Button SairComOGrupo;
    private Usuario uu = new Usuario();
    private TextView nomeGrupo;
    private ListView listViewIntegrantes;
    private Map<String, Integer> cletosDic = new HashMap<>();
    private ArrayList<Usuario> integrantes = new ArrayList<>();
    private AdapterPersoUsuario adapterPersoUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_detalhes);
        preferencias = Sessao.getInstancia(this.getApplicationContext());
        Intent intent = getIntent();
        grupo = (Grupo) intent.getSerializableExtra("grupinho");
        SairComOGrupo = findViewById(R.id.SairComOGrupo);
        nomeGrupo = findViewById(R.id.TextViewNomeGrupoDeta);
        nomeGrupo.setText(grupo.getNome());
        listViewIntegrantes = findViewById(R.id.ListViewIntegrantes);
        interseccaoDosUsuarios();
        inteseccaoMagica();
        imprimirIntegrantes();
        SairComOGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SlopeCletoTeste.class);
                uu.setAutenticacao(null);
                intent.putExtra("usuarioMagico", uu);
                startActivity(intent);


            }
        });


    }

    private void interseccaoDosUsuarios() {
        final ArrayList<ArrayList> interesses = new ArrayList<>();

        for (final String idUsuarios : grupo.getIntegrantes()) {

            preferenciasdoUsuarioX = new ArrayList<String>();
            databaseReference = FirebaseDatabase.getInstance().getReference("perfil").child(idUsuarios);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                int a = 0;

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    preferenciasdoUsuarioX.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (!data.getKey().toString().equals("id")) {
                            if (!data.getValue().toString().equals("null")) {
                                preferenciasdoUsuarioX.add(data.getValue().toString());


                            }
                        }
                    }

                    databaseReference = FirebaseDatabase.getInstance().getReference("interessesGrupo").
                            child("grupo").child(grupo.getId()).child(idUsuarios);

                    databaseReference.setValue(preferenciasdoUsuarioX);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }


    private void inteseccaoMagica() {

        final ArrayList<ArrayList<String>> lista = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("interessesGrupo").
                child("grupo").child(grupo.getId());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> array = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    ArrayList<String> a = (ArrayList<String>) dataSnapshot1.getValue();
                    lista.add(a);
                }


                ArrayList<String> finalmente = aMagica(lista);

                uu.setEmail(grupo.getId());
                databaseReference = FirebaseDatabase.getInstance().getReference("perfil").
                        child(Codificador.codificador(uu.getEmail()));
                databaseReference.setValue(finalmente);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<String> aMagica(ArrayList<ArrayList<String>> lista) {
        ArrayList<String> listafinal = new ArrayList<>();

        for (int x = 0; x < lista.size(); x++) {
            for (String y : lista.get(x)) {
                try {

                    boolean b = false;
                    for (int a = 0; a < lista.size(); a++) {
                        if (lista.get(a).contains(y)) {

                            b = true;

                        } else {

                            b = false;
                            break;
                        }
                    }

                    if (b == true) {

                        listafinal.add(y);
                    }


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Erro", Toast.LENGTH_LONG).show();

                }

            }


            if (listafinal.size() == 0) {

                maiorPreferencia(lista);

            }


        }

        return listafinal;
    }

    private String maiorPreferencia(ArrayList<ArrayList<String>> alista) {

        for (ArrayList<String> listaprefs : alista) {
            for (String s : listaprefs) {

                if (!cletosDic.containsKey(s)) {

                    cletosDic.put(s, 1);
                } else {

                    cletosDic.put(s, cletosDic.get(s) + 1);

                }
            }
        }

        int maior = 0;
        String maiorString = new String();
        for (Map.Entry<String, Integer> entrada : cletosDic.entrySet()) {

            if (entrada.getValue() > maior) {
                maiorString = entrada.getKey();
                maior = entrada.getValue();

            }

        }

        if (maior == 0 || maiorString.equals(null)) {

            for (Map.Entry<String, Integer> entrada : cletosDic.entrySet()) {

                if (entrada.getValue() < maior) {
                    maiorString = entrada.getKey();
                    maior = entrada.getValue();

                }

            }


        }

        return maiorString;
    }


    private void imprimirIntegrantes() {

        for (String conta2 : grupo.getIntegrantes()) {
            databaseReference = FirebaseDatabase.getInstance().getReference("conta").child(conta2);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Usuario amigo = new Usuario();
                    amigo.setNome(dataSnapshot.child("nome").getValue().toString());
                    amigo.setEmail(dataSnapshot.child("email").getValue().toString());
                    amigo.setSexo(dataSnapshot.child("sexo").getValue().toString());
                    amigo.setUrlFoto(dataSnapshot.child("urlFoto").getValue().toString());

                    integrantes.add(amigo);


                    adapterPersoUsuario = new AdapterPersoUsuario(integrantes, GrupoDetalhesActivity.this);
                    listViewIntegrantes.setAdapter(adapterPersoUsuario);


                    listViewIntegrantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Usuario s = (Usuario) parent.getAdapter().getItem(position);

                            Intent intent = new Intent(getApplicationContext(), AmigoDetalhesActivity.class);
                            intent.putExtra("usuarinho", s);
                            startActivity(intent);


                        }
                    });

                    listViewIntegrantes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            Usuario UsuarinhoDetalhes = (Usuario) parent.getAdapter().getItem(position);

                            Usuario s = (Usuario) parent.getAdapter().getItem(position);


                            //excluirAmigo(s);

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

}