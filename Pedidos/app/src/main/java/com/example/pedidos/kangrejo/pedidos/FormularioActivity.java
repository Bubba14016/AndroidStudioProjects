package com.example.pedidos.kangrejo.pedidos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FormularioActivity extends AppCompatActivity {
    EditText nombre,telefono,direccion,descripcion;
    double total=0,lat=0,lon=0;
    String token;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        nombre=(EditText) findViewById(R.id.nombre);
        direccion=(EditText) findViewById(R.id.direccion);
        telefono=(EditText) findViewById(R.id.telefono);
        descripcion=(EditText) findViewById(R.id.descripcion);
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
    }
    public void enviar(View view){
        recuperarPreferencias();
        String nombre=this.nombre.getText().toString();
        String direccion=this.direccion.getText().toString();
        String telefono=this.telefono.getText().toString();
        String descripcion=this.descripcion.getText().toString();
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Registrando datos, por favor espere...");
        try {
            new EnviarPedido(progress, this).execute("https://pizzadoncangrejo.000webhostapp.com/registrarPedido.php?nombre=" + nombre + "&direccion=" + direccion +
                    "&token=" + token + "&descripcion=" + descripcion + "&telefono=" + telefono + "&lat=" + lat + "&lon=" + lon + "&total=" + total);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
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

    public  class EnviarPedido extends AsyncTask<String, Void, String> {
        ProgressDialog progress;
        FormularioActivity act;

        public EnviarPedido(ProgressDialog progress, FormularioActivity act) {
            this.progress = progress;
            this.act = act;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();

        }
        public void msg(String txt) {
            AlertDialog.Builder builder = new AlertDialog.Builder(act);
            builder.setIcon(R.drawable.ic_launcher_background);
            builder.setMessage(txt);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }

        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //act.token1.setText(result);
            //Toast.makeText(act.getApplicationContext(), result, Toast.LENGTH_LONG).show();
            msg(result);
            act.descripcion.setText("");
            act.telefono.setText("");
            act.nombre.setText("");
            act.direccion.setText("");
            progress.dismiss();




        }
    }

    private  String downloadUrl(String myurl) throws IOException {
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

    public  String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
