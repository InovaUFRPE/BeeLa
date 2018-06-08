package com.beela.beela.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beela.beela.Entidades.PreferenciasPerfil;
import com.beela.beela.R;

import java.util.ArrayList;

public class adapterPersonalizado extends BaseAdapter {

    private ArrayList<PreferenciasPerfil> listaPerfil = new ArrayList<>();
    private final Activity act;

    public adapterPersonalizado(ArrayList<PreferenciasPerfil> perfil, Activity act) {
        this.listaPerfil = perfil;
        this.act = act;
    }

    @Override
    public int getCount() {
        return listaPerfil.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPerfil.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater()
                .inflate(R.layout.adapter_personalizado, parent, false);


        PreferenciasPerfil perfil = listaPerfil.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_nome);
        TextView descricao = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_descricao);

        nome.setText(perfil.getValor());
        descricao.setText(perfil.getChave());

        return view;
    }
}