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

        System.out.println("Receta added to the database.");

        sqlController.closeConnection();
        System.out.println("Connection closed.");
    }
}
