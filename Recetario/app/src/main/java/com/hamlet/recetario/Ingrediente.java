package com.hamlet.recetario;

import java.io.Serializable;

public class Ingrediente implements Serializable {
    //Fields
    String cantidad,nombre;

    //Getter

    public String getCantidad() {
        return cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    //Constructor

    public Ingrediente(String cantidad, String nombre) {
        this.cantidad = cantidad;
        this.nombre = nombre;
    }
}
