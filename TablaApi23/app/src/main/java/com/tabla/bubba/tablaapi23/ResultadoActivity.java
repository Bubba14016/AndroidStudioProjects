package com.tabla.bubba.tablaapi23;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {
    TextView tabla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        tabla=(TextView) findViewById(R.id.tabla);

        Typeface tf=Typeface.createFromAsset(getAssets(),"font/Saiyan.ttf");
        tabla.setTypeface(tf);

        Bundle datos=this.getIntent().getExtras();
        int n = datos.getInt("valor");
        String cadena="";
        for(int i=1;i<11;i++){
            cadena+=""+n+" X "+i+" = "+(n*i);
            cadena+="\n";

        }
        tabla.setText(cadena);
    }

    public void volver(View view){
        finish();
    }
}
