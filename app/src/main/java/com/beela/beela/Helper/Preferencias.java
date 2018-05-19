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

    private final String CHAVE_INTERESSE_1 = "interesse1";
    private final String CHAVE_INTERESSE_2 = "interesse2";
    private final String CHAVE_INTERESSE_3 = "interesse3";
    private final String CHAVE_INTERESSE_4 = "interesse4";
    private final String CHAVE_INTERESSE_5 = "interesse5";
    private final String CHAVE_INTERESSE_6 = "interesse6";
    private final String CHAVE_INTERESSE_7 = "interesse7";
    private final String CHAVE_INTERESSE_8 = "interesse8";
    private final String CHAVE_INTERESSE_9 = "interesse9";
    private final String CHAVE_INTERESSE_10 = "interesse10";

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

    public void setInteresse1(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_1, interesse);
        editor.commit();
    }

    public void setInteresse2(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_2, interesse);
        editor.commit();
    }

    public void setInteresse3(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_3, interesse);
        editor.commit();
    }

    public void setInteresse4(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_4, interesse);
        editor.commit();
    }

    public void setInteresse5(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_5, interesse);
        editor.commit();
    }

    public void setInteresse6(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_6, interesse);
        editor.commit();
    }

    public void setInteresse7(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_7, interesse);
        editor.commit();
    }

    public void setInteresse8(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_8, interesse);
        editor.commit();
    }

    public void setInteresse9(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_9, interesse);
        editor.commit();
    }

    public void setInteresse10(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_10, interesse);
        editor.commit();
    }

    public String getInteresse1() {
        return preferencias.getString(CHAVE_INTERESSE_1, null);
    }

    public String getInteresse2() {
        return preferencias.getString(CHAVE_INTERESSE_2, null);
    }

    public String getInteresse3() {
        return preferencias.getString(CHAVE_INTERESSE_3, null);
    }

    public String getInteresse4() {
        return preferencias.getString(CHAVE_INTERESSE_4, null);
    }

    public String getInteresse5() {
        return preferencias.getString(CHAVE_INTERESSE_5, null);
    }

    public String getInteresse6() {
        return preferencias.getString(CHAVE_INTERESSE_6, null);
    }

    public String getInteresse7() {
        return preferencias.getString(CHAVE_INTERESSE_7, null);
    }

    public String getInteresse8() {
        return preferencias.getString(CHAVE_INTERESSE_8, null);
    }

    public String getInteresse9() {
        return preferencias.getString(CHAVE_INTERESSE_9, null);
    }

    public String getInteresse10() {
        return preferencias.getString(CHAVE_INTERESSE_10, null);
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
