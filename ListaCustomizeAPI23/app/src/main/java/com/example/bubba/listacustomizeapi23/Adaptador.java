package com.example.bubba.listacustomizeapi23;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bubba on 07/03/2018.
 */

public class Adaptador extends ArrayAdapter<Pais> {
    ArrayList<Pais> items;
    public Adaptador(Context context, int resource, ArrayList<Pais> objects) {
        super(context, resource,  objects);
        items=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Pais pais=getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_custom,parent,false);
        }
        TextView tvPais=(TextView) convertView.findViewById(R.id.tvPais);
        TextView tvCapital=(TextView) convertView.findViewById(R.id.tvCapital);
        ImageView bandera=(ImageView) convertView.findViewById(R.id.img);

        tvPais.setText(pais.nombre);
        tvCapital.setText(pais.capital);
        bandera.setImageResource(pais.bandera);
        return convertView;
    }


    @Override
    public Pais getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
