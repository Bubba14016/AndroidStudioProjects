package com.example.examen.examenapi23;

public class Estudiante {
    private int idestudiante;
    private String carnet;
    private String nombre;
    private String examindao;

    public Estudiante(int idestudiante, String carnet, String nombre, String examindao) {
        this.idestudiante = idestudiante;
        this.carnet = carnet;
        this.nombre = nombre;
        this.examindao = examindao;
    }

    public Estudiante(String carnet, String nombre, String examindao) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.examindao = examindao;
    }

    public int getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(int idestudiante) {
        this.idestudiante = idestudiante;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExamindao() {
        return examindao;
    }

    public void setExamindao(String examindao) {
        this.examindao = examindao;
    }

    @Override
    public String toString() {
        return carnet+"\n"+nombre;
    }
}
