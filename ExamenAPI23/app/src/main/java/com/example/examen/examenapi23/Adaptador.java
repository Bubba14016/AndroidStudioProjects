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

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Estudiante> {


    public Adaptador(@NonNull Context context, int resource, ArrayList<Estudiante> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Estudiante estudiante=getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_estudiante,parent, false);
        }
        TextView carnet=(TextView) convertView.findViewById(R.id.carnet);
        TextView nombre=(TextView) convertView.findViewById(R.id.nombre);
        Button btnBorrar=(Button) convertView.findViewById(R.id.btnBorrar);

        carnet.setText(estudiante.getCarnet());
        nombre.setText(formato(estudiante.getNombre()));
        btnBorrar.setTag(estudiante.getIdestudiante());

        if (estudiante.getExamindao().equals("0")){
            btnBorrar.setBackgroundColor(Color.parseColor("#ff669900"));
        }else {
            btnBorrar.setBackgroundColor(Color.parseColor("#ffcc0000"));
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

