package com.example.bubba.parcial1api23;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EmpleadoActivity extends AppCompatActivity {
    ArrayList<Empleado> empleados;
    ArrayList<String> deptos;
    EditText nombre, apellido, sueldo;
    Spinner depto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);
        if((ArrayList<Empleado>) getIntent().getSerializableExtra("emplados")==null){
            empleados=new ArrayList<>();
        }else{
        empleados= (ArrayList<Empleado>) getIntent().getSerializableExtra("emplados");
        }
        nombre=(EditText) findViewById(R.id.nombre);
        apellido=(EditText) findViewById(R.id.apellido);
        sueldo=(EditText) findViewById(R.id.sueldo);
        depto=(Spinner) findViewById(R.id.spinner);
        try {
            deptos = (ArrayList<String>) getIntent().getSerializableExtra("deptos");
        }catch ( Exception e){
            msg(e.getMessage());
        }

        llenar();


    }

    public void llenar(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, deptos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depto.setAdapter(dataAdapter);
    }

    public void guardar(View view){
        if (nombre.getText().toString().equals("")||
                apellido.getText().toString().equals("")||
                sueldo.getText().toString().equals("")){
            msg("Campos Vacios");
        }else{
            try {
                empleados.add(new Empleado(nombre.getText().toString(), apellido.getText().toString(), Double.parseDouble(sueldo.getText().toString()), String.valueOf(depto.getSelectedItem())));
            }catch (Exception e){
                msg(e.getMessage());
            }
            limpiar();
        }
    }

    public void menu(View view){
        Intent in=getIntent();
        in.putExtra("empleados", empleados);
        in.putExtra("depto", deptos);
        in.putExtra("lugar", "emp");
        setResult(RESULT_OK, in);
        finish();
    }

    public void limpiar(){
        nombre.setText("");
        apellido.setText("");
        sueldo.setText("");
        depto.setSelection(0);
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
}
