package com.example.bubba.parcial1api23;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> deptos;
    ArrayList<Empleado> empleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deptos = new ArrayList<>();
        empleados = new ArrayList<>();
    }

    public void salir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_action_name);

        builder.setMessage("Desea Salir?");
        builder.setTitle("Planilla");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }

    public void departamento(View view) {
        Intent intent = new Intent(this, DepartamentosActivity.class);
        intent.putExtra("deptos", deptos);
        intent.putExtra("empleados", empleados);
        startActivityForResult(intent, 2);
    }

    public void empleado(View view) {
        try {
            Intent intent = new Intent(this, EmpleadoActivity.class);
            intent.putExtra("deptos", deptos);
            intent.putExtra("empleados", empleados);
            startActivityForResult(intent, 2);
        }catch (Exception e){
            msg(e.getMessage());
        }
    }
    public void planilla(View view){
        Intent intent = new Intent(this, PlanillaActivity.class);
        intent.putExtra("deptos", deptos);
        intent.putExtra("empleados", empleados);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data.getExtras().getString("lugar").equals("dep")) {
            deptos = (ArrayList<String>) data.getExtras().get("deptos");
            empleados = (ArrayList<Empleado>) data.getExtras().get("empleados");
        }
        if (resultCode == RESULT_OK && data.getExtras().getString("lugar").equals("emp")) {
            empleados = (ArrayList<Empleado>) data.getExtras().get("empleados");
            deptos = (ArrayList<String>) data.getExtras().get("deptos");
        }

        if (resultCode == RESULT_OK && data.getExtras().getString("lugar").equals("pla")) {
            empleados = (ArrayList<Empleado>) data.getExtras().get("empleados");
            deptos = (ArrayList<String>) data.getExtras().get("deptos");
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
