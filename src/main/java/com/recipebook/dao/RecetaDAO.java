package com.recipebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.recipebook.logic.Paso;
import com.recipebook.logic.Receta;

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
}