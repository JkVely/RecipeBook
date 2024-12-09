package com.recipebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.recipebook.logic.Paso;
import com.recipebook.logic.Receta;

/**
 * RecetaDAO (Data Access Object) es una clase que se encarga de manejar la
 * persistencia de las recetas en la base de datos.
 * Proporciona métodos para agregar, borrar y obtener información de las
 * recetas.
 * 
 * @see com.recipebook.logic.Receta
 * @see com.recipebook.logic.Paso
 * @see com.recipebook.logic.RecipeTypes
 */
public class RecetaDAO {
    private final SQLController sqlController;

    public RecetaDAO(SQLController sqlController) {
        this.sqlController = sqlController;
        try {
            sqlController.executeUpdate("USE recipe");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al seleccionar la base de datos.");
        }
    }

    /**
     * Agrega una receta a la base de datos.
     * 
     * @param receta Receta a agregar.
     * @return true si la receta se agregó correctamente, false en caso contrario.
     */
    public boolean agregarReceta(Receta receta) {
        String insertRecetaQuery = String.format(
            "INSERT INTO Recetas (Nombre, Tipo, Imagen, Descripcion, Tiempo, Valor) VALUES ('%s', '%s', '%s', '%s', %d, %.2f)",
            receta.getNombre(), receta.getTipo(), receta.getImagen(), receta.getDescripcion(), receta.getTiempo(), receta.getValor()
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