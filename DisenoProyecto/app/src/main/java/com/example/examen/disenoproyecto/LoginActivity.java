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
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText correo, contra;
    RequestParams parametros;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        if (MainActivity.temp!=null){
            Intent intent= new Intent(this,InicioEmpresaActivity.class);
            startActivity(intent);
        }
        correo=(EditText) findViewById(R.id.correo);
        contra=(EditText) findViewById(R.id.contra);
        parametros=new RequestParams();
        progressDialog=new ProgressDialog(this,0);
        progressDialog.setTitle("Espere...");

    }

    public void entrar(View view){
        if (correo.getText().toString().equals("")||
                contra.getText().toString().equals("")){
            Snackbar.make(view, "Campos Vacios", Snackbar.LENGTH_SHORT).show();
        }else{
            parametros.put("correo", correo.getText().toString());
            parametros.put("contra", contra.getText().toString());
            guardarDatos("login");
        }


    }

    public void msg(String txt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setMessage(txt);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // limpiar();

            }
        }).show();
    }

    public void limpiar(){

        correo.setText("");
        contra.setText("");
        Intent intent= new Intent(this,InicioEmpresaActivity.class);
        startActivity(intent);
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

                    if(new String(responseBody).equals("Usuario No Valido")) {
                        msg(new String(responseBody));
                    }else{
                        MainActivity.temp=obtenerDatosJson(new String(responseBody));
                        msg("Sesion Iniciada");
                        limpiar();
                    }

                }
            }

            public Empresa obtenerDatosJson(String response) {
               Empresa temp=null;
                try{
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i< jsonArray.length();i++){
                        temp=new Empresa(
                                jsonArray.getJSONObject(i).getInt("idempresa"),
                                jsonArray.getJSONObject(i).getString("nombre"),
                                jsonArray.getJSONObject(i).getString("descripcion"),
                                jsonArray.getJSONObject(i).getString("correo"),
                                jsonArray.getJSONObject(i).getString("contra"),
                                jsonArray.getJSONObject(i).getString("telefono")
                        );
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                return temp;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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
        getMenuInflater().inflate(R.menu.login, menu);
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
