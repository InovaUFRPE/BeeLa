package com.beela.beela.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterAmigosCriarGrupo extends BaseAdapter {


    private Context ctx;
    //private PerfilAct perfil = new PerfilAct();
    private ArrayList<Usuario> listaUsuarios;




    public adapterAmigosCriarGrupo(Context ctx, ArrayList<Usuario> preferenciasLista) {

        this.ctx = ctx;
        this.listaUsuarios = preferenciasLista;

    }

    @Override
    public int getCount() {
        return listaUsuarios.size();
    }

    @Override
    public Object getItem(int posicao) {
        return listaUsuarios.get(posicao);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        final Usuario usuario = listaUsuarios.get(posicao);

        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_list_view_perfil, null);
        TextView texto = view.findViewById(R.id.adapterGruppNome);
        ImageView foto = view.findViewById(R.id.imageViewGrupoFoto);
        CheckBox listCheckBox6 = (CheckBox) view.findViewById(R.id.checkBoxDoInferno);



        listCheckBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                usuario.setSelecionado(isChecked);
            }
        });


        String url = usuario.getUrlFoto();

        Picasso.get().load(url).into(foto);

        texto.setText(usuario.getNome());


        return view;



    }



}