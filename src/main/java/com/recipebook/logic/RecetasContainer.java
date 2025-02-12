package com.recipebook.logic;

import java.util.ArrayList;
import java.util.List;

public class RecetasContainer {
    private final List<Receta> recetas;

    public RecetasContainer(){
        recetas = new ArrayList<>();
    }

    public void setRecetas(List<Receta> recetas){
        this.recetas.clear();
        this.recetas.addAll(recetas);
    }

    public void addReceta(Receta receta){
        recetas.add(receta);
    }

    public void deleteReceta(Receta receta){
        recetas.remove(receta);
    }

    public Receta selectReceta(int id){
        return recetas.get(id);
    }

    public List<Receta> getRecetas(){
        return recetas;
    }
}
