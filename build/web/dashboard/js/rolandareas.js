/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function llamarArea(areaedit) {

    $('#lblmodaleliminar').text('').removeClass('alert alert-danger');

    area = document.getElementById('areas').value;
    $('#lblmodal').text('El área ' + areaedit).addClass('text-gray-900 text-uppercase');
    $('#hd').val(1);

    $('.eye').removeClass('eye');

    $('#txtarearol').val(areaedit);
    $('#btnupdate').val(area);
    $('#btnmodaleliminar').val(area);
}

function llamarRol(roledit) {
    $('#lblmodaleliminar').text('').removeClass('alert alert-danger');


    rol = document.getElementById('roles').value;

    $('#lblmodal').text('El rol ' + roledit).addClass('text-gray-900 text-uppercase');
    $('#hd').val(2);
    $('.eye').removeClass('eye');

    $('#btnmodaleliminar').val(rol);
    $('#txtarearol').val(roledit);
    $('#btnupdate').val(rol);

}


function updateAreaORol() {
    nombre = document.getElementById('txtarearol').value;
    dato = document.getElementById('btnupdate').value;
    opcion = document.getElementById('hd').value;

    switch (opcion) {
        case '1': // Area

            $.post("roleandaddres?accion=Actualizar", {area: dato, opcion: opcion, nombre: nombre}, function (rs) {
                if (rs === 'true') {
                    $('#labelmodalcarga').text('Actualizando ' + nombre);

                    var modalcarag = $('#modalcarga');
                    modalcarag.modal('show');

                    setTimeout("location.reload(true);", 1000);
                } else if (rs === 'false') {
                    $('#inforolesyareas').text('Área no actualizada').addClass('alert alert-danger');
                } else if (rs === 'campos') {
                    $('#inforolesyareas').text('Campos vacios').addClass('alert alert-danger');
                } else if (rs === 'existe') {
                    $('#inforolesyareas').text('El área ya existe').addClass('alert alert-danger');
                } else if (rs === 'nopermitido') {
                    $('#inforolesyareas').text('Esta área no se puede actualizar').addClass('alert alert-danger');
                } else if (rs === 'igual') {
                    $('#inforolesyareas').text('No hay cambios').addClass('alert alert-danger');
                }
            });

            break;

        case '2': // Rol

            $.post("roleandaddres?accion=Actualizar", {rol: dato, opcion: opcion, nombre: nombre}, function (rs) {
                console.log('se fue al post de ');

                if (rs === 'true') {

                    $('#labelmodalcarga').text('Actualizando ' + nombre);

                    var modalcarag = $('#modalcarga');
                    modalcarag.modal('show');

                    setTimeout("location.reload(true);", 1000);
                } else if (rs === 'false') {
                    $('#inforolesyareas').text('Rol no actualizada').addClass('alert alert-danger');
                } else if (rs === 'campos') {
                    $('#inforolesyareas').text('Campos vacios').addClass('alert alert-danger');
                } else if (rs === 'existe') {
                    $('#inforolesyareas').text('El rol ya existe').addClass('alert alert-danger');
                } else if (rs === 'nopermitido') {
                    $('#inforolesyareas').text('Este rol no se puede actualizar').addClass('alert alert-danger');
                } else if (rs === 'igual') {
                    $('#inforolesyareas').text('No hay cambios').addClass('alert alert-danger');
                }
            });

            break;

        default:
            console.log();
            break;
    }
}

function registrar() {

    opcion = document.getElementById('ddlopcion').value;
    nombre = document.getElementById('txtnewregistro').value;
    console.log(opcion);

    if (opcion > 0) {
        if (nombre !== '') {
            $('#txtnewregistro').removeClass('is-invalid').addClass('is-valid');
            switch (opcion) {
                case '1':
                    registrarArea(opcion, nombre);
                    break;

                case '2':
                    registrarRol(opcion, nombre);
                    break;

                default:

                    break;
            }
        } else {
            $('#ddlopcion').removeClass('border border-danger').addClass('border border-success');
            $('#txtnewregistro').removeClass('border border-success is-valid').addClass('border border-danger is-invalid');
        }
    } else {
        $('#ddlopcion').addClass('border border-danger');
    }

}

function registrarArea(opcion, nombrearea) {
    $.post("roleandaddres?accion=Registrar", {opcion: opcion, nombre: nombrearea}, function (rs) {
        if (rs === 'truearea') {
            modalCarga(nombrearea);
        } else if (rs === 'falsearea') {
            $('#inforegistro').text('Rol no actualizada').addClass('alert alert-danger');
        } else if (rs === 'campos') {
            $('#inforegistro').text('Campos vacios').addClass('alert alert-danger');
        } else if (rs === 'existearea') {
            $('#txtnewregistro').removeClass('is-valid').addClass('is-invalid');
            $('#inforegistro').text('Esta área ya existe').addClass('alert alert-danger');
        } else {
            $('#inforegistro').text('No realizo cambios').addClass('alert alert-danger');
        }
    });
}

function registrarRol(opcion, nombrerol) {
    $.post("roleandaddres?accion=Registrar", {opcion: opcion, nombre: nombrerol}, function (rs) {

        if (rs === 'truerol') {
            modalCarga(nombrerol);
        } else if (rs === 'false') {
            $('#inforegistro').text('Rol no actualizada').addClass('alert alert-danger');
        } else if (rs === 'campos') {
            $('#inforegistro').text('Campos vacios').addClass('alert alert-danger');
        } else if (rs === 'existerol') {
            $('#txtnewregistro').removeClass('is-valid').addClass('is-invalid');
            $('#inforegistro').text('Este rol ya existe').addClass('alert alert-danger');
        }
    });
}

function modalCarga(texto) {
    $('#labelmodalcarga').text('Registrando ' + texto);

    var modalcarag = $('#modalcarga');
    modalcarag.modal('show');

    setTimeout("location.reload(true);", 1000);
}


function eliminar() {

    opcion = document.getElementById('hd').value;
    eliminar = document.getElementById('btnmodaleliminar').value;

    $.post("roleandaddres?accion=Eliminar", {opcion: opcion, eliminar: eliminar}, function (rs) {

        if (rs === 'true') {
            $('#labelmodalcarga').text('Eliminando');

            var modaleliminar = $('#modaleliminar');
            modaleliminar.modal('hide');

            var modalcarag = $('#modalcarga');
            modalcarag.modal('show');

            setTimeout("location.reload(true);", 1000);
        } else if (rs === 'false') {
            $('#lblmodaleliminar').text('Rol no actualizada').addClass('alert alert-danger');
        } else if (rs === 'use') {
            if (opcion === 1 || opcion === '1') {
                $('#lblmodaleliminar').text('Hay usuarios que pertenecen a esta área').addClass('alert alert-danger');
            } else if (opcion === 2 || opcion === '2') {
                $('#lblmodaleliminar').text('Usuarios tienen este cargo').addClass('alert alert-danger');
            }
        } else if (rs === 'nopermitido') {
            $('#lblmodaleliminar').text('No esta permitido eliminar.').addClass('alert alert-danger');
        } else {
            $('#lblmodaleliminar').text('No se logro eliminar').addClass('alert alert-danger');
        }
    });
}



