package com.example.bubba.preferenciasapi23;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    EditText usuario, password;
    CheckBox recordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.password);
        recordar = (CheckBox) findViewById(R.id.recordar);

        recuperarPreferencias();
    }

    public void recuperarPreferencias(){
        boolean isguard=preferences.getBoolean("estado",false);
        if (isguard){
            usuario.setText(preferences.getString("usuario",""));
            password.setText(preferences.getString("password",""));
            recordar.setChecked(true);

        }
    }

    public void entrar(View view){
        if (usuario.getText().toString().equals("")||password.getText().toString().equals("")){
            Toast.makeText(getBaseContext(),"Complete los campos",Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("estado", recordar.isChecked());
            editor.putString("usuario", usuario.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.commit();

            Intent intent=new Intent(this, NuevaActivity.class);
            startActivity(intent);
            finish();



        }
    }
    public void salir(){
        finish();
    }
}
