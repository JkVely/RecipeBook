package com.recipebook.dao;

import com.recipebook.logic.User;
import com.recipebook.logic.Receta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final SQLController sqlController;
    private final RecetaDAO recetaDAO;

    public UserDao(SQLController sqlController) {
        this.sqlController = sqlController;
        this.recetaDAO = new RecetaDAO(sqlController);
    }

    /**
     * Agrega un usuario a la base de datos.
     * 
     * @param user Usuario a agregar.
     * @return true si el usuario se agregó correctamente, false en caso contrario.
     */
    public boolean agregarUsuario(User user) {
        String insertUserQuery = String.format(
            "INSERT INTO Users (Name, Password) VALUES ('%s', '%s')",
            user.getUsername(), user.getPassword()
        );

        try {
            sqlController.executeUpdate(insertUserQuery);
            ResultSet rs = sqlController.executeQuery("SELECT SCOPE_IDENTITY() AS UserID");
            rs.next();
            int userID = rs.getInt("UserID");

            for (Receta receta : user.getRecetas().getRecetas()) {
                recetaDAO.agregarReceta(receta, userID);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Otros métodos como obtenerUsuario y eliminarUsuario pueden permanecer igual
}