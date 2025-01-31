<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.User" %>
<%@ page import="com.recipebook.logic.UsersContainer" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.stream.*" %>
<%
    UsersContainer usersContainer = (UsersContainer)(session.getAttribute("usersContainer"));
    List<String> usernames = new ArrayList<>();
    if(usersContainer != null){
        for(User user : usersContainer.getUsers()) {
            usernames.add(user.getUsername());
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Registro de Usuario - RecipeBook</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/registro.css">
        <script>
            let usernames = [<%= usernames.stream().map(username -> "\"" + username + "\"").collect(Collectors.joining(",")) %>];
        </script>
        <script src="./js/registro.js"></script>
    </head>
    <body>
        <div class="container">
            <header>
                <h1>Registro de usuario</h1>
            </header>
            <form id="receta-form" action="RegistroServlet" method="POST">
                <div class="form-group">
                    <label for="username">Nombre de usuario:</label>
                    <input type="text" id="username" name="username" required onblur="verificarUsername()">
                </div>
                <div class="form-group">
                    <label for="password">Contraseña:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="confPassword">Confirmar Contraseña:</label>
                    <input type="password" id="confPassword" name="confPassword" required>
                </div>
                <p id="mensaje"></p>
                <div class="button-group">
                    <button id="registrarse" type="submit" onclick="validarFormulario()">Registrarse</button>
                    <a href="index.html" class="cancel-button">Cancelar</a>
                </div>
            </form>
            <div class="login-link">
                <a href="login.html">¿Ya tienes una cuenta? Inicia sesión</a>
            </div>
        </div>
    </body>
</html>