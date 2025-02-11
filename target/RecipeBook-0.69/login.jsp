<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.User" %>
<%@ page import="com.recipebook.logic.UsersContainer" %>
<%@ page import="com.recipebook.dao.UserDao" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
                    <input type="text" id="username" name="username" required value=" ">
                    <script>
                        document.getElementById('username').onblur = function() {
                            var inputUsername = <%(String)%> this.value;

                            if(usernames.includes(inputUsername) && inputUsername !== '') {
                                <% String username = (String)%> 'inputUsername' <%;%>
                                this.style.borderColor = '#4CAF50';
                            }
                        };
                    </script>
                </div>
                <div>
                    <label for="password">Contraseña:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="button-group">
                    <button id="login" type="button">Iniciar sesión</button>
                    <% 
                        User user = null;
                        for(User u : usersContainer.getUsers()) {
                            if(u.getUsername().equals(username)) {
                                user = u;
                                break;
                            }
                        }
                    %>
                    <script>
                        document.getElementById('login').onclick = function() {
                            var inputUsername = document.getElementById('username').value;
                            var inputPassword = document.getElementById('password').value;
                            <% if(user.validatePassword(%>'inputPassword'<%)) { %>
                                document.getElementById('login-form').submit();
                            <% } else { %>
                                alert('Contraseña incorrecta');
                            <% } %>
                        };                            
                    </script>
                    <a href="index.html" class="cancel-button">Cancelar</a>
                </div>
            </form>
        </div>
    </body>
</html>