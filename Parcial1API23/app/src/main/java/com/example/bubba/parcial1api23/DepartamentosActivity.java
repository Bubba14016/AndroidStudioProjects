package com.example.bubba.parcial1api23;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DepartamentosActivity extends AppCompatActivity {
    EditText depto;
    ListView lista;
    ArrayList<String> deptos;
    ArrayList<Empleado> empleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamentos);

        depto=(EditText) findViewById(R.id.departamento);
        lista=(ListView) findViewById(R.id.lista);
        deptos=(ArrayList<String>) getIntent().getSerializableExtra("deptos");
        empleados=(ArrayList<Empleado>) getIntent().getSerializableExtra("empleados");
        llenar();
    }

    public void guardar(View view){
        if (depto.getText().toString().equals("")){
            msg("Campo vacio");
        }else{
            deptos.add(depto.getText().toString());
            llenar();
            depto.setText("");
            depto.requestFocus();
        }
    }

    public void menu(View view){
        Intent in=getIntent();



        in.putExtra("empleados", empleados);
        in.putExtra("deptos", deptos);
        in.putExtra("lugar", "pla");
        setResult(RESULT_OK, in);
        finish();

    }

    public  void llenar(){
        try {
            //Collections.sort(personas);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deptos);
            lista.setAdapter(adapter);
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


}
