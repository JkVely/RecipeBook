package com.recipebook.dao;

import com.recipebook.logic.User;
import com.recipebook.logic.UsersContainer;
import com.recipebook.logic.Receta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private final SQLController sqlController;
    private final RecetaDao recetaDAO;

    public UserDao(SQLController sqlController) {
        this.sqlController = sqlController;
        this.recetaDAO = new RecetaDao(sqlController);
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

    /**
     * Obtiene todos los usuarios de la base de datos junto con sus recetas.
     * 
     * @return Contenedor de usuarios.
     */
    public UsersContainer obtenerUsuarios() {
        UsersContainer usersContainer = new UsersContainer();
        String selectUsersQuery = "SELECT * FROM Users";

        try {
            ResultSet rs = sqlController.executeQuery(selectUsersQuery);

            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String username = rs.getString("Name");
                String password = rs.getString("Password");

                User user = new User(username, password);
                user.setRecetas(recetaDAO.obtenerRecetasPorUsuario(userID));
                usersContainer.addUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersContainer;
    }

    /**
     * Elimina un usuario de la base de datos.
     * 
     * @param username Nombre del usuario a eliminar.
     * @return true si el usuario se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarUsuario(String username) {
        String deleteUserQuery = String.format(
            "DELETE FROM Users WHERE Name = '%s'", username
        );

        String obtainUserIDQuery = String.format(
            "SELECT UserID FROM Users WHERE Name = '%s'", username
        );

        try {
            ResultSet userIdQ = sqlController.executeQuery(obtainUserIDQuery);
                int userID = userIdQ.getInt("UserID");
                recetaDAO.eliminarTodasRecetasPorUsuario(userID);
            sqlController.executeUpdate(deleteUserQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtiene un usuario de la base de datos por su nombre.
     * 
     * @param username Nombre del usuario a obtener.
     * @return Usuario encontrado o null si no se encuentra.
     */
    public User obtenerUsuario(String username) {
        String selectUserQuery = String.format(
            "SELECT * FROM Users WHERE Name = '%s'", username
        );

        try {
            ResultSet rs = sqlController.executeQuery(selectUserQuery);

            if (rs.next()) {
                int userID = rs.getInt("UserID");
                String password = rs.getString("Password");

                User user = new User(username, password);
                user.setRecetas(recetaDAO.obtenerRecetasPorUsuario(userID));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Obtiene el ID de un usuario por su nombre.
     * 
     * @param username Nombre del usuario.
     * @return ID del usuario o -1 si no se encuentra.
     */
    public int obtenerUserID(String username) {
        String selectUserIDQuery = String.format(
            "SELECT UserID FROM Users WHERE Name = '%s'", username
        );

        try {
            ResultSet rs = sqlController.executeQuery(selectUserIDQuery);

            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Obtiene el RecetaDAO asociado.
     * 
     * @return RecetaDAO.
     */
    public RecetaDao getRecetaDAO() {
        return recetaDAO;
    }
}