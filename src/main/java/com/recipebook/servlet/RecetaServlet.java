package com.recipebook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.recipebook.logic.*;
import java.util.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * 
 * @author Juan Quintero
 * @author Giovanni
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        String imagen = request.getParameter("imagen");
        String descripcion = request.getParameter("descripcion");

        String ingredientesJson = request.getParameter("ingredientes");
        String[] ingredientes = new Gson().fromJson(ingredientesJson, String[].class);

        String utensiliosJson = request.getParameter("utensilios");
        String[] utensilios = new Gson().fromJson(utensiliosJson, String[].class);

        String pasosJson = request.getParameter("pasos");
        Paso[] pasos = new Gson().fromJson(pasosJson, Paso[].class);

        Receta receta = new Receta(nombre, imagen, descripcion, RecipeTypes.valueOf(tipo));
        for (String ingrediente : ingredientes) {
            receta.addIngrediente(ingrediente);
        }
        for (String utensilio : utensilios) {
            receta.addUtensilio(utensilio);
        }
        for (Paso paso : pasos) {
            receta.addStep(paso.getDescripcion(), paso.getTiempo(), paso.getUtensilios(), paso.getIngredientes(),
                    paso.getImagen());
        }

        HttpSession session = request.getSession();
        session.setAttribute("receta", receta);

        switch (receta.getTipo()) {
            case ALMUERZO -> {
                response.sendRedirect("almuerzo.jsp");
            }
            case DESAYUNO -> {
                response.sendRedirect("desayuno.jsp");
            }
            case CENA -> {
                response.sendRedirect("cena.jsp");
            }
            case POSTRE -> {
                response.sendRedirect("postre.jsp");
            }
            case BEBIDA -> {
                response.sendRedirect("bebida.jsp");
            }
            case ENSALADA -> {
                response.sendRedirect("ensalada.jsp");
            }
            case GUARNICION -> {
                response.sendRedirect("guarnicion.jsp");
            }
            case SNACK -> {
                response.sendRedirect("snack.jsp");
            }
            default -> throw new AssertionError();
        }
        /*
         * // Obtener datos del formulario
         * String nombre = request.getParameter("nombre");
         * String apellido = request.getParameter("apellido");
         * String email = request.getParameter("email");
         * String telefono = request.getParameter("telefono");
         * String direccion = request.getParameter("direccion");
         * String fechaNacimiento = request.getParameter("fechaNacimiento");
         * Part filePart = request.getPart("foto");
         * 
         * // Obtener el nombre del archivo (con extensión)
         * String fileName =
         * Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
         * 
         * // Definir el path donde se subirá la foto
         * String uploadPath = getServletContext().getRealPath("") + "Uploads";
         * 
         * // Crear el directorio si no existe
         * File uploadsDir = new File(uploadPath);
         * if (!uploadsDir.exists()) {
         * uploadsDir.mkdir(); // Puedes usar mkdirs() si deseas crear también los
         * directorios padre si no existen
         * }
         * 
         * // Definir la ruta completa del archivo donde se guardará
         * String filePath = uploadPath + File.separator + fileName;
         * 
         * // Escribir el archivo en el disco
         * filePart.write(filePath);
         * 
         * // Aquí puedes hacer alguna acción adicional, como guardar la ruta del
         * archivo en la base de datos o en sesión.
         * System.out.println("Archivo guardado en: " + filePath);
         * 
         * // Create InformacionPersonal object with the uploaded file path
         * InformacionPersonal infoPer = new InformacionPersonal(nombre, apellido,
         * email, telefono, direccion, fechaNacimiento,fileName);
         * 
         * 
         * String tipoEducacion = request.getParameter("tipoEducacion");
         * String institucion = request.getParameter("institucion");
         * String carrera = request.getParameter("carrera");
         * String anio = request.getParameter("anio");
         * 
         * Educacion educacion = new Educacion(tipoEducacion, institucion, carrera,
         * anio);
         * 
         * String nivelIngles = (request.getParameter("nivel"));
         * System.out.println(nivelIngles);
         * 
         * String empresa = request.getParameter("empresa");
         * String cargo = request.getParameter("cargo");
         * String anioComienzo = request.getParameter("anioComienzo");
         * String anioFin = request.getParameter("anioFin");
         * String descripcion = request.getParameter("descripcion");
         * 
         * ExperienciaLaboral experiencia = new ExperienciaLaboral(empresa, cargo,
         * anioComienzo, anioFin, descripcion);
         * 
         * HojaDeVida hojaDeVida = new HojaDeVida(infoPer, educacion, experiencia ,
         * nivelIngles);
         * 
         * // Obtener la sesión del usuario
         * HttpSession session = request.getSession();
         * 
         * // Guardar el objeto HojaDeVida en la sesión
         * session.setAttribute("hojaDeVida", hojaDeVida);
         * 
         * // Redirigir a otra página donde mostrarás la información de la Hoja de Vida
         * response.sendRedirect("Hoja.jsp");
         */

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
