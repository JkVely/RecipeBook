<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.recipebook.logic.Receta" %>
<%@ page import="com.recipebook.logic.RecetasContainer" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Explora Nuestras Recetas - Global Recipe Book</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,400;0,600;1,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/explorar.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Explora Nuestras Recetas</h1>
            <p class="subtitle">Descubre sabores del mundo en cada categoría</p>
        </header>
        <hr>
        <div class="categories">
            <button class="category" onclick="seleccionarCategoria('desayuno')">🍳 Desayuno</button>
            <button class="category" onclick="seleccionarCategoria('almuerzo')">🍝 Almuerzo</button>
            <button class="category" onclick="seleccionarCategoria('cena')">🍽️ Cena</button>
            <button class="category" onclick="seleccionarCategoria('postre')">🍰 Postre</button>
            <button class="category" onclick="seleccionarCategoria('snack')">🥨 Snack</button>
            <button class="category" onclick="seleccionarCategoria('bebida')">🍹 Bebida</button>
            <button class="category" onclick="seleccionarCategoria('ensalada')">🥗 Ensalada</button>
            <button class="category" onclick="seleccionarCategoria('guarnicion')">🥔 Guarnición</button>
        </div>
        <hr>
        <div id="contenedor" class="recetas-container"></div>
    </div>
    
    <script src="js/scroll.js"></script>
</body>
</html>
