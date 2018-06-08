package com.beela.beela.Entidades;

import android.content.Context;

import com.beela.beela.DAO.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {
    private Context contexto;
    private String id;
    private String nome;
    private String email;
    private String dataaniversario;
    private String sexo;
    private String senha;
    private FirebaseAuth autenticacao;

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    private String urlFoto;

    public Usuario() {
    }

    public void salvar() {
        DatabaseReference referencia = Firebase.getFirebase();
        referencia.child("conta").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapConta = new HashMap<>();

        hashMapConta.put("id", getId());
        hashMapConta.put("nome", getNome());
        hashMapConta.put("email", getEmail());
        hashMapConta.put("dataaniversario", getDataAniversario());
        hashMapConta.put("sexo", getSexo());
        hashMapConta.put("senha", getSenha());

        return hashMapConta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataAniversario() {
        return dataaniversario;
    }

    public void setDataAniversario(String dataaniversario) {
        this.dataaniversario = dataaniversario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setAutenticacao(FirebaseAuth autentica) {
        this.autenticacao = autentica;
    }

    public FirebaseAuth getAutenticacao() {
        return autenticacao;
    }

}
