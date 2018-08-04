package com.example.examen.examenapi23;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorNotas extends ArrayAdapter<Estudiante> {


    public AdaptadorNotas(@NonNull Context context, int resource, ArrayList<Estudiante> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Estudiante e=getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_notas,parent, false);
        }
        TextView carnet=(TextView) convertView.findViewById(R.id.carnet);
        TextView nombre=(TextView) convertView.findViewById(R.id.nombre);
        TextView lanota=(TextView) convertView.findViewById(R.id.lanota);


        carnet.setText(e.getCarnet());
        nombre.setText(formato(e.getNombre()));
        double nota=MainActivity.baseSQLite.calculoNota(e.getIdestudiante());
        if (e.getExamindao().equals(0)){
            lanota.setText("No Examinado");
        }else{
            DecimalFormat df=new DecimalFormat("0.00");
            lanota.setText(String.valueOf(df.format(nota)));
        }



        
        return convertView;
    }

    public String formato(String nombre){
        String nuevacadena="";
        String[] palabras=nombre.split(" ");
        for (int i=0;i<palabras.length;i++){
            nuevacadena+=palabras[i].substring(0,1).toUpperCase()+palabras[i].substring(1,palabras[i].length()).toLowerCase();
        }
        nuevacadena=nuevacadena.trim();
        return nuevacadena;
    }
}

