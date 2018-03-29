package com.example.bubba.listapersonalizadaapi23;

/**
 * Created by Bubba on 07/03/2018.
 */

public class persona {
    private String nombre;
    private int edad;
    private boolean genero;

    public persona(String nombre, int edad, boolean genero) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        String sexo="";
        if (genero){
            sexo="Masculino";
        }else{
            sexo="Femenino";
        }
        return  "Nombre: " + nombre + '\n' +
                "Edad: " + edad + '\n' +
                "Genero: " + sexo ;
    }
}
