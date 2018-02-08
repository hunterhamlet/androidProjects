package com.example.hamonpc.destinosturisticos;

import java.io.Serializable;

/**
 * Created by HamonPC on 27/09/2017.
 */

public class Destino implements Serializable {
    //Fields
    String nombre,pais,atractivos,costoaproximado,urlDestino;

    //Getters

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    public String getAtractivos() {
        return atractivos;
    }

    public String getCostoaproximado() {
        return costoaproximado;
    }

    public String getUrDestino() {
        return urlDestino;
    }

    //constructor

    public Destino(String nombre, String pais, String atractivos, String costoaproximado,String urlDestino) {
        this.nombre = nombre;
        this.pais = pais;
        this.atractivos = atractivos;
        this.costoaproximado = costoaproximado;
        this.urlDestino =urlDestino;
    }
}
