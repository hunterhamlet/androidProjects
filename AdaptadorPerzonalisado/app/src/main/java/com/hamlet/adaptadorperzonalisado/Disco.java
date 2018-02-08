package com.hamlet.adaptadorperzonalisado;

/**
 * Created by IDS Comercial on 26/09/2017.
 */

public class Disco {
    int cover;
    String titulo,album,precio;

    public int getCover() {
        return cover;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAlbum() {
        return album;
    }

    public String getPrecio() {
        return precio;
    }

    public Disco(int cover, String titulo, String album, String precio) {
        this.cover = cover;
        this.titulo = titulo;
        this.album = album;
        this.precio = precio;
    }
}
