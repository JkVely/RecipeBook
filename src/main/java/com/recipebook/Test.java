package com.recipebook;
import com.recipebook.logic.*;

public class Test {

    private Receta receta;

    public Test(Receta receta){
        this.receta = receta;
    }

    public void initialize() {
        this.getReceta(this.receta);
    }

    public void getReceta(Receta receta){
        if(receta != null){
            System.out.println("Receta: " + receta.getNombre());
            System.out.println("Tipo: " + receta.getTipo());
            System.out.println("Imagen: " + receta.getImagen());
            System.out.println("Descripcion: " + receta.getDescripcion());
            System.out.println("Ingredientes: ");
            for (String i : receta.getIngredientes()){
                System.out.println(i);
            }
            System.out.println("Utensilios: ");
            for (String u : receta.getUtensilios()){
                System.out.println(u);
            }
            System.out.println("Pasos: ");
            for (Paso p : receta.getPasos()){
                System.out.println("Paso " + p.getId());
                System.out.println("Descripcion: " + p.getDescripcion());
                System.out.println("Tiempo: " + p.getTiempo());
                System.out.println("Utensilios: ");
                for (String u : p.getUtensilios()){
                    System.out.println(u);
                }
                System.out.println("Ingredientes: ");
                for (String i : p.getIngredientes()){
                    System.out.println(i);
                }
                System.out.println("Imagen: " + p.getImagen());
            }
        }
    }

    public void setReceta(Receta receta){
        this.receta = receta;
        initialize();
    }
}
