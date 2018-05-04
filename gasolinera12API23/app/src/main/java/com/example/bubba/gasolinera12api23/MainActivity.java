package com.example.bubba.gasolinera12api23;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    EditText precioGalonSuper, precioGalonRegular, precioGalonDisel, monto;
    public static Precios precios;
    public static ArrayList<Ventas> ventas;
    RadioButton rsuper, rregular, rdisel;
    public static int resultadoDinero = 1;
    public static int resultadoGalones = 2;
    Button btnSuper, btnRegular, btnDisel, btnModificar, btnVender;
    RadioGroup grupo;
    SharedPreferences preferences, preferences2;
    Gson gson = new Gson();
    BaseDatos bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        precios = new Precios(0, 0, 0);
        precioGalonSuper = (EditText) findViewById(R.id.precioGalonSuper);
        precioGalonRegular = (EditText) findViewById(R.id.precioGalonRegular);
        precioGalonDisel = (EditText) findViewById(R.id.precioGalonDisel);
        monto = (EditText) findViewById(R.id.monto);

        rsuper = (RadioButton) findViewById(R.id.rsuper);
        rregular = (RadioButton) findViewById(R.id.rregular);
        rdisel = (RadioButton) findViewById(R.id.rdisel);

        btnSuper = (Button) findViewById(R.id.btnSuper);
        btnRegular = (Button) findViewById(R.id.btnRegular);
        btnDisel = (Button) findViewById(R.id.btnDisel);

        btnModificar=(Button) findViewById(R.id.btnModificar);
        btnVender=(Button) findViewById(R.id.btnVender);

        grupo=(RadioGroup)findViewById(R.id.grupo);

        precioGalonSuper.setEnabled(false);
        precioGalonRegular.setEnabled(false);
        precioGalonDisel.setEnabled(false);
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
       // preferences2 = getSharedPreferences("datos2", Context.MODE_PRIVATE);

        ventas = new ArrayList<>();
        recuperarPrecios();
        bd.llenarPrecios();
        extraerPrecios();
        restaurarEstado(savedInstanceState);
        //recuperarPreferencias();
        recuperarPreferenciasVentas();
        obtenerVentas();
    }

    public void recuperarPrecios(){
        bd=new BaseDatos(this,"DBGasolinera",null,2);
        //bd.llenarPrecios();
        bd.consultarPrecios();
        }

    public void modificar(View view){
        if(btnModificar.getText().equals("MODIFICAR")) {
            btnVender.setEnabled(false);
            btnModificar.setText("EDITAR");
            precioGalonSuper.setEnabled(true);
            precioGalonRegular.setEnabled(true);
            precioGalonDisel.setEnabled(true);
            monto.setEnabled(false);
            precioGalonSuper.requestFocus();
        }else{
            try {
                precios.setPrecioGalonSuper(Float.parseFloat(precioGalonSuper.getText().toString()));
                precios.setPrecioGalonRegular(Float.parseFloat(precioGalonRegular.getText().toString()));
                precios.setPrecioGalonDisel(Float.parseFloat(precioGalonDisel.getText().toString()));
                btnVender.setEnabled(true);
                btnModificar.setText("MODIFICAR");
                precioGalonSuper.setEnabled(false);
                precioGalonRegular.setEnabled(false);
                precioGalonDisel.setEnabled(false);
                monto.setEnabled(true);
                msg("Precios Actualizados");
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("estado", true);
                editor.putString("super", precioGalonSuper.getText().toString());
                editor.putString("regular", precioGalonRegular.getText().toString());
                editor.putString("diesel", precioGalonDisel.getText().toString());
                editor.commit();
            }catch (Exception e){
                Toast.makeText(getBaseContext(),"Valores invalidos", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void llenar(){
        try {
            //Collections.sort(ventas);
            ArrayList<String> ventasJson=new ArrayList<>();
            SharedPreferences.Editor editor = preferences.edit();
            Set arregloVentas = new HashSet();
            //Gson gson = new Gson();
            String ventas=gson.toJson(MainActivity.ventas);
            for (Ventas venta : MainActivity.ventas) {
                //ventasJson.add(gson.toJson(venta));
                arregloVentas.add(gson.toJson(venta));
            }
            editor.putStringSet("saveventas", arregloVentas);
            editor.commit();
        }catch (Exception e){
            msg(e.getMessage());
        }
    }

    public void recuperarPreferenciasVentas(){
        try {
            Set arregloVentas = new HashSet();
            arregloVentas = preferences.getStringSet("saveventas", null);

            if (arregloVentas != null) {
                Type type = new TypeToken<ArrayList<Ventas>>() {
                        }.getType();
                ArrayList<Ventas> ventas=gson.fromJson(String.valueOf(arregloVentas), type);
                //for (Object movie : arregloVentas) {
                //    Type type = new TypeToken<ArrayList<Ventas>>() {
                //    }.getType();
                    //MainActivity.ventas.add(gson.fromJson(String.valueOf(arregloVentas), type));
                     //ventas.add((Ventas) gson.fromJson(String.valueOf(arregloVentas), type));
               // }
                // Type type = new TypeToken<ArrayList<Ventas>>(){}.getType();
                //ArrayList<Ventas> ventas = gson.fromJson(arregloVentas, type);
                MainActivity.ventas=ventas;
            }
        }catch (Exception e){
            msg(e.getMessage());
        }
    }

    public void recuperarPreferencias(){
        boolean isguard=preferences.getBoolean("estado",false);
        if (isguard){
            precioGalonSuper.setText(preferences.getString("super",""));
            precioGalonRegular.setText(preferences.getString("regular",""));
            precioGalonDisel.setText(preferences.getString("diesel",""));

            precios.setPrecioGalonSuper(Float.parseFloat(precioGalonSuper.getText().toString()));
            precios.setPrecioGalonRegular(Float.parseFloat(precioGalonRegular.getText().toString()));
            precios.setPrecioGalonDisel(Float.parseFloat(precioGalonDisel.getText().toString()));

        }
    }

    private void restaurarEstado(Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            MainActivity.ventas=(ArrayList<Ventas>)savedInstanceState.getSerializable("lasventas");
            obtenerVentas();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("lasventas", (ArrayList<? extends Parcelable>) MainActivity.ventas);
        super.onSaveInstanceState(outState);
    }

    public void vender(View view) {
        if ((!rsuper.isChecked() && !rregular.isChecked() && !rdisel.isChecked()) ||
                monto.getText().toString().equals("")) {
            msg("Seleccione Parametros");
        } else {
            try {
                float montos = Float.parseFloat(monto.getText().toString());
                String stipo = "";
                float precio = 0;
                if (rsuper.isChecked()) {
                    stipo = "Super";
                    precio = Float.parseFloat(precioGalonSuper.getText().toString());
                }
                if (rregular.isChecked()) {
                    stipo = "Regular";
                    precio = Float.parseFloat(precioGalonRegular.getText().toString());
                }

                if (rdisel.isChecked()) {
                    stipo = "Diesel";
                    precio = Float.parseFloat(precioGalonDisel.getText().toString());
                }

                MainActivity.ventas.add(new Ventas(stipo, precio, montos));
                obtenerVentas();//llevar control de cuantas ventas por tipo
                llenar();
                limpiar();

            } catch (NumberFormatException e) {
                msg("Monto Incorrecto");
                monto.setText("");
                monto.requestFocus();
            }
        }
    }

    private void limpiar() {
        monto.setText("");
        monto.requestFocus();
        grupo.clearCheck();

    }

    public void cuantoGalon(View view){
        Intent intent=new Intent(this, CuantoGalonActivity.class);
        startActivity(intent);
    }

    public void cuantoDinero(View view){
        Intent intent=new Intent(this, cuantoDineroActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, resultadoDinero);
    }

    private void obtenerVentas() {
        int contadorSuper = 0, contadorRegular = 0, contadorDise = 0;
        for (Ventas ventas : MainActivity.ventas) {
            if (ventas.getTipo().equals("Super")) contadorSuper++;
            if (ventas.getTipo().equals("Regular")) contadorRegular++;
            if (ventas.getTipo().equals("Diesel")) contadorDise++;
        }
        btnSuper.setText("Super (" + contadorSuper + ")");
        btnRegular.setText("Regular (" + contadorRegular + ")");
        btnDisel.setText("Diesel (" + contadorDise + ")");
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

    public void extraerPrecios() {
        precioGalonSuper.setText(String.valueOf(precios.getPrecioGalonSuper()));
        precioGalonRegular.setText(String.valueOf(precios.getPrecioGalonRegular()));
        precioGalonDisel.setText(String.valueOf(precios.getPrecioGalonDisel()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            String texto=data.getExtras().getString("RESULTADO");
            Toast.makeText(getBaseContext(), texto, Toast.LENGTH_SHORT).show();
        }
        if (requestCode==RESULT_CANCELED){
            Toast.makeText(getBaseContext(),"La actividad hija se cancelo", Toast.LENGTH_SHORT).show();
        }
    }
}
