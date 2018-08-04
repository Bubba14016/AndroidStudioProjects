package com.example.examen.disenoproyecto;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.Response;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class NuevoProdActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private Bitmap bitmap=null;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="http://serverandrofast.webcindario.com/upload.php";

    private String KEY_IMAGEN = "foto";
    private String KEY_NOMBRE = "nombre";
    EditText nombre,descripcion,precio;

    ArrayList<Sucursal> sucursals;

    ImageView imagen;
    String path;
    public static int idproducto;
    Button botonCargar;

    RequestParams parametros, params2;
    ProgressDialog progressDialog;

    ListView lista;
    ArrayAdapter<Sucursal> adapter;
    TextView prueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_prod);
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
        sucursals=new ArrayList<>();
        imagen=(ImageView) findViewById(R.id.imageView2);
        botonCargar=(Button) findViewById(R.id.button2);
        nombre=(EditText) findViewById(R.id.nombre);
        descripcion=(EditText) findViewById(R.id.descripcion);
        precio=(EditText) findViewById(R.id.precio);
        parametros=new RequestParams();
        params2=new RequestParams();
        progressDialog=new ProgressDialog(this,0);
        progressDialog.setTitle("Guardando...");
        lista=(ListView) findViewById(R.id.lista);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setOnItemClickListener(this);
        //prueba=(TextView) findViewById(R.id.prueba);
        //adapter=new ArrayAdapter<Sucursal>(this,android.R.layout.simple_list_item_multiple_choice, MainActivity.sucursales);
        llenar(String.valueOf(MainActivity.temp.getId()));
        llenar(String.valueOf(MainActivity.temp.getId()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent=new Intent(this, InicioEmpresaActivity.class);
            startActivity(intent);
        }
    }

    public void llenar(String criterio){
        //progressDialog.show();
        obtenerDatos(criterio,"consultarsucs");
        //MainActivity.baseSQLite.consultarE();
        adapter=new ArrayAdapter<Sucursal>(this,android.R.layout.simple_list_item_checked, MainActivity.sucursales);
        lista.setAdapter(adapter);

        //progressDialog.dismiss();
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
                     //prueba.setText(new String(responseBody));
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
                        jsonArray.getJSONObject(i).getInt("idempresa"),
                        jsonArray.getJSONObject(i).getString("nombre_su"),
                        jsonArray.getJSONObject(i).getString("direccion")

                ));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void obtenerId(String opcion) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url=MainActivity.direccion ;

        //parametros.put("criterio", criterio);
        parametros.put("opcion", opcion);
        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    //prueba.setText(new String(responseBody));
                    obtenerIdJson(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }



    public void obtenerIdJson(String response) {

        try{
            JSONArray jsonArray=new JSONArray(response);
            for (int i=0;i< jsonArray.length();i++){

                        idproducto=jsonArray.getJSONObject(i).getInt("idproducto");



            }
           // msg(idproducto+"");
            //nombre.setText(idproducto+"");
            params2.put("idproducto", idproducto);
            for (int i=0; i<sucursals.size();i++) {
                params2.put("idsucursal", sucursals.get(i).getIdsucursal());
                guardarDetalles("guardardetalle");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void guardar(View view){
        if (nombre.getText().toString().equals("")||
                descripcion.getText().toString().equals("")||
                precio.getText().toString().equals("")||
                sucursals.size()==0||
                bitmap==null){
            Snackbar.make(view,"Campos vacios",Snackbar.LENGTH_SHORT).show();
        }else{
            progressDialog.show();
            parametros.put("nombre", nombre.getText().toString());
            parametros.put("descripcion", descripcion.getText().toString());
            parametros.put("precio", precio.getText().toString());
            parametros.put(KEY_IMAGEN, getStringImagen(bitmap));
            guardarDatos("guardarprod");

           // obtenerId("consultaridprod");





           progressDialog.dismiss();

        }
    }

    public void msg(String txt) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setMessage(txt);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            limpiar();
            }
        }).show();
    }

    public void limpiar(){
        nombre.setText("");
        descripcion.setText("");
        precio.setText("");

        nombre.requestFocus();
    }

    public void guardarDetalles(String opcion) {
       // progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url=MainActivity.direccion ;

        params2.put("opcion", opcion);

        client.post(url, params2, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    //progressDialog.dismiss();
                    //msg(new String(responseBody));
                    //
                    msg("Datos Guardados");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
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
                   // progressDialog.dismiss();
                    //msg(new String(responseBody));
                    obtenerId("consultaridprod");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nuevo_prod, menu);
        return true;
    }

    public void foto(View view){
        showFileChooser();


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

       if (id == R.id.nav_empresa) {
            Intent intent= new Intent(this,InicioEmpresaActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            MainActivity.temp=null;
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                imagen.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!sucursals.contains(parent.getItemAtPosition(position))) {
            sucursals.add((Sucursal) parent.getItemAtPosition(position));
        }else {
            sucursals.remove(position);
        }

      //  nombre.setText(sucursals.size()+"");

    }



}
