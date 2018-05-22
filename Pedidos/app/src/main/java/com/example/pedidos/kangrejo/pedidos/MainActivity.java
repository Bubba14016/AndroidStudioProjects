package com.example.pedidos.kangrejo.pedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aPedido(View view){
        Intent intent=new Intent(this,FormularioActivity.class);
        startActivity(intent);
    }
}
