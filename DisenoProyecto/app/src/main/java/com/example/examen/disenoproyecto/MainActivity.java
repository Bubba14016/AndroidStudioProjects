package com.example.examen.disenoproyecto;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener{
    public static ArrayList<Empresa> empresas;
    public static ArrayList<Producto> productos;
    public static ArrayList<Sucursal> sucursales;
    public static Empresa temp;
    public static int idproducto;
    public static int idsucursal;
    ArrayAdapter<Producto> adaptador;

    ListView lista;
    ProgressDialog progressDialog;
    EditText filtro;
    RequestParams parametros;
    public static String direccion="http://pizzadoncangrejo.000webhostapp.com/conexion.php";


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        lista=(ListView) findViewById(R.id.lista);
        empresas=new ArrayList<>();
        productos=new ArrayList<>();
        sucursales= new ArrayList<>();
        parametros=new RequestParams();
        progressDialog=new ProgressDialog(this,0);
        progressDialog.setTitle("Cargando...");
        filtro=(EditText) findViewById(R.id.filtro);
        filtro.clearFocus();
        lista.setOnItemClickListener(this);
        if (MainActivity.temp!=null){
            Intent intent=new Intent(this,InicioEmpresaActivity.class);
            startActivity(intent);
        }
        //obtenerDatos("","consultarprod");
        llenar("");
        llenar("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    public void llenar(String criterio){
        progressDialog.show();
        obtenerDatos(criterio,"consultarprod");
        //MainActivity.baseSQLite.consultarE();
        adaptador=new AdaptadorProducto(this,R.layout.activity_custom_productos, MainActivity.productos);
        lista.setAdapter(adaptador);
        progressDialog.dismiss();
    }

    public void buscar(View view){

        llenar(filtro.getText().toString());
    }

    private void obtenerDatos(String criterio, String opcion) {

        AsyncHttpClient client = new AsyncHttpClient();

        String url= direccion;

        parametros.put("criterio", criterio);
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
               msg("error: "+statusCode+ error.getMessage());
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

    public void obtenerDatosJson(String response) {
        MainActivity.productos.clear();
        try{
            JSONArray jsonArray=new JSONArray(response);
            for (int i=0;i< jsonArray.length();i++){
                MainActivity.productos.add(new Producto(
                        jsonArray.getJSONObject(i).getInt("idproducto"),
                        jsonArray.getJSONObject(i).getString("nombre_pro"),
                        jsonArray.getJSONObject(i).getString("descripcion"),
                        jsonArray.getJSONObject(i).getString("foto"),
                        jsonArray.getJSONObject(i).getDouble("precio"),
                        jsonArray.getJSONObject(i).getInt("id_sucursal")
                ));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

            getMenuInflater().inflate(R.menu.main, menu);

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity.idproducto=((Producto)parent.getItemAtPosition(position)).getIdproducto();
        MainActivity.idsucursal=((Producto)parent.getItemAtPosition(position)).getIdsucursal();
       // msg(""+idproducto);

        Intent intent=new Intent(this,ProductoActivity.class);
        intent.putExtra("parametro", MainActivity.idproducto);
        intent.putExtra("parametro2", MainActivity.idsucursal);
        startActivity(intent);
    }
}
