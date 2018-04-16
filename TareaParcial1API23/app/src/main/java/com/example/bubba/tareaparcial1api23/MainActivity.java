package com.example.bubba.tareaparcial1api23;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText nombre,apellido;
    Spinner edad;
    Button guardar;
    public ArrayList<Persona> personas;
    List<String> edades=new ArrayList<>();
    RadioGroup grupo;
    RadioButton masculino,femenino;
    SharedPreferences preferences;
    Gson gson = new Gson();
    int posicio=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre=(EditText) findViewById(R.id.nombre);
        apellido=(EditText) findViewById(R.id.apellido);
        edad=(Spinner) findViewById(R.id.edad);
        personas=new ArrayList<>();
        guardar=(Button) findViewById(R.id.guardar);
        guardar.setTag("1");
        //edades=new ArrayList<>();
        llenarArrayEdad();
        grupo=(RadioGroup)findViewById(R.id.grupo);
        masculino=(RadioButton) findViewById(R.id.masculino);
        femenino=(RadioButton) findViewById(R.id.femenino);
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        recuperarPreferencias();




    }


    public void agregar(View view){
        if (guardar.getTag().toString().equals("1")) {
            if (nombre.getText().toString().equals("") || apellido.getText().toString().equals("") ||
                    (!masculino.isChecked() && !femenino.isChecked())) {
                msg("Campos vacios");

            } else {
                boolean sexo;
                if (masculino.isChecked()) {
                    sexo = true;
                } else {
                    sexo = false;
                }
                personas.add(new Persona(nombre.getText().toString(), apellido.getText().toString(),
                        Integer.parseInt(edad.getSelectedItem().toString()), sexo));
                limpiar();
                llenar();

            }
        }else{
            boolean sexo;
            if (masculino.isChecked()) {
                sexo = true;
            } else {
                sexo = false;
            }
            Persona nuevo=new Persona(nombre.getText().toString(), apellido.getText().toString(),
                    Integer.parseInt(edad.getSelectedItem().toString()), sexo);

            personas.set(posicio,nuevo);
            limpiar();
            llenar();
        }
    }

    public void llenar(){
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

    public void recuperarPreferencias(){
        try {
            Set arregloPersonas = new HashSet();
            arregloPersonas = preferences.getStringSet("savepersonas", null);

            if (arregloPersonas != null) {
                Type type = new TypeToken<ArrayList<Persona>>() {
                }.getType();
                ArrayList<Persona> prefPersonas=gson.fromJson(String.valueOf(arregloPersonas), type);
                personas=prefPersonas;
            }
        }catch (Exception e){
            msg(e.getMessage());
        }
    }

    private void llenarArrayEdad(){
        for (int i=1;i<100;i++){
            edades.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, edades);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edad.setAdapter(dataAdapter);
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

    public void listar(View view){

        Intent intent = new Intent(this, ListaActivity.class);
        intent.putExtra("personas", personas);
        startActivityForResult(intent, 2);
    }

    public void limpiar(){
        nombre.setText("");
        apellido.setText("");
        grupo.clearCheck();
        nombre.requestFocus();
        guardar.setText("Guardar");
        guardar.setTag("1");
        edad.setSelection(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            guardar.setTag("2");
            guardar.setText("Editar");
            Persona persona=(Persona) data.getExtras().get("persona");
            nombre.setText(persona.getNombre());
            apellido.setText(persona.getApellido());
            edad.setSelection(persona.getEdad()-1);
            if (persona.isSexo()){
                masculino.setChecked(true);
            }else{
                femenino.setChecked(true);
            }
            posicio=data.getExtras().getInt("posicion");
            //msg(persona.toString());

        }
        if (resultCode==RESULT_CANCELED){
            recuperarPreferencias();
        }
    }
}
