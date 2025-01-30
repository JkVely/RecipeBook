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
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            let usernames = [<%= usernames.stream().map(username -> "\"" + username + "\"").collect(Collectors.joining(",")) %>];
        </script>
        <script src="js/registro.js" defer></script>
    </head>
    <body>
        <div>
            <header>
                <h1>Registro de usuario</h1>
            </header>
            <form id="receta-form" action="RegistroServlet" method="POST">
                <div>
                    <label for="username">Nombre de usuario:</label>
                    <input type="text" id="username" name="username" required onblur="verificarUsername()">
                </div>
                <div>
                    <label for="password">Contraseña:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div>
                    <label for="confPassword">Confirmar Contraseña:</label>
                    <input type="password" id="confPassword" name="confPassword" required>
                </div>
                <p id="mensaje"></p>
                <button type="submit" onclick="compararContraseñas()">Subir</button>
            </form>
            <a href="login.html">login</a>
        </div>
    </body>
</html>