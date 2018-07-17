package com.beela.beela.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.DAO.Firebase;
import com.beela.beela.Entidades.Lugar;
import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.beela.beela.Helper.Sessao;
import com.beela.beela.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrincipalFragment extends Fragment {


    private Sessao preferencias;
    private ImageView fotoUsuario;
    private TextView nome, perfilAtual;
    private ImageButton imageButtonAmigos;
    private ImageButton imageButtonLugares;
    private ImageButton imageButtonGrupos;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth autenticacao;
    public PrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        imageButtonLugares = view.findViewById(R.id.imageButtonLugares);
        imageButtonAmigos = view.findViewById(R.id.imageButtonAmigos);
        imageButtonGrupos = view.findViewById(R.id.imageButtonGrupos);
        preferencias = Sessao.getInstancia(getContext());
        nome = view.findViewById(R.id.fragTextViewNome);
        nome.setText(preferencias.getNome());
        fotoUsuario = view.findViewById(R.id.imageViewPrincipalFrag);
        setarFotoUsuario();
        setarImageButton();

        return view;

    }




        public void setarImageButton(){


        imageButtonLugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LugarActivity.class);
                startActivity(intent);
            }
        });


        imageButtonAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),GerenciarAmigos2Activity.class);
                startActivity(intent);

            }
        });



        imageButtonGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),GruposActivity.class);
                startActivity(intent);


            }
        });

        // Inflate the layout for this fragment
    }

    private void setarFotoUsuario()  {
        //TODO Fazer um dada Snapshot
        String url = preferencias.getUrlFoto();

   //     Glide.with(getContext()).load(url).into(fotoUsuario);
        Picasso.get().load(url).into(fotoUsuario);

    }


}
