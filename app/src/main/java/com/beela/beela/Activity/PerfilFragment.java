package com.beela.beela.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private Sessao preferencias;
    private android.widget.Button gerenciarInteresses;

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
