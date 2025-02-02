<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.User" %>
<%@ page import="com.recipebook.logic.UsersContainer" %>
<%@ page import="java.util.List" %>
<%
    UsersContainer usersContainer = (UsersContainer) request.getAttribute("usersContainer");
    User currentUser = (User) request.getAttribute("currentUser");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Perfil de Usuario</title>
</head>
<body>
    <h1>Bienvenido, <%= currentUser.getUsername() %>!</h1>
    <h2>Información de Usuario</h2>
    <p>Nombre de usuario: <%= currentUser.getUsername() %></p>
    <p>Contraseña: <%= currentUser.getPassword() %></p>
    
    <h2>Lista de Usuarios Registrados</h2>
    <ul>
        <% 
            List<User> usersList = usersContainer.getUsers();
            for (User user : usersList) {
        %>
            <li><%= user.getUsername() %></li>
        <% 
            }
        %>
    </ul>
</body>
</html>