<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.Receta" %>
<%@ page import="com.recipebook.logic.Paso" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%
    Receta receta = (Receta) session.getAttribute("receta");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Receta de Cena</title>
</head>
<body>
    <h1>Receta de Cena: "${receta.getNombre()}"</h1>
    <img src="${receta.getImagen()}" alt="Imagen de la receta" />
    <p><strong>Descripción:</strong> "${receta.getDescripcion()}"</p>
    <h2>Ingredientes:</h2>
    <ul>
        <% for (String ingrediente : receta.getIngredientes()) { %>
            <li><%= ingrediente %></li>
        <% } %>
    </ul>
    <h2>Utensilios:</h2>
    <ul>
        <% for (String utensilio : receta.getUtensilios()) { %>
            <li><%= utensilio %></li>
        <% } %>
    </ul>
    <h2>Pasos:</h2>
</body>
</html>