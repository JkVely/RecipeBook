<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.recipebook.logic.Receta" %>
<%@ page import="com.recipebook.logic.Paso" %>
<%@ page import="java.util.List" %>
<%
    HttpSession session = request.getSession();
    Receta receta = (Receta) session.getAttribute("receta");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Receta de Bebida</title>
</head>
<body>
    <h1>Receta de Bebida: <%= receta.getNombre() %></h1>
    <img src="<%= receta.getImagen() %>" alt="Imagen de la receta" />
    <p><strong>Descripción:</strong> <%= receta.getDescripcion() %></p>
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
        <% for (Paso paso : receta.getPasos()) { %>
            <li>
                <p><strong>Descripción:</strong> <%= paso.getDescripcion() %></p>
                <p><strong>Tiempo:</strong> <%= paso.getTiempo() %> minutos</p>
                <p><strong>Utensilios:</strong> <%= String.join(", ", paso.getUtensilios()) %></p>
                <p><strong>Ingredientes:</strong> <%= String.join(", ", paso.getIngredientes()) %></p>
                <img src="<%= paso.getImagen() %>" alt="Imagen del paso" />
            </li>
        <% } %>
    </ol>
</body>
</html>