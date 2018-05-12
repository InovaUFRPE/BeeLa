package com.beela.beela.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {

    private static DatabaseReference referencia;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase() {
        if (referencia == null) {
            referencia = FirebaseDatabase.getInstance().getReference();
        }

        return referencia;
    }

    public static FirebaseAuth getAutenticacao() {
        if (autenticacao == null) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;

    }

}
