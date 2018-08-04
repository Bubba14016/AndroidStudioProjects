package com.example.examen.disenoproyecto;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RegistrarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText nombre,descripcion,telefono,correo,contra,contra2;
    RequestParams parametros;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nombre=(EditText) findViewById(R.id.nombre);
        descripcion=(EditText) findViewById(R.id.descripcion);
        telefono=(EditText) findViewById(R.id.telefono);
        correo=(EditText) findViewById(R.id.email);
        contra=(EditText) findViewById(R.id.contra);
        contra2=(EditText) findViewById(R.id.contra2);
        parametros=new RequestParams();
        progressDialog=new ProgressDialog(this,0);
        progressDialog.setTitle("Guardando...");


    }



    private void obtenerDatos(String criterio, String opcion) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url=MainActivity.direccion ;

        parametros.put("criterio", criterio);
        parametros.put("opcion", opcion);
        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                   // prueba.setText(new String(responseBody));
                    obtenerDatosJson(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }



    public void obtenerDatosJson(String response) {
        MainActivity.sucursales.clear();
        try{
            JSONArray jsonArray=new JSONArray(response);
            for (int i=0;i< jsonArray.length();i++){
                MainActivity.sucursales.add(new Sucursal(
                        jsonArray.getJSONObject(i).getInt("id_sucursal"),
                        jsonArray.getJSONObject(i).getString("nombre_su"),
                        jsonArray.getJSONObject(i).getString("direccion")

                ));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void guardar(View view){
        try {
            if (nombre.getText().toString().equals("") ||
                    descripcion.getText().toString().equals("") ||
                    telefono.getText().toString().equals("") ||
                    correo.getText().toString().equals("") ||
                    contra.getText().toString().equals("") ||
                    contra2.getText().toString().equals("")) {
                Snackbar.make(view, "Campos Vacios", Snackbar.LENGTH_SHORT).show();
                nombre.requestFocus();
            } else if(contra.getText().toString().equals(contra2.getText().toString())) {
                parametros.put("nombre", nombre.getText().toString());
                parametros.put("descripcion", descripcion.getText().toString());
                parametros.put("telefono", telefono.getText().toString());
                parametros.put("correo", correo.getText().toString());
                parametros.put("contra", contra.getText().toString());
                guardarDatos("guardaremp");
                limpiar();
            }else{
                Snackbar.make(view, "Contrasenas no coinciden", Snackbar.LENGTH_SHORT).show();
                contra.requestFocus();
            }
        }catch (Exception e){
            msg(e.getMessage());
        }
    }

    public void limpiar(){
        nombre.setText("");
        descripcion.setText("");
        telefono.setText("");
        correo.setText("");
        contra.setText("");
        contra2.setText("");
        nombre.requestFocus();
    }

    public void guardarDatos(String opcion) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url=MainActivity.direccion ;

        parametros.put("opcion", opcion);

        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    progressDialog.dismiss();
                    msg(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_regitro) {
            Intent intent= new Intent(this,RegistrarActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_login) {
            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
