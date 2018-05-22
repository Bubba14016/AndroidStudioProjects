package com.example.examen.examenapi23;

public class Preguntas {
    private int idpreguntas;
    private int idestudiante;
    private int idrespuestas;

    public Preguntas(int idpreguntas, int idestudiante, int idrespuestas) {
        this.idrespuestas = idrespuestas;
        this.idestudiante = idestudiante;
        this.idpreguntas = idpreguntas;
    }

    public int getIdrespuestas() {
        return idrespuestas;
    }

    public void setIdrespuestas(int idrespuestas) {
        this.idrespuestas = idrespuestas;
    }

    public int getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(int idestudiante) {
        this.idestudiante = idestudiante;
    }

    public int getIdpreguntas() {
        return idpreguntas;
    }

    public void setIdpreguntas(int idpreguntas) {
        this.idpreguntas = idpreguntas;
    }
}
