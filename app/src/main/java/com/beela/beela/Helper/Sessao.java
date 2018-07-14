package com.beela.beela.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Entidades.Perfil;

public final class Sessao {
    private static Sessao instancia;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private Context contexto;
    private String CHAVE_FOTO = "fotoUsuarioLogado";

    private Usuario usuarioAtivo;
    private com.beela.beela.Entidades.Perfil perfilAtivo;

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
    private final String CHAVE_INTERESSE_11 = "interesse11";
    private final String CHAVE_INTERESSE_12 = "interesse12";
    private final String CHAVE_INTERESSE_13 = "interesse13";
    private final String CHAVE_INTERESSE_14 = "interesse14";
    private final String CHAVE_INTERESSE_15 = "interesse15";
    private final String CHAVE_INTERESSE_16 = "interesse16";
    private final String CHAVE_INTERESSE_17 = "interesse17";
    private final String CHAVE_INTERESSE_18 = "interesse18";
    private final String CHAVE_INTERESSE_19 = "interesse19";
    private final String CHAVE_INTERESSE_20 = "interesse20";
    private final String CHAVE_INTERESSE_21 = "interesse21";
    private final String CHAVE_INTERESSE_22 = "interesse22";
    private final String CHAVE_INTERESSE_23 = "interesse23";
    private final String CHAVE_INTERESSE_24 = "interesse24";
    private final String CHAVE_INTERESSE_25 = "interesse25";
    private final String CHAVE_INTERESSE_26 = "interesse26";
    private final String CHAVE_INTERESSE_27 = "interesse27";
    private final String CHAVE_INTERESSE_28 = "interesse28";

    private final String STATUS_SESSAO = "0";

    public String getInteresse1() {
        return preferencias.getString(CHAVE_INTERESSE_1, null);
    }

    public String getInteresse2() {
        return preferencias.getString(CHAVE_INTERESSE_2, null);
    }


    public static synchronized Sessao getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new Sessao(contexto.getApplicationContext());
        }

        return instancia;
    }

    public Sessao(Context contexto) {
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


    public void salvarUrlFoto(String emailcodificado, String urlFoto) {

        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_FOTO, urlFoto);
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

    public void setInteresse11(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_10, interesse);
        editor.commit();
    }

    public void setInteresse12(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_10, interesse);
        editor.commit();
    }

    public void setInteresse13(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_13, interesse);
        editor.commit();
    }

    public void setInteresse14(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_14, interesse);
        editor.commit();
    }

    public void setInteresse15(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_15, interesse);
        editor.commit();
    }

    public void setInteresse16(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_16, interesse);
        editor.commit();
    }

    public void setInteresse17(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_17, interesse);
        editor.commit();
    }

    public void setInteresse18(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_18, interesse);
        editor.commit();
    }

    public void setInteresse19(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_19, interesse);
        editor.commit();
    }

    public void setInteresse20(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_20, interesse);
        editor.commit();
    }

    public void setInteresse21(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_21, interesse);
        editor.commit();
    }

    public void setInteresse22(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_22, interesse);
        editor.commit();
    }

    public void setInteresse23(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_23, interesse);
        editor.commit();
    }

    public void setInteresse24(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_24, interesse);
        editor.commit();
    }
    public void setInteresse25(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_25, interesse);
        editor.commit();
    }

    public void setInteresse26(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_26, interesse);
        editor.commit();
    }

    public void setInteresse27(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_27, interesse);
        editor.commit();
    }

    public void setInteresse28(String emailcodificado, String interesse) {
        editor.putString(CHAVE_IDENTIFICADOR, emailcodificado);
        editor.putString(CHAVE_INTERESSE_28, interesse);
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

    public void setUsuario(Usuario usuario) {
        this.usuarioAtivo = usuario;

    }

    public Usuario getUsuario() {
        return usuarioAtivo;
    }

    public void setPerfil(com.beela.beela.Entidades.Perfil perfil) {
        this.perfilAtivo = perfil;
    }

    public String getUrlFoto() {
        return preferencias.getString(CHAVE_FOTO, null);
    }



    public Perfil getPerfil() {
        return perfilAtivo;
    }

    public void iniciarSessao() {
        editor.putString(STATUS_SESSAO, "1");
        editor.commit();
    }

    public void finalizarSessao() {
        editor.putString(STATUS_SESSAO, "0");
        editor.commit();
    }

    public String getStatusSessao() {
        return preferencias.getString(STATUS_SESSAO, null);
    }

    public void setUrlFoto(String urlFoto) {
        this.CHAVE_FOTO = urlFoto;
    }
}

