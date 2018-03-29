package com.example.bubba.listacustomizeapi23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VistaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista);

        Bundle datos=this.getIntent().getExtras();

        TextView tvNombre=(TextView) findViewById(R.id.tvPais);
        TextView tvCapital=(TextView) findViewById(R.id.tvCapital);
        ImageView ivBanders=(ImageView) findViewById(R.id.img);

        tvNombre.setText(datos.getString("nombre"));
        tvCapital.setText(datos.getString("capital"));
        ivBanders.setImageResource(datos.getInt("bandera"));

    }

    public void volver(View view){
        finish();
    }
}
