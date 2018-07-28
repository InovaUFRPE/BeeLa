package com.beela.beela.Entidades;

public class CletoLino {

    public CletoLino (String preferencia,int pontos){

        this.pontos = pontos;
        this.preferencia = preferencia;
    }


    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    private String preferencia;
    private int pontos;


}
