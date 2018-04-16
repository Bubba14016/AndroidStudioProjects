package com.example.bubba.compras16api23;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;

/**
 * Created by Bubba on 10/04/2018.
 */

public class Productos implements Comparable<Productos> {
    private String nombre;
    private float precio;

    public Productos(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        DecimalFormat df =new DecimalFormat("0.00");
        return nombre+"\n"+df.format(precio);
    }

    @Override
    public int compareTo(@NonNull Productos productos) {
        return new String(getNombre()).compareTo(productos.getNombre());
    }
}
