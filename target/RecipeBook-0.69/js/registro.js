function compararContraseñas() {
    const password = document.getElementById('password').value;
    const confPassword = document.getElementById('confPassword').value;

    const mensaje = document.getElementById('mensaje');

    if (password === confPassword) {
        mensaje.textContent = 'Contraseña subida con éxito.';
        mensaje.style.color = 'green';
    } else {
        mensaje.textContent = 'Error: Las contraseñas no coinciden.';
        mensaje.style.color = 'red';
    }
}

function verificarUsername() {
    const username = document.getElementById('username').value;
    const mensaje = document.getElementById('mensaje');

    if (usernames.includes(username)) {
        mensaje.textContent = 'Error: El nombre de usuario ya existe.';
        mensaje.style.color = 'red';
    } else {
        mensaje.textContent = '';
    }
}