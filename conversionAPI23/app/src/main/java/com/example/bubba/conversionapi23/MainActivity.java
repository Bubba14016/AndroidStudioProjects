package com.example.bubba.conversionapi23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText numero;
    TextView resultado;
    Spinner base1, base2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero=(EditText) findViewById(R.id.numero);
        resultado=(TextView) findViewById(R.id.resultado);
        base1=(Spinner) findViewById(R.id.base1);
        base2=(Spinner) findViewById(R.id.base2);

        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.bases, android.R.layout.simple_list_item_1);

        base1.setAdapter(adapter);
        base2.setAdapter(adapter);

        base1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Integer.valueOf(base1.getSelectedItem().toString())<16){
                    numero.setInputType(InputType.TYPE_CLASS_NUMBER);
                }else{
                    numero.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void convertir(View view){
        String aux="";
        try {
            if (Integer.valueOf(base1.getSelectedItem().toString()) != 10) {
                aux = String.valueOf(dexa10(numero.getText().toString()));

            } else {
                aux = numero.getText().toString();
            }
            String resultado = de10ax(aux);

            this.resultado.setText(resultado);
        }catch (Exception e){
            Toast.makeText(getBaseContext(),"Numero invalido", Toast.LENGTH_SHORT).show();
        }
    }

    public String de10ax(String numero){
        String valor=Integer.toString(Integer.valueOf(numero),Integer.valueOf(base2.getSelectedItem().toString()));//de base 10 a x
        return valor;
    }

    public int  dexa10(String numero){
        int valor2=Integer.parseInt(numero,Integer.valueOf(base1.getSelectedItem().toString()));// de base x a 10
        return valor2;
    }



}
