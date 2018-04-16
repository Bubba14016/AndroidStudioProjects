package com.example.bubba.compras16api23;

import java.text.DecimalFormat;

/**
 * Created by Bubba on 10/04/2018.
 */

public class Compras {
    //private String nombre;
    //private float precio;
    private Productos producto;
    private int posicionOriginal;

    public Compras(Productos producto, int posicionOriginal) {
        this.producto = producto;
        this.posicionOriginal = posicionOriginal;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public int getPosicionOriginal() {
        return posicionOriginal;
    }

    public void setPosicionOriginal(int posicionOriginal) {
        this.posicionOriginal = posicionOriginal;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return producto.getNombre()+"\n"+df.format(producto.getPrecio());
    }
}
