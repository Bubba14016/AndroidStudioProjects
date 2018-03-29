package com.example.bubba.listacustomizeapi23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Vista2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista2);

        Bundle datos=this.getIntent().getExtras();

        TextView tvNombre=(TextView) findViewById(R.id.tvPais);
        TextView tvCapital=(TextView) findViewById(R.id.tvCapital);
        ImageView ivBanders=(ImageView) findViewById(R.id.img);

        tvNombre.setText(datos.getString("nombre"));
        tvCapital.setText(datos.getString("capital"));
        ivBanders.setImageResource(datos.getInt("bandera"));
    }
}
