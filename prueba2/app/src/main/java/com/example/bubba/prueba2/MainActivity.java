package com.example.bubba.prueba2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    EditText nombre,pass;
    Thread consulta, registro;
    public static int cantidad=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre=(EditText) findViewById(R.id.txNombre);
        pass=(EditText) findViewById(R.id.tcPass);
        //new SubirDatos2().execute(0,0,0);
        //new VerDatos().execute("https://pizzadoncangrejo.000webhostapp.com/consultar.php");
        //startService(new Intent(this, SubirDatos2.class));
        consulta=new Thread(new Runnable() {
            @Override
            public void run() {
                https://pizzadoncangrejo.000webhostapp.com/cons
                while(true) {

                    try {
                        String dato=downloadUrl("ultar.php");
                        JSONArray ja=new JSONArray(dato);
                        String cantidad=ja.getString(0);
                        if(Integer.parseInt(cantidad)!=MainActivity.cantidad) {
                            notificacion("Funciona", cantidad+"");
                            MainActivity.cantidad=Integer.parseInt(cantidad);
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        consulta.start();




    }

    public void limpiar(){
        nombre.setText("");
        pass.setText("");
    }
    public void consultar(){
        new VerDatos().execute("https://pizzadoncangrejo.000webhostapp.com/consultar.php");
    }

    public class SubirDatos extends AsyncTask<String, Void, String> {
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
            msg("Datos Almacenados");
            limpiar();
            //Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();
            // notificacion("Funciona","Se enviaron los datos");

        }

        private String downloadUrl(String myurl) throws IOException {
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

        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }

    public class SubirDatos2 extends IntentService{

        public SubirDatos2() {
            super("Notificaciones");
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {

        }
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
                if(cantidad!=MainActivity.cantidad) {
                    notificacion("Funciona", cantidad+"");
                    MainActivity.cantidad=cantidad;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }



    private String downloadUrl(String myurl) throws IOException {
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

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    public void enviar(View view){
        //String nombre1=this.nombre.getText().toString();
        //String pass1=this.pass.getText().toString();

            registro=new Thread(new Runnable() {
                @Override
                public void run() {

                    nombre.post(new Runnable() {
                        @Override
                        public void run() {
                            String nombre1=nombre.getText().toString();
                        }
                    });
                    pass.post(new Runnable() {
                        @Override
                        public void run() {
                            String pass1=pass.getText().toString();
                        }
                    });
                    //String pass1=pass.getText().toString();
                    try {
                        downloadUrl("https://pizzadoncangrejo.000webhostapp.com/?nombre=" + nombre + "&pass=" + pass);
                        //msg("Datos Almacenados");
                        //limpiar();
                    } catch (Exception e) {
                        msg(e.getMessage());
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            msg("Datos Almacenados");
                            limpiar();
                        }
                    });
                }
            });
            registro.start();
            //new SubirDatos().execute("https://pizzadoncangrejo.000webhostapp.com/?nombre=" + nombre + "&pass=" + pass);

           // Toast.makeText(getApplication(),"Insertado",Toast.LENGTH_SHORT).show();


    }
    public void msg(String txt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setMessage(txt);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    public void notificacion(String contenido,String titulo) {
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        int icono = R.mipmap.ic_launcher;
        Intent i=new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, i, 0);

        mBuilder =new NotificationCompat.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle(titulo)
                .setContentText(contenido)
                .setVibrate(new long[] {100, 250, 100, 500})
                .setAutoCancel(true);

        mNotifyMgr.notify(1, mBuilder.build());
    }
}
