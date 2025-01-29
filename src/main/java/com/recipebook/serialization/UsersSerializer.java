package com.recipebook.serialization;

import com.recipebook.logic.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UsersSerializer {

    private static final String FILE_PATH = "src/main/webapp/recetas/users.ser";

    public void serializeUser(User user) {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(user);
            System.out.println("User serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User deserializeUser() {
        User user = null;
        try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            user = (User) in.readObject();
            System.out.println("User deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }
}