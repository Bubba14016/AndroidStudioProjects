package com.example.bubba.tareaparcial1api23;

import java.io.Serializable;

/**
 * Created by Bubba on 03/04/2018.
 */

public class Persona implements Serializable{
    private String nombre;
    private String apellido;
    private int edad;
    private boolean sexo;



    public Persona(String nombre, String apellido, int edad, boolean sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo= sexo;


    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
