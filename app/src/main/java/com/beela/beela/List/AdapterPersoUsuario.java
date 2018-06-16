package com.beela.beela.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPersoUsuario extends BaseAdapter {

        private ArrayList<Usuario> listaUsuario = new ArrayList<>();
        private final Activity act;

        public AdapterPersoUsuario(ArrayList<Usuario> usuario, Activity act) {
            this.listaUsuario = usuario;
            this.act = act;
        }

        @Override
        public int getCount() {
            return listaUsuario.size();
        }

        @Override
        public Object getItem(int position) {
            return listaUsuario.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = act.getLayoutInflater()
                    .inflate(R.layout.adapterpersousuarios, parent, false);


            Usuario u = listaUsuario.get(position);

            TextView nome = (TextView)
                    view.findViewById(R.id.adaptermigosNome);

            TextView descricao = (TextView)
                    view.findViewById(R.id.adaptermigosSexo);

            ImageView foto = view.findViewById(R.id.imageViewFotoUsuario);

            String url = u.getUrlFoto();


            Picasso.get().load(url).into(foto);


            nome.setText(u.getNome());
            descricao.setText(u.getSexo());




            return view;
        }




}
