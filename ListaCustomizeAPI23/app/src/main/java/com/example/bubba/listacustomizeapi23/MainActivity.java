package com.example.bubba.listacustomizeapi23;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pais> paises;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paises=new ArrayList<>();
        paises.add(new Pais("Alemania","Berlin",R.drawable.alemania));
        paises.add(new Pais("Francia","Paris",R.drawable.francia));
        paises.add(new Pais("Inglaterra","Londres",R.drawable.inglaterra));
        paises.add(new Pais("Japon","Tokio",R.drawable.japon));
        paises.add(new Pais("Korea","Koreiata",R.drawable.korea));

        Adaptador adaptador=new Adaptador(this,R.layout.activity_custom,paises);

        lista=(ListView)findViewById(R.id.lvPaises);
        lista.setAdapter(adaptador);
       // lista.setClickable(true);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                        msg1(paises.get(i).getNombre());

                }
        });

    lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
           Intent intent = new Intent(MainActivity.this, VistaActivity.class);
            intent.putExtra("nombre", paises.get(i).getNombre());
            intent.putExtra("capital", paises.get(i).getCapital());
            intent.putExtra("bandera", paises.get(i).getBandera());
            startActivity(intent);
          // msg1("mierda");
            return true;
        }
    });







    }

    public void msg1(String texto){
        AlertDialog.Builder alerta= new AlertDialog.Builder(MainActivity.this);
        alerta.setMessage(texto);
        alerta.setCancelable(true);
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alerta.setTitle("");
        alerta.show();
    }

    public void msg12(int bandera){
        LayoutInflater imagen=LayoutInflater.from(MainActivity.this);
        final View vista=imagen.inflate(R.layout.imagen, null);

        ImageView imagen2=(ImageView) vista.findViewById(R.id.bandera);
        imagen2.setImageResource(bandera);
        AlertDialog.Builder alerta= new AlertDialog.Builder(MainActivity.this);
        //alerta.setMessage(texto);
        alerta.setCancelable(true);
        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alerta.setTitle("");
        alerta.setView(vista);
        alerta.show();
    }
}
