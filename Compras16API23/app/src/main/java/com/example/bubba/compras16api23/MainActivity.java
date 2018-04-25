package com.example.bubba.compras16api23;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    public static ArrayList<Productos> listaProductos;
    public static ArrayList<Compras> listaCompras;
    ListView lista;
    ArrayAdapter<Productos> adapter;
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

        listaProductos=new ArrayList<>();
        listaCompras=new ArrayList<>();
        lista=(ListView)findViewById(R.id.lista);
        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lista.setOnItemClickListener(this);
        llenarLista();

    }

    private void llenarLista(){
        Collections.sort(MainActivity.listaProductos);
        adapter=new ArrayAdapter<Productos>(this,android.R.layout.simple_list_item_checked,MainActivity.listaProductos);
        lista.setAdapter(adapter);
    }

    public void marcarSeleccionados(){

        for (int i=0;i<lista.getAdapter().getCount();i++){
            String [] partes;
            partes = lista.getAdapter().getItem(i).toString().split("\n");
            if (buscar(partes[i])){
                lista.setItemChecked(i,true);
            }
        }
    }

    public boolean buscar(String texto){
        for (Compras c: MainActivity.listaCompras){
            if (c.getProducto().getNombre().toString().equals(texto)){
               return true;
            }
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==1)&&(resultCode==RESULT_OK)){
            llenarLista();

            marcarSeleccionados();
        }
        if ((requestCode==2)&&(resultCode==RESULT_OK)){
            llenarLista();

            marcarSeleccionados();
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

        if (id == R.id.nav_compra) {
            // Handle the camera action
        } else if (id == R.id.nav_producto) {
            Intent intent=new Intent(this,ProductoActivity.class);
            startActivityForResult(intent,1);

        } else if (id == R.id.nav_lista) {
            Intent intent=new Intent(this,MisProductosActivity.class);
            startActivityForResult(intent,2);
        } else if (id == R.id.nav_salir) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (!existe(adapter.getItem(i).getNombre())){
            MainActivity.listaCompras.add(new Compras(adapter.getItem(i)));
        }
    }

    private boolean existe(String nombre){
        boolean bandera=false;
        if (MainActivity.listaCompras.size()>0){
            int index=-1;
            for (Compras c:MainActivity.listaCompras){
                index++;
                if (c.getProducto().getNombre().equals(nombre)){
                    MainActivity.listaCompras.remove(c.getPosicionOriginal());
                    bandera= true;
                }
            }
        }
        return bandera;
    }
}
