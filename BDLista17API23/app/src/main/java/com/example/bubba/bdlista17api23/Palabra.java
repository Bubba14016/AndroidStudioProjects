package com.example.bubba.bdlista17api23;

/**
 * Created by Bubba on 17/04/2018.
 */

public class Palabra {
    private int id;
    private String palabra;

    public Palabra(int id, String palabra) {
        this.id = id;
        this.palabra = palabra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    @Override
    public String toString() {
        return  palabra ;
    }
}
