package com.recipebook.logic;

import java.io.Serializable;
import java.util.Optional;

public class Paso implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private String descripcion;
    private int tiempo; 
    private String[] utensilios;
    private String[] ingredientes;
    private Optional<String> imagen;

    public Paso(int id, String descripcion, int tiempo, String[] utensilios, String[] ingredientes, String imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.utensilios = utensilios;
        this.ingredientes = ingredientes;
        this.imagen = Optional.ofNullable(imagen);
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getTiempo() {
        return tiempo;
    }

    public String[] getUtensilios() {
        return utensilios;
    }

    public String[] getIngredientes() {
        return ingredientes;
    }

    public String getImagen() {
        return imagen.orElse(null);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setUtensilios(String[] utensilios) {
        this.utensilios = utensilios;
    }

    public void setIngredientes(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setImagen(String imagen) {
        this.imagen = Optional.ofNullable(imagen);
    }
}