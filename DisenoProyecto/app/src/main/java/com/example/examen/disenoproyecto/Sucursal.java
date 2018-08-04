package com.example.examen.disenoproyecto;

public class Sucursal {
    int idsucursal, idempresa;
    String nombre,direccion;

    public Sucursal(int idsucursal, int idempresa, String nombre, String direccion) {
        this.idsucursal = idsucursal;
        this.idempresa = idempresa;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Sucursal(int idempresa, String nombre, String direccion) {
        this.idempresa = idempresa;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return  nombre + "\n"+ direccion ;
    }
}
