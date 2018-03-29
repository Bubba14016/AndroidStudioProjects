package com.example.bubba.listapersonalizadaapi23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<persona> personas;
    EditText nombre,edad;
    ListView lista;
    RadioButton radioM, radioF;
    RadioGroup grupoRadio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista=(ListView) findViewById(R.id.lista);
        nombre=(EditText) findViewById(R.id.nombre);
        edad=(EditText) findViewById(R.id.edad);
        radioF=(RadioButton)findViewById(R.id.radioF);
        radioM=(RadioButton)findViewById(R.id.radioM);
        personas=new ArrayList<>();
        grupoRadio=(RadioGroup)findViewById(R.id.radioGroup);
        llenar();

    }

    public void llenar(){
        ArrayAdapter<persona> adapter=new ArrayAdapter<persona>(this,android.R.layout.simple_list_item_1, personas);
        lista.setAdapter(adapter);

    }
    public void almacenar(View view){
        if (nombre.getText().toString().equals("")||edad.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),"Complete los campos", Toast.LENGTH_SHORT).show();

        }else if (Integer.parseInt(edad.getText().toString())<=0){
            Toast.makeText(getBaseContext(),"Edad debe ser superior a 0", Toast.LENGTH_SHORT).show();
        }else if (!radioM.isChecked()&&!radioF.isChecked()){
            Toast.makeText(getBaseContext(),"Seleccione sexo", Toast.LENGTH_SHORT).show();
        }else{
            boolean temporal=false;
            if (radioM.isChecked()){
                temporal=true;
            }
            personas.add(new persona(nombre.getText().toString(),Integer.parseInt(edad.getText().toString()),temporal));
            llenar();
            limpiar();
        }

    }

    public void limpiar(){
        nombre.setText("");
        edad.setText("");
        grupoRadio.clearCheck();
      //  (RadioButton)findViewById(R.id.radioGroup).clearFocus();
    }
}
