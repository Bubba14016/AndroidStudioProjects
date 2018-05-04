package com.example.bubba.pruebanotificaciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public String token="";
    SharedPreferences preferences;
    EditText nombre,pass;

    TextView token1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        token1=(TextView) findViewById(R.id.token);
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        //token1.setText(token);
        //Log.d("MSG", token);
        nombre=(EditText) findViewById(R.id.nombre);
        pass=(EditText) findViewById(R.id.pass);
        recuperarPreferencias();
        //new EnviarToken().execute("https://pizzadoncangrejo.000webhostapp.com/?nombre=yo&pass=mero&token="+token);


    }

    public void meterToken(String token){
        this.token1.setText(token);
    }

    public void recuperarPreferencias(){
        try {
            String token;
            token = preferences.getString("token",null);

            if (token != null) {
                //meterToken(token);
                this.token=token;

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage()+"",Toast.LENGTH_SHORT).show();
        }
    }

    public void enviar(View view){
        recuperarPreferencias();
        String nombre=this.nombre.getText().toString();
        String pass=this.pass.getText().toString();
        new EnviarToken().execute("https://pizzadoncangrejo.000webhostapp.com/?nombre="+nombre+"&pass="+pass+"&token="+token);
    }

    private class VerDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();
            try {
                JSONArray ja=new JSONArray(result);
                int cantidad=ja.getInt(0);
                if(cantidad==0){

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public static class EnviarToken extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();



        }
    }

    private static String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
