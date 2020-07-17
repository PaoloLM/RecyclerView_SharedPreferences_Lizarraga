package com.desarrollo.laboratorio2unidad2.ENTIDAD;

public class ClsHistorial {

    private String contenido;
    private String titulo;

    public ClsHistorial(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
