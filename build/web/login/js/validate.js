/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function validarEmail(correo) {
    if (/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.([a-zA-Z]{2,4})+$/.test(correo)) {
        return true;
    } else {
        return false;
    }
}

function validarNumero(numero) {
    if (/^([0-9])*$/.test(numero)) {
        return true;
    } else {
        return false;
    }
}

function cambios() {
    history.pushState(null, "", "activateaccount.jsp");
}


function confirmarCorreo() {
    correo = document.getElementById('correoconfirmar').value;

    if (correo === null || correo === '') {
        $('#alertemail').text('Ingresa tu correo').addClass('text-danger');
    } else if (validarEmail(correo) === false) {
        $('#alertemail').text('El correo es invalido').addClass('text-danger');
    } else {
        $('#alertemail').text('');
        $.post("validate?accion=Confirmar", {correoconfirmar: correo}, function (rs) {

            if (rs === 'true') {
                $('#alertemail').text('Confirmando').removeClass('text-danger').addClass('text-success');
                setTimeout("location.href='confirmcode.jsp'", 1000);
            } else if (rs === 'emailvacio') {
                $('#alertemail').text('Ingresa tu correo').addClass('text-danger');
            } else if (rs === 'emailfalse') {
                $('#alertemail').text('Correo invalido').addClass('text-danger');
            } else if (rs === 'false') {
                $('#alertemail').text('Correo invalido').addClass('text-danger');
            }
        });
    }
}

function confirmarCodigo() {
    // Confirmar codigo
    cod = document.getElementById('txtcodigo').value;

    if (cod === null || cod === '') {
        $('#alertcod').text('Ingrese el codigo').addClass('text-danger');
    } else if (validarNumero(cod) === false) {
        $('#alertcod').text('Formato incorrecto').addClass('text-danger');
    } else {
        $('#alertcod').text('');
        $.post("validate?accion=Codigo", {codigo: cod}, function (rs) {
            console.log(rs);
            if (rs === 'true') {
                setTimeout("location.href='updatepassword.jsp'", 2000);
            } else if (rs === 'codvacio') {
                $('#alertcod').text('Ingresa el codigo').addClass('text-danger');
            } else if (rs === 'codfalse') {
                $('#alertcod').text('Codigo invalido').addClass('text-danger');
            } else if (rs === 'codinvalido') {
                $('#alertcod').text('Formato invalido').addClass('text-danger');
            }
        });
    }
}

function cambioPass() {
    // Controlador = validate la accion es Actualizar
    passwordone = document.getElementById('passone').value;
    passconfirma = document.getElementById('passconfirma').value;

    if (passconfirma === '' && passwordone === '') {
        $('#alert').text('Ingresa los campos completos').addClass('text-danger');
    } else if (passwordone !== passconfirma) {
        $('#alert').text('Las claves no coinciden').addClass('text-danger');
    } else if (passconfirma.length < 8 && passwordone.length < 8) {
        $('#alert').text('Tu clave es insegura').addClass('text-danger');
    } else {
        $.post("validate?accion=Actualizar", {passone: passwordone, passconfirma: passconfirma}, function (rs) {
            if (rs === 'true') {
                $('#alert').removeClass('text-danger').addClass('text-succes');
                $('#alert').addClass('text-success').text('Clave actualizada');
                setTimeout("location.href='index.jsp'", 4000);
            }
        });
    }
}

function confirmarDatos() {
    $('#alert').text('').removeClass('verde');
    $('#alert').text('').removeClass('rojo');

    $.post("validate?", {accion: 'Confirmar datos'}, function (rs) {
        if (rs === 'true') {
            $('#alert').text('Cuenta activada revisa tu correo').addClass('verde');
        } else if (rs === 'false') {
            $('#alert').text('Cuenta no activada').addClass('rojo');
        } else if (rs === 'correo') {
            $('#alert').text('Este correo ya existe').addClass('rojo');
        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        } else {
            setTimeout("location.href='index.jsp'", 1000);
        }
    });
}

function ps() {
    var tipo = document.getElementById("pass");
    if (tipo.type === "password") {
        tipo.type = "text";
        $('#eye').text('Ocultar');
    } else {
        tipo.type = "password";
        $('#eye').text('Ver contraseña');
    }
}


function pss() {
    var tipo = document.getElementById("passconfirma");

    if (tipo.type === "password") {
        tipo.type = "text";
        $('#eye').text('Ocultar');
    } else {
        tipo.type = "password";
        $('#eye').text('Ver contraseña');
    }

    var tipos = document.getElementById("passone");

    if (tipos.type === "password") {
        tipos.type = "text";
        $('#eye').text('Ocultar');
    } else {
        tipos.type = "password";
        $('#eye').text('Ver contraseña');
    }
}

window.onload = function () {

    var passconfirma = document.getElementById('passconfirma');
    passconfirma.onpaste = function (e) {
        e.preventDefault();
    };

    var passone = document.getElementById('passone');
    passone.onpaste = function (e) {
        e.preventDefault();
    };

};

window.onload = function () {

    var pass = document.getElementById('pass');
    pass.onpaste = function (e) {
        e.preventDefault();
    };

};




//=============




