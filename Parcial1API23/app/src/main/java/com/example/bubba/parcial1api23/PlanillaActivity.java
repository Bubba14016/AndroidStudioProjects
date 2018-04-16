package com.example.bubba.parcial1api23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PlanillaActivity extends AppCompatActivity {
    ArrayList<Empleado> empleados;
    ArrayList<String> deptos;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planilla);

        deptos=(ArrayList<String>) getIntent().getSerializableExtra("deptos");
        empleados=(ArrayList<Empleado>) getIntent().getSerializableExtra("empleados");
        lista=(ListView) findViewById(R.id.listaPlanilla);

        llenar();
    }

    public  void llenar(){

            //Collections.sort(personas);
            ArrayAdapter<Empleado> adapter = new ArrayAdapter<Empleado>(this, android.R.layout.simple_list_item_1, empleados);
            lista.setAdapter(adapter);

    }

    public void menu(View view){
        Intent in=getIntent();



        in.putExtra("empleados", empleados);
        in.putExtra("deptos", deptos);
        in.putExtra("lugar", "dep");
        setResult(RESULT_OK, in);
        finish();

    }
}
