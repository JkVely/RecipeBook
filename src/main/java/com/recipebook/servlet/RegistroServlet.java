package com.recipebook.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.recipebook.logic.*;
import com.recipebook.serialization.UsersSerializer;

@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsersContainer usersContainer = (UsersContainer) request.getAttribute("usersContainer");

        User newUser = new User(username, password);
        usersContainer.addUser(newUser);

        request.setAttribute("usersContainer", usersContainer);

        UsersSerializer usersSerializer = new UsersSerializer();
        usersSerializer.serializeUser(usersContainer);

        request.setAttribute("currentUser", newUser);
        response.sendRedirect("perfil.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

