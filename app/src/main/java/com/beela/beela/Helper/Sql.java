package com.beela.beela.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sql extends SQLiteOpenHelper {

    private static final String NOME_DO_BD = "Beela.db";
    private static final int VERSAO = 1;
    private static final String TABELA_LUGAR = "lugar";
    private static final String COLUNA_ID = "id_lugar";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_DESCRICAO = "descricao";
    private static final String COLUNA_CATEGORIA = "categoria";;


    public Sql(Context contexto) {
        super(contexto , NOME_DO_BD, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {

        bd.execSQL("create table lugar (" +
                "id_lugar integer primary key autoincrement, nome text not null , descricao text not null, categoria text not null);");
    }

    public void onUpgrade (SQLiteDatabase bd,int i, int i1){


    }

}
