package com.beela.beela.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.beela.beela.Entidades.Lugar;
import com.beela.beela.Helper.Sql;
import com.beela.beela.R;

import java.util.ArrayList;

public class LugarDao  {

    private SQLiteDatabase bd;

    public SQLiteDatabase getLer(Context context) {
        Sql auxBd = new Sql(context);
        bd = auxBd.getReadableDatabase();
        return bd;
    }

    public SQLiteDatabase getEscrever(Context context) {
        Sql auxBd = new Sql(context);
        bd = auxBd.getWritableDatabase();
        return bd;
    }

    public void inserir(Lugar lugar) {
        ContentValues valores = new ContentValues();
        valores.put("nome", lugar.getNome());
        valores.put("descricao", lugar.getDescricao());
        valores.put("categoria", lugar.getCategoria());
        bd.insert("lugar", null, valores);
        bd.close();
    }


}
