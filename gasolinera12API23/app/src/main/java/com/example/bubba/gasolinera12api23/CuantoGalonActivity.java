package com.example.bubba.gasolinera12api23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CuantoGalonActivity extends AppCompatActivity {
    EditText rsuper, rregular, rdiesel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuanto_galon);
        rsuper=(EditText)findViewById(R.id.txSuper1);
        rregular=(EditText)findViewById(R.id.txRegular1);
        rdiesel=(EditText)findViewById(R.id.txDisel1);

        montoGalon();
    }

    private void montoGalon(){
        float gsuper=0, gregular=0, gdisel=0;
        for (Ventas ventas : MainActivity.ventas) {
            if (ventas.getTipo().equals("Super")) gsuper+=ventas.galones();
            if (ventas.getTipo().equals("Regular")) gregular+=ventas.galones();
            if (ventas.getTipo().equals("Diesel")) gdisel+=ventas.galones();
        }
        rsuper.setText(String.valueOf(gsuper));
        rregular.setText(String.valueOf(gregular));
        rdiesel.setText(String.valueOf(gdisel));
    }

    public void volver(View view){
        finish();
    }
}
