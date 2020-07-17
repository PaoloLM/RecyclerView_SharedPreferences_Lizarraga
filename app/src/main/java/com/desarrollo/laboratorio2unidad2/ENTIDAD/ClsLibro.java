package com.desarrollo.laboratorio2unidad2.ENTIDAD;

public class ClsLibro {

    private String titulo;
    private String descripcion;
    private String descripcionlarga;
    private int imagen;

    public ClsLibro(String titulo, String descripcion, String descripcionlarga, int imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.descripcionlarga = descripcionlarga;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionlarga() {
        return descripcionlarga;
    }

    public void setDescripcionlarga(String descripcionlarga) {
        this.descripcionlarga = descripcionlarga;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
