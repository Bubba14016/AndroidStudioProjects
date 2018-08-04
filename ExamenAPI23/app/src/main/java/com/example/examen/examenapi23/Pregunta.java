package com.example.examen.examenapi23;

public class Pregunta {
    private int idpregunta;
    private String item;
    private String ra;
    private String rb;
    private String rc;
    private String rd;
    private String rcorrecta;
    int correlativo;

    public Pregunta(int idpregunta, String item, String ra, String rb, String rc, String rd, String rcorrecta, int correlativo) {
        this.idpregunta = idpregunta;
        this.item = item;
        this.ra = ra;
        this.rb = rb;
        this.rc = rc;
        this.rd = rd;
        this.rcorrecta = rcorrecta;
        this.correlativo=correlativo;
    }

    public Pregunta(int anInt, String item, String ra, String rb, String rc, String rd, String rcorrecta) {
        this.item = item;
        this.ra = ra;
        this.rb = rb;
        this.rc = rc;
        this.rd = rd;
        this.rcorrecta = rcorrecta;
    }

    public Pregunta(String item, String ra, String rb, String rc, String rd, String rcorrecta) {
        this.item = item;
        this.ra = ra;
        this.rb = rb;
        this.rc = rc;
        this.rd = rd;
        this.rcorrecta = rcorrecta;
    }

    public int getIdpregunta() {
        return idpregunta;
    }

    public void setIdpregunta(int idpregunta) {
        this.idpregunta = idpregunta;
    }

    public String getItem() {
        return item;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setItem(String item) {
        this.item = item;
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

    public String getRcorrecta() {
        return rcorrecta;
    }

    public void setRcorrecta(String rcorrecta) {
        this.rcorrecta = rcorrecta;
    }
}
