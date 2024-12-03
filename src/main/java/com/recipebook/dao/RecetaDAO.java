package com.recipebook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.recipebook.logic.Paso;
import com.recipebook.logic.Receta;
import com.recipebook.logic.RecipeTypes;

public class RecetaDAO {

    private static final String DB_URL = "jdbc:sqlserver://recipebook.c7ek2so26gog.us-east-2.rds.amazonaws.com:1433;databaseName=RecipeBook";
    private static final String USER = "admin";
    private static final String PASS = "JkVely1029";

    public void addReceta(Receta receta) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Insertar la receta
            String sqlReceta = "INSERT INTO Recetas (Nombre, Descripcion, Tipo, Imagen, Tiempo, Valor) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmtReceta = conn.prepareStatement(sqlReceta,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtReceta.setString(1, receta.getNombre());
                stmtReceta.setString(2, receta.getDescripcion());
                stmtReceta.setString(3, receta.getTipo().name());
                stmtReceta.setString(4, receta.getImagen());
                stmtReceta.setInt(5, receta.getTiempo());
                stmtReceta.setDouble(6, receta.getValor());
                stmtReceta.executeUpdate();

                // Obtener el ID de la receta insertada
                try (ResultSet rs = stmtReceta.getGeneratedKeys()) {
                    if (rs.next()) {
                        int recetaId = rs.getInt(1);

                        // Insertar los pasos
                        String sqlPaso = "INSERT INTO Pasos (RecetaID, Descripcion, Tiempo, Imagen) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement stmtPaso = conn.prepareStatement(sqlPaso)) {
                            for (Paso paso : receta.getPasos()) {
                                stmtPaso.setInt(1, recetaId);
                                stmtPaso.setString(2, paso.getDescripcion());
                                stmtPaso.setInt(3, paso.getTiempo());
                                stmtPaso.setString(4, paso.getImagen());
                                stmtPaso.executeUpdate();
                            }
                        }

                        // Insertar los ingredientes
                        String sqlIngrediente = "INSERT INTO Ingredientes (RecetaID, Nombre) VALUES (?, ?)";
                        try (PreparedStatement stmtIngrediente = conn.prepareStatement(sqlIngrediente)) {
                            for (String ingrediente : receta.getIngredientes()) {
                                stmtIngrediente.setInt(1, recetaId);
                                stmtIngrediente.setString(2, ingrediente);
                                stmtIngrediente.executeUpdate();
                            }
                        }

                        // Insertar los utensilios
                        String sqlUtensilio = "INSERT INTO Utensilios (RecetaID, Nombre) VALUES (?, ?)";
                        try (PreparedStatement stmtUtensilio = conn.prepareStatement(sqlUtensilio)) {
                            for (String utensilio : receta.getUtensilios()) {
                                stmtUtensilio.setInt(1, recetaId);
                                stmtUtensilio.setString(2, utensilio);
                                stmtUtensilio.executeUpdate();
                            }
                        }
                    }
                }
            }
        }
    }

    public void deleteReceta(int recetaId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Borrar los pasos
            String sqlPaso = "DELETE FROM Pasos WHERE RecetaID = ?";
            try (PreparedStatement stmtPaso = conn.prepareStatement(sqlPaso)) {
                stmtPaso.setInt(1, recetaId);
                stmtPaso.executeUpdate();
            }

            // Borrar los ingredientes
            String sqlIngrediente = "DELETE FROM Ingredientes WHERE RecetaID = ?";
            try (PreparedStatement stmtIngrediente = conn.prepareStatement(sqlIngrediente)) {
                stmtIngrediente.setInt(1, recetaId);
                stmtIngrediente.executeUpdate();
            }

            // Borrar los utensilios
            String sqlUtensilio = "DELETE FROM Utensilios WHERE RecetaID = ?";
            try (PreparedStatement stmtUtensilio = conn.prepareStatement(sqlUtensilio)) {
                stmtUtensilio.setInt(1, recetaId);
                stmtUtensilio.executeUpdate();
            }

            // Borrar la receta
            String sqlReceta = "DELETE FROM Recetas WHERE RecetaID = ?";
            try (PreparedStatement stmtReceta = conn.prepareStatement(sqlReceta)) {
                stmtReceta.setInt(1, recetaId);
                stmtReceta.executeUpdate();
            }
        }
    }

    public Receta getReceta(int recetaId) throws SQLException {
        Receta receta = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Obtener la receta
            String sqlReceta = "SELECT * FROM Recetas WHERE RecetaID = ?";
            try (PreparedStatement stmtReceta = conn.prepareStatement(sqlReceta)) {
                stmtReceta.setInt(1, recetaId);
                try (ResultSet rs = stmtReceta.executeQuery()) {
                    if (rs.next()) {
                        String nombre = rs.getString("Nombre");
                        String descripcion = rs.getString("Descripcion");
                        String tipo = rs.getString("Tipo");
                        String imagen = rs.getString("Imagen");
                        double valor = rs.getDouble("Valor");

                        receta = new Receta(nombre, imagen, descripcion, RecipeTypes.valueOf(tipo));
                        receta.addValoracion(valor);
                    }
                }
            }

            if (receta != null) {
                // Obtener los pasos
                String sqlPaso = "SELECT * FROM Pasos WHERE RecetaID = ?";
                try (PreparedStatement stmtPaso = conn.prepareStatement(sqlPaso)) {
                    stmtPaso.setInt(1, recetaId);
                    try (ResultSet rs = stmtPaso.executeQuery()) {
                        while (rs.next()) {
                            int pasoId = rs.getInt("PasoID");
                            String descripcion = rs.getString("Descripcion");
                            int tiempo = rs.getInt("Tiempo");
                            String imagen = rs.getString("Imagen");
                            receta.addStep(descripcion, tiempo, new String[0], new String[0], imagen);
                        }
                    }
                }

                // Obtener los ingredientes
                String sqlIngrediente = "SELECT * FROM Ingredientes WHERE RecetaID = ?";
                try (PreparedStatement stmtIngrediente = conn.prepareStatement(sqlIngrediente)) {
                    stmtIngrediente.setInt(1, recetaId);
                    try (ResultSet rs = stmtIngrediente.executeQuery()) {
                        while (rs.next()) {
                            String nombre = rs.getString("Nombre");
                            receta.addIngrediente(nombre);
                        }
                    }
                }

                // Obtener los utensilios
                String sqlUtensilio = "SELECT * FROM Utensilios WHERE RecetaID = ?";
                try (PreparedStatement stmtUtensilio = conn.prepareStatement(sqlUtensilio)) {
                    stmtUtensilio.setInt(1, recetaId);
                    try (ResultSet rs = stmtUtensilio.executeQuery()) {
                        while (rs.next()) {
                            String nombre = rs.getString("Nombre");
                            receta.addUtensilio(nombre);
                        }
                    }
                }
            }
        }
        return receta;
    }

    public List<Receta> getAllRecetas() throws SQLException {
        List<Receta> recetas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Obtener todas las recetas
            String sqlReceta = "SELECT * FROM Recetas";
            try (PreparedStatement stmtReceta = conn.prepareStatement(sqlReceta);
                    ResultSet rs = stmtReceta.executeQuery()) {
                while (rs.next()) {
                    int recetaId = rs.getInt("RecetaID");
                    String nombre = rs.getString("Nombre");
                    String descripcion = rs.getString("Descripcion");
                    String tipo = rs.getString("Tipo");
                    String imagen = rs.getString("Imagen");
                    double valor = rs.getDouble("Valor");

                    Receta receta = new Receta(nombre, imagen, descripcion, RecipeTypes.valueOf(tipo));
                    receta.addValoracion(valor);
                    recetas.add(receta);
                }
            }
        }
        return recetas;
    }
}