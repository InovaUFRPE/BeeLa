package com.beela.beela.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.R;

import java.util.ArrayList;

public class adapterLugares extends BaseAdapter {


    private ArrayList<LugarGoogle> listaLugares = new ArrayList<>();
    private final Activity act;

    public adapterLugares(ArrayList<LugarGoogle> lugares, Activity act) {
        this.listaLugares = lugares;
        this.act = act;
    }

    @Override
    public int getCount() {
        return listaLugares.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLugares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = act.getLayoutInflater()
                .inflate(R.layout.adapterpersousuarios, parent, false);


        LugarGoogle lugares = listaLugares.get(position);

        TextView nome = (TextView)
                view.findViewById(R.id.adaptermigosNome);
        TextView descricao = (TextView)
                view.findViewById(R.id.adaptermigosSexo);
        TextView aberto = (TextView) view.findViewById(R.id.textviewaberto);

        TextView nota = (TextView)view.findViewById(R.id.textViewNota);


        TextView latlang = (TextView)view.findViewById(R.id.textViewlatlang);

        nome.setText(lugares.getNome());
        descricao.setText(lugares.getEndereco());

        if (lugares.getAbertoagora().equals(true)) {

            aberto.setText("Aberto agora: " + lugares.getAbertoagora().toString());
        }

        else {

            aberto.setText("Aberto agora: " + "sem informacao");
        }

        nota.setText(lugares.getNota().toString());

        //latlang.setText(lugares.getLocaliza().toString());



        return view;
    }
}





