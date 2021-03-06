package com.example.examen.examenapi23;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Estudiante> listaEstudiantes;
    public static ArrayList<Pregunta> listaPreguntas;
    public static ArrayList<Respuesta> listaRespuestas;
    public static BaseSQLite baseSQLite;
    public static boolean logueado;
    public static Estudiante estudianteGlobal;
    EditText usuario, contra;
    public static int idpregunta;

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

        baseSQLite=new BaseSQLite(this,"BDExamen",null,3);
        Toast.makeText(this, "HAsta aki", Toast.LENGTH_LONG).show();
        listaEstudiantes=new ArrayList<>();
        listaPreguntas= new ArrayList<>();
        listaRespuestas=new ArrayList<>();
        logueado=false;
        estudianteGlobal=null;
        usuario=(EditText) findViewById(R.id.usuario);
        contra=(EditText) findViewById(R.id.contra);
    }

    public void entrar(View view){
        if (usuario.getText().toString().isEmpty()||contra.getText().toString().isEmpty()){
            Snackbar.make(view, "Digite sus credencuales",Snackbar.LENGTH_SHORT).show();
        }else{
           try {
               Estudiante temp = MainActivity.baseSQLite.login(usuario.getText().toString(), contra.getText().toString());

               if (temp != null) {
                   if (temp.getExamindao().equals("1")) {
                       Snackbar.make(view, "ya se examino", Snackbar.LENGTH_SHORT).show();
                       usuario.setText("");
                       contra.setText("");
                       usuario.requestFocus();
                   } else {
                       logueado = true;
                       estudianteGlobal = temp;
                       finish();
                       Intent intent=new Intent(this, ExamenActivity.class);
                       startActivity(intent);
                   }
               } else {
                   if (usuario.getText().toString().equals("admin") && contra.getText().toString().equals("admin")) {

                       logueado = true;
                       //estudianteGlobal=temp;
                       finish();
                       Intent intent = new Intent(this, EstudianteActivity.class);
                       startActivity(intent);
                   } else {
                       Snackbar.make(view, "Credenciales incorrectas", Snackbar.LENGTH_SHORT).show();
                       usuario.setText("");
                       contra.setText("");
                       usuario.requestFocus();
                   }
               }
           }catch (Exception e){
               Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
           }
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

        if (id == R.id.nav_camera) {
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
