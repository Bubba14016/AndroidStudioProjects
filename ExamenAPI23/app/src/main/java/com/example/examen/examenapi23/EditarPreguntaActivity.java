package com.example.examen.examenapi23;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditarPreguntaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText opciona, opcionb, opcionc, opciond, pregunta;
    RadioButton ra,rb,rc,rd;
    RadioGroup grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pregunta);
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

        Pregunta temp=MainActivity.baseSQLite.consultarP(MainActivity.idpregunta);
        pregunta.setText(temp.getItem());
        pregunta.setSelection(pregunta.length());

        opciona.setText(temp.getRa());
        opcionb.setText(temp.getRb());
        opcionc.setText(temp.getRc());
        opciond.setText(temp.getRd());

        if (temp.getRcorrecta().equals("a")) ra.setChecked(true);
        if (temp.getRcorrecta().equals("b")) rb.setChecked(true);
        if (temp.getRcorrecta().equals("c")) rc.setChecked(true);
        if (temp.getRcorrecta().equals("d")) rd.setChecked(true);
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

    public void editar(View view){
        if (pregunta.getText().toString().equals("")||
                opciona.getText().toString().equals("")||
                opcionb.getText().toString().equals("")||
                opcionc.getText().toString().equals("")||
                opciond.getText().toString().equals("")){
            Snackbar.make(view, "Complete los campos", Snackbar.LENGTH_SHORT).show();
        }else {
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(pregunta.getWindowToken(),0);
            inputMethodManager.hideSoftInputFromWindow(opciona.getWindowToken(),0);
            inputMethodManager.hideSoftInputFromWindow(opcionb.getWindowToken(),0);
            inputMethodManager.hideSoftInputFromWindow(opcionc.getWindowToken(),0);
            inputMethodManager.hideSoftInputFromWindow(opciond.getWindowToken(),0);

            if ((!ra.isChecked()&& !rb.isChecked()&&!rc.isChecked()&&!rd.isChecked())){
                Snackbar.make(view, "Selecciona la respuesta correcta", Snackbar.LENGTH_SHORT).show();
            }else {
                String respuesta=null;
                if (ra.isChecked())respuesta="a";
                if (rb.isChecked())respuesta="b";
                if (rc.isChecked())respuesta="c";
                if (rd.isChecked())respuesta="d";

                String oa,ob,oc,od,item;
                oa=opciona.getText().toString().toUpperCase();
                ob=opcionb.getText().toString().toUpperCase();
                oc=opcionc.getText().toString().toUpperCase();
                od=opciond.getText().toString().toUpperCase();
                item=pregunta.getText().toString().toUpperCase();
                MainActivity.baseSQLite.modificarP(new Pregunta(MainActivity.idpregunta,item,oa,ob,oc,od,respuesta));

                Snackbar.make(view, "Datos Modificados", Snackbar.LENGTH_SHORT).show();
                finish();
                Intent intent=new Intent(this, GestionPreguntaActivity.class);
                startActivity(intent);
                //limpiar();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.editar_pregunta, menu);
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
