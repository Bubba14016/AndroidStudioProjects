package com.example.examen.disenoproyecto;

public class Empresa {
    int id;
    String nombre, descripcion, correo, contra, telefono;

    public Empresa(int id, String nombre, String descripcion, String correo, String contra, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correo = correo;
        this.contra = contra;
        this.telefono=telefono;
    }

    public Empresa(String nombre, String descripcion, String correo, String contra, String telefono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correo = correo;
        this.contra = contra;
        this.telefono=telefono;
    }

    public int getIdempresa() {
        return id;
    }

    public void setIdempresa(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
