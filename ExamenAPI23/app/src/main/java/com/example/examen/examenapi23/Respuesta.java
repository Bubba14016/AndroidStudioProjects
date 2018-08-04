package com.example.examen.examenapi23;

public class Respuesta {
    private int idpreguntas;
    //private int idestudiante;
    //private int idrespuestas;
    int correlativo;
    String ra,rb,rc,rd, item, restudiante;

    public Respuesta(int idpreguntas, String ra, String rb, String rc, String rd, String rcorrecta, int correlativo) {
        this.idpreguntas = idpreguntas;
        this.correlativo = correlativo;
        this.ra = ra;
        this.rb = rb;
        this.rc = rc;
        this.rd = rd;
        this.item = rcorrecta;
    }

    public int getIdpreguntas() {
        return idpreguntas;
    }

    public void setIdpreguntas(int idpreguntas) {
        this.idpreguntas = idpreguntas;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getRb() {
        return rb;
    }


    public void setRb(String rb) {
        this.rb = rb;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String rcorrecta) {
        this.item = rcorrecta;
    }

    public String getRestudiante() {
        return restudiante;
    }

    public void setRestudiante(String restudiante) {
        this.restudiante = restudiante;
    }
}
