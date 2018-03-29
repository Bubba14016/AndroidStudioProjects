package com.example.bubba.gasolinera12api23;

/**
 * Created by Bubba on 14/03/2018.
 */

public class Precios {
    private float precioGalonSuper;
    private float precioGalonRegular;
    private float precioGalonDisel;

    public Precios(float precioGalonSuper, float precioGalonRegular, float precioGalonDisel) {
        this.precioGalonSuper = precioGalonSuper;
        this.precioGalonRegular = precioGalonRegular;
        this.precioGalonDisel = precioGalonDisel;
    }

    public float getPrecioGalonSuper() {
        return precioGalonSuper;
    }

    public void setPrecioGalonSuper(float precioGalonSuper) {
        this.precioGalonSuper = precioGalonSuper;
    }

    public float getPrecioGalonRegular() {
        return precioGalonRegular;
    }

    public void setPrecioGalonRegular(float precioGalonRegular) {
        this.precioGalonRegular = precioGalonRegular;
    }

    public float getPrecioGalonDisel() {
        return precioGalonDisel;
    }

    public void setPrecioGalonDisel(float precioGalonDisel) {
        this.precioGalonDisel = precioGalonDisel;
    }


}
