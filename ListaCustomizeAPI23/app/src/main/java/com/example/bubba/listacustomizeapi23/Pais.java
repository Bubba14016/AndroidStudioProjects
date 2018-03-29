package com.example.bubba.listacustomizeapi23;

/**
 * Created by Bubba on 07/03/2018.
 */

public class Pais {
    String nombre;
    String capital;
    Integer bandera;

    public Pais(String nombre, String capital, Integer bandera) {
        this.nombre = nombre;
        this.capital = capital;
        this.bandera = bandera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }
}
