package com.recipebook;

import com.recipebook.dao.SQLController;

public class Test {
    public static void main(String[] args) {
        SQLController sqlController = new SQLController();
        if (sqlController.isConnected()) {
            System.out.println("Connected to the database.");
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
