package com.recipebook;
import com.recipebook.logic.*;

public class Main {
    private static Receta receta;
    public static void main(String[] args) {
        new Test(receta).initialize();
    }

    public static void setReceta(Receta receta) {
        Main.receta = receta;
    }

    public static Receta getReceta() {
        return receta;
    }

}
