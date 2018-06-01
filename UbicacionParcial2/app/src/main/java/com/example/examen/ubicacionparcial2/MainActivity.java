package com.example.examen.ubicacionparcial2;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    TextView lat, lon, estado;
    ListView lista;
    ArrayAdapter<Coordenadas> adaptador;
    Switch recordar;
    Button iniciar,parar;
    boolean ciclo=false;
    ArrayList<Coordenadas> coordenadas;
    Timer timer;
    SharedPreferences preferences;
    Gson gson = new Gson();



    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat = (TextView) findViewById(R.id.lat);
        lon = (TextView) findViewById(R.id.lon);
        estado = (TextView) findViewById(R.id.estado);
        lista=(ListView) findViewById(R.id.lista);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        recordar=(Switch) findViewById(R.id.recordar);
        iniciar=(Button) findViewById(R.id.btnStart);
        parar=(Button) findViewById(R.id.btnParar);
        parar.setEnabled(false);
        coordenadas=new ArrayList<>();
        recuperarPreferencias();
       llenar1();




    }

    public void llenar1(){
        adaptador = new ArrayAdapter<Coordenadas>(this,android.R.layout.simple_list_item_1,coordenadas);
        lista.setAdapter(adaptador);
    }


    public void llenar(){
        try {

            SharedPreferences.Editor editor = preferences.edit();
            Set arregloCoordenadas = new HashSet();

            //String ventas=gson.toJson(personas);
            for (Coordenadas persona : coordenadas) {

                arregloCoordenadas.add(gson.toJson(persona));
            }
            editor.putStringSet("savepersonas", arregloCoordenadas);
            editor.commit();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void recuperarPreferencias(){
        try {
            Set arregloPersonas = new HashSet();
            arregloPersonas = preferences.getStringSet("savepersonas", null);

            if (arregloPersonas != null) {
                Type type = new TypeToken<ArrayList<Coordenadas>>() {
                }.getType();
                ArrayList<Coordenadas> prefPersonas=gson.fromJson(String.valueOf(arregloPersonas), type);
                coordenadas=prefPersonas;
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void iniciar(View view){

            timer=new Timer();
            ciclo = recordar.isChecked();
            parar.setEnabled(true);
            iniciar.setEnabled(false);
            recordar.setEnabled(false);

        //Creamos el Timer

        //Que actue cada 3000 milisegundos
        //Empezando des de el segundo 0
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //La función que queremos ejecutar
                FuncionParaEsteHilo();
            }
        }, 0, 5000);



    }

    public void FuncionParaEsteHilo(){
        this.runOnUiThread(Accion);
    }

    private Runnable Accion = new Runnable() {
        public void run() {
            //Aquí iría lo que queramos que haga,
            //en este caso mostrar un mensaje.
           llenarLista();
        }
    };

    public void parar(View view){
        timer.cancel();
        parar.setEnabled(false);
        iniciar.setEnabled(true);
        recordar.setEnabled(true);
    }



    public void llenarLista(){
        coordenadas.add(new Coordenadas(lat.getText().toString(), lon.getText().toString(), recordar.isChecked()));
        if (ciclo==true){

            llenar();
        }

        llenar1();



    }

    public void eliminar(View view){
        preferences.edit().remove("savepersonas").commit();
        llenar1();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StringBuffer stringBuffer = new StringBuffer();
        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        List<String> lista = locationManager.getProviders(criteria, true);
        if (lista.isEmpty()) {
            estado.setText("Ningun proveedor disponible");
        } else {
            for (String listado : lista) {
                stringBuffer.append(lista).append(" ");
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(listado, 1000, 0, this);
            }
            estado.setText(stringBuffer);
        }
        lat.setText("");
        lon.setText("");
    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat.setText(""+String.valueOf(location.getLatitude()));
        lon.setText(""+String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
