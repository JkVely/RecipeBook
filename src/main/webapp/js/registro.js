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