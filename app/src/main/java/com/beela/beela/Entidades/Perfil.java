package com.beela.beela.Entidades;

import android.content.Context;

import com.beela.beela.DAO.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Perfil implements java.io.Serializable {
    private Context context;
    private String id;

    private ArrayList<String> interesses = new ArrayList<String>();
    private ArrayList<String> interessesEsportes = new ArrayList<String>();
    private ArrayList<String> interessesLugarP = new ArrayList<String>();
    private ArrayList<String> interessesMusicaP = new ArrayList<String>();
    private ArrayList<String> interessesComidaP = new ArrayList<String>();

    private String CHAVE_INTERESSE_1 = "null";
    private String CHAVE_INTERESSE_2 = "null";
    private String CHAVE_INTERESSE_3 = "null";
    private String CHAVE_INTERESSE_4 = "null";
    private String CHAVE_INTERESSE_5 = "null";
    private String CHAVE_INTERESSE_6 = "null";
    private String CHAVE_INTERESSE_7 = "null";
    private String CHAVE_INTERESSE_8 = "null";
    private String CHAVE_INTERESSE_9 = "null";
    private String CHAVE_INTERESSE_10 = "null";
    private String CHAVE_INTERESSE_11 = "null";
    private String CHAVE_INTERESSE_12 = "null";
    private String CHAVE_INTERESSE_13 = "null";
    private String CHAVE_INTERESSE_14 = "null";
    private String CHAVE_INTERESSE_15 = "null";
    private String CHAVE_INTERESSE_16 = "null";
    private String CHAVE_INTERESSE_17 = "null";
    private String CHAVE_INTERESSE_18 = "null";
    private String CHAVE_INTERESSE_19 = "null";
    private String CHAVE_INTERESSE_20 = "null";
    private String CHAVE_INTERESSE_21 = "null";
    private String CHAVE_INTERESSE_22 = "null";
    private String CHAVE_INTERESSE_23 = "null";
    private String CHAVE_INTERESSE_24 = "null";
    private String CHAVE_INTERESSE_25 = "null";
    private String CHAVE_INTERESSE_26 = "null";
    private String CHAVE_INTERESSE_27 = "null";
    private String CHAVE_INTERESSE_28 = "null";


    public Perfil() {

    }

    public void addInteresse(String interesse) {
        interesses.add(interesse);
    }

    public void addInteresseEsporte(String interesse) {
        interessesEsportes.add(interesse);
    }

    public void addInteresseLugarP(String interesse) {
        interessesLugarP.add(interesse);
    }

    public void addInteresseMusicaP(String interesse) {
        interessesMusicaP.add(interesse);
    }

    public void addInteresseComidaP(String interesse) {
        interessesComidaP.add(interesse);
    }




    public ArrayList<String> getInteressesLugarP() {
        return interessesLugarP;
    }

    public ArrayList<String> getInteressesComidaP() {
        return interessesComidaP;
    }

    public ArrayList<String> getInteressesMusicaP() {
        return interessesMusicaP;
    }

    public ArrayList<String> getInteresses() {
        return interesses;
    }

    public ArrayList<String> getInteressesEsportes() {
        return interessesEsportes;
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
        hashMapPerfil.put(CHAVE_INTERESSE_11, getInteresse11());
        hashMapPerfil.put(CHAVE_INTERESSE_12, getInteresse12());
        hashMapPerfil.put(CHAVE_INTERESSE_13, getInteresse13());
        hashMapPerfil.put(CHAVE_INTERESSE_14, getInteresse14());
        hashMapPerfil.put(CHAVE_INTERESSE_15, getInteresse15());
        hashMapPerfil.put(CHAVE_INTERESSE_16, getInteresse16());
        hashMapPerfil.put(CHAVE_INTERESSE_17, getInteresse17());
        hashMapPerfil.put(CHAVE_INTERESSE_18, getInteresse18());
        hashMapPerfil.put(CHAVE_INTERESSE_19, getInteresse19());
        hashMapPerfil.put(CHAVE_INTERESSE_20, getInteresse20());
        hashMapPerfil.put(CHAVE_INTERESSE_21, getInteresse21());
        hashMapPerfil.put(CHAVE_INTERESSE_22, getInteresse22());
        hashMapPerfil.put(CHAVE_INTERESSE_23, getInteresse23());
        hashMapPerfil.put(CHAVE_INTERESSE_24, getInteresse24());
        hashMapPerfil.put(CHAVE_INTERESSE_25, getInteresse25());
        hashMapPerfil.put(CHAVE_INTERESSE_26, getInteresse26());
        hashMapPerfil.put(CHAVE_INTERESSE_27, getInteresse27());
        hashMapPerfil.put(CHAVE_INTERESSE_28, getInteresse28());

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
    public void setInteresse11(String interesse) {
        this.CHAVE_INTERESSE_11 = interesse;
    }
    public void setInteresse12(String interesse) {
        this.CHAVE_INTERESSE_12 = interesse;
    }
    public void setInteresse13(String interesse) {
        this.CHAVE_INTERESSE_13 = interesse;
    }
    public void setInteresse14(String interesse) {
        this.CHAVE_INTERESSE_14 = interesse;
    }
    public void setInteresse15(String interesse) {
        this.CHAVE_INTERESSE_15 = interesse;
    }
    public void setInteresse16(String interesse) {
        this.CHAVE_INTERESSE_16 = interesse;
    }
    public void setInteresse17(String interesse) {
        this.CHAVE_INTERESSE_17 = interesse;
    }
    public void setInteresse18(String interesse) {
        this.CHAVE_INTERESSE_18 = interesse;
    }
    public void setInteresse19(String interesse) {
        this.CHAVE_INTERESSE_19 = interesse;
    }
    public void setInteresse20(String interesse) {
        this.CHAVE_INTERESSE_20 = interesse;
    }
    public void setInteresse21(String interesse) {
        this.CHAVE_INTERESSE_21 = interesse;
    }
    public void setInteresse22(String interesse) {
        this.CHAVE_INTERESSE_22 = interesse;
    }
    public void setInteresse23(String interesse) {
        this.CHAVE_INTERESSE_23 = interesse;
    }
    public void setInteresse24(String interesse) {
        this.CHAVE_INTERESSE_24 = interesse;
    }
    public void setInteresse25(String interesse) {
        this.CHAVE_INTERESSE_25 = interesse;
    }
    public void setInteresse26(String interesse) {
        this.CHAVE_INTERESSE_26 = interesse;
    }
    public void setInteresse27(String interesse) {
        this.CHAVE_INTERESSE_27 = interesse;
    }
    public void setInteresse28(String interesse) {
        this.CHAVE_INTERESSE_28 = interesse;
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

    public String getInteresse11() {
        return this.CHAVE_INTERESSE_11;
    }

    public String getInteresse12() {
        return this.CHAVE_INTERESSE_12;
    }
    public String getInteresse13() {
        return this.CHAVE_INTERESSE_13;
    }

    public String getInteresse14() {
        return this.CHAVE_INTERESSE_14;
    }

    public String getInteresse15() {
        return this.CHAVE_INTERESSE_15;
    }

    public String getInteresse16() {
        return this.CHAVE_INTERESSE_16;
    }

    public String getInteresse17() {
        return this.CHAVE_INTERESSE_17;
    }

    public String getInteresse18() {
        return this.CHAVE_INTERESSE_18;
    }

    public String getInteresse19() {
        return this.CHAVE_INTERESSE_19;
    }

    public String getInteresse20() {
        return this.CHAVE_INTERESSE_20;
    }

    public String getInteresse21() {
        return this.CHAVE_INTERESSE_21;
    }

    public String getInteresse22() {
        return this.CHAVE_INTERESSE_22;
    }

    public String getInteresse23() {
        return this.CHAVE_INTERESSE_23;
    }

    public String getInteresse24() {
        return this.CHAVE_INTERESSE_24;
    }
    public String getInteresse25() {
        return this.CHAVE_INTERESSE_25;
    }

    public String getInteresse26() {
        return this.CHAVE_INTERESSE_26;
    }

    public String getInteresse27() {
        return this.CHAVE_INTERESSE_27;
    }

    public String getInteresse28() {
        return this.CHAVE_INTERESSE_28;
    }






}
