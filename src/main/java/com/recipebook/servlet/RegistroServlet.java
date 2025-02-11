package com.recipebook.servlet;

import java.io.IOException;

import com.recipebook.logic.User;
import com.recipebook.logic.UsersContainer;
import com.recipebook.dao.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        UserDao userDao = (UserDao) session.getAttribute("userDao");
        System.out.println("UserDao: " + userDao);


        User newUser = new User(username, password);
        System.out.println("New User: " + newUser.getUsername());

        userDao.agregarUsuario(newUser);

        session.setAttribute("currentUser", newUser);
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

