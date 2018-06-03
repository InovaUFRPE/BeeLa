package com.beela.beela.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.beela.beela.Entidades.PreferenciasPerfil;
import com.beela.beela.R;

import java.util.ArrayList;

public class ListViewPreferencias extends BaseAdapter {


    private Context ctx;
    //private PerfilAct perfil = new PerfilAct();
    private ArrayList<PreferenciasPerfil> listaPreferencias;




    public ListViewPreferencias(Context ctx, ArrayList<PreferenciasPerfil> preferenciasLista) {

     this.ctx = ctx;
     this.listaPreferencias = preferenciasLista;

    }

    @Override
    public int getCount() {
        return listaPreferencias.size();
    }

    @Override
    public Object getItem(int posicao) {
        return listaPreferencias.get(posicao);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        final PreferenciasPerfil preferenciasItem = listaPreferencias.get(posicao);

        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_list_view_perfil, null);
        TextView texto = view.findViewById(R.id.textViewListView);
        CheckBox listCheckBox6 = (CheckBox) view.findViewById(R.id.checkBox6);


        AdapterView.OnItemLongClickListener clickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        };




        listCheckBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        preferenciasItem.setSelecionado(isChecked);
                    }
                });

        texto.setText(preferenciasItem.getValor());
        return view;
    }



}
