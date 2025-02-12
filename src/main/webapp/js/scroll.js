let recetas = '';
let pagina = 1;
let ultimaReceta;
let categoriaSeleccionada = '';

let observer = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            pagina++;
            cargarRecetas();
        }
    });
}, {
    rootMargin: '0px 0px 200px 0px',
    threshold: 1.0
});

function seleccionarCategoria(categoria) {
    categoriaSeleccionada = categoria;
    pagina = 1;
    recetas = '';
    document.getElementById('contenedor').innerHTML = '';
    cargarRecetas();
}

const cargarRecetas = async () => {
    if (!categoriaSeleccionada) return;
    try {
        const respuesta = await fetch(`/RecipeBook/RecetasServlet?categoria=${categoriaSeleccionada}&page=${pagina}`);

        if (respuesta.status === 200) {
            const datos = await respuesta.json();

            datos.forEach(receta => {
                recetas += `
                    <div class="receta">
                        <h2>${receta.nombre}</h2>
                        <p><strong>Tipo:</strong> ${receta.tipo}</p>
                        <p><strong>Descripción:</strong> ${receta.descripcion}</p>
                        <p><strong>Tiempo:</strong> ${receta.tiempo} minutos</p>
                    </div>
                `;
            });
            document.getElementById('contenedor').innerHTML += recetas;
            if (datos.length > 0) {
                if (ultimaReceta) {
                    observer.unobserve(ultimaReceta);
                }

                const recetasEnPantalla = document.querySelectorAll('.receta');
                ultimaReceta = recetasEnPantalla[recetasEnPantalla.length - 1];
                observer.observe(ultimaReceta);
            }
        } else {
            console.log('Error al cargar las recetas');
        }
    } catch (error) {
        console.log(error);
    }
};
