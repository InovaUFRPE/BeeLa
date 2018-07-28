package com.beela.beela.Entidades;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class LugarGoogle implements Serializable, Comparable<LugarGoogle> {
    private int id;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    private Double lat;
    private Double lng;


    private String idGoogle;
    private String nome;
    private Boolean abertoagora = false;
    private Double nota;
    private String endereco;
    //private LatLng localiza;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getNotaGeral() {
        return notaGeral;
    }

    public void setNotaGeral(Double notaGeral) {
        this.notaGeral = notaGeral;
    }

    public Double getNotaProvisoria() {
        return notaProvisoria;
    }

    public void setNotaProvisoria(Double notaProvisoria) {
        this.notaProvisoria = notaProvisoria;
    }

    private Double notaGeral = 0.0;
    private Double notaProvisoria = 0.0;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAbertoagora() {
        return abertoagora;
    }

    public void setAbertoagora(Boolean abertoagora) {
        this.abertoagora = abertoagora;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

//    public LatLng getLocaliza() {
//        return localiza;
//    }



//    public void setLocaliza(LatLng localiza) {
//        this.localiza = localiza;
//    }


    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }


    @Override
    public int compareTo(@NonNull LugarGoogle o) {
        if (this.nota > o.getNota()) {
            return -1;
        }
        if (this.nota < o.getNota()) {
            return 1;
        }
        return 0;
    }


}

