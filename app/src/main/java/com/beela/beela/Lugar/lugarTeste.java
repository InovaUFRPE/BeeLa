package com.beela.beela.Lugar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.beela.beela.DAO.LugarDao;
import com.beela.beela.R;

import java.util.ArrayList;

public class lugarTeste extends AppCompatActivity {

    private LugarDao lugarDao = new LugarDao();
   // private ArrayList lugar = new ArrayList(lugarDao.getListaLugar());
    private TextView lugarTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_teste);


        lugarTexto = (TextView) findViewById(R.id.textView3);
        //Toast.makeText(lugarTeste.this, lugarDao.getLugar("IgrejaCarmo").getDescricao().toString(), Toast.LENGTH_SHORT).show();



    }
}
