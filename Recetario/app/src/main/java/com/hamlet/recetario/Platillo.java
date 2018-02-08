package com.hamlet.recetario;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by IDS Comercial on 26/09/2017.
 */

public class Platillo implements Serializable {
    //Fields
    String nombre,receta;
    int imagen;
    ArrayList<Ingrediente> ingredientes;

    //Getters


    public String getNombre() {
        return nombre;
    }

    public String getReceta() {
        return receta;
    }

    public int getImagen() {
        return imagen;
    }

    //Constructor


    public Platillo(String nombre, String receta, int imagen, ArrayList<Ingrediente> ingredientes) {
        this.nombre = nombre;
        this.receta = receta;
        this.imagen = imagen;
        this.ingredientes = ingredientes;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }
}
