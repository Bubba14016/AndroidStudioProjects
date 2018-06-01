package com.example.bubba.localizacion14api23;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener{
    LocationManager locationManager;
    TextView latitud, longitud, proveedor, precision, estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        latitud=(TextView) findViewById(R.id.latitud);
        longitud=(TextView) findViewById(R.id.longitud);
        proveedor=(TextView) findViewById(R.id.proveedor);
        precision=(TextView) findViewById(R.id.precision);
        estado=(TextView) findViewById(R.id.estado);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();

        StringBuffer stringBuffer=new StringBuffer();
        Criteria criteria=new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        List<String> lista=locationManager.getProviders(criteria,true);
        if (lista.isEmpty()){
            estado.setText("Ningun proveedor disponible");
        }else{
            for(String listado:lista){
                stringBuffer.append(lista).append(" ");
                locationManager.requestLocationUpdates(listado,1000,0,this);
            }
            proveedor.setText(stringBuffer);
        }
        latitud.setText("");
        longitud.setText("");
        proveedor.setText("");
        precision.setText("");
        precision.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }
    public void click(View view){
        startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
    }

    @Override
    public void onLocationChanged(Location location) {
        latitud.setText("Latitud: "+String.valueOf(location.getLatitude()));
        longitud.setText("Longitud: "+String.valueOf(location.getLongitude()));
        proveedor.setText("Proveedor: "+String.valueOf(location.getProvider()));
        precision.setText("Precision: "+String.valueOf(location.getAccuracy()));
        precision.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
