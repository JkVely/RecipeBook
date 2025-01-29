package com.recipebook.serialization;

import com.recipebook.logic.RecetasContainer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RecetasSerializer {

    private static final String FILE_PATH = "src/main/webapp/resources/recetas/recetas.ser";

    public void serializeRecetas(RecetasContainer recetasContainer) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(recetasContainer);
            System.out.println("Recetas serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RecetasContainer deserializeRecetas() {
        RecetasContainer recetasContainer = null;
        try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            recetasContainer = (RecetasContainer) in.readObject();
            System.out.println("Recetas deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recetasContainer;
    }
}