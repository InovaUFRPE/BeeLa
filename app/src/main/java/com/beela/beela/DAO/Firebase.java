package com.beela.beela.DAO;

import com.beela.beela.Entidades.Usuario;
import com.beela.beela.Helper.Codificador;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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



    public static void SalvarInteressesUsuario(ArrayList<String> lista , Usuario usuario){
            for(String interesse: lista) {

                referencia = FirebaseDatabase.getInstance().getReference("perfil").child(Codificador.
                        codificador(usuario.getEmail())).child(interesse);
                referencia.setValue(interesse);

            }



    }




}
