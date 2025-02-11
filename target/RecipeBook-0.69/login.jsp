<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.recipebook.logic.User" %>
<%@ page import="com.recipebook.logic.UsersContainer" %>
<%@ page import="com.recipebook.dao.UserDao" %>
<%
    UsersContainer usersContainer = ((UserDao)session.getAttribute("userDao")).obtenerUsuarios();
    List<String> usernames = new ArrayList<>();
    if(usersContainer != null){
        for(User user : usersContainer.getUsers()) {
            usernames.add(user.getUsername());
        }
    }
%>
<html>
    <head>
        <title>Iniciar Sesión</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/login.css">
    </head>
    <body>
        <div>
            <header>
                <h1>Iniciar Sesión</h1>
            </header>
            <form id="login-form" action="LoginServlet" method="POST">
                <div>
                    <label for="username">Nombre de usuario:</label>
                    <input type="text" id="username" name="username" required>
                    <script>
                        document.getElementById('username').onblur = function() {
                            var inputUsername = this.value;

                            if(usernames.includes(inputUsername)) {
                                this.style.borderColor = '#4CAF50';
                            }
                        };
                    </script>
                </div>
                <div>
                    <label for="password">Contraseña:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">Enviar</button> 
            </form>
        </div>
    </body>
</html>