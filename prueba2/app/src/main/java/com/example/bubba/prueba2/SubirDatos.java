package com.example.bubba.prueba2;

import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Provider;

/**
 * Created by Bubba on 1/5/2018.
 */

public class SubirDatos extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("msg","Servicio creado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i( "msg", "Servicio reiniciado");
        //Codigo
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
