package com.beela.beela.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.beela.beela.Entidades.Usuario;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferencias;
    private String NOME_ARQUIVO = "BeeLa.preferencias";
    private Usuario usuario;
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificarUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    public Preferencias (Context contexto) {
        this.contexto = contexto;
        preferencias = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);

        editor = preferencias.edit();
        editor.apply();
    }

    public void salvarPreferencias(String idUsuario, String nomeUsuario) {
        editor.putString(CHAVE_IDENTIFICADOR, idUsuario);
        editor.putString(CHAVE_NOME, nomeUsuario);
        editor.commit();
    }

    public String getIdentificador() {
        return preferencias.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNome() {
        return preferencias.getString(CHAVE_NOME, null);
    }

    public void setUsuario(Usuario usuarioRecebido) {
        usuario = usuarioRecebido;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
