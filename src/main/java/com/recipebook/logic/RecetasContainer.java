package com.recipebook.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecetasContainer implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Receta> recetas;

    public RecetasContainer(){
        recetas = new ArrayList<>();
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
