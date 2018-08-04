package com.example.examen.examenapi23;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ExamenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText opciona, opcionb, opcionc, opciond, pregunta;
    RadioButton ra,rb,rc,rd;
    RadioGroup grupo;
    Spinner numeropregunta;
    int posicion=0;
    Button btnAlamcenar;
    TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen);
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

        opciona=(EditText) findViewById(R.id.opciona);
        opcionb=(EditText) findViewById(R.id.opcionb);
        opcionc=(EditText) findViewById(R.id.opcionc);
        opciond=(EditText) findViewById(R.id.opciond);
        pregunta=(EditText) findViewById(R.id.pregunta);

        ra=(RadioButton) findViewById(R.id.ra);
        rb=(RadioButton) findViewById(R.id.rb);
        rc=(RadioButton) findViewById(R.id.rc);
        rd=(RadioButton) findViewById(R.id.rd);

        grupo=(RadioGroup) findViewById(R.id.gruporadio);

        btnAlamcenar=(Button) findViewById(R.id.btnsiguiente);
        nombre=(TextView) findViewById(R.id.nombreestudiante);

        ra.setOnClickListener(this);
        rb.setOnClickListener(this);
        rc.setOnClickListener(this);
        rd.setOnClickListener(this);

        numeropregunta=(Spinner) findViewById(R.id.numeropregunta);
        numeropregunta.setOnItemSelectedListener(this);
        llenar();
        nombre.setText(MainActivity.estudianteGlobal.getNombre());
    }

    private void llenar() {

            MainActivity.baseSQLite.Respuestas();
            ArrayAdapter<Respuesta> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.listaRespuestas);
            numeropregunta.setAdapter(adapter);

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
        getMenuInflater().inflate(R.menu.examen, menu);
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

    @Override
    public void onClick(View v) {
        if (ra.isChecked()){
            MainActivity.listaRespuestas.get(posicion).setRestudiante("a");
        }
        if (rb.isChecked()){
            MainActivity.listaRespuestas.get(posicion).setRestudiante("b");
        }
        if (rc.isChecked()){
            MainActivity.listaRespuestas.get(posicion).setRestudiante("c");
        }
        if (rd.isChecked()){
            MainActivity.listaRespuestas.get(posicion).setRestudiante("d");
        }
    }
    public void siguiente(View view){
        if (btnAlamcenar.getText().equals("Terminar")){
            msg();
        }
        if (posicion<(numeropregunta.getCount()-1)){
            posicion++;
            numeropregunta.setSelection(posicion);
        }
        if (posicion==(numeropregunta.getCount()-1)){
            btnAlamcenar.setText("Terminar");
        }
    }

    public void anterior(View view){
        if (posicion>0){
            posicion--;
            numeropregunta.setSelection(posicion);
        }
        if (posicion<(numeropregunta.getCount()-0)){
            btnAlamcenar.setText("Siguiente");
        }
    }

    public void msg(){
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Examen");
        ad.setMessage("Desea Terminar su examen????");
        ad.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int x=0;x<MainActivity.listaRespuestas.size();x++){
                    MainActivity.baseSQLite.insertarR(MainActivity.listaRespuestas.get(x),MainActivity.estudianteGlobal);
                }
                MainActivity.baseSQLite.actualizarEstudiante(MainActivity.estudianteGlobal);
                Snackbar.make(getCurrentFocus(),"DAtos Almacenados",Snackbar.LENGTH_SHORT).show();
                MainActivity.logueado=false;
                MainActivity.estudianteGlobal=null;
                finish();
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        ad.setNegativeButton("Nel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.show();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        grupo.clearCheck();
        try{
        pregunta.setText(MainActivity.listaRespuestas.get(i).getItem());
        opciona.setText(MainActivity.listaRespuestas.get(i).ra);
        opcionb.setText(MainActivity.listaRespuestas.get(i).rb);
        opcionc.setText(MainActivity.listaRespuestas.get(i).rc);
        opciond.setText(MainActivity.listaRespuestas.get(i).rd);

        String restudiante=MainActivity.listaRespuestas.get(i).getRestudiante();

        if (restudiante.equals("a")) ra.setChecked(true);
        if (restudiante.equals("b")) ra.setChecked(true);
        if (restudiante.equals("c")) ra.setChecked(true);
        if (restudiante.equals("d")) ra.setChecked(true);

        posicion=i;
        if (posicion<(numeropregunta.getCount()-1)){
            btnAlamcenar.setText("Siguiente");
        }else{
            btnAlamcenar.setText("Terminar");
        }

    }catch (Exception e){
        Toast.makeText(this,e.getMessage()+"",Toast.LENGTH_LONG).show();
    }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
