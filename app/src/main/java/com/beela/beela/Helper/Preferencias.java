package com.beela.beela.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.beela.beela.Entidades.Usuario;

public class Preferencias {
    private static Preferencias instancia;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private Context contexto;

    private int MODE = 0;

    private String NOME_ARQUIVO = "BeeLa.preferencias";
    private final String CHAVE_IDENTIFICADOR = "identificarUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";
    private final String CHAVE_EMAIL = "emailUsuarioLogado";
    private final String CHAVE_DATAANIVERSARIO = "dataAniversarioUsuarioLogado";
    private final String CHAVE_GENERO = "generoUsuarioLogado";

    public static synchronized Preferencias getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new Preferencias(contexto.getApplicationContext());
        }

        return instancia;
    }

    public Preferencias (Context contexto) {
        this.contexto = contexto;
        preferencias = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);

        editor = preferencias.edit();
        editor.apply();
    }

    public void salvarNome(String emailcodificado, String nome) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_NOME, nome);
        editor.commit();
    }

    public void salvarEmail(String emailcodificado, String email) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_EMAIL, email);
        editor.commit();
    }
    public void salvarDataAniversario(String emailcodificado, String data) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_DATAANIVERSARIO, data);
        editor.commit();
    }
    public void salvarGenero(String emailcodificado, String genero) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_GENERO, genero);
        editor.commit();
    }

    public void salvarDados(String emailcodificado, String nome, String email, String data, String genero) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_EMAIL, email);
        editor.putString(CHAVE_DATAANIVERSARIO, data);
        editor.putString(CHAVE_GENERO, genero);
        editor.commit();
    }

    public String getIdentificador() {
        return preferencias.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNome() {
        return preferencias.getString(CHAVE_NOME, null);
    }

    public String getEmail() {
        return preferencias.getString(CHAVE_EMAIL, null);
    }

    public String getDataAniversario() {
        return preferencias.getString(CHAVE_DATAANIVERSARIO, null);
    }

    public String getGenero() {
        return preferencias.getString(CHAVE_GENERO, null);
    }

}
