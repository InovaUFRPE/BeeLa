package com.beela.beela.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.beela.beela.Helper.DownloadJson;
import com.beela.beela.R;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Response;


public class TesteJson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_json);

       // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location
        // =-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=YOUR_API_KEY
        DownloadJson cu =  new DownloadJson("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location =-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyDheLyJyZoZjZdIv4faOM_hvPfutuaHxLs");
        JSONObject cu2 = new JSONObject();




        }
}