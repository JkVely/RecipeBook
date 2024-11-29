<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.Receta" %>
<%@ page import="com.recipebook.logic.Paso" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%
    Receta receta = (Receta) session.getAttribute("receta");
    List<Paso> pasos = (List<Paso>) session.getAttribute("pasos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Receta de Bebida</title>
</head>
<body>
    <h1>Receta de Bebida: "${receta.getNombre()}"</h1>
    <%-- <img src="${receta.getImagen()}" alt="Imagen de la receta" /> --%>
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
    <ol>
        <% for (Paso paso : pasos) { %>
            <li>
                <p><strong>Descripción:</strong> <%= paso.getDescripcion() %></p>
                <p><strong>Tiempo:</strong> <%= paso.getTiempo() %> minutos</p>
                <p><strong>Utensilios:</strong></p>
                <ul>
                    <% for (String utensilio : paso.getUtensilios()) { %>
                        <li><%= utensilio %></li>
                    <% } %>
                </ul>
                <p><strong>Ingredientes:</strong></p>
                <ul>
                    <% for (String ingrediente : paso.getIngredientes()) { %>
                        <li><%= ingrediente %></li>
                    <% } %>
                </ul>
                <% if (paso.getImagen() != null) { %>
                    <p><img src="<%= paso.getImagen() %>" alt="Imagen del paso" /></p>
                <% } %>
            </li>
        <% } %>
    </ol>    
</body>
</html>