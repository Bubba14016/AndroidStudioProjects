package com.example.examen.examenapi23;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EstudianteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText carnet,nombre;
    Button btnAlmacenar;
    ListView lista;
    ArrayAdapter<Estudiante> adaptador;
    private static int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!MainActivity.logueado){
            finish();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }

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

        carnet=(EditText) findViewById(R.id.carnet1);
        nombre=(EditText) findViewById(R.id.nombre);
        lista=(ListView) findViewById(R.id.lista);
        btnAlmacenar=(Button) findViewById(R.id.btnAlmacenar);
        llenar();
    }

    public void llenar(){
        MainActivity.baseSQLite.consultarE();
        adaptador=new Adaptador(this,R.layout.activity_custom_estudiante, MainActivity.listaEstudiantes);
        lista.setAdapter(adaptador);
    }

    public void guardar(View view){
        try {
        if (carnet.getText().toString().isEmpty()||nombre.getText().toString().isEmpty()){
            Snackbar.make(view,"Complete los campos",Snackbar.LENGTH_SHORT).show();
        }else {
            //InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            //inputMethodManager.hideSoftInputFromWindow(carnet.getWindowToken(),0);
            //inputMethodManager.hideSoftInputFromWindow(nombre.getWindowToken(),0);
            if (btnAlmacenar.getText().equals("Almacenar")){
                try {

                    MainActivity.baseSQLite.insertarE(new Estudiante(
                            carnet.getText().toString(), nombre.getText().toString(), "0"
                    ));
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(),Snackbar.LENGTH_SHORT).show();
                }
                Snackbar.make(view, "Datos Guardados",Snackbar.LENGTH_SHORT).show();
            }

            llenar();
            nombre.setText("");
            carnet.setText("");
            carnet.requestFocus();
        }
        }catch (Exception e){
            Snackbar.make(view, e.getMessage()+"",Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void borrar(View view){
        final int id=Integer.parseInt(String.valueOf(view.getTag()));
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("(Examen!!!!)");
        ad.setIcon(android.R.drawable.star_big_off);
        ad.setMessage("Eliminar Intento?");
        ad.setPositiveButton("si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.baseSQLite.borrarExamen(id);
                MainActivity.baseSQLite.suprimirExaminado(id);
                Snackbar.make(getCurrentFocus(),"Complete los campos", Snackbar.LENGTH_SHORT).show();
                llenar();
            }
        });
        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.estudiante, menu);
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

        if (id == R.id.nav_preguntas) {
            Intent intent=new Intent(this, PreguntasActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
