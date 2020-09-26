/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//
//let error = document.getElementById('alertcampos');
//error.style.coalert - campos;
function validarEmail(correo) {
    if (/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.([a-zA-Z]{2,4})+$/.test(correo)) {
        return true;
    } else {
        return false;
    }
}

function validarNumero(telefono) {
    if (/^([0-9])*$/.test(telefono)) {
        return true;
    } else {
        return false;
    }
}

function validarLetras(cadena) {
    if (/^([a-z ñ A-Z])*$/.test(cadena)) {
        return true;
    } else {
        return false;
    }
}

//============================================================= USUARIOS
//=============== REGISTRAR USUARIO EN ALLUSERS.JSP


$("#btnadd").click(function () {
    n1 = 2;
    $('alert alert-warning').removeClass('.alert alert-warning');
    $('alert alert-danger').removeClass('.alert alert-danger');
    removerClases('#ddlarea');
    removerClases('#ddlrol');
    removerClases('#alertcampos');
    removerClases('#txtnombres');
    removerClases('#txtapellidos');
    removerClases('#txtcorreo');
    removerClases('#txttelefono');
    $('#alertcampos').text('');
    try {

        correo = document.getElementById('txtcorreo').value;
        nombres = document.getElementById('txtnombres').value;
        apellidos = document.getElementById('txtapellidos').value;
        telefono = document.getElementById('txttelefono').value;
        area = document.getElementById('ddlarea').value;
        rol = document.getElementById('ddlrol').value;
        if (nombres === null || nombres === '') { // Validacion de nombres
            $('#txtnombres').focus().removeClass('is-valid').addClass('is-invalid');
            $('#alertcampos').text('Ingrese nombres').addClass('alert alert-warning');
        } else if (validarLetras(nombres) === false) {
            $('#txtnombres').focus().removeClass('is-valid').addClass('is-invalid');
            $('#alertcampos').text('Nombre invalido').addClass('alert alert-warning');
        } else if (apellidos === null || apellidos === '') { // Validacion de apellidos
            $('#txtapellidos').focus().removeClass('is-valid').addClass('is-invalid');
            $('#alertcampos').text('Ingrese apellidos').addClass('alert alert-warning');
        } else if (validarLetras(apellidos) === false) {
            $('#txtapellidos').focus().removeClass('is-valid').addClass('is-invalid');
            $('#alertcampos').text('Apellido invalido').addClass('alert alert-warning');
        } else if (validarNumero(telefono) === false) { // Telefono
            $('#txttelefono').focus().removeClass('is-valid border-success').addClass('is-invalid border-danger');
            $('#alertcampos').text('Teléfono invalido').addClass('alert alert-warning');
        } else if (correo === null || correo === '') { // Validacion de correo 
            $('#txtcorreo').focus().removeClass('is-valid').addClass('is-invalid');
            $('#alertcampos').text('Ingrese correo electrónico').addClass('alert alert-warning');
        } else if (validarEmail(correo) === false) {
            $('#txtcorreo').focus().removeClass('is-valid').addClass('is-invalid');
            $('#alertcampos').text('Ingrese un correo valido example@dominio.com  y sin espacios').addClass('alert alert-warning');
        } else if (area === '0' || area === 0) {
            $('#alertcampos').text('Seleccione un área').addClass('alert alert-warning');
            $('#ddlarea').addClass('border-danger');
        } else if (rol === '0' || rol === 0) {
            $('#alertcampos').text('Seleccione un rol').addClass('alert alert-warning');
            $('#ddlrol').addClass('border-danger');
        } else {
            // $('#alertcampos').removeClass('alert alert-warning');
            Spinner();
            var input = ["#txtnombres", "#txtapellidos", "#txtcorreo", "#txttelefono"];
            for (var i = 0; i < input.length; i++) {
                $(input[i]).removeClass('is-invalid border-danger').addClass('is-valid border-success');
            }

            $('#ddlarea').removeClass('border-danger').addClass('border-success');
            $('#ddlrol').removeClass('border-danger').addClass('border-success');
            console.log(area);
            console.log(rol);
            // Envio de datos al controlador users
            $.post("users?accion=Agregar", {nombres: nombres, apellidos: apellidos, telefono: telefono, correo: correo, ddlarea: area, ddlrol: rol}, function (rs) {
                $('.alert alert-warning').removeClass('alert alert-warning');
                if (rs === 'true') {
                    removerSpinner();
                    $('#txtcorreo').val('');
                    $('#txtnombres').val('');
                    $('#txtapellidos').val('');
                    $('#txttelefono').val('');
                    $('#ddlarea').val(0);
                    $('#ddlrol').val(0);
                    var modalAddUsuario = $('#modalAddUsuario');
                    modalAddUsuario.modal('hide');
                    $('#mensajeusuarios').text('Enviamos un correo de confirmacion a la cuenta, esta se activara cuando se confirme desde el correo.');
                    var modalinfousuario = $('#modalinfousuario');
                    modalinfousuario.modal('show');
                } else if (rs === 'mail') {
                    removerSpinner();
                    $('#txtcorreo').focus().removeClass('is-valid border-success').addClass('is-invalid border-danger');
                    $('#alertcampos').text('Este correo no esta disponible').addClass('alert alert-warning');
                } else if (rs === 'campos') {
                    for (var i = 0; i < input.length; i++) {
                        $(input[i]).removeClass('is-valid border-success');
                    }
                    $('#ddlarea').removeClass('border-success');
                    $('#ddlrol').removeClass('border-success');
                    removerSpinner();
                    $('#alertcampos').text('Los campos estan incompletos').addClass('alert alert-danger');
                } else if (rs === 'correoinvalido') {
                    removerSpinner();
                    $('#txtcorreo').focus().removeClass('is-valid border-success').addClass('is-invalid border-danger');
                    $('#alertcampos').text('El correo no tiene formato correcto ( example@dominio.com )').addClass('alert alert-danger');
                } else if (rs === 'numeroinvalido') {
                    $('#txttelefono').focus().removeClass('is-valid border-success').addClass('is-invalid border-danger');
                    removerSpinner();
                    $('#alertcampos').text('El numero es invalido').addClass('alert alert-warning');
                } else if (rs === 'refresh') {
                    location.reload();
                } else if (rs === 'rolnopermitido') {
                    removerSpinner();
                    $('#alertcampos').text('No tiene permiso para registrar este usuario').addClass('alert alert-warning');
                } else if (rs === 'rolareainvalid') {
                    removerSpinner();
                    $('#alertcampos').text('Seleccione informacion valida').addClass('alert alert-warning');
                } else {
                    removerSpinner();
                    $('#alertcampos').text('No se realizo el registro').addClass('alert alert-danger');
                }
            });
        }

    } catch (e) {
        console.log('Error en addusuario en js');
    }
});



function removerClases(id) {
    var clases = ["border-danger", "border-success", "is-valid", "is-invalid", "alert alert-warning", "alert alert-danger"];
    for (var i = 0; i < clases.length; i++) {
        $(id).removeClass(clases[i]);
    }
}

function Spinner() {
    $('#loading1').addClass('spinner-grow text-info');
    $('#loading2').addClass('spinner-grow text-primary');
    $('#loading3').addClass('spinner-grow text-info');
    $('#loading4').addClass('spinner-grow text-primary');
}

function removerSpinner() {
    $('#loading1').removeClass('spinner-grow text-info');
    $('#loading2').removeClass('spinner-grow text-primary');
    $('#loading3').removeClass('spinner-grow text-info');
    $('#loading4').removeClass('spinner-grow text-primary');
}


//============= FUNCION PARA SUBIR EL EXCEL Y LEER EL DOCUMENTO PARA LA CARGA MASIVA

$("#btncargausuarios").click(function () {

    var fileInput = document.getElementById('file');
    var filePath = fileInput.value;
    var allowedExtensions = /(.xlsx)$/i;
    if (!allowedExtensions.exec(filePath)) {
        $('#lblcarga').text('El documento debe tener extension .xlsx').addClass('alert alert-danger');
    } else {
        $('#lblcarga').text('').removeClass('alert alert-danger');
        var $mdaddusuario = $('#modalCargar');
        $mdaddusuario.modal('hide');
        $('#labelmodalcarga').text('Cargando usuarios. ');
        var mdcarga = $('#modalcarga');
        mdcarga.modal('show');
        nombrefile = document.getElementById('file').value;
        var data = new FormData();
        jQuery.each(jQuery('#file')[0].files, function (i, file) {
            data.append('file-' + i, file);
        });
        jQuery.ajax({
            url: 'users?accion=Subirdoc',
            data: data,
            //cache: false,
            contentType: false,
            processData: false,
            method: 'POST',
            type: 'POST', // For jQuery < 1.9
            success: function (rs) {
                if (rs === 'true') {
                    $.post("users?accion=Carga", {nombrefile: nombrefile}, function (rs) {
                        if (rs === 'true') {
                            var modalCargar = $('#modalCargar');
                            modalCargar.modal('hide');
                            mdcarga.modal('hide');
                            $('#mensajeusuarios').text('Enviamos correos a los usuarios agregados para terminar el proceso de registro.');
                            var modalinfousuario = $('#modalinfousuario');
                            modalinfousuario.modal('show');
//                            setTimeout(function () {
//                                location.reload();
//                            }, 4000);
                        } else if (rs === 'false') {
                            console.log('false');
                        } else if (rs === 'refresh') {
                            location.reload();
                        } else {
                            mdcarga.modal('hide');

                            var json = JSON.parse(rs);
                            for (var i = 0; i < json.length; i++) {
                                document.getElementById("listnoagregados").innerHTML += "<li class=\"list-group-item py-1 sansserif border-0\">" + json[i] + "</li>";
                            }
                            var mdnoagregados = $('#mdnoagregados');
                            mdnoagregados.modal('show');
                        }
                    });
                }
            }
        });
    }
});

// ==== FUNCION PARA CARGAR LA FOTO DE PERFIL DEL USUARIOS

$("#btncargafoto").click(function () {
    var fileInput = document.getElementById('file');
    var filePath = fileInput.value;
    var allowedExtensions = /(.jpg|.png)$/i;
    if (!allowedExtensions.exec(filePath)) {
        $('#lblcarga').text('La foto debe ser en formato png o jpg').addClass('text-danger p-2');
    } else {
        $('#lblcarga').text('').removeClass('alert alert-danger');
        nombrefile = document.getElementById('file').value;
        var data = new FormData();
        jQuery.each(jQuery('#file')[0].files, function (i, file) {
            data.append('file-' + i, file);
        });
        jQuery.ajax({
            url: 'users?accion=Subirfoto',
            data: data,
            //cache: false,
            contentType: false,
            processData: false,
            method: 'POST',
            type: 'POST', // For jQuery < 1.9
            success: function (rs) {
                if (rs === 'true') {
                    $.post("users?accion=InsertarFoto", {nombrefoto: nombrefile}, function (rs) {
                        if (rs === 'true') {

                            var modalfoto = $('#modalfoto');
                            modalfoto.modal('hide');
                            $('#labelmodalcarga').text('Cargando foto.');
                            var mdcarga = $('#modalcarga');
                            mdcarga.modal('show');
                            setTimeout("location.reload(true);", 3500);
                        } else if (rs === 'false') {
                            console.log('false');
                        } else if (rs === 'refresh') {
                            location.reload();
                        } else {
                            console.log('ultimo else');
                        }
                    });
                }
            }
        });
    }
});

// ==== FUNCION PARA ELIMINAR FOTO


$('#btneliminafoto').click(function () {
    $.post("users?accion=Eliminarfoto");
    $('#labelmodalcarga').text('Eliminando foto.');
    var mdcarga = $('#modalcarga');
    mdcarga.modal('show');
    setTimeout("location.reload(true);", 1000);
});

function opcionRadio(opcion) {
    $('#btnexcel').val(opcion);
    $('#btnexcel').removeClass('eye');
    console.log(opcion);
}



$('#btnexcel').click(function () {
    var mdReporte = $('#mdReporte');
    mdReporte.modal('hide');
    opcion = document.getElementById('btnexcel').value;
    location.href = "users?menu=Consultar&accion=ReporteExcel&opcionusuarios=" + opcion;
});

$("#btnrefresh").click(function () {
    location.reload();
});

$("#btnrefreshh").click(function () {
    location.reload();
});

$("#btnrefreshhh").click(function () {
    location.reload();
});


function refrescarPagina() {
    location.reload();
}

//=============== EDICION DE UN USUARIO EN ALLUSERS.JSP

// Funcion para abrir modal con datos del usuario a editar
// Parametros del usuario a editar y del usuario que va editar



// Funcion para edicion de usuarios en allusers.jsp
function editarUsuario(usuario) {
    $.post("users?", {accion: 'Editar', usuario: usuario}, function (rs) {
        if (rs === 'false') {
            var mdalert = $('#modalalert');
            mdalert.modal('show');
        } else {

            var json = JSON.parse(rs);

            console.log(json);

            $('#txtcorreoedit').val(json.correo);
            $('#areanew').val(json.area);
            $('#rolnew').val(json.rol);
            var modalUpdate = $('#modalUpdate');
            modalUpdate.modal('show');
        }

    });
}

//=============== ACTUALIZACION DE DATOS DE USUARIO EN ALLUSERS.JSP


$("#btnupdate").click(function () {
    try {
        $('#usupdate').text('').removeClass('alert alert-danger');
        $('#usupdate').text('').removeClass('alert alert-warning');
        var modalcodigoalterno = $('#modalcodigoalterno');
        modalcodigoalterno.modal('hide');
        var modalUpdate = $('#modalUpdate');
        $('#usupdate').addClass('alert alert-warning');
        emailnew = document.getElementById('txtcorreoedit').value;
        areanew = document.getElementById('areanew').value;
        rolnew = document.getElementById('rolnew').value;
        codupdate = document.getElementById('txtcodupdate').value;
        var roltexto = document.getElementById("rolnew");
        var selectedrol = roltexto.options[roltexto.selectedIndex].text;
        var areatexto = document.getElementById("areanew");
        var selectedarea = areatexto.options[areatexto.selectedIndex].text;

        if (codupdate === '' || codupdate === null) {
            codupdate = 0;
        }

        if (emailnew === '' || emailnew === null) {
            $('#usupdate').text('El correo electrónico es obligatorio');
        } else if (validarEmail(emailnew) === false) {
            $('#usupdate').text('Ingrese un correo valido example@dominio.com  y sin espacios');
        } else if (areanew === 0 || areanew === '0') {
            $('#usupdate').text('Seleccione un área');
        } else if (rolnew === 0 || rolnew === '0') {
            $('#usupdate').text('Seleccione un rol'); // 
        } else {
            $('#usupdate').text(null).removeClass('alert alert-warning');
            // Envia datos al controlador users
            $.post("users?accion=Actualizar", {correo: emailnew, rol: rolnew, area: areanew, codigocorreo: codupdate, roltexto: selectedrol, areatexto: selectedarea}, function (rs) {

                if (rs === 'true') {

                    $('#labelmodalcarga').text('Actualizando datos');
                    modalUpdate.modal('hide');
                    var modalcarga = $('#modalcarga');
                    modalcarga.modal('show');
                    setTimeout("location.reload(true);", 2000);
                } else if (rs === 'false') {
                    $('#usupdate').text('No se actualizo').addClass('alert alert-warning');
                } else if (rs === 'pr') {
                    $('#usupdate').text('No cuenta con permisos').addClass('alert alert-warning');
                } else if (rs === 'refresh') {
                    location.reload();
                } else if (rs === 'correonodisponible') {
                    $('#usupdate').text('Este correo no esta disponible').addClass('alert alert-warning');
                } else if (rs === 'campos') {
                    $('#usupdate').text('Campos incompletos').addClass('alert alert-warning');
                } else if (rs === 'rolareainvalid') {
                    $('#usupdate').text('Seleccione datos validos').addClass('alert alert-warning');
                } else if (rs === 'correoinvalido') {
                    $('#usupdate').text('Ingrese un correo valido example@dominio.com  y sin espacios').addClass('alert alert-warning');
                } else if (rs === 'cambiocorreo') {
                    modalcodigoalterno.modal('show');
                } else if (rs === 'codigoinvalido') {
                    modalcodigoalterno.modal('show');
                    $('#lblcodigo').text('Código invalido').addClass('alert alert-warning');
                } else if (rs === 'nosepuedeusar') {
                    $('#usupdate').text('Este correo no puede ser el principal').addClass('alert alert-warning');
                } else if (rs === 'rolareainvalida') {
                    $('#usupdate').text('No tiene permisos para esta actualización').addClass('alert alert-warning');
                } else {
                    $('#usupdate').text('No se actualizo');
                }
            });
        }
    } catch (e) {
        console.log('Error en updatedatois en js');
    }
});



$("#btnupdatedatoscod").click(function () {
    try {
        $('#usupdate').text('').removeClass('alert alert-danger');
        $('#usupdate').text('').removeClass('alert alert-warning');
        var modalcodigoalterno = $('#modalcodigoalterno');
        modalcodigoalterno.modal('hide');
        var modalUpdate = $('#modalUpdate');
        $('#usupdate').addClass('alert alert-warning');
        emailnew = document.getElementById('txtcorreoedit').value;
        areanew = document.getElementById('areanew').value;
        rolnew = document.getElementById('rolnew').value;
        codupdate = document.getElementById('txtcodupdate').value;
        var roltexto = document.getElementById("rolnew");
        var selectedrol = roltexto.options[roltexto.selectedIndex].text;
        var areatexto = document.getElementById("areanew");
        var selectedarea = areatexto.options[areatexto.selectedIndex].text;

        if (codupdate === '' || codupdate === null) {
            codupdate = 0;
        }
        if (emailnew === '' || emailnew === null) {
            $('#usupdate').text('El correo electrónico es obligatorio');
        } else if (validarEmail(emailnew) === false) {
            $('#usupdate').text('Ingrese un correo valido example@dominio.com  y sin espacios');
        } else if (areanew === 0 || areanew === '0') {
            $('#usupdate').text('Seleccione un área');
        } else if (rolnew === 0 || rolnew === '0') {
            $('#usupdate').text('Seleccione un rol'); // 
        } else {
            $('#usupdate').text(null).removeClass('alert alert-warning');
            // Envia datos al controlador users
            $.post("users?accion=Actualizar", {correo: emailnew, rol: rolnew, area: areanew, codigocorreo: codupdate, roltexto: selectedrol, areatexto: selectedarea}, function (rs) {

                if (rs === 'true') {

                    $('#labelmodalcarga').text('Actualizando datos');
                    modalUpdate.modal('hide');
                    var modalcarga = $('#modalcarga');
                    modalcarga.modal('show');
                    setTimeout("location.reload(true);", 2000);
                } else if (rs === 'false') {
                    $('#usupdate').text('No se actualizo').addClass('alert alert-warning');
                } else if (rs === 'pr') {
                    $('#usupdate').text('No cuenta con permisos').addClass('alert alert-warning');
                } else if (rs === 'refresh') {
                    location.reload();
                } else if (rs === 'correonodisponible') {
                    $('#usupdate').text('Este correo no esta disponible').addClass('alert alert-warning');
                } else if (rs === 'campos') {
                    $('#usupdate').text('Campos incompletos').addClass('alert alert-warning');
                } else if (rs === 'rolareainvalid') {
                    $('#usupdate').text('Seleccione datos validos').addClass('alert alert-warning');
                } else if (rs === 'correoinvalido') {
                    $('#usupdate').text('Ingrese un correo valido example@dominio.com  y sin espacios').addClass('alert alert-warning');
                } else if (rs === 'cambiocorreo') {
                    modalcodigoalterno.modal('show');
                } else if (rs === 'codigoinvalido') {
                    modalcodigoalterno.modal('show');
                    $('#lblcodigo').text('Incorrecto').addClass('alert alert-warning');
                } else if (rs === 'nosepuedeusar') {
                    $('#usupdate').text('Este correo no puede ser el principal').addClass('alert alert-warning');
                } else if (rs === 'rolareainvalida') {
                    $('#usupdate').text('No tiene permisos para esta actualización').addClass('alert alert-warning');
                } else {
                    $('#usupdate').text('No se actualizo');
                }
            });
        }
    } catch (e) {
        console.log('Error en updatedatois en js');
    }
});




//=============== DESHABILITAR USUARIO EN ALLUSERS.JSP
// Abre modal para la confirmacion de deshabilitar usuario 

function confirmarInactivar(usuario, nombre) {
    try {

        $('#hdus').val(usuario);
        $.post("users?", {accion: 'Editar', usuario: usuario}, function (rs) {
            if (rs === 'false') {
                var mdalert = $('#modalalert');
                mdalert.modal('show');
            } else {
                $('#nmusdeshabilita').text(nombre);
                var mdinactivarUser = $('#inactivarUser');
                mdinactivarUser.modal('show');
            }

        });
    } catch (e) {
        console.log('Error al confirmar deshabilitar usuario en js', e);
    }
}
// ============== CONFIRMA ACTIVAR USUARIO.


function confirmaActivar(usuario, nombre) {
    try {
        $('#hdus').val(usuario);
        $.post("users?", {accion: 'Editar', usuario: usuario}, function (rs) {
            if (rs === 'false') {
                var mdalert = $('#modalalert');
                mdalert.modal('show');
            } else {
                $('#nmushabilita').text(nombre);
                var activarUser = $('#activarUser');
                activarUser.modal('show');
            }

        });
    } catch (e) {
        console.log('Error al confirmar delete de us en js', e);
    }
}


// Habilitar usuario


$("#btninactiva").click(function () {
    try {
        $.post("users?accion=Inactivar", function (rs) {
            if (rs === 'true') {
                $('#labelmodalcarga').text('Deshabilitando');
                var mdinactivarUser = $('#inactivarUser');
                mdinactivarUser.modal('hide');
                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');
                setTimeout("location.reload(true);", 1200);
            } else if (rs === 'false') {
                var mdactiva = $('#modalalert');
                mdactiva.modal('show');
            } else if (rs === 'refresh') {
                location.reload();
            } else {
                $('#alert').addClass('alert alert-danger');
                $('#lbl').text('No se deshabilito el usuario');
            }
        });
        // Envio de datos al controlador users
    } catch (e) {
        console.log('Error al deleteusuario en js', e);
    }
});



//=============== ELIMINA UN USUARIO EN ALLUSERS.JSP


$("#btnactiva").click(function () {
    try {
        $.post("users?accion=Activar", function (rs) {
            if (rs === 'true') {
                var activarUser = $('#activarUser');
                activarUser.modal('hide');
                $('#labelmodalcarga').text('Habilitando');
                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');
                setTimeout("location.reload(true);", 1200);
            } else if (rs === 'refresh') {
                location.reload();
            } else if (rs === 'false') {
                $('#alert').addClass('alert alert-danger');
                $('#lbl').text('No se habilito el usuario');
            } else {
                $('#alert').addClass('alert alert-danger');
                $('#lbl').text('No se logro habilitar');
            }
        });
        // Envio de datos al controlador users
    } catch (e) {
        console.log('Error al deleteusuario en js', e);
    }
});





// Elimina usuario en allusers.jsp


function confirmaEliminar(usuario, nombreusuario) {
    $('#labelelimina').removeClass('alert alert-danger').text('');
    $.post("users?", {accion: 'Editar', usuario: usuario}, function (rs) {
        if (rs === 'false') {
            var mdalert = $('#modalalert');
            mdalert.modal('show');
        } else {

            $('#hdus').val(usuario);
            $('#nmuserelimina').text(nombreusuario);
            var mdeliminarus = $('#mdeliminaruser');
            mdeliminarus.modal('show');
        }
    });
}


$("#btneliminar").click(function () {
    try {
        $.post("users?accion=Eliminar", function (rs) {

            if (rs === 'true') {
                $('#labelmodalcarga').text('Eliminando');
                var mdeliminarus = $('#mdeliminaruser');
                mdeliminarus.modal('hide');
                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');
                us = document.getElementById('hdus').value;
                setTimeout(function () {
                    $('#tra' + us + 'tra').empty();
                    $('#trd' + us + 'trd').empty();
                    $('#tra' + us + 'tra').remove();
                    $('#trd' + us + 'trd').remove();
                    modalcarga.modal('hide');
                }, 1500);
            } else if (rs === 'false') {
                $('#labelelimina').text('No se elimino el usuario').addClass('alert alert-danger');
            } else if (rs === 'sinpermisos') {
                $('#labelelimina').text('No tiene permisos').addClass('alert alert-danger');
            } else {
                $('#labelelimina').text('No se elimino el usuario').addClass('alert alert-danger');
            }
        });
    } catch (e) {
        console.log('Error al deleteusuario en js', e);
    }
});

// ====================================================== METHODS IN PROFILE


$("#btnupdatepass").click(function () {
    passact = document.getElementById('txtpsactual').value;
    passone = document.getElementById('txtpssone').value;
    passtwo = document.getElementById('txtpsstwo').value;
    if (passact === '' || passact === null) {

        $('#lblps').text('Ingrese la clave actual').addClass('text-danger');
        $('#txtpsactual').addClass('border border-danger');
        $('#txtpsactual').focus();
    } else if (passone === '' || passone === null || passtwo === '' || passtwo === null) {

        $('#txtpsactual').removeClass('border border-danger').addClass('border border-success');
        $('#alert').addClass('alert alert-danger');
        $('#txtpsactual').focus();
        $('#lblps').text('Ingrese la clave nueva y confirmela').addClass('text-danger');
    } else if (passone !== passtwo) {

        $('#txtpsactual').removeClass('border border-danger').addClass('border border-success');
        $('#txtpssone').addClass('border border-danger');
        $('#txtpsstwo').addClass('border border-danger');
        $('#lblps').text('Las claves no coinciden').addClass('text-danger');
    } else if (passone.length < 9 && passtwo.length < 9) {
        $('#txtpsactual').removeClass('border border-danger').addClass('border border-success');
        $('#txtpssone').addClass('border border-danger');
        $('#txtpsstwo').addClass('border border-danger');
        $('#lblps').text('Tu clave es insegura').addClass('text-danger');
    } else {

        $('#p').removeClass('eye');
        var segundos = 0;
        window.setInterval(function () {
            $('#progress').width(segundos);
            segundos++;
        }, 01);
        $.post("users?accion=Updpass", {passact: passact, pass: passone, passtwo: passtwo}, function (rs) {
            if (rs === 'true') {
                $('#labelmodalcarga').text('Actualizando');
                var updatePassword = $('#updatePassword');
                updatePassword.modal('hide');
                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');
                $('#p').addClass('eye');
                $('#txtpsactual').val(null);
                $('#txtpssone').val(null);
                $('#txtpsstwo').val(null);
                $('#txtpsactual').text('');
                $('#txtpssone').text('');
                $('#txtpsstwo').text('');
                setTimeout(function () {
                    modalcarga.modal('hide');
                }, 800);
            } else if (rs === 'psinvalidact') {
                $('#p').addClass('eye');
                $('#txtpsactual').focus();
                $('#lblps').text('Clave no valida').addClass('text-danger');
                $('#txtpsactual').addClass('border border-danger');
            } else if (rs === 'psinvalidnew') {
                $('#p').addClass('eye');
                $('#lblps').text('Claves invalidas').addClass('text-danger');
                $('#txtpssone').addClass('border border-danger');
            } else {
                $('#p').addClass('eye');
                $('#lblps').text('No se actualizo la clave').addClass('text-danger');
            }
        });
    }
});



// Actualizacion de informacion desde el perfil del usuario
function updInfo() {

    var modalcodigoalterno = $('#modalcodigoalterno');
    modalcodigoalterno.modal('hide');
    name = document.getElementById('txtnombres').value;
    last = document.getElementById('txtapellidos').value;
    tele = document.getElementById('txttelefono').value;
    correoalterno = document.getElementById('txtcorreoalterno').value;
    cod = document.getElementById('txtcodalterno').value;
    if (cod === null || cod === '') {
        cod = 0;
    }

    var inputs = ["#txtnombres", "#txtapellidos", "#txttelefono", "#txtcorreoalterno"];
    for (var i = 0; i < inputs.length; i++) {
        $(inputs[i]).removeClass('is-invalid').addClass('is-valid');
    }

    $('#lblinf').addClass('text-danger');
    if (name === null || name === '') {
        $('#txtnombres').removeClass('is-valid').addClass('is-invalid');
        $('#lblinf').text('Ingrese el nombre');
    } else if (validarLetras(name) === false) {
        $('#txtnombres').removeClass('is-valid').addClass('is-invalid');
        $('#lblinf').text('El nombre es invalido');
    } else if (last === '' || last === '') {
        $('#txtapellidos').removeClass('is-valid').addClass('is-invalid');
        $('#lblinf').text('Ingrese apellidos');
    } else if (validarLetras(last) === false) {
        $('#txtapellidos').removeClass('is-valid').addClass('is-invalid');
        $('#lblinf').text('Apellido invalido');
    } else if (validarNumero(tele) === false) {
        $('#txttelefono').removeClass('is-valid').addClass('is-invalid');
        $('#lblinf').text('Número invalido');
    } else if (correoalterno === '' || correoalterno === null) {
        $('#txtcorreoalterno').removeClass('is-valid').addClass('is-invalid');
        $('#lblinf').text('Ingrese un correo alternativo');
    } else if (validarEmail(correoalterno) === false) {
        $('#txtcorreoalterno').removeClass('is-valid').addClass('is-invalid');
        $('#lblinf').text('Ingrese un correo valido example@dominio.com  y sin espacios');
    } else {
        $.post("users?accion=Updmidatos", {names: name, last: last, correoalterno: correoalterno, telep: tele, codalterno: cod}, function (rs) {
            if (rs === 'true') {
                $('#labelmodalcarga').text('Actualizando.');
                var updateDatos = $('#updateDatos');
                updateDatos.modal('hide');
                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');
                setTimeout(function () {

                    $('#lblnombres').text(name);
                    $('#lblapellidos').text(last);
                    $('#lbltelefono').text(tele);
                    $('#lblcorreoalterno').text(correoalterno);
                    $('#txtnombres').val(name);
                    $('#txtapellidos').val(last);
                    $('#txttelefono').val(tele);
                    $('#txtcorreoalterno').val(correoalterno);
                    $('#alercmp').empty();

                }, 500);
                setTimeout(function () {
                    modalcarga.modal('hide');
                }, 800);
            } else if (rs === 'campos') {
                for (var i = 0; i < inputs.length; i++) {
                    $(inputs[i]).removeClass('is-valid');
                }
                $('#lblinf').text('Campos incompletos').addClass('text-danger');
            } else if (rs === 'false') {
                $('#lblinf').text('No se actualizaron los datos.').addClass('text-danger');
            } else if (rs === 'emailnodisponible') {
                $('#txtcorreoalterno').removeClass('is-valid').addClass('is-invalid');
                $('#lblinf').text('Este correo no esta disponible').addClass('text-danger');
            } else if (rs === 'emailigual') {
                $('#txtcorreoalterno').removeClass('is-valid').addClass('is-invalid');
                $('#lblinf').text('Usa un correo alternativo diferente.').addClass('text-danger');
            } else if (rs === 'correoinvalido') {
                $('#txtcorreoalterno').removeClass('is-valid').addClass('is-invalid');
                $('#lblinf').text('Ingrese un correo valido example@dominio.com  y sin espacios').addClass('text-danger');
            } else if (rs === 'numeroinvalido') {
                $('#txttelefono').removeClass('is-valid').addClass('is-invalid');
                $('#lblinf').text('Ingrese un número valido').addClass('text-danger');
            } else if (rs === 'validaralterno') {
                modalcodigoalterno.modal('show');
            } else if (rs === 'codinvalido') {
                $('#txtcodalterno').addClass(' is-invalid');
                $('#lblcodigo').text('El código es invalido').addClass('alert alert-danger');
                modalcodigoalterno.modal('show');
            } else if (rs === 'nuevoalterno') {
                $('#divmensajecodigo').text('Enviamos un código al correo ingresado, por favor ingresalo');
                modalcodigoalterno.modal('show');
            } else {
                $('#lblinf').text('No se actualizaron los datos.').addClass('text-danger');
            }
        });
    }
}




