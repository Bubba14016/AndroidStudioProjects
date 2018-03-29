package com.example.bubba.tarealistaapi23;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arreglo;
    ListView lista;
    EditText palabra;
    SharedPreferences preferences;
    private static int posicion=0;
    private static boolean editar=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences=getSharedPreferences("datos", Context.MODE_PRIVATE);
        lista=(ListView)findViewById(R.id.lista);
        palabra=(EditText)findViewById(R.id.palabra);
        arreglo=new ArrayList<>();

        registerForContextMenu(lista);

        recuperarPreferencias();
        llenar();


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;

        menu.setHeaderTitle("Que quiere hacer con"+lista.getAdapter().getItem(info.position).toString());
        inflater.inflate(R.menu.menu, menu);
        posicion=info.position;
    }

    public void llenar(){
        Collections.sort(arreglo);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        lista.setAdapter(adapter);

        SharedPreferences.Editor editor=preferences.edit();
        Set arregloPalaras=new HashSet();
        for(String texto:arreglo){
            arregloPalaras.add(texto);
        }
        editor.putStringSet("savePalabras", arregloPalaras);
        editor.commit();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcion1:
                palabra.setText(arreglo.get(posicion).toString());
                palabra.setSelection(palabra.getText().length());
                editar=true;
                return true;
            case R.id.opcion2:
                msg();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public void msg(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Desea eliminar")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        arreglo.remove(posicion);
                        llenar();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    public void agregar(View view){
        if (palabra.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),"Ingrese palabras",Toast.LENGTH_SHORT);
        }else{
            if (!editar){
                arreglo.add(posicion,palabra.getText().toString());
            }else{
                arreglo.set(posicion,palabra.getText().toString());
                editar=false;
            }
            llenar();
        }
        palabra.setText("");
        palabra.requestFocus();
    }

    public void recuperarPreferencias(){

        Set arregloPalabras=new HashSet();
        arregloPalabras=preferences.getStringSet("savePalabras",null);
        if (arregloPalabras!=null){
            for (Object movie:arregloPalabras){
                arreglo.add(String.valueOf(movie));
            }
        }

    }
}
