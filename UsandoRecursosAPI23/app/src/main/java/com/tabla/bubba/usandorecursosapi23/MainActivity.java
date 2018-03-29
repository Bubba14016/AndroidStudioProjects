package com.tabla.bubba.usandorecursosapi23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    EditText frase,resultado;
    TextView tvSeleccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner =(Spinner) findViewById(R.id.spinner);
        resultado=(EditText) findViewById(R.id.resultado);
        frase=(EditText) findViewById(R.id.frase);
        tvSeleccion=(TextView) findViewById(R.id.tvSeleccion);

        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.opciones, android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //tvSeleccion.setText(spinner.getSelectedItem().toString());
                proceso();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void proceso(){
        String seleccion=spinner.getSelectedItem().toString();
        String stringFrase=frase.getText().toString().toUpperCase();
        String cadenaVocales="",cadenaConsonantes="";

        for(int i=0;i<stringFrase.length();i++) {
            if (stringFrase.charAt(i)=='A'||
                    stringFrase.charAt(i)=='E'||
                    stringFrase.charAt(i)=='I'||
                    stringFrase.charAt(i)=='O'||
                    stringFrase.charAt(i)=='U'){
                cadenaVocales+= stringFrase.charAt(i)+"-";
            }else{
                cadenaConsonantes+= stringFrase.charAt(i)+"-";

            }
            if(seleccion.equals("Vocales")){
                resultado.setText(cadenaVocales);
            }
            if (seleccion.equals("Consonantes")){
                resultado.setText(cadenaConsonantes);
            }

            tvSeleccion.setText(seleccion);

        }

    }
    public void extraer(View view){
        proceso();
    }
}
