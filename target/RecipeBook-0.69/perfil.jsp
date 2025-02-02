<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.User" %>
<%@ page import="com.recipebook.logic.Receta" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Perfil de Usuario</title>
    </style>
</head>
<body>
    <div class="profile-container">
        <div class="profile-header">
            <h1>Bienvenido, <%= currentUser.getUsername() %>!</h1>
        </div>
        <div class="profile-details">
            <h2>Información de Usuario</h2>
            <p><strong>Nombre de usuario:</strong> <%= currentUser.getUsername() %></p>
            <p><strong>Contraseña:</strong> <%= currentUser.getPassword() %></p>
            <h2>Recetas Subidas</h2>
            <ul>
                <%
                    if (currentUser.getRecetas() != null && currentUser.getRecetas().getRecetas() != null) {
                        for (Receta receta : currentUser.getRecetas().getRecetas()) {
                %>
                    <li><%= receta.getNombre() %></li>
                <%
                        }
                    }
                %>
            </ul>
        <div class="add-recipe">
            <button onclick="window.location.href='subir.html'">Agregar Nueva Receta</button>
        </div>
        </div>
    </div>
</body>
</html>