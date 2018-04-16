package com.example.bubba.tareaparcial1api23;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ListaActivity extends AppCompatActivity {
    ListView lista;
    ArrayList<Persona> personas;
    RadioButton masculino,femenino;
    SharedPreferences preferences;
    Gson gson = new Gson();
    boolean estado=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lista=(ListView) findViewById(R.id.lista);
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        personas = (ArrayList<Persona>) getIntent().getSerializableExtra("personas");

        llenar();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in=getIntent();
                //in.putExtra("RESULTADO","EDITAR");

                    Persona elegido = (Persona) adapterView.getItemAtPosition(i);

               in.putExtra("persona", elegido);
               in.putExtra("posicion", i);
                setResult(RESULT_OK, in);
                finish();
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               msg2("Seguro que desea borrar?", i);

                return true;
            }
        });
    }

    public void llenar(){
        try {
            //Collections.sort(personas);
            ArrayAdapter<Persona> adapter = new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, personas);
            lista.setAdapter(adapter);
        }catch (Exception e){
            msg(e.getMessage());
        }
    }

    public void llenarPref(){
        try {

            SharedPreferences.Editor editor = preferences.edit();
            Set arregloPersonas = new HashSet();

            //String ventas=gson.toJson(personas);
            for (Persona persona : personas) {

                arregloPersonas.add(gson.toJson(persona));
            }
            editor.putStringSet("savepersonas", arregloPersonas);
            editor.commit();
        }catch (Exception e){
            msg(e.getMessage());
        }
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
    public  void volver(View view){
        Intent in=getIntent();
        //in.putExtra("RESULTADO","EDITAR");

        //Persona elegido = (Persona) adapterView.getItemAtPosition(i);


        setResult(RESULT_CANCELED, in);
        finish();
    }

    public void msg2(String txt, final int posicion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher_background);
        builder.setMessage(txt);
        //boolean estado=false;
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                personas.remove(posicion);
                llenar();
                llenarPref();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               //estado=false;
            }
        }).show();
        //return estado;
    }
}
