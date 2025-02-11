/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.recipebook.servlet;

import java.io.IOException;

import com.recipebook.logic.UsersContainer;
import com.recipebook.dao.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jkqui
 */
@WebServlet(name = "IndexServlet", urlPatterns = { "/IndexServlet" })
public class IndexServlet extends HttpServlet {

    private Connection connection;
    private boolean connected = false;
    private final String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=RECIPE_BOOK;integratedSecurity=true;encrypt=false;trustServerCertificate=true";

    private boolean start(String connectionUrl) {
        try {
            System.out.print("Connecting to SQL Server ... ");
            connection = DriverManager.getConnection(connectionUrl);
            System.out.println("Done.");
            return true;
        } catch (SQLException e) {
            System.out.println();
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        if (isConnected()) {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } else {
            throw new SQLException("Not connected to the database.");
        }
    }

    public int executeUpdate(String query) throws SQLException {
        if (isConnected()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query);
        } else {
            throw new SQLException("Not connected to the database.");
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate(); // Invalidar la sesión actual
        }
        session = request.getSession(true); // Crear una nueva sesión

        SQLController sqlController = new SQLController();
        connected = sqlController.isConnected();
        String conexion = connected ? "Conectado - C" : "Desconectado - C";

        if(!connected) {
        start(connectionUrl);
        conexion = isConnected() ? "Conectado" : "Desconectado";
        }

        session.setAttribute("conexion", conexion);

        session.setAttribute("userContainer", null);
        UsersContainer usersContainer = (UsersContainer) session.getAttribute("usersContainer");
        if (usersContainer == null) {
            usersContainer = new UsersContainer();
        } else {
            // [ ]: Agregar la recopilacion de la DB
        }

        session.setAttribute("usersContainer", usersContainer);
        response.sendRedirect("registro.jsp");
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
}
