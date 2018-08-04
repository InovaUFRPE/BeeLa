package com.beela.beela.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

public class LugaresGoogleActivity extends AppCompatActivity

        implements OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    int PLACE_PICKER_REQUEST = 1;
    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);


        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);

                LugarGoogle lugarGoogle = new LugarGoogle();

                //verificar
                lugarGoogle.setNome(place.getName().toString());
                lugarGoogle.setIdGoogle(place.getId());
                lugarGoogle.setEndereco(place.getAddress().toString());

                double lat = place.getLatLng().latitude;
                double lng = place.getLatLng().longitude;
                lugarGoogle.setAbertoagora(0);

                lugarGoogle.setLat(lat);
                lugarGoogle.setLng(lng);

                lugarGoogle.setNota(Double.valueOf(place.getRating()));


                Intent intent = new Intent(LugaresGoogleActivity.this,LugarDetalhesActivity.class);
                intent.putExtra("lugarzinho",lugarGoogle);

                startActivity(intent);
                finish();

            }
        }
    }




    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override
    public void onBackPressed() {

        finish();

    }

}
