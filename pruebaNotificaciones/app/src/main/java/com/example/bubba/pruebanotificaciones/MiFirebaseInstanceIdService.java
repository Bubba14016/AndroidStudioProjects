package com.example.bubba.pruebanotificaciones;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bubba on 2/5/2018.
 */

public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService {
    SharedPreferences preferences;
    @SuppressLint("WrongThread")
    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String token=FirebaseInstanceId.getInstance().getToken();
        Log.d("TOOOOOOOOKen", token);
        //MainActivity.token=token;
        llenar(token);
        //new MainActivity.EnviarToken().execute("https://pizzadoncangrejo.000webhostapp.com/?nombre=yo&pass=mero&token="+token);
    }

    public void llenar(String token){
        try {

            SharedPreferences.Editor editor = preferences.edit();
            Set arregloPersonas = new HashSet();

            //String ventas=gson.toJson(personas);

            editor.putString("token", token);
            editor.commit();
        }catch (Exception e){
            Log.d("MSG",e.getMessage());
        }
    }
}
