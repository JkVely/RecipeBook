package com.recipebook.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Receta implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private RecipeTypes tipo;
    private Optional<String> imagen;
    private Optional<String> descripcion;
    private final List<String> ingredientes;
    private final List<String> utensilios;
    private int tiempo; // en minutos.segundos
    private String duracion;
    private final List<Paso> pasos;
    private double valor;
    private final List<Double> valoracion;

    public Receta(String nombre, String imagen, String descripcion, RecipeTypes tipo){
        this.nombre = nombre;
        this.tipo = tipo;
        this.imagen = Optional.ofNullable(imagen);
        this.descripcion = Optional.ofNullable(descripcion);
        ingredientes = new ArrayList<>();
        utensilios = new ArrayList<>();
        pasos = new ArrayList<>();
        valoracion = new ArrayList<>();
        valor = 0;
    }
    
    public void addStep(String descripcion, int tiempo, String[] utensilios, String[] ingredientes, String imagen){
        int id = pasos.size() + 1;
        pasos.add(new Paso(id, descripcion, tiempo, utensilios, ingredientes, imagen));
    }
    
    public void deleteStep(int id){
        int _id = id - 1;
        pasos.remove(_id);
    }

    public Paso selectStep(int id){
        int _id = id - 1;
        return pasos.get(_id);
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setTipo(RecipeTypes tipo){
        this.tipo = tipo;
    }

    public RecipeTypes getTipo(){
        return this.tipo;
    }

    public void setImagen(String imagen){
        this.imagen = Optional.ofNullable(imagen);
    }

    public String getImagen(){
        return this.imagen.orElse(null);
    }

    public void setDescripcion(String descripcion){
        this.descripcion = Optional.ofNullable(descripcion);
    }

    public String getDescripcion(){
        return this.descripcion.orElse(null);
    }

    public void addIngrediente(String ingrediente){
        ingredientes.add(ingrediente);
    }

    public void deleteIngrediente(String ingrediente){
        ingredientes.remove(ingrediente);
    }

    public List<String> getIngredientes(){
        return ingredientes;
    }

    public void addUtensilio(String utensilio){
        utensilios.add(utensilio);
    }

    public void deleteUtensilio(String utensilio){
        utensilios.remove(utensilio);
    }

    public List<String> getUtensilios(){
        return utensilios;
    }

    public int getTiempo(){
        int tiempo_ = 0;
        for(Paso paso : pasos){
            tiempo_ += paso.getTiempo();
        }
        this.tiempo = tiempo_;
        return this.tiempo;
    }

    public String getDuracion(){
        int minutos = (int) getTiempo() / 60;
        int segundos = getTiempo() - (minutos*60);
        this.duracion = minutos + " minutos " + segundos + " segundos";
        return this.duracion;
    }

    public double getValor(){
        return this.valor;
    }

    public void addValoracion(double valor){
        valoracion.add(valor);
        double valor_ = 0;
        for(double val : valoracion){
            valor_ += val;
        }
        this.valor = valor_ / valoracion.size();
    }

    public List<Paso> getPasos(){
        return pasos;
    }
}
