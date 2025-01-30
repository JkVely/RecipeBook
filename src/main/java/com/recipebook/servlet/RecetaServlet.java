package com.recipebook.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;

import com.google.gson.Gson;
import com.recipebook.dao.RecetaDAO;
import com.recipebook.dao.SQLController;
import com.recipebook.logic.Receta;
import com.recipebook.logic.RecipeTypes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * 
 * @author Juan Quintero
 * @author Giovanni
 * @author David
 * 
 * @version 1.0
 * @since 2023-10-01
 */
@WebServlet(name = "RecetaServlet", urlPatterns = { "/RecetaServlet" })
public class RecetaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     * @throws SQLException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SQLController sqlController = new SQLController();
        RecetaDAO recetaDAO = new RecetaDAO(sqlController);
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        String imagen = request.getParameter("imagen");
        String descripcion = request.getParameter("descripcion");

        String ingredientesJson = request.getParameter("ingredientes");
        String[] ingredientes = new Gson().fromJson(ingredientesJson, String[].class);

        String utensiliosJson = request.getParameter("utensilios");
        String[] utensilios = new Gson().fromJson(utensiliosJson, String[].class);

        Receta receta = new Receta(nombre, imagen, descripcion, RecipeTypes.valueOf(tipo));

        String pasosJson = request.getParameter("pasos");
        Map<String, String>[] mapPasos;
        mapPasos = new Gson().fromJson(
                pasosJson,
                Map[].class);
        for (Map<String, String> p : mapPasos) {
            String descripcionPaso = (String) p.get("descripcion");
            String tiempoStr = (String) p.get("tiempo");
            int tiempo = Integer.parseInt(tiempoStr);
            String imagenPaso = (String) p.get("imagen");
            String utensiliosPasoJson = new Gson().toJson(p.get("utensilios"));
            String utensiliosPasoStr = new Gson().fromJson(utensiliosPasoJson, String.class);
            String[] utensiliosPaso = utensiliosPasoStr.split(",");
            String ingredientesPasoJson = new Gson().toJson(p.get("ingredientes"));
            String ingredientesPasoStr = new Gson().fromJson(ingredientesPasoJson, String.class);
            String[] ingredientesPaso = ingredientesPasoStr.split(",");
            receta.addStep(descripcionPaso, tiempo, utensiliosPaso, ingredientesPaso, imagenPaso);
        }

        for (String ingrediente : ingredientes) {
            
            receta.addIngrediente(ingrediente);
        }
        for (String utensilio : utensilios) {
            receta.addUtensilio(utensilio);
        }

        //recetaDAO.agregarReceta((Receta) receta);
        System.out.println("receta agregada");

        HttpSession session = request.getSession();
        session.setAttribute("receta", receta);
        session.setAttribute("pasos", receta.getPasos());

        response.sendRedirect("./receta.jsp");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
