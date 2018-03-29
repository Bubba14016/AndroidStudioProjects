package com.tabla.bubba.tablaapi23;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText numero;
    private int progresStatus=0;
    private ProgressBar progressBar;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero = (EditText) findViewById(R.id.numero);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        handler=new Handler();
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void llamar(){
        Intent intent=new Intent(this, ResultadoActivity.class);
        intent.putExtra("valor",Integer.valueOf(numero.getText().toString()));
        startActivity(intent);
        numero.setText("");
        numero.requestFocus();
    }

    public void mostrar(View view){
        if (numero.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),"Ingrese un numero", Toast.LENGTH_SHORT).show();
            numero.requestFocus();

        }else{
            progresStatus=0;
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(progresStatus<100) {
                        progresStatus++;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progresStatus);
                                if (progresStatus >= 100) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    llamar();
                                }
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }
}
