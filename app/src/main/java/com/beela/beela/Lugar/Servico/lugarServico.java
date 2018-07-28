package com.beela.beela.Lugar.Servico;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

public class lugarServico {


    private Intent mapa(double destinolatitude, double destinolongitude) {
        Intent googleMaps = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + "" + "&daddr=" + destinolatitude + "," + destinolongitude));
        googleMaps.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
        return (googleMaps);
    }

    public Intent getMapa(double l, double lg) {
        return mapa(l, lg);
    }




}


