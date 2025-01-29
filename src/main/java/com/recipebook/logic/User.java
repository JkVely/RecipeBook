package com.recipebook.logic;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private RecetasContainer recetas;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        recetas = new RecetasContainer();
    }

    public void addReceta(Receta receta){
        recetas.addReceta(receta);
    }

    public void deleteReceta(Receta receta){
        recetas.deleteReceta(receta);
    }

    public Receta selectReceta(int id){
        return recetas.selectReceta(id);
    }

    public RecetasContainer getRecetas(){
        return recetas;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setRecetas(RecetasContainer recetas){
        this.recetas = recetas;
    }

    public boolean validatePassword(String password){
        return this.password.equals(password);
    }
}
