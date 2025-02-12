<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recetas por Categoría</title>
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:ital,wght@0,400;0,600;1,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/recetas.css">
</head>
<body>
    <div class="container">
        <header>
            <h1 id="categoria-titulo">Recetas</h1>
            <p class="subtitle">Explora deliciosas recetas en esta categoría</p>
        </header>
        <hr>
        <div id="contenedor" class="recetas-container"></div>
    </div>
    
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const params = new URLSearchParams(window.location.search);
            const categoria = params.get("categoria");
            
            if (!categoria) {
                document.getElementById("contenedor").innerHTML = "<p>Selecciona una categoría válida.</p>";
                return;
            }
            
            document.getElementById("categoria-titulo").innerText = `Recetas de ${categoria.charAt(0).toUpperCase() + categoria.slice(1)}`;
            cargarRecetas(categoria);
        });

        const cargarRecetas = async (categoria) => {
            try {
                const respuesta = await fetch(`/RecipeBook/RecetasServlet?categoria=${categoria}`);

                if (respuesta.status === 200) {
                    const datos = await respuesta.json();
                    let recetasHTML = "";

                    datos.forEach(receta => {
                        recetasHTML += `
                            <div class="receta">
                                <h2>${receta.nombre}</h2>
                                <p><strong>Tipo:</strong> ${receta.tipo}</p>
                                <p><strong>Descripción:</strong> ${receta.descripcion}</p>
                                <p><strong>Tiempo:</strong> ${receta.tiempo} minutos</p>
                            </div>
                        `;
                    });
                    
                    document.getElementById("contenedor").innerHTML = recetasHTML || "<p>No hay recetas disponibles en esta categoría.</p>";
                } else {
                    document.getElementById("contenedor").innerHTML = "<p>Error al cargar las recetas.</p>";
                }
            } catch (error) {
                console.error(error);
                document.getElementById("contenedor").innerHTML = "<p>Error de conexión.</p>";
            }
        };
    </script>
</body>
</html>

