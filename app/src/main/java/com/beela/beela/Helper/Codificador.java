package com.beela.beela.Helper;

import android.util.Base64;

public class Codificador {

    public static String codificador(String textoDecodificado) {
        return Base64.encodeToString(textoDecodificado.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");

    }

    public static String decodificador(String textoCodificado) {
        return new String (Base64.decode(textoCodificado, Base64.DEFAULT));

    }

}
