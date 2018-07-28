package com.beela.beela.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.beela.beela.Entidades.Perfil;
import com.beela.beela.Entidades.PreferenciasPerfil;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.List.ListViewPreferencias;
import com.beela.beela.List.adapterPersonalizado;
import com.beela.beela.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private Sessao preferencias;
    private android.widget.Button gerenciarInteresses;

    private adapterPersonalizado arrayadapterListaPreferencias;

    private DatabaseReference referencia;
    private FirebaseDatabase firebaseDatabase;
    private Perfil perfil;
    private DatabaseReference databaseReference;
    String identificador;
    private ArrayList<PreferenciasPerfil> preferenciasLista = new ArrayList<PreferenciasPerfil>();
    ListViewPreferencias adapterPreferencias ;
    private Button adicInteresse;
    private HashMap<String, String> prefetenciasDicionario = new HashMap<String, String>();
    private ListView listView;
    private TextView quantidadeInteresses;
    private Button excluir;
    private static PreferenciasPerfil preferenciasPerfil = new PreferenciasPerfil();


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        gerenciarInteresses = view.findViewById(R.id.buttonGerenciarInteresses);

        gerenciarInteresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(getActivity(), GerenciarInteressesActivity.class);
                startActivity(intent);

            }
        });







        return view;
    }

}
