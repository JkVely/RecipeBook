package com.recipebook;
import com.recipebook.logic.*;

public class Main {
    public static void main(String[] args) {
        Receta receta = new Receta("Tarta de Manzana", "imagen.jpg", "Deliciosa tarta de manzana", RecipeTypes.POSTRE);
        receta.addIngrediente("Manzanas");
        receta.addIngrediente("Harina");
        receta.addIngrediente("Azúcar");
        receta.addIngrediente("Huevos");
        receta.addUtensilio("Molde");
        receta.addUtensilio("Horno");
        receta.addStep("Pelar y cortar las manzanas", 10, new String[]{"Cuchillo"}, new String[]{"Manzanas"}, "imagen1.jpg");
        receta.addStep("Mezclar los ingredientes", 15, new String[]{"Bol", "Batidor"}, new String[]{"Harina", "Azúcar", "Huevos"}, "imagen2.jpg");
        receta.addStep("Hornear la mezcla", 45, new String[]{"Horno"}, new String[]{"Mezcla"}, "imagen3.jpg");

        System.out.println("Nombre: " + receta.getNombre());
        System.out.println("Tipo: " + receta.getTipo());
        System.out.println("Imagen: " + receta.getImagen());
        System.out.println("Descripción: " + receta.getDescripcion());
        System.out.println("Ingredientes: " + receta.getIngredientes());
        System.out.println("Utensilios: " + receta.getUtensilios());
        System.out.println("Tiempo total: " + receta.getDuracion());
        System.out.println("Valoración: " + receta.getValor());
    }
}
