<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.User" %>
<%@ page import="com.recipebook.logic.Receta" %>
<%@ page import="java.util.List" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");
    List<Receta> userRecetas = (List<Receta>) request.getAttribute("userRecetas");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil de Usuario - Global Recipe Book</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,400;0,600;1,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/perfil.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Bienvenido, <%= currentUser.getUsername() %>!</h1>
            <nav>
                <a href="explorar.html" class="nav-button">Explorar Recetas</a>
                <a href="index.html" class="nav-button">Cerrar Sesión</a>
            </nav>
        </header>
        <main>
            <section class="profile-info">
                <h2>Información de Usuario</h2>
                <p><strong>Nombre de usuario:</strong> <%= currentUser.getUsername() %></p>
            </section>
            <section class="user-recipes">
                <h2>Mis Recetas</h2>
                <div class="recipe-grid">
                    <% if (userRecetas != null && !userRecetas.isEmpty()) {
                        for (Receta receta : userRecetas) { %>
                            <div class="recipe-card">
                                <img src="<%= receta.getImagen() %>" alt="<%= receta.getNombre() %>" class="recipe-image">
                                <div class="recipe-info">
                                    <h3><%= receta.getNombre() %></h3>
                                    <p><%= receta.getDescripcion() %></p>
                                    <span class="recipe-type"><%= receta.getTipo() %></span>
                                </div>
                            </div>
                    <% }
                    } else { %>
                        <p>Aún no has subido ninguna receta.</p>
                    <% } %>
                </div>
            </section>
            <div class="add-recipe">
                <a href="subir.html" class="button">Agregar Nueva Receta</a>
            </div>
        </main>
        <footer>
            <p>&copy; 2023 Global Recipe Book. Todos los derechos reservados.</p>
        </footer>
    </div>
</body>
</html>

