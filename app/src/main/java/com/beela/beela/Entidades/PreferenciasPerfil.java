package com.beela.beela.Entidades;

import java.util.ArrayList;

public class PreferenciasPerfil {

    private ArrayList<PreferenciasPerfil> lista = new ArrayList<>();

    public  ArrayList<PreferenciasPerfil> getLista() {
        return lista;
    }

    public void setLista(ArrayList<PreferenciasPerfil> lista) {
        this.lista = lista;
    }

    private int id;
    private String valor;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    private String chave;


    public String getValor() {
        return valor;
    }

    public void setValor(String nome) {
        this.valor = nome;
    }

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    private Boolean selecionado = false;



}
