package com.example.examen.disenoproyecto;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class ProductoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView titulo,descripcion,direccion,precio,telefono, correo, suc;
    ImageView imagen;
    RequestParams parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
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

        titulo=(TextView) findViewById(R.id.titulo);
        descripcion=(TextView) findViewById(R.id.descripcion);
        direccion=(TextView) findViewById(R.id.direccion);
        telefono=(TextView) findViewById(R.id.telefono);
        correo=(TextView) findViewById(R.id.correo);
        precio=(TextView) findViewById(R.id.precio);
        suc=(TextView) findViewById(R.id.suc);
        parametros=new RequestParams();
        imagen=(ImageView) findViewById(R.id.fotito);

        obtenerDatos("producto");

    }

    public void obtenerDatosJson(String response) {
        //MainActivity.productos.clear();
        try{
            JSONArray jsonArray=new JSONArray(response);
            for (int i=0;i< jsonArray.length();i++){

                        //jsonArray.getJSONObject(i).getInt("idproducto");
                       titulo.setText( jsonArray.getJSONObject(i).getString("nombre_pro"));
                        descripcion.setText(jsonArray.getJSONObject(i).getString("descripcion"));
                        //jsonArray.getJSONObject(i).getString("foto");
                        precio.setText(String.valueOf(jsonArray.getJSONObject(i).getDouble("precio")));
                direccion.setText(jsonArray.getJSONObject(i).getString("direccion"));
                correo.setText(jsonArray.getJSONObject(i).getString("correo"));
                telefono.setText(jsonArray.getJSONObject(i).getString("telefono"));
                suc.setText(jsonArray.getJSONObject(i).getString("nombre_su"));
                Picasso.with(this)
                        .load(jsonArray.getJSONObject(i).getString("foto"))
                        .error(R.mipmap.foto)
                        .fit()
                        .centerInside()
                        .into(imagen);
                //));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void obtenerDatos(String opcion) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url=MainActivity.direccion ;
        int idproducto=getIntent().getExtras().getInt("parametro");
        int idsucursal=getIntent().getExtras().getInt("parametro2");
        parametros.put("criterio", idproducto);
        parametros.put("criterio2", idsucursal);
        parametros.put("opcion", opcion);
        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    obtenerDatosJson(new String(responseBody));
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
        getMenuInflater().inflate(R.menu.producto, menu);
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
