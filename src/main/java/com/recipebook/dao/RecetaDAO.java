package com.recipebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.recipebook.logic.steps.Paso;
import com.recipebook.logic.steps.PasoSimple;
import com.recipebook.logic.Receta;
import com.recipebook.logic.RecetasContainer;
import com.recipebook.logic.RecipeTypes;

public class RecetaDAO {
    private final SQLController sqlController;

    public RecetaDAO(SQLController sqlController) {
        this.sqlController = sqlController;
    }

    /**
     * Agrega una receta a la base de datos.
     * 
     * @param receta Receta a agregar.
     * @param userID ID del usuario al que pertenece la receta.
     * @return true si la receta se agregó correctamente, false en caso contrario.
     */
    public boolean agregarReceta(Receta receta, int userID) {
        String insertRecetaQuery = String.format(
            "INSERT INTO Recetas (UserID, Nombre, Tipo, Imagen, Descripcion, Tiempo, Valor) VALUES (%d, '%s', '%s', '%s', '%s', %d, %.2f)",
            userID, receta.getNombre(), receta.getTipo(), receta.getImagen(), receta.getDescripcion(), receta.getTiempo(), receta.getValor()
        );

        try {
            sqlController.executeUpdate(insertRecetaQuery);
            ResultSet rs = sqlController.executeQuery("SELECT SCOPE_IDENTITY() AS RecetaID");
            rs.next();
            int recetaID = rs.getInt("RecetaID");

            for (Paso paso : receta.getPasos()) {
                String insertPasoQuery = String.format(
                    "INSERT INTO Pasos (RecetaID, Descripcion, Tiempo, Imagen) VALUES (%d, '%s', %d, '%s')",
                    recetaID, paso.getDescripcion(), paso.getTiempo(), paso.getImagen()
                );
                sqlController.executeUpdate(insertPasoQuery);
            }

            for (String utensilio : receta.getUtensilios()) {
                String insertUtensilioQuery = String.format(
                    "INSERT INTO Utensilios (RecetaID, Nombre) VALUES (%d, '%s')",
                    recetaID, utensilio
                );
                sqlController.executeUpdate(insertUtensilioQuery);
            }

            for (String ingrediente : receta.getIngredientes()) {
                String insertIngredienteQuery = String.format(
                    "INSERT INTO Ingredientes (RecetaID, Nombre) VALUES (%d, '%s')",
                    recetaID, ingrediente
                );
                sqlController.executeUpdate(insertIngredienteQuery);
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las recetas de la base de datos.
     * 
     * @return Contenedor de recetas.
     */
    public RecetasContainer obtenerRecetas() {
        RecetasContainer recetasContainer = new RecetasContainer();
        String selectRecetasQuery = "SELECT * FROM Recetas";

        try {
            ResultSet rs = sqlController.executeQuery(selectRecetasQuery);

            while (rs.next()) {
                Receta receta = mapResultSetToReceta(rs);
                recetasContainer.addReceta(receta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recetasContainer;
    }

    /**
     * Obtiene todas las recetas de un usuario específico.
     * 
     * @param userID ID del usuario.
     * @return Contenedor de recetas del usuario.
     */
    public RecetasContainer obtenerRecetasPorUsuario(int userID) {
        RecetasContainer recetasContainer = new RecetasContainer();
        String selectRecetasQuery = String.format("SELECT * FROM Recetas WHERE UserID = %d", userID);

        try {
            ResultSet rs = sqlController.executeQuery(selectRecetasQuery);

            while (rs.next()) {
                Receta receta = mapResultSetToReceta(rs);
                recetasContainer.addReceta(receta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recetasContainer;
    }

    /**
     * Obtiene todas las recetas de un tipo específico.
     * 
     * @param tipo Tipo de receta.
     * @return Contenedor de recetas del tipo especificado.
     */
    public RecetasContainer obtenerRecetasPorTipo(RecipeTypes tipo) {
        RecetasContainer recetasContainer = new RecetasContainer();
        String selectRecetasQuery = String.format("SELECT * FROM Recetas WHERE Tipo = '%s'", tipo.name());

        try {
            ResultSet rs = sqlController.executeQuery(selectRecetasQuery);

            while (rs.next()) {
                Receta receta = mapResultSetToReceta(rs);
                recetasContainer.addReceta(receta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recetasContainer;
    }

    /**
     * Mapea un ResultSet a un objeto Receta.
     * 
     * @param rs ResultSet a mapear.
     * @return Objeto Receta.
     * @throws SQLException Si ocurre un error al mapear el ResultSet.
     */
    private Receta mapResultSetToReceta(ResultSet rs) throws SQLException {
        int recetaID = rs.getInt("RecetaID");
        String nombre = rs.getString("Nombre");
        RecipeTypes tipo = RecipeTypes.valueOf(rs.getString("Tipo"));
        String imagen = rs.getString("Imagen");
        String descripcion = rs.getString("Descripcion");
        int tiempo = rs.getInt("Tiempo");
        double valor = rs.getDouble("Valor");

        Receta receta = new Receta(nombre, imagen, descripcion, tipo);
        receta.setTiempo(tiempo);
        receta.setValor(valor);

        // Obtener pasos
        String selectPasosQuery = String.format("SELECT * FROM Pasos WHERE RecetaID = %d", recetaID);
        ResultSet rsPasos = sqlController.executeQuery(selectPasosQuery);
        while (rsPasos.next()) {
            int pasoID = rsPasos.getInt("PasoID");
            String pasoDescripcion = rsPasos.getString("Descripcion");
            int pasoTiempo = rsPasos.getInt("Tiempo");
            String pasoImagen = rsPasos.getString("Imagen");

            Paso paso = new PasoSimple(pasoID, pasoDescripcion, pasoTiempo, pasoImagen);
            receta.getPasos().add(paso);
        }

        // Obtener utensilios
        String selectUtensiliosQuery = String.format("SELECT * FROM Utensilios WHERE RecetaID = %d", recetaID);
        ResultSet rsUtensilios = sqlController.executeQuery(selectUtensiliosQuery);
        while (rsUtensilios.next()) {
            String utensilio = rsUtensilios.getString("Nombre");
            receta.getUtensilios().add(utensilio);
        }

        // Obtener ingredientes
        String selectIngredientesQuery = String.format("SELECT * FROM Ingredientes WHERE RecetaID = %d", recetaID);
        ResultSet rsIngredientes = sqlController.executeQuery(selectIngredientesQuery);
        while (rsIngredientes.next()) {
            String ingrediente = rsIngredientes.getString("Nombre");
            receta.getIngredientes().add(ingrediente);
        }

        return receta;
    }

    /**
     * Elimina una receta de un usuario específico.
     * 
     * @param recetaID ID de la receta a eliminar.
     * @param userID ID del usuario al que pertenece la receta.
     * @return true si la receta se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarReceta(int recetaID, int userID) {
        try {
            // Eliminar referencias en Pasos
            String deletePasosQuery = String.format("DELETE FROM Pasos WHERE RecetaID = %d", recetaID);
            sqlController.executeUpdate(deletePasosQuery);

            // Eliminar referencias en Utensilios
            String deleteUtensiliosQuery = String.format("DELETE FROM Utensilios WHERE RecetaID = %d", recetaID);
            sqlController.executeUpdate(deleteUtensiliosQuery);

            // Eliminar referencias en Ingredientes
            String deleteIngredientesQuery = String.format("DELETE FROM Ingredientes WHERE RecetaID = %d", recetaID);
            sqlController.executeUpdate(deleteIngredientesQuery);

            // Eliminar la receta
            String deleteRecetaQuery = String.format("DELETE FROM Recetas WHERE RecetaID = %d AND UserID = %d", recetaID, userID);
            int affectedRows = sqlController.executeUpdate(deleteRecetaQuery);

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina todas las recetas de un usuario específico.
     * 
     * @param userID ID del usuario.
     * @return true si las recetas se eliminaron correctamente, false en caso contrario.
     */
    public boolean eliminarTodasRecetasPorUsuario(int userID) {
        try {
            // Obtener todas las recetas del usuario
            String selectRecetasQuery = String.format("SELECT RecetaID FROM Recetas WHERE UserID = %d", userID);
            ResultSet rs = sqlController.executeQuery(selectRecetasQuery);

            // Eliminar referencias de cada receta
            while (rs.next()) {
                int recetaID = rs.getInt("RecetaID");

                // Eliminar referencias en Pasos
                String deletePasosQuery = String.format("DELETE FROM Pasos WHERE RecetaID = %d", recetaID);
                sqlController.executeUpdate(deletePasosQuery);

                // Eliminar referencias en Utensilios
                String deleteUtensiliosQuery = String.format("DELETE FROM Utensilios WHERE RecetaID = %d", recetaID);
                sqlController.executeUpdate(deleteUtensiliosQuery);

                // Eliminar referencias en Ingredientes
                String deleteIngredientesQuery = String.format("DELETE FROM Ingredientes WHERE RecetaID = %d", recetaID);
                sqlController.executeUpdate(deleteIngredientesQuery);
            }

            // Eliminar todas las recetas del usuario
            String deleteRecetasQuery = String.format("DELETE FROM Recetas WHERE UserID = %d", userID);
            int affectedRows = sqlController.executeUpdate(deleteRecetasQuery);

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}