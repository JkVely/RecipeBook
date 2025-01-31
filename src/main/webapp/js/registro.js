function validarFormulario() {
    const password = document.getElementById('password').value;
    const confPassword = document.getElementById('confPassword').value;
    const username = document.getElementById('username').value;
    const mensaje = document.getElementById('mensaje');
    let aceptar = document.getElementById('registrarse');

    let errorMessage = '';

    if (!password || !confPassword || !username) {
        errorMessage = 'Error: Debe completar todos los campos.';
    } else if (password !== confPassword) {
        errorMessage = 'Error: Las contraseñas no coinciden.';
    } else if (usernames.includes(username)) {
        errorMessage = 'Error: El nombre de usuario ya existe.';
    }

    if (errorMessage) {
        mensaje.textContent = errorMessage;
        mensaje.style.color = 'red';
        aceptar.disabled = true;
        aceptar.disabled = true;
        aceptar.setClickeable = false;
    } else {
        mensaje.textContent = '';
        aceptar.disabled = false;
    }
}

document.getElementById('password').addEventListener('input', validarFormulario);
document.getElementById('confPassword').addEventListener('input', validarFormulario);
document.getElementById('username').addEventListener('input', validarFormulario);