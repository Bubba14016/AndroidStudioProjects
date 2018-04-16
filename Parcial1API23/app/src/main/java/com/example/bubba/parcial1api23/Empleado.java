package com.example.bubba.parcial1api23;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by Bubba on 04/04/2018.
 */

public class Empleado implements Serializable{
    private String nombre;
    private String apellido;
    private Double sueldo;
    private String departamento;

    public Empleado(String nombre, String apellido, Double sueldo, String departamento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sueldo = sueldo;
        this.departamento = departamento;
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

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        double isss=0;
        double afp=sueldo*0.0725;

        DecimalFormat df=new DecimalFormat("0.00");
        if (sueldo>1000){
            isss=30;
        }else{
            isss=sueldo*0.03;
        }

        double liq=sueldo-isss-afp;
        String planilla=apellido+" "+nombre+"\n"+" Sueldo ($) "+df.format(sueldo)
                +"\n ISSS ($)"+df.format(isss)+"\n"+" AFP ($) "+ df.format(afp)
                +"\n Liquido ($)"+df.format(liq);
        return planilla;
    }
}
