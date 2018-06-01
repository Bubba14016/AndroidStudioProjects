package com.example.examen.ubicacionparcial2;

public class Coordenadas {
    private String lat;
    private String lon;
    private boolean recordar;

    public Coordenadas(String lat, String lon, boolean recordar) {
        this.lat = lat;
        this.lon = lon;
        this.recordar = recordar;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public boolean isRecordar() {
        return recordar;
    }

    public void setRecordar(boolean recordar) {
        this.recordar = recordar;
    }

    @Override
    public String toString() {
        String rec;
        if (recordar){
            rec="si";
        }else{
            rec="no";
        }
        return "La:"+ lat+"\nLo:"+lon+"\n"+rec;
    }
}
