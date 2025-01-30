package com.recipebook.serialization;

import com.recipebook.logic.UsersContainer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.ArrayList;

public class UsersSerializer {

    private static final String FILE_PATH = "src\\main\\webapp\\resources\\users\\users.ser";
    private File file = new File(FILE_PATH);

    public UsersSerializer() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeUser(UsersContainer usersData) {
        try {
            try (FileOutputStream fileOut = new FileOutputStream(file);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(usersData);
                System.out.println("Users serialized successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UsersContainer deserializeUser() {
        UsersContainer usersContainer = null;
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                try (FileInputStream fileIn = new FileInputStream(file);
                        ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    usersContainer = (UsersContainer) in.readObject();
                    System.out.println("Users deserialized successfully.");
                }
            } else {
                usersContainer = new UsersContainer();
                usersContainer.setUsers(new ArrayList<>());
                System.out.println("File not found. Returning empty UsersContainer.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usersContainer;
    }
}