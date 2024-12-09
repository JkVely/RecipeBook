package com.recipebook;

import com.recipebook.dao.RecetaDAO;
import com.recipebook.dao.SQLController;
import com.recipebook.logic.Receta;
import com.recipebook.logic.RecipeTypes;

public class Test {
    public static void main(String[] args) {
        SQLController sqlController = new SQLController();
        if (sqlController.isConnected()) {
            System.out.println("Connected to the database.");
        } else {
            System.out.println("Failed to connect to the database.");
            return;
        }
        RecetaDAO recetaDAO = new RecetaDAO(sqlController);
        System.out.println("RecetaDAO created.");
        Receta receta = new Receta("Receta de prueba", "imagen.jpg", "Descripción de la receta", RecipeTypes.POSTRE);
        receta.addStep("Paso 1", 10, new String[] { "Cuchara", "Olla" }, new String[] { "Azúcar", "Leche" }, "paso1.jpg");
        receta.addStep("Paso 2", 20, new String[] { "Cuchara", "Olla" }, new String[] { "Azúcar", "Leche" }, "paso2.jpg");
        receta.addStep("Paso 3", 30, new String[] { "Cuchara", "Olla" }, new String[] { "Azúcar", "Leche" }, "paso3.jpg");

        recetaDAO.agregarReceta(receta);
        System.out.println("Receta added to the database.");

        sqlController.closeConnection();
        System.out.println("Connection closed.");
    }
}
