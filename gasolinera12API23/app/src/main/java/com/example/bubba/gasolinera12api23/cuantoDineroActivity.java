package com.example.bubba.gasolinera12api23;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

public class cuantoDineroActivity extends AppCompatActivity {
    EditText rsuper, rregular, rdiesel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuanto_dinero);
        rsuper=(EditText)findViewById(R.id.txSuper2);
        rregular=(EditText)findViewById(R.id.txRegular2);
        rdiesel=(EditText)findViewById(R.id.txDisel2);

        montoDinero();
    }

    private void montoDinero(){
        float totals=0, totalr=0, totald=0;
        DecimalFormat df=new DecimalFormat("0.00");
        for (Ventas ventas : MainActivity.ventas) {

            if (ventas.getTipo().equals("Super")) totals+=ventas.getVenta();
            if (ventas.getTipo().equals("Regular")) totalr+=ventas.getVenta();
            if (ventas.getTipo().equals("Diesel")) totald+=ventas.getVenta();
        }

        rsuper.setText(df.format(totals));
        rregular.setText(df.format(totalr));
        rdiesel.setText(df.format(totald));

    }

    public void cancelar(View view){

        setResult(RESULT_CANCELED);
        finish();
    }

    public void volver(View view){
        //Intent intent=new Intent(this,MainActivity.class);
        Intent i=getIntent();
        i.putExtra("RESULTADO","SOY DE AYA");
        setResult(RESULT_OK, i);
        finish();
    }
}
