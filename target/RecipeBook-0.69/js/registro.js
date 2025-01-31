function compararContraseñas() {
    const password = document.getElementById('password').value;
    const confPassword = document.getElementById('confPassword').value;

    const mensaje = document.getElementById('mensaje');
    let aceptar = document.getElementById('registrarse');

    if (password.isEmpty || confPassword.isEmpty) {
        mensaje.textContent = 'Error: Debe completar todos los campos.';
        mensaje.style.color = 'red';
        aceptar.disabled = true;
    }

    if (password !== confPassword) {
        mensaje.textContent = 'Error: Las contraseñas no coinciden.';
        mensaje.style.color = 'red';
        aceptar.disabled = true;
    } else {
        mensaje.textContent = '';
        aceptar.disabled = false;
    }
}

function verificarUsername() {
    const username = document.getElementById('username').value;
    const mensaje = document.getElementById('mensaje');
    let aceptar = document.getElementById('registrarse');

    if (usernames.includes(username)) {
        mensaje.textContent = 'Error: El nombre de usuario ya existe.';
        mensaje.style.color = 'red';
        aceptar.disabled = true;
    } else {
        mensaje.textContent = '';
        aceptar.disabled = false;
    }
}