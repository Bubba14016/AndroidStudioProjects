package com.example.bubba.gasolinera12api23;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Bubba on 14/03/2018.
 */

public class Ventas implements Parcelable, Serializable {
    private String tipo;
    private float precio;
    private float venta;

    public Ventas(String tipo, float precio, float venta) {
        this.tipo = tipo;
        this.precio = precio;
        this.venta = venta;
    }

    protected Ventas(Parcel in) {
        tipo = in.readString();
        precio = in.readFloat();
        venta = in.readFloat();
    }

    public static final Creator<Ventas> CREATOR = new Creator<Ventas>() {
        @Override
        public Ventas createFromParcel(Parcel in) {
            return new Ventas(in);
        }

        @Override
        public Ventas[] newArray(int size) {
            return new Ventas[size];
        }
    };

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getVenta() {
        return venta;
    }

    public void setVenta(float venta) {
        this.venta = venta;
    }

    public float galones(){
        return venta/precio;
    }

    @Override
    public String toString() {
        DecimalFormat df=new DecimalFormat("0.00");


        return "Tipo= "+ tipo+"\nPrecio= $"+df.format(precio)+"\nVenta= $"+df.format(venta)+"\nGalones= $"+df.format(galones());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tipo);
        parcel.writeFloat(precio);
        parcel.writeFloat(venta);
    }
}
