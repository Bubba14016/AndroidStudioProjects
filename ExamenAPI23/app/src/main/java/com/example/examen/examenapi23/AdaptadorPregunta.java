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

public class AdaptadorPregunta extends ArrayAdapter<Pregunta> {


    public AdaptadorPregunta(@NonNull Context context, int resource, ArrayList<Pregunta> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pregunta p=getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_custom_estudiante,parent, false);
        }
        TextView item=(TextView) convertView.findViewById(R.id.textView3);
        Button btnEditar=(Button) convertView.findViewById(R.id.button3);
        Button btnBorrar=(Button) convertView.findViewById(R.id.button2);

        item.setText(p.getItem());
        btnEditar.setTag(p.getIdpregunta());
        btnBorrar.setTag(p.getIdpregunta());

        if (MainActivity.baseSQLite.examinado()){
            btnEditar.setBackgroundColor(Color.parseColor("#ffcc0000"));
            btnBorrar.setBackgroundColor(Color.parseColor("#ffcc0000"));
        }else{
            btnBorrar.setBackgroundColor(Color.parseColor("#ff669900"));
            btnEditar.setBackgroundColor(Color.parseColor("#ff669900"));
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

