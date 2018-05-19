package com.beela.beela.Entidades;

import android.content.Context;

import com.beela.beela.DAO.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Perfil {
    private Context context;
    private String id;

    private String CHAVE_INTERESSE_1 = "CHAVE_INTERESSE_1";
    private String CHAVE_INTERESSE_2 = "CHAVE_INTERESSE_2";
    private String CHAVE_INTERESSE_3 = "CHAVE_INTERESSE_3";
    private String CHAVE_INTERESSE_4 = "CHAVE_INTERESSE_4";
    private String CHAVE_INTERESSE_5 = "CHAVE_INTERESSE_5";
    private String CHAVE_INTERESSE_6 = "CHAVE_INTERESSE_6";
    private String CHAVE_INTERESSE_7 = "CHAVE_INTERESSE_7";
    private String CHAVE_INTERESSE_8 = "CHAVE_INTERESSE_8";
    private String CHAVE_INTERESSE_9 = "CHAVE_INTERESSE_9";
    private String CHAVE_INTERESSE_10 = "CHAVE_INTERESSE_10";

    public Perfil() {

    }

    public void salvar() {
        DatabaseReference referencia = Firebase.getFirebase();
        referencia.child("perfil").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapPerfil = new HashMap<>();

        hashMapPerfil.put("id", getId());
        hashMapPerfil.put(CHAVE_INTERESSE_1, getInteresse1());
        hashMapPerfil.put(CHAVE_INTERESSE_2, getInteresse2());
        hashMapPerfil.put(CHAVE_INTERESSE_3, getInteresse3());
        hashMapPerfil.put(CHAVE_INTERESSE_4, getInteresse4());
        hashMapPerfil.put(CHAVE_INTERESSE_5, getInteresse5());
        hashMapPerfil.put(CHAVE_INTERESSE_6, getInteresse6());
        hashMapPerfil.put(CHAVE_INTERESSE_7, getInteresse7());
        hashMapPerfil.put(CHAVE_INTERESSE_8, getInteresse8());
        hashMapPerfil.put(CHAVE_INTERESSE_9, getInteresse9());
        hashMapPerfil.put(CHAVE_INTERESSE_10, getInteresse10());

        return hashMapPerfil;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setInteresse1(String interesse) {
        this.CHAVE_INTERESSE_1 = interesse;
    }

    public void setInteresse2(String interesse) {
        this.CHAVE_INTERESSE_2 = interesse;
    }

    public void setInteresse3(String interesse) {
        this.CHAVE_INTERESSE_3 = interesse;
    }

    public void setInteresse4(String interesse) {
        this.CHAVE_INTERESSE_4 = interesse;
    }

    public void setInteresse5(String interesse) {
        this.CHAVE_INTERESSE_5 = interesse;
    }

    public void setInteresse6(String interesse) {
        this.CHAVE_INTERESSE_6 = interesse;
    }

    public void setInteresse7(String interesse) {
        this.CHAVE_INTERESSE_7 = interesse;
    }

    public void setInteresse8(String interesse) {
        this.CHAVE_INTERESSE_8 = interesse;
    }

    public void setInteresse9(String interesse) {
        this.CHAVE_INTERESSE_9 = interesse;
    }

    public void setInteresse10(String interesse) {
        this.CHAVE_INTERESSE_10 = interesse;
    }

    public String getInteresse1() {
        return this.CHAVE_INTERESSE_1;
    }

    public String getInteresse2() {
        return this.CHAVE_INTERESSE_2;
    }

    public String getInteresse3() {
        return this.CHAVE_INTERESSE_3;
    }

    public String getInteresse4() {
        return this.CHAVE_INTERESSE_4;
    }

    public String getInteresse5() {
        return this.CHAVE_INTERESSE_5;
    }

    public String getInteresse6() {
        return this.CHAVE_INTERESSE_6;
    }

    public String getInteresse7() {
        return this.CHAVE_INTERESSE_7;
    }

    public String getInteresse8() {
        return this.CHAVE_INTERESSE_8;
    }

    public String getInteresse9() {
        return this.CHAVE_INTERESSE_9;
    }

    public String getInteresse10() {
        return this.CHAVE_INTERESSE_10;
    }
}
