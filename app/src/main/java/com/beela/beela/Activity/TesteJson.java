package com.beela.beela.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.HttpsConnection;
import android.net.http.RequestQueue;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beela.beela.Entidades.Lugar;
import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Helper.DownloadJson;
import com.beela.beela.Helper.HttpConnections;
import com.beela.beela.List.adapterLugares;
import com.beela.beela.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TesteJson extends AppCompatActivity {

    private adapterLugares adapterLugares;
    private ListView listViewLugares;
    private com.android.volley.RequestQueue mQueue;
    private ArrayList<LugarGoogle> lugares = new ArrayList<>();
    private LocationManager locationManager;
    private GoogleMap mMap;
    private static final int REQUEST_FINE_LOCATION = 1;
    private Double latt;
    private Double langg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_json);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listViewLugares = findViewById(R.id.listViewLugaresJson);
        updateLocation();
        mQueue = Volley.newRequestQueue(this);
        jsonParse();
        imprimirListView();



    }

    private void jsonParse() {

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-8.189369,%20-34.955066&radius=15000&" +
                "type=restaurant&key=AIzaSyCs4g8KU95xizS77El9HbxhTZcBfiaJk7A";

        final JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.
                Method.GET, url, null, new
                com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for(int i = 0 ;i<jsonArray.length();i++){
                                LugarGoogle lugarGoogle = new LugarGoogle();
                                JSONObject lugaJson = jsonArray.getJSONObject(i);
                                Toast.makeText(getApplicationContext(),lugaJson.getInt("name"),Toast.LENGTH_LONG).show();
                                lugarGoogle.setNome(lugaJson.getString("name"));
                                lugarGoogle.setEndereco(lugaJson.getString("vicinity"));







                                lugarGoogle.setNota(lugaJson.getDouble("rating"));

                                double lat = lugaJson.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                double lng = lugaJson.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                                //lugarGoogle.setLocaliza(new LatLng(lat, lng));
                                lugares.add(lugarGoogle);


                            }

                            //adapterLugares = new adapterLugares(lugares,TesteJson.this);
                            //listViewLugares.setAdapter(adapterLugares);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });

    mQueue.add(request);

    }


    public void updateLocation() {
        checkPermission();
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED /*&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
                return;
            }
            goToCurrentLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mMap.setMyLocationEnabled(true);
            goToCurrentLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    public void goToCurrentLocation(Location location){
        if (location!=null){
            latt = location.getLatitude();
            langg = location.getLongitude();
            LatLng latLng = new LatLng(latt, langg);
        }
    }




    private void checkPermission() {
        boolean permissionFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
        boolean permissionCoarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;

        if (permissionFineLocation && permissionCoarseLocation) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }
    }

    public void imprimirListView(){

        adapterLugares = new adapterLugares(lugares,TesteJson.this);
        listViewLugares.setAdapter(adapterLugares);





    }



}
