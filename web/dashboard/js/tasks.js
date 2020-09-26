/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global id*/

//============================================================= TAREAS

//=============== CREAR TAREA EN TASKSOFGROUP.JSP


function addTarea() {

    try {
        $('#upd').removeClass('alert alert-danger').text('');
        var fa = new Date();
        // opcion = document.getElementById('opcion').value;
        idusuario = document.getElementById('idus').value;
        nametarea = document.getElementById('txtnombretr').value;
        anotacion = document.getElementById('txtanotacion').value;
        dateinicio = document.getElementById('addtimeinicio').value;
        datefin = document.getElementById('addtimefinal').value;
        caractarea = document.getElementById('txtcaractr').value;
        commentdir = document.getElementById('txtcomentarionewtr').value;




        dti = new Date(dateinicio);
        dtf = new Date(datefin);

        dti.setDate(dti.getDate() + 1);
        dtf.setDate(dtf.getDate() + 1);

        fa.setHours(0, 0, 0, 0);
        dti.setHours(0, 0, 0, 0);
        dtf.setHours(0, 0, 0, 0);

        $('#alertcampos').addClass('alert alert-danger');

        if (idusuario === 0 || idusuario === '0') {
            $('#alertcampos').text('Seleccione usuario');
        } else if (dateinicio === '' || dateinicio === null) {
            $('#alertcampos').text('Asigne fecha de inicio');
        } else if (dti < fa) {
            $('#alertcampos').text('Fecha de inicio invalida');
        } else if (datefin === '' || datefin === null) {
            $('#alertcampos').text('Asigne fecha final');
        } else if (dtf < fa || dtf < dti) {
            $('#alertcampos').text('Fecha final invalida');
        } else if (nametarea === null || nametarea === '') { // Validacion de nombres
            $('#txtnombretr').focus();
            $('#alertcampos').text('Ingrese nombre a la tarea');
        } else if (anotacion === null || anotacion === '') {
            $('#alertcampos').text('Ingrese la anotacion.');
        } else if (caractarea === null || caractarea === '') { // Validacion de apellidos
            $('#txtcaractr').focus();
            $('#alertcampos').text('Ingrese características');
        } else if (commentdir === null || commentdir === '') { // Validacion de correo 
            $('#txtcomdir').focus();
            $('#alertcampos').text('Ingrese comentarios');
        } else {
            $.post("tasksofgroup?accion=Asignar", {idus: idusuario, nombretr: nametarea, anotacion: anotacion, caractr: caractarea, comdir: commentdir, addtimeinicio: dateinicio, addtimefinal: datefin}, function (rs) {
                try {
                    if (rs === 'true') {

                        $('#idus').val(0);
                        $('#txtnombretr').val('');
                        $('#txtanotacion').val('');
                        $('#addtimeinicio').val(null);
                        $('#addtimefinal').val(null);
                        $('#txtcaractr').val('');
                        $('#txtcomentarionewtr').val('');
                        $('#alertcampos').text('').removeClass('alert alert-danger');

                        var mdaddtr = $('#modaltarea');
                        mdaddtr.modal('hide');
                        $('#labelmodalcarga').text('Asignando la tarea ');

                        var modalcarga = $('#modalcarga');
                        modalcarga.modal('show');
                        //  setTimeout("location.reload(true);", 1500);

                        setTimeout(function () {
                            verTareasUsuario(idusuario, '1');
                            modalcarga.modal('hide');
                        }, 800);

                    } else if (rs === "dtiniciofalse") {
                        $('#alertcampos').text('Fecha inicial invalida');
                    } else if (rs === "dtfinfalse") {
                        $('#alertcampos').text('Fecha final invalida');
                    } else if (rs === 'notper') {
                        $('#alertcampos').text('No tiene permisos de agregar estar tarea.');
                    } else if (rs === 'campos') {
                        $('#alertcampos').text('Campos incompletos');
                    } else if (rs === 'index') {
                        setTimeout("location.href='index.jsp'", 1000);
                    } else {
                        var $mdalert = $('#mdalert'); // Ocurre error
                        $mdalert.modal('show');
                        $('#alermd').text('Tarea no asignadass');
                    }
                } catch (e) {
                    console.log('Error en rs addtr');
                }
            });
        }

    } catch (e) {
        console.log('Error en addtarea en js');
    }
}


//========== Actualizacion de la tarea solo por parte del director en tareas grupales
function updateTr() {

    $('#upd').removeClass('alert alert-danger').text('');
    val = document.getElementById('htr').value;
    tr = document.getElementById('btnupdatetarea').value;

    if (val === '1' && tr > 0) {

        var fa = new Date();
        //  Datos de la tarea actuales o actualizadados
        nametr = document.getElementById('txtnameupd').value;
        caractr = document.getElementById('txtcaractupd').value;
        anotacion = document.getElementById('txtanotacionupd').value;
        dtinicioupd = document.getElementById('dtinicioupd').value;
        dtfinupd = document.getElementById('dtfinupd').value;
        us = document.getElementById('hus').value;
        tr = document.getElementById('btnupdatetarea').value;

        // Fechas actuales en la tarea cuando la selecciono para ver = Fecha original en el momento
        diniciooriginal = document.getElementById('dinicio').value; // Fecha de los hidden originales

        // Conversion de fechas
        dtiupdate = new Date(dtinicioupd); // Fecha de inicio que va actualizar si la actualiza en input que esta visible
        dtffinal = new Date(dtfinupd); // Fecha final que va actualizar si actualiza en input que esta visible

        dtioriginal = new Date(diniciooriginal); // Fecha de inicio original en el hidden

        // Se le suma un dia a las fechas porque llegan con un dia menos
        dtiupdate.setDate(dtiupdate.getDate() + 1);
        dtffinal.setDate(dtffinal.getDate() + 1);
        dtioriginal.setDate(dtioriginal.getDate() + 1);
        // Se dejan las horas en 0
        fa.setHours(0, 0, 0, 0);
        dtiupdate.setHours(0, 0, 0, 0);
        dtffinal.setHours(0, 0, 0, 0);
        dtioriginal.setHours(0, 0, 0, 0);

        if (dtiupdate >= dtioriginal || dtiupdate >= fa && dtffinal >= fa && dtffinal > dtiupdate) {
            if (nametr === null || nametr === '') {
                $('#upd').addClass('alert alert-danger');
                $('#upd').text('Ingrese el nombre.');
            } else if (caractr === null || caractr === '') {
                $('#upd').addClass('alert alert-danger');
                $('#upd').text('Ingrese caracteristicas.');
            } else if (anotacion === null || anotacion === '') {
                $('#upd').addClass('alert alert-danger');
                $('#upd').text('Ingrese sus comentarios.');
            } else {
                try {
                    $.post("tasksofgroup?accion=Actualizar", {nombre: nametr, caract: caractr, anotacion: anotacion, dtinicio: dtinicioupd, dtfinal: dtfinupd}, function (rs) {
                        if (rs === 'true') {

                            $('#upd').removeClass('alert alert-danger').text('');

                            $('#labelmodalcarga').text('Actualizando');

                            modalcarga = $('#modalcarga');
                            modalcarga.modal('show');
                            $('#dinicio').val(dtinicioupd);

                            setTimeout(function () {
                                // Pone el nombre nuevo en rl nombre de la tareas que tiene ese id
                                calcularProgreso(dtinicioupd, dtfinupd, 1);
                                $('#nmtr' + tr + 'nmtr').text(nametr);
                                $('#nombretarea').text(nametr);
                                modalcarga.modal('hide');
                            }, 900);

                        } else if (rs === 'campos') {
                            $('#upd').text('Ingrese los campos completos.').addClass('alert alert-danger');
                        } else if (rs === 'notselect') {
                            $('#upd').text('Seleccione una tarea.').addClass('alert alert-danger');
                        } else if (rs === 'false') {
                            $('#upd').text('No se actualizo.').addClass('alert alert-danger');
                        } else if (rs === 'index') {
                            setTimeout("location.href='index.jsp'", 1000);
                        } else {
                            $('#upd').text('No se actualizo la informacion.').addClass('alert alert-danger');
                        }
                    });
                } catch (e) {
                    console.log('Error en el sw send comentarios en js');
                }
            }
        } else {
            $('#upd').text('Las fechas nuevas no son validas.').addClass('alert alert-danger');
        }
    } else {
        var $mdalert = $('#mdalert');
        $mdalert.modal('show');
        $('#alermd').text('Selecione una tarea.');
    }
}



// ========= FUNCION PARA LISTAR LAS TAREAS DEL USUARIO EN TAREAS GRUPALES 
function verTareasUsuario(usuario, opc) {
    $('#upd').removeClass('alert alert-danger').text('');
    try {
        if (opc == 1) {
            $('#contenedorcomentarios').addClass('eye');
        }

        $.post("tasksofgroup?accion=tareasusuario", {accion: 'tareasusuario', usuario: usuario}, function (rs) {
            $('#tareas').empty(); // Limpia la lista de tareas para no duplicar la lista por cada click
            if (rs !== 'null') {
                tareasDelGrupo(rs);
            } else {
                $('#progresotarea').empty();
            }
        });
    } catch (e) {
        console.log('Error en js');
    }

}

// Funcion para filtar las tareas en tareas grupales 
function filtrarTareas(opcion) {
    $('#upd').removeClass('alert alert-danger').text('');
    $.post("tasksofgroup?accion=Filtrar", {opcion: opcion}, function (rs) {
        $('#tareas').empty();
        if (rs !== 'null') {
            tareasDelGrupo(rs);
        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        }
    });
}

// Funcion para listar las tareas de un usuario en tasksofgroup.jsp
function tareasDelGrupo(rs) {
    //p = document.createElement("p");
    $('#upd').removeClass('alert alert-danger').text('');
    var json = JSON.parse(rs);

    colorprogreso = "";
    for (let item of json) {

        progreso = calcularProgreso(item.fechainicio, item.fechafinal, 2);
        if (progreso <= 15) {
            colorprogreso = 'bg-danger';
        } else if (progreso > 15 && progreso <= 35) {
            colorprogreso = 'bg-warning';
        } else if (progreso > 35 && progreso <= 70) {
            colorprogreso = 'bg-info';
        } else if (progreso > 70) {
            colorprogreso = 'bg-success';
        }

        limpiarInformacion();
        progreso = parseInt(progreso);

        document.getElementById("tareas").innerHTML += "<div id='trg" + item.idtarea + "trg'> <a class='card bg-gray-200 text-gray-600 shadow mb-1' href='#'  onclick='verInfoTarea(" + item.idtarea + "," + 1 + ")'> " +
                "<span id='nmtr" + item.idtarea + "nmtr' class='text-center'>" + item.nombre + "</span>" +
                "<div class='card-body'>  <div class='progress progress-bar " + colorprogreso + " ' role='progressbar' style='width:" + progreso + "%;" +
                "height: 1px;' aria-valuenow='25' aria-valuemin'0' aria-valuemax='100'></div></div></a></div>";
    }
}


// funcion para ver la informacion de la tarea en las tareas grupales
function verInfoTarea(tarea, opc) {

    try {
        $.post("tasksofgroup?", {accion: 'infotarea', tarea: tarea, opc: 'info'}, function (rs) {
            limpiarInformacion();
            $('#htr').val('1');

            if (rs !== 'null') {
                $('#btnupdatetarea').val(tarea);
                var json = JSON.parse(rs);

                $('#nombretarea').text(json.nombre);
                $('#txtnameupd').val(json.nombre);
                $('#dinicio').val(json.fechainicio);
                $('#labelestado').text(json.estado);
                $('#labelfechaasignada').text(json.fechaasignada);
                $('#fechainicio').text(json.fechainicio);
                $('#fechafinal').text(json.fechafinal);
                $('#dtinicioupd').val(json.fechainicio);
                $('#dtfinupd').val(json.fechafinal);
                $('#txtanotacionupd').val(json.anotacion);
                $('#txtcaractupd').val(json.caracteristicas);

                // barradeProgreso(json.progreso);
                calcularProgreso(json.fechainicio, json.fechafinal, 1);

                if (opc > 0) {
                    listaComentarios(tarea);
                }

            } else if (rs === 'index') {
                setTimeout("location.href='index.jsp'", 1000);
            }
        });
    } catch (e) {
        console.log('error en ver la info de la tarea');
    }
}

// LIsta comentarios de una tarea en las tareas grupales
function listaComentarios(tarea) {

    $('#comentarios').empty();

    $.post("tasksofgroup?", {accion: 'infotarea', tarea: tarea, opc: 'com'}, function (rs) {
        if (rs != '[]') {
            $('#contenedorcomentarios').removeClass('eye');

            var json = JSON.parse(rs);
            onclick = "";
            for (let item of json) {
                var onclick = (item.iddesarrolla == id.value) ? "onclick='editComent(`" + item.comentarios + "`," + item.idcomentario + ")'" : null;
                var editar = (item.iddesarrolla == id.value) ? "fas fa-pencil-alt" : "";
                var color = (item.iddesarrolla == id.value) ? "alert-primary" : "amarillo";
                document.getElementById("comentarios").innerHTML += "<div class='sansserif card " + color + " text-gray-700 mb-1 col-auto'> <span class='text-purple'>" + item.usuario + " : </span> <span  id='c" + item.idcomentario + "c' > " + item.comentarios + "</span>" +
                        "<a href='#' class='text-primary text-right' style='font-size:12px;' " + onclick + " > <i class='" + editar + "'></i> </a>" +
                        " </div>";
            }

        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        } else {
            $('#contenedorcomentarios').addClass('eye');
            console.log('sin comentarios');
        }
    });

}

// Calcula el progreso de la tarea
function calcularProgreso(fechainicio, fechafinal, opcion) {
    var fa = new Date();
    fa.setHours(0, 0, 0, 0);
    progreso = 0;
    diastotal = 0;
    diasfaltan = 0;

    dti = new Date(fechainicio);
    dtf = new Date(fechafinal);

    dti.setDate(dti.getDate() + 1);
    dtf.setDate(dtf.getDate() + 1);

    fa.setHours(0, 0, 0, 0);
    dti.setHours(0, 0, 0, 0);
    dtf.setHours(0, 0, 0, 0);

    if (dtf === fa || dtf < fa) {
        progreso = 100;
    } else if (dti < fa || dti === fa) {
        diastotal = (dtf - dti) / 86400000;
        diasfaltan = (dtf - fa) / 86400000;
        progreso = (100 - (diasfaltan / diastotal) * 100);
    }
    if (progreso > 100) {
        progreso = 100;
    }

    progreso = parseInt(progreso, 10);

    if (opcion === 1) {
        colorprogreso = "";
        if (progreso <= 15) {
            colorprogreso = 'bg-danger';
        } else if (progreso > 15 && progreso <= 35) {
            colorprogreso = 'bg-warning';
        } else if (progreso > 35 && progreso <= 70) {
            colorprogreso = 'bg-info';
        } else if (progreso > 70) {
            colorprogreso = 'bg-success';
        }

        if (progreso > 0) {
            document.getElementById('progresotarea').innerHTML = "<div class='progress progress-bar progress-bar-striped " + colorprogreso + " text-white' role='progressbar' aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: " + progreso + "%'> " + progreso + "%</div>";
        }

    } else if (opcion === 2) {
        return progreso;
    }
}



// Limpia todos los campos en tareas grupales
function limpiarInformacion() {
    $('#upd').removeClass('alert alert-danger').text('');
    $('#progresotarea').empty();
    $('#comentarios').empty();
    $('#nombretarea').text('');
    $('#labelestado').text('');
    $('#labelfechaasignada').text('');
    $('#fechainicio').text('');
    $('#fechafinal').text('');

    $('#btnupdatetarea').val(0);
    $('#dinicio').val('');
    $('#dtinicioupd').val('');
    $('#dtfinupd').val('');
    $('#txtnameupd').val('');
    $('#txtanotacionupd').val('');
    $('#txtcaractupd').val('');
}



// Funcion para enviar desde tasksofgroup.jsp
function enviarComentario() {
    $('#upd').removeClass('alert alert-danger').text('');
    try {
//opcion = document.getElementById('opcion').value;
        coment = document.getElementById('txtcomentarionuevo').value;
        if (coment === null || coment === '') {
            $('#upd').text('Escriba su comentario.').addClass('alert alert-danger');
        } else {
            $.post("tasksofgroup?accion=Comentar", {comentario: coment}, function (rs) {
                if (rs === 'true') {
                    $('#txtcomentarionuevo').val('');
                    $('#txtcomentarionuevo').text('');

                    tarea = document.getElementById('btnupdatetarea').value;
                    verInfoTarea(tarea, 1);

                } else if (rs === 'false') {
                    $('#alert').text('No se envio el comentario').addClass('alert alert-danger');
                } else if (rs === 'sintarea') {
                    $('#alert').text('Seleccione una tarea').addClass('alert alert-danger');
                } else if (rs === 'index') {
                    setTimeout("location.href='index.jsp'", 1000);
                } else {
                    $('#alert').text('No se envio tu comentario').addClass('alert alert-danger');
                }
            });
        }
    } catch (e) {
        console.log('Error en sencomnt js');
    }
}


// Editar comentario de usuartio
function editComent(comentario, idecomentario) {

    $('#enviarcomentario').addClass('eye');
    $('#txtcomnew').focus();
    $('#btnupdcom').val(idecomentario);
    $('#txtcomnew').val(comentario);
    $('#toastt').removeClass('eye');
    $('#toastcomentario').toast('show');
}

function actualizarComentario(opc) {
    $('#upd').removeClass('alert alert-danger').text('');
    newcomentario = document.getElementById('txtcomnew').value;
    idecomentario = document.getElementById('btnupdcom').value;
//   tr = document.getElementById('btnupdatetarea').value;
    if (newcomentario !== '' || newcomentario !== null) {
        switch (opc) {
            case '1':

                $.post("tasks?", {accion: 'ActualizarComentario', idcomentario: idecomentario, comentario: newcomentario}, function (rs) {

                    if (rs === 'true') {
                        $('#cc' + idecomentario + 'cc').text(newcomentario);
                        closeEdit();
                    } else if (rs === 'index') {
                        setTimeout("location.href='index.jsp'", 1000);
                    }
                });
                break;

            case '2':
                $.post("tasksofgroup?accion=ActualizarComentario", {idcomentario: idecomentario, comentario: newcomentario}, function (rs) {
                    if (rs === 'true') {
                        // location.reload();
                        $('#c' + idecomentario + 'c').text(newcomentario);
                        closeEdit();
                    } else if (rs === 'index') {
                        setTimeout("location.href='index.jsp'", 1000);
                    }
                });
                break;

            default:

                break;
        }
    }
}
function closeEdit() {
    $('#enviarcomentario').removeClass('eye');
    $('#toastt').addClass('eye');
    $('#toastcomentario').toast('hide');
}

function mdDeeleteTarea() {
    $('#upd').removeClass('alert alert-danger').text('');
    val = document.getElementById('htr').value;
    if (val === '1') {
        var mddelete = $('#deleteTarea');
        mddelete.modal('show');
    } else {
        var mdalert = $('#mdalert');
        mdalert.modal('show');
        $('#alermd').text('Selecione una tarea.');
    }
}

function mdaprobarTarea() {
    $('#upd').removeClass('alert alert-danger').text('');
    val = document.getElementById('htr').value;
    if (val === '1') {
        var mdaprTarea = $('#mdaprTarea');
        mdaprTarea.modal('show');
    } else {
        var mdalert = $('#mdalert');
        mdalert.modal('show');
        $('#alermd').text('Selecione una tarea.');
    }
}

function deleteTarea() {
    $('#upd').removeClass('alert alert-danger').text('');
    try {
        tr = document.getElementById('btnupdatetarea').value;
        // opcion = document.getElementById('opcion').value;
        $('#lblInfo').addClass('alert alert-danger');

        $.post("tasksofgroup?accion=Eliminar", {}, function (rs) {
            if (rs === 'true') {

                var deleteTarea = $('#deleteTarea');
                deleteTarea.modal('hide');

                $('#labelmodalcarga').text('Eliminando tarea');
                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');

                setTimeout(function () {
                    $('#trg' + tr + 'trg').remove();
                }, 800);

                setTimeout(function () {
                    limpiarInformacion();
                    modalcarga.modal('hide');
                }, 1000);


            } else if (rs === 'noselect') {

                var mddelete = $('#deleteTarea');
                mddelete.modal('hide');

                var mdalert = $('#mdalert');
                mdalert.modal('show');
                $('#alermd').text('Selecione una tarea.');

            } else if (rs === 'index') {
                setTimeout("location.href='index.jsp'", 1000);
            } else {
                $('#lblInfo').text('No eliminada');
            }
        });
    } catch (e) {
        console.log('Error en deletetarea en js');
    }
}

function aprobarTarea() {
    try {
        // opcion = document.getElementById('opcion').value;
        $('#lblInfo').addClass('alert alert-danger');
        $('#upd').removeClass('alert alert-danger').text('');

        $.post("tasksofgroup?accion=Aprobar", function (rs) {
            if (rs === 'true') {
                var $mdapr = $('#mdaprTarea');
                $mdapr.modal('hide');

                $('#labelmodalcarga').text('Aprobando la tarea');

                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');

                setTimeout(function () {
                    tr = document.getElementById('btnupdatetarea').value;
                    $('#trg' + tr + 'trg').remove();
                }, 800);

                setTimeout(function () {
                    limpiarInformacion();
                    modalcarga.modal('hide');
                }, 1000);


            } else if (rs === 'noselect') {
                var $mdapr = $('#mdaprTarea');
                $mdapr.modal('hide');
                var $mdalert = $('#mdalert');
                $mdalert.modal('show');
                $('#alermd').text('Selecione una tarea.');
            } else if (rs === 'index') {
                setTimeout("location.href='index.jsp'", 1000);
            } else {
                $('#lblInfo').text('No aprobada');
            }
        });
    } catch (e) {
        console.log('Error en aprobarTarea en js');
    }
}


function esperaTarea() { // Estado en espera
    val = document.getElementById('htr').value;
    $('#upd').removeClass('alert alert-danger').text('');
    try {
        if (val === '1') {
            $.post("tasksofgroup?accion=Estado", {estado: '3'}, function (rs) {
                if (rs === 'true') {
                    $('#labelestado').text('En espera').removeClass('text-warning').addClass('text-primary');
                } else if (rs === 'index') {
                    setTimeout("location.href='index.jsp'", 1000);
                }
            });
        } else {
            var mdalert = $('#mdalert');
            mdalert.modal('show');
            $('#alermd').text('Selecione una tarea.');
        }
    } catch (e) {
        console.log('error' + e);
    }

}

function dprTarea() { // Desaprueba
    val = document.getElementById('htr').value;
    $('#upd').removeClass('alert alert-danger').text('');
    try {
        if (val === '1') {
            $.post("tasksofgroup?accion=Estado", {estado: '4'}, function (rs) {
                if (rs === 'true') {
                    $('#labelestado').text('Desaprobada').removeClass('text-primary').addClass('text-warning');
                } else if (rs === 'index') {
                    setTimeout("location.href='index.jsp'", 1000);
                }
            });

        } else {
            var mdalert = $('#mdalert');
            mdalert.modal('show');
            $('#alermd').text('Selecione una tarea.');
        }
    } catch (e) {
        console.log('error' + e);
    }
}

// =============================== FUNCIONES QUE EL USUARIO EN MYTASKS.JSP VA REALIZAR SOBRE SOLO SUS TAREAS CREADAS POR EL MISMO
function addTareapersonal() {

    var dateactual = new Date();
    nombretarea = document.getElementById('txtnametarea').value;
    dtinicio = document.getElementById('dtinicio').value;
    dtfinal = document.getElementById('dtfinal').value;
    anotacion = document.getElementById('txtanotaciones').value;
    caracteristicas = document.getElementById('txtcaracteristicas').value;
    dti = new Date(dtinicio);
    dtf = new Date(dtfinal);
    dti.setDate(dti.getDate() + 1);
    dtf.setDate(dtf.getDate() + 1);
    dateactual.setHours(0, 0, 0, 0);
    dti.setHours(0, 0, 0, 0);
    dtf.setHours(0, 0, 0, 0);
    if (dti > dtf || dti < dateactual || dtinicio === null || dtinicio === '') {
        $('#lblnewtarea').text('Fecha inicial invalida').addClass('alert alert-danger');
    } else if (dtf < dti || dtf < dateactual || dtfinal === null || dtfinal === '') {
        $('#lblnewtarea').text('Fecha final invalida').addClass('alert alert-danger');
    } else if (nombretarea === null | nombretarea === '') {
        $('#lblnewtarea').text('Ingrese nombre de la tarea').addClass('alert alert-danger');
    } else {
        $.post("tasks?accion=Addtareapersonal", {nombretarea: nombretarea, fechainicio: dtinicio, fechafinal: dtfinal, anotacion: anotacion, caracteristicas: caracteristicas}, function (rs) {
            if (rs === 'true') {
                $('#labelmodalcarga').text('Creando mi tarea');

                var mdnewtarea = $('#newtarea');
                mdnewtarea.modal('hide');

                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');

                $('#txtnametarea').val(null);
                $('#dtinicio').val(null);
                $('#dtfinal').val(null);
                $('#txtanotaciones').val(null);
                $('#txtcaracteristicas').val(null);

                setTimeout(function () {
                    filtrarmisTareas(7);
                    modalcarga.modal('hide');
                }, 800);

            } else if (rs === 'false') {
                $('#lblnewtarea').text('No se creo la tarea').addClass('alert alert-danger');
            } else if (rs === 'index') {
                setTimeout("location.href='index.jsp'", 1000);
            } else {
                $('#lblnewtarea').text('No se agrego su tarea').addClass('alert alert-danger');
            }
        });
    }
}

// Actualizacion de tareas en mytasks.jsp
function actualizarMitarea() {

    var fa = new Date(); // Fecha actual

    dtinicionew = document.getElementById('dtinicionew').value;
    dtfinalnew = document.getElementById('dtfinalnew').value;
    nombrenew = document.getElementById('txtnombrenew').value;
    anotacionew = document.getElementById('txtanotacionnew').value;
    caractenew = document.getElementById('txtcaracnew').value;
    dti = new Date(dtinicionew);
    dtf = new Date(dtfinalnew);
    dti.setDate(dti.getDate() + 1);
    dtf.setDate(dtf.getDate() + 1);
    fa.setHours(0, 0, 0, 0);
    dti.setHours(0, 0, 0, 0);
    dtf.setHours(0, 0, 0, 0);
    if (dtf < fa || dtf < dti) {
        $('#updatetr').text('Fechas final invalida').addClass('alert alert-danger');
    } else if (nombrenew === null || nombrenew === '' || anotacionew === null || anotacionew === '' || caractenew === null || caractenew === '') {
        $('#updatetr').text('Ingrese los campos completos').addClass('alert alert-danger');
    } else {
        $.post("tasks?accion=Actualizarmitr", {nombrenew: nombrenew, anotacionnew: anotacionew, caractenew: caractenew, fechainicionew: dtinicionew, fechafinnew: dtfinalnew}, function (rs) {

            if (rs === 'true') {
                tr = document.getElementById('htr').value;

                var modaledit = $('#modaledit');
                modaledit.modal('hide');

                $('#labelmodalcarga').text('Actualizando mi tarea');
                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');

                setTimeout(function () {
                    $('#nt' + tr + 'nt').text(nombrenew);
                    $('#caracteristicas').text(caractenew);
                    $('#anotacion').text(anotacionew);

                    $('#nombretarea').text(nombrenew);
                    modalcarga.modal('hide');
                }, 800);


            } else if (rs === 'false') {
                $('#updatetr').text('No se actualizo la tarea').addClass('alert alert-danger');
            } else if (rs === 'nopermisos') {
                $('#updatetr').text('No puede actualizar esta tarea').addClass('alert alert-danger');
            } else if (rs === 'index') {
                setTimeout("location.href='index.jsp'", 1000);
            } else {
                $('#updatetr').text('No se actualizo la tarea').addClass('alert alert-danger');
            }

        });
    }
}


// Funcion para filtrar tareas en mytasks.jsp
function filtrarmisTareas(opcion) {

    if (opcion === 7) {
        limpiaEtiquetas();
    }

    $.post("tasks?", {accion: 'Filtrar', opcion: opcion}, function (rs) {
        $('#listadetareas').empty(); // Limpia las tareas que se listan con jstl
        $('#tareasusuario').empty(); // Limpia las tareas que se listan con el json en js

        if (rs != '[]') {
            var json = JSON.parse(rs);
            
            if (opcion === 7) {
                $('#btns').empty();
                botones();
            } else {
                $('#btns').empty();
            }

            t = document.createElement('p');
            t.innerHTML = "";
            colorprogreso = "";
            for (let item of json) {
                if (item.progreso <= 15) {
                    colorprogreso = 'bg-danger';
                } else if (item.progreso > 15 && item.progreso <= 35) {
                    colorprogreso = 'bg-warning';
                } else if (item.progreso > 35 && item.progreso <= 70) {
                    colorprogreso = 'bg-info';
                } else if (item.progreso > 70) {
                    colorprogreso = 'bg-success';
                }
                t.innerHTML += "<div  id='tt" + item.idtarea + "tt'>  <a class='card shadow h-100 py-1 bg-gray-200 mb-1' href='#' onclick='verTarea(" + item.idtarea + ")' ><div class='card-body'>" +
                        "<div class='text-xs font-weight-normal text-black-50 text-uppercase mb-1'> <span id='nt" + item.idtarea + "nt'> " + item.nombre + "</span></div>" +
                        "<div class='row no-gutters align-items-center'><div class='col mr-2'><div class='row no-gutters align-items-center'>" +
                        "<div class='progress progress-bar progress-bar-striped " + colorprogreso + "' role='progressbar' style='width:" + item.progreso + "%; height: 11px'; " +
                        "aria-valuenow='50' aria-valuemin='0' aria-valuemax='100'> " + item.progreso + "%</div></div></div></div></div></a></div>";
                document.getElementById("tareasusuario").appendChild(t);
            }


        }

    });
}

function botones() {

    b = document.createElement('p');
    b.innerHTML = "<div class='btn-group mr-2 float-right' role='group' aria-label='Second group'>" +
            "<a href='#' class='btn  btn-icon-split' data-toggle='modal' data-target='#modaleliminar' title='Eliminar' >" +
            "<span class='icon text-danger' data-toggle='tooltip' ><i class='fas fa-trash'></i></span> </a>" +
            "<a href='#' class='btn btn-icon-split ' ><span class='icon text-primary' data-toggle='modal' data-target='#modaledit' title='Editar' >" +
            "<i class='fas fa-pen'></i></span></a><a href='#' class='btn btn-icon-split' ><span class='icon text-info' data-toggle='modal'" +
            "data-target='#modalhistorial' title='Archivar'><i class='fas fa-briefcase'></i></span></a></div>";
    document.getElementById('btns').appendChild(b);
}

function limpiaEtiquetas() {
    $('#nombretarea').text('');
    $('#caracteristicas').text('');
    $('#asignada').text('');
    $('#inicia').text('');
    $('#entrega').text('');
    $('#anotacion').text('');
    $('#estado').text('');
}


// Funciona para enviar comentarios desde tasks.jsp
function sendComent() {

    try {
        coment = document.getElementById('txtcomentarionuevo').value;
        if (coment === null || coment === '') {
            $('#upd').text('Escriba su comentario.').addClass('alert alert-danger');
        } else {
            $.post("tasks?", {accion: 'Comentar', comments: coment}, function (rs) {
                console.log(rs);
                if (rs === 'true') {
                    $('#txtcomentarionuevo').val('');
                    $('#txtcomentarionuevo').text('');
                    comentariosmiTarea();
                } else if (rs === 'false') {
                    $('#alert').text('No se envio el comentario.');
                } else if (rs === 'index') {
                    setTimeout("location.href='index.jsp'", 1000);
                } else if (rs === 'noselecciono') {
                    $('#alert').text('No seleccionaste una tarea.');
                } else {
                    $('#alert').text('No se envio tu comentario.');
                }
            });
        }
    } catch (e) {
        console.log('Error en sencomnt js');
    }
}

function archivarTarea() {
    $('#alertarchivar').text('').removeClass('alert alert-danger');

    $.post("tasks?accion=Archivar", function (rs) {
        console.log(rs);
        if (rs === 'true') {

            var modalhistorial = $('#modalhistorial');
            modalhistorial.modal('hide');

            $('#labelmodalcarga').text('Archivando mi tarea. ');
            var modalcarga = $('#modalcarga');

            tr = document.getElementById('htr').value;

            modalcarga.modal('show');
            setTimeout(function () {
                $('#tt' + tr + 'tt').empty();
                modalcarga.modal('hide');
                limpiaEtiquetas();
            }, 800);

        } else if (rs === 'false') {
            $('#alertarchivar').text('No se archivo tu tarea').addClass('alert alert-danger');
        } else if (rs === 'sintarea') {
            $('#alertarchivar').text('Selecciona una tarea').addClass('alert alert-danger');
        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        } else {
            $('#alertarchivar').text('No se archivo tu tarea').addClass('alert alert-danger');
        }
    });
}


function eliminarmiTarea() {
    $.post("tasks?accion=Eliminatarea", function (rs) {
        if (rs === 'true') {

            var modaleliminar = $('#modaleliminar');
            modaleliminar.modal('hide');

            $('#labelmodalcarga').text('Eliminando mi tarea. ');
            var modalcarga = $('#modalcarga');
            modalcarga.modal('show');
            tr = document.getElementById('htr').value;
            modalcarga.modal('show');
            setTimeout(function () {
                $('#tt' + tr + 'tt').empty();
                modalcarga.modal('hide');
                limpiaEtiquetas();
            }, 800);
        } else if (rs === 'false') {
            $('#lblnewtarea').text('No se elimino').addClass('alert alert-danger');
        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        } else if (rs === 'notpertenece') {
            $('#lblnewtarea').text('No se elimino').addClass('alert alert-danger');
        }
    });
}

// ===========  METODO PARA VER TAREAS EN MYTASKS.JSP  ================

function verTarea(tarea) {

    $.post("tasks?", {accion: 'Vertarea', mytarea: tarea}, function (rs) {
        if (rs != '[]' && rs !== 'null') {
            var json = JSON.parse(rs);

            $('#nombretarea').text(json.nombre);
            $('#caracteristicas').text(json.caracteristicas);
            $('#asignada').text(json.fechaasignada);
            $('#inicia').text(json.fechainicio);
            $('#entrega').text(json.fechafinal);
            $('#anotacion').text(json.anotacion);
            $('#estado').text(json.estado);

            $('#htr').val(tarea);
            // Enviar datos al modal de editar
            $('#txtnombrenew').val(json.nombre);
            $('#txtcaracnew').val(json.caracteristicas);
            $('#asignada').val(json.fechaasignada);
            $('#dtinicionew').val(json.fechainicio);
            $('#dtfinalnew').val(json.fechafinal);
            $('#txtanotacionnew').val(json.anotacion);

            comentariosmiTarea();
        }
    });
}

// Muestra los comentarios de una tarea en mis tareas.
function comentariosmiTarea() {
    $.post("tasks?", {accion: 'Comentarios'}, function (rs) {
        $('#comentariostr').empty();

        if (rs != '[]') {

            $('#contcomentarios').removeClass('eye');

            var json = JSON.parse(rs);
            c = document.createElement("p");
            c.innerHTML = "";
            onclick = "";
            for (let item of json) {
                var onclick = (item.iddesarrolla === parseInt(id.value)) ? "onclick='editComent(`" + item.comentarios + "`," + item.idcomentario + ")'" : null;
                var editar = (item.iddesarrolla === parseInt(id.value)) ? "fas fa-pencil-alt" : "";
                var color = (item.iddesarrolla === parseInt(id.value)) ? "alert-primary" : "amarillo";

                c.innerHTML += "<div class='sansserif card " + color + " text-gray-700 mb-1 col-auto'> <span class='text-purple'>" + item.usuario + " : </span> <span  id='cc" + item.idcomentario + "cc' > " + item.comentarios + "</span>" +
                        "<a href='#' class='text-primary text-right' style='font-size:12px;' " + onclick + " ><i class='  " + editar + "'></i>  </a>" +
                        " </div>";
                document.getElementById("comentariostr").appendChild(c);

            }

        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        } else {
            $('#contenedorcomentarios').addClass('eye');
        }
    });
}





// ================================================================== METODOS PARA LA PAGINA DEL HISTORIAL DE TAREAS

function verTareahistorial(tarea) {
    $.post("taskshistory?", {accion: 'VerTarea', tarea: tarea}, function (rs) {
        var json = JSON.parse(rs);

        $('#hdtr').val(json.idtarea);
        $('#nombretarea').text(json.nombreTarea);
        $('#anotacion').text(json.anotacion);
        $('#caracteristicas').text(json.caracteristicas);
        $('#fechaasignada').text(json.fechainicio);
        $('#fechainicio').text(json.fechainicio);
        $('#fechafinal').text(json.fechafinal);
        $('#fechaaprobacion').text(json.fechaaprobacion);

        $('#divbotones').removeClass('eye');

    });
}


// Funcion para listar las tareas de usuario seleccionado
function listarTareasUsuario(usuario) {
    $('#tareashistorial').empty();
    $('#divbotones').addClass('eye');
    cleanCamposHistorial();
    $.post("taskshistory?", {accion: 'tareasusuario', usuario: usuario}, function (rs) {
        if (rs !== 'null') {
            var json = JSON.parse(rs);

            for (let item of json) {
                document.getElementById('tareashistorial').innerHTML += "<a id='tr" + item.idtarea + "tr' href='#' onclick='verTareahistorial(" + item.idtarea + ")' class='col-xl-2 col-md-4 mb-2'><div class='card shadow h-100 bg-aqua'>" +
                        " <div class='card-body'> <div class='row no-gutters align-items-center'> <div class='mb-0 font-weight-bold text-gray-200'>" + item.nombreTarea + "</div>" +
                        "</div></div></div></a>";
            }

        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        }
    });
}

function restaurarTarea() {
    $.post("taskshistory?", {accion: 'Restaurar'}, function (rs) {
        if (rs === 'true') {
            var recoveryTarea = $('#recoveryTarea');
            recoveryTarea.modal('hide');
            $('#labelmodalcarga').text('Restaurando. ');

            var modalcarga = $('#modalcarga');
            modalcarga.modal('show');


            setTimeout(function () {
                tr = document.getElementById('hdtr').value;
                $('#tr' + tr + 'tr').empty();
                modalcarga.modal('hide');
                cleanCamposHistorial();
            }, 800);

        } else if (rs === 'false') {
            $('#.infohistorial').text('No se logro eliminar la tarea').addClass('alert alert-danger');
        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        }
    });
}

function eliminarHistorial() {
    $.post("taskshistory?", {accion: 'Eliminar'}, function (rs) {
        if (rs === 'true') {
            var deleteTarea = $('#deleteTarea');
            deleteTarea.modal('hide');
            $('#labelmodalcarga').text('Eliminando. ');

            var modalcarga = $('#modalcarga');
            modalcarga.modal('show');

            setTimeout(function () {
                // Pone el nombre nuevo en la lista de tareas que tiene ese id
                tr = document.getElementById('hdtr').value;
                $('#tr' + tr + 'tr').empty();
                modalcarga.modal('hide');
                cleanCamposHistorial();
            }, 800);
        } else if (rs === 'false') {
            $('#.infohistorial').text('No se logro eliminar la tarea').addClass('alert alert-danger');
        } else if (rs === 'index') {
            setTimeout("location.href='index.jsp'", 1000);
        }
    });
}


function cleanCamposHistorial() {
    $('#nombretarea').text('');
    $('#anotacion').text('');
    $('#caracteristicas').text('');
    $('#fechaasignada').text('');
    $('#fechainicio').text('');
    $('#fechafinal').text('');
    $('#fechaaprobacion').text('');
}



