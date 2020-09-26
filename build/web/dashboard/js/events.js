/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global  fetch, dtti */

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

function validarLetras(telefono) {
    if (/^([a-z A-Z])*$/.test(telefono)) {
        return true;
    } else {
        return false;
    }
}

//============================================================= OBJETOS


// REGISTRAR OBJETO EN OBJECTSOFEVENTS.JSP

function addOjeto() {

    try {
        nameoj = document.getElementById('txtnombreobj').value;
        marcaobj = document.getElementById('txtmarcaobj').value;
        serialobj = document.getElementById('txtserialobj').value;
        caract = document.getElementById('txtcaracte').value;
        estado = document.getElementById('ddlestado').value;
        console.log(estado);
        $('#addobjnew').addClass('alert alert-danger');
        if (nameoj === null || nameoj === '') {
            $('#addobjnew').text('Ingrese nombre del objeto');
        } else if (marcaobj === null || marcaobj === '') {
            $('#addobjnew').text('Ingrese la marca');
        } else if (serialobj === null || serialobj === '') {
            $('#addobjnew').text('Ingrese el serial');
        } else if (estado === '0' || estado === 0) {
            $('#addobjnew').text('Ingrese el estado');
        } else {
            $.post("objectsofevents?accion=Registrar", {nombreobj: nameoj, serialobj: serialobj, marcaobj: marcaobj, caracteobj: caract, estadoobj: estado}, function (rs) {
                console.log(rs);
                if (rs === 'true') {

                    var $mdactualizarobj = $('#mdaddObjeto');
                    $mdactualizarobj.modal('hide');

                    $('#labelmodalcarga').text('Registrando objeto.');

                    var $modalcarga = $('#modalcarga');
                    $modalcarga.modal('show');

                    setTimeout("location.reload(true);", 1700);
                } else if (rs === 'campos') {
                    $('#addobjnew').text('Ingrese campos completos');
                } else if (rs === 'false') {
                    $('#addobjnew').text('Objeto no registrado');
                } else if (rs === 'serial') {
                    $('#addobjnew').text('Este serial ya lo tiene un objeto.');
                } else {
                    $('#addobjnew').text('No se registro el objeto');
                }
            });
        }
    } catch (e) {
        console.log('Error en add objeto en js');
    }

}

// ============ FUNCION PARA LA CARGA MASIVA DE OBJETOS

function cargarObjetos() {

    var fileInput = document.getElementById('file');
    var filePath = fileInput.value;
    var allowedExtensions = /(.xlsx)$/i;

    // Valida que el documento tenga el formato correcto.
    if (!allowedExtensions.exec(filePath)) {
        $('#lblcarga').text('El documento debe tener extensión .xlsx').addClass('alert alert-danger');
    } else {
        $('#lblcarga').text('').removeClass('alert alert-danger');

        var $mdcargaobjetos = $('#mdcargaobjetos');
        $mdcargaobjetos.modal('hide');

        $('#labelmodalcarga').text('Cargando objetos. ');

        var $mdcarga = $('#modalcarga');
        $mdcarga.modal('show');

        nombrefile = document.getElementById('file').value;
        // Obtiene los archivos seleccionados en el input file
        var data = new FormData();
        jQuery.each(jQuery('#file')[0].files, function (i, file) {
            data.append('file-' + i, file);
        });

        // Se envia al controlador con la accion de subir el data que obtiene el documento.
        jQuery.ajax({
            url: 'objectsofevents?accion=Subir',
            data: data,
            //cache: false,
            contentType: false,
            processData: false,
            method: 'POST',
            type: 'POST', // For jQuery < 1.9
            success: function (rs) {
                // rs obtiene la respuesta desde el controlador
                if (rs === 'true') {
                    // SI es true envia el nombre del archivo.
                    $.post("objectsofevents?accion=Carga", {nombrefile: nombrefile}, function (rs) {
                        if (rs === 'true') {
                            // Refresca la pagina despues de 2 segundos.
                            setTimeout("location.reload(true);", 2000);
                        } else if (rs === 'false') {
                            console.log('false');
                        } else {
                            $mdcarga.modal('hide');
                            $mdcargaobjetos.modal('hide');
                            var json = JSON.parse(rs);
                            for (var i = 0; i < json.length; i++) {
                                document.getElementById("listnoagregados").innerHTML += "<li class=\"list-group-item py-1 sansserif border-0\">" + json[i] + "</li>";
                            }
                            var $mdnoagregados = $('#mdnoagregados');
                            $mdnoagregados.modal('show');
                        }
                    });
                }
            }
        });
    }
}

function reporteObjetos() {
    var mdreporte = $('#mdreporte');
    mdreporte.modal('hide');

    location.href = "objectsofevents?accion=ReporteExcel";
}

function refrescarPagina() {
    location.reload();
}

// ============= FUNCION PARA EDITAR LOS DATOS DE UN OBJETO 

function editarObjeto(objeto) {

    try {
        $('#updateobjeto').text('').removeClass('alert alert-danger');
        $.post("objectsofevents", {accion: 'Verinfo', objeto: objeto}, function (rs) {
            var json = JSON.parse(rs);
            $('#txtnombreobjupd').val(json.objetonombre);
            $('#txtmarcaobjupd').val(json.marca);
            $('#txtserialobjupd').val(json.serial);
            $('#txtcaracteristicasupd').val(json.caracteristicas);
            $('#btnactualizarobj').val(objeto);
            $('#ddlestadoupd').val(json.estado);
        });
        var $mdactualizarobj = $('#mdactualizarobj');
        $mdactualizarobj.modal('show');
    } catch (e) {
        console.log('Error al editar objeto en js');
    }
}

// ============= FUNCION PARA ACTUALIZAR LOS DATOS DE UN OBJETO 

function actualizarObjeto() {
    try {
        objeto = document.getElementById('btnactualizarobj').value;
        nombreobj = document.getElementById('txtnombreobjupd').value;
        marcaobj = document.getElementById('txtmarcaobjupd').value;
        serialobj = document.getElementById('txtserialobjupd').value;
        caracteristicas = document.getElementById('txtcaracteristicasupd').value;
        estado = document.getElementById('ddlestadoupd').value;


        if (objeto < 1) {
            $('#updateobjeto').text('Seleccione un objeto').addClass('alert alert-danger');
        } else if (nombreobj === null || nombreobj === '') {
            $('#updateobjeto').text('Ingrese el nombre').addClass('alert alert-danger');
        } else if (marcaobj === null || marcaobj === '') {
            $('#updateobjeto').text('Ingrese la marca').addClass('alert alert-danger');
        } else if (serialobj === null || serialobj === '') {
            $('#updateobjeto').text('Ingrese el serial').addClass('alert alert-danger');
        } else if (caracteristicas === null || caracteristicas === '') {
            $('#updateobjeto').text('Ingrese caracteristicas').addClass('alert alert-danger');
        } else if (estado === 0 || estado === '0') {
            $('#updateobjeto').text('Seleccione el estado').addClass('alert alert-danger');
        } else if (estado < 7 || estado > 9) {
            $('#updateobjeto').text('El estado es invalido').addClass('alert alert-danger');
        } else {
            $.post("objectsofevents?accion=Actualizar", {objeto: objeto, nombreobj: nombreobj, marcaobj: marcaobj, serialobj: serialobj, caracteristicasobj: caracteristicas, estadoobj: estado}, function (rs) {
                console.log(rs);
                if (rs === 'true') {
                    var mdactualizarobj = $('#mdactualizarobj');
                    mdactualizarobj.modal('hide');

                    $('#labelmodalcarga').text('Actualizando objeto');

                    var modalcarga = $('#modalcarga');
                    modalcarga.modal('show');

                    setTimeout("location.reload(true);", 1200);
                } else if (rs === 'false') {
                    $('#updateobjeto').text('Objeto no actualizado').addClass('alert alert-danger');
                } else if (rs === 'campos') {
                    $('#updateobjeto').text('Campos incompletos').addClass('alert alert-danger');
                } else if (rs === 'estadoinvalido') {
                    $('#updateobjeto').text('Estado invalido').addClass('alert alert-danger');
                } else if (rs === 'serial') {
                    $('#updateobjeto').text('Este serial ya existe').addClass('alert alert-danger');
                } else if (rs === 'usado') {
                    $('#updateobjeto').text('Mientras este en uso no puedes actualizar').addClass('alert alert-danger');
                } else {
                    $('#updateobjeto').text('No se logro actualizar').addClass('alert alert-danger');
                }
            });
        }
    } catch (e) {
        console.log('Error en la actualizacion del objeto en js');
    }

}

// ============== VER CARACTERISTICAS DE UN OBJETO DISPONIBLE

function verCaracteristicas(objeto) {
    try {

        $.post("objectsofevents", {accion: 'Verinfo', objeto: objeto}, function (rs) {
            var json = JSON.parse(rs);
            console.log(json);
            $("#infoobjeto").removeClass('eye');
            $('#nombredelobjeto').text(json.objetonombre);
            $('#txtserial').text(json.serial);
            $('#txtcarac').text(json.caracteristicas);

        });


    } catch (e) {
        console.log(e);
    }
}


//=============== VALIDACION PARA VER SI UN USUARIO TIENE EVENTOS PARA AGREGAR OBJETOS

function validaObjeto(idobj, nmobj) {
    limpiarEventos();
    try {
        $('#list-tab').empty();
        $.post("events?", {accion: 'Listeventos'}, function (rs) {

            if (rs != '[]') {
                var json = JSON.parse(rs);
                for (let item of json) {

                    document.getElementById('listmiseventos').innerHTML += "<option id='option'  class='btn btn-primary list-group-item list-group-item-action mb-1' data-toggle='list' onclick='separarObjeto(" + item.idevento + ");'>" +
                            item.nombreevento +
                            "</option>";
                }

                $('option').val(idobj);
                $('#nmobjetos').text(nmobj);

                var modalSeparar = $('#modalSeparar');
                modalSeparar.modal('show');

            } else {
                $('#infobj').text('Para separar objetos debes crear un evento.').addClass('fas text-primary');
                var $mdevento = $('#mdCrearEvento');
                $mdevento.modal('show');
            }

        });

    } catch (e) {
        console.log('Error en validarobjeto en js', e);
    }
    // return error.innerHTML = alertobj.join('');
}



//=============== SEPARA OBJETO 
function separarObjeto(idevent) {

    try {

        idobj = document.getElementById('option').value;
        $('#objinfo').addClass('alert alert-danger');

        if (idobj === null || idobj === '') {
            $('#objinfo').text('No seleciono objeto');
        } else if (idevent === null || idevent === '') {
            $('#objinfo').text('No seleciono evento');
        } else {
            $.post("objectsofevents?accion=Separar", {idobj: idobj, idevent: idevent}, function (rs) {
                if (rs === 'true') {

                    $('#objinfo').removeClass('alert alert-danger').text('');
                    var modalSeparar = $('#modalSeparar');
                    modalSeparar.modal('hide');

                    $('#labelmodalcarga').text('Agregando objeto');

                    var modalcarga = $('#modalcarga');
                    modalcarga.modal('show');

                    setTimeout(function () {
                        $('#trobjeto' + idobj).empty();
                        $('#trobjeto' + idobj).remove();
                        listarObjetosEvento(idevent, 'show');
                        modalcarga.modal('hide');
                    }, 500);

                    // setTimeout("location.reload(true);", 1000);
                } else if (rs === 'not') {
                    $('#objinfo').text('Objeto no disponible');
                } else if (rs === 'false') {
                    $('#objinfo').text('No se asigno el objeto');
                } else if (rs === 'sinevent') {
                    $('#objinfo').text('No cuenta con evento');
                } else if (rs === 'notid') {
                    $('#objinfo').text('No selecciono lo solicitado');
                } else {
                    $('#objinfo').text('No se completo la acción');
                }
            });
        }
    } catch (e) {
        console.log(idobj);
    }
}

// Funcion que comprueba que un objeto se desea eliminar

function comfirmaEliminarObj(objeto, nombreobj) {

    $('#btneliminarobj').val(objeto);
    $('#obj').text(nombreobj);

    $mdEliminarobjeto = $('#mdeliminarobjeto');
    $mdEliminarobjeto.modal('show');
}

function eliminarObjeto() {

    objeto = document.getElementById('btneliminarobj').value;

    if (objeto === 0 || objeto === '0' || objeto === '' || objeto === null) {
        $('#lbleliminarobj').text('Selecione un objeto').addClass('alert alert-danger');
    } else {
        $.post("objectsofevents?accion=Eliminar", {objeto: objeto}, function (rs) {
            if (rs === 'true') {
                var mdeliminarobjeto = $('#mdeliminarobjeto');
                mdeliminarobjeto.modal('hide');

                $('#labelmodalcarga').text('Eliminando objeto');

                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');

                setTimeout(function () {
                    modalcarga.modal('hide');
                    $('#trobjeto' + objeto).empty();
                    $('#trobjeto' + objeto).remove();
                }, 800);

                //   setTimeout("location.reload(true);", 700);
            } else if (rs === 'false') {
                $('#lbleliminarobj').text('No se elimino el objeto').addClass('alert alert-danger');
            } else if (rs === 'enuso') {
                $('#lbleliminarobj').text('Este objeto esta en uso').addClass('alert alert-danger');
            } else {
                $('#lbleliminarobj').text('No se logro eliminar el objeto').addClass('alert alert-danger');
            }
        });
    }
}


// Abre los eventos para cambiar el objeto de evento
function traspasoObjeto(idobj, nmobj) {

    try {
        $('#list-tab').empty();
        $.post("events?", {accion: 'Listeventos'}, function (rs) {

            if (rs != '[]') {
                var json = JSON.parse(rs);
                console.log(json);

                for (let item of json) {

                    document.getElementById('list-tab').innerHTML += "<option id='optionobj' value=" + idobj + "  class='btn btn-primary list-group-item list-group-item-action objetoide mb-1'" +
                            "data-toggle='list' onclick='pasarObjeto(" + item.idevento + ");'>" +
                            item.nombreevento +
                            "</option>";
                }

                $('#objetoide').val(idobj);
                $('#nmobjeto').text(nmobj);

                var mdCambiarObjeto = $('#mdCambiarObjeto');
                mdCambiarObjeto.modal('show');

            } else {

                $('#infobj').text('Para separar objetos debes crear un evento.').addClass('fas text-primary');
                var $mdevento = $('#mdCrearEvento');
                $mdevento.modal('show');
            }

        });

    } catch (e) {
        console.log('Error en validarobjeto en js', e);
    }
}

function limpiarEventos() {
    $('#tbody').empty();
    $('#list-tab').empty();
    $('#listmiseventos').empty();
}

function pasarObjeto(idevent) {
    try {
        idob = document.getElementById('optionobj').value;
        evento = document.getElementById('eventoseleccionado').value;
        if (evento != idevent) {

            if (idob === '' || idob === null) {
                $('#objupdevento').text('No seleciono objeto').addClass('text-danger');
            } else if (idevent === '' || idevent === null) {
                $('#objupdevento').text('No seleciono evento').addClass('text-danger');
            } else {
                $.post("objectsofevents?accion=Traspasoobj", {idobj: idob, ideven: idevent}, function (rs) {
                    console.log(rs);
                    if (rs === 'true') {
                        var mdCambiarObjeto = $('#mdCambiarObjeto');
                        mdCambiarObjeto.modal('hide');

                        $('#labelmodalcarga').text('Traspasando objeto');

                        var $modalcarga = $('#modalcarga');
                        $modalcarga.modal('show');

                        setTimeout(function () {
                            $('#objeto' + idob).empty();
                            $modalcarga.modal('hide');
                        }, 500);

                    } else if (rs === 'false') {
                        $('#objupdevento').text('No se traspaso').addClass('text-danger alert alert-danger');
                    } else if (rs === 'notevent') {
                        $('#objupdevento').text('Seleccione su evento').addClass('text-danger alert alert-danger');
                    } else if (rs === 'notid') {
                        $('#objupdevento').text('No seleciono datos').addClass('text-danger alert alert-danger');
                    } else {
                        $('#objupdevento').text('No se logro el traspaso').addClass('text-danger alert alert-danger');
                    }
                });
            }

        }
    } catch (e) {

    }

}
// Funcion que muestra los eventos de un usuario en collapse acordion
function eventosUsuario() {

    $('#listeventosusuario').empty();
    $.post("events?", {accion: 'Listeventos'}, function (rs) {
        if (rs != '[]') {
            var json = JSON.parse(rs);
            for (let item of json) {

                document.getElementById('listeventosusuario').innerHTML += "<div id=\"tr" + item.idevento + "tr\"> <div class=\"card-header bg-gradient-aqua\" id=\"headingThree\">" +
                        "<h5 class=\"mb-0\">" +
                        "<button id=\"btnevento" + item.idevento + "\" class=\"btn collapsed text-white fas sansserif\" data-toggle=\"collapse\"  onclick=\"listarObjetosEvento(" + item.idevento + ",'toggle')\" aria-expanded=\"false\" aria-controls=\"collapseThree\"><span class=\"sansserif\">" + item.nombreevento +
                        "</span> </button>  </h5> </div>" +
                        "  <div id=\"collapse" + item.idevento + "\" class=\"collapse cll\" aria-labelledby=\"headingThree\" data-parent=\"#accordion\">" +
                        " <div class=\"card-body\">  <div id=\"listaobjetos" + item.idevento + "\" class=\"listadeobjetos\"></div>  </div> </div></div>";
            }
        }
    });
}

//============= FUNCION PARA VER LOS OBJETOS DE UN EVENTO

function listarObjetosEvento(evento, acordion) {
    $.post("objectsofevents?", {accion: 'Objetosevento', evento: evento}, function (rs) {
        $('#eventoseleccionado').val(evento);
        if (rs != '[]') {

            $('.listadeobjetos').empty();

            var json = JSON.parse(rs);
            for (let item of json) {
                document.getElementById('listaobjetos' + evento).innerHTML += "<div  id=\"objeto" + item.idobjeto + "\"> <div class='btn list-group-itemobjetos list-group-item-action text-gray-600  mb-1'" +
                        "data-toggle='list'><span class=\"\sansserif\">" +
                        item.objetonombre + "</span><button type='button' class='btn  text-info ml-1 float-right hover-gradient-aqua' onclick='verCaracteristicas(" + item.idobjeto + ");'><i class='gg-search' title='Consultar'></i></button>" +
                        "<button type='button' class='btn text-danger float-right ml-1 hover-gradient-danger' title='Eliminar de mi lista' onclick='confirmEliminarObj(" + item.idobjeto + ", `" + item.objetonombre + "`)'><i class='gg-trash'></i></button>" +
                        "<button type=\"button\" class=\"btn text-success float-right hover-gradient-success\" title=\"Cambiar.\" onclick=\"traspasoObjeto(" + item.idobjeto + ", '" + item.objetonombre + "');\" ><i class=\"gg-undo\"></i></button>" +
                        "</div> </div> ";
            }

            if (acordion === 'toggle') {
                $('#collapse' + evento).collapse('toggle');
            } else if (acordion === 'show') {
                $('#collapse' + evento).collapse('show');
            }

        }
    });
}






//=============== CONFIRMA LA ELIMINACION UN OBJETO DE UN EVENTO  
function confirmEliminarObj(idobjdelete, nmobjdelete) {
    try {
        $('#btndeleteobj').val(idobjdelete);
        $('#stnmobj').text(nmobjdelete);

        var $mdupdateobj = $('#mbdeleteobj');
        $mdupdateobj.modal('show');
    } catch (e) {
        console.log('No se logro traer datos del objeto en js');
    }
}

//=============== ELIMINA EL OBJETO DEL EVENTO 


function deleteMiObj() {

    try {
        idmiobj = document.getElementById('btndeleteobj').value;

        $.post("objectsofevents?accion=Removeobj", {idobj: idmiobj}, function (rs) {
            if (rs === 'true') {
                var mbdeleteobj = $('#mbdeleteobj');
                mbdeleteobj.modal('hide');

                $('#labelmodalcarga').text('Eliminando mi objeto');

                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');

                $.post("objectsofevents", {accion: 'Verinfo', objeto: idmiobj}, function (rs) {
                    var json = JSON.parse(rs);

                    $('#objeto' + idmiobj).empty();
                    $('#objeto' + idmiobj).remove();

                    if (json.estado == 7) {
                        estado = 'Perfecto';
                    } else if (json.estado == 8) {
                        estado = 'Regular';
                    } else if (json.estado == 9) {
                        estado = 'Mal';
                    }
                    document.getElementById('tbodydisponibles').innerHTML += "<tr id='trobjeto" + idmiobj + "'>" +
                            "<td>" + json.objetonombre + "</td>" +
                            "<td>" + json.marca + "</td>" +
                            "<td>" + estado + "</td>" +
                            "<td>" +
                            "<button type=\"button\" class=\"btn text-info hover-gradient-aqua  mr-1\" onclick=\"verCaracteristicas(" + idmiobj + ");\"><i class=\"gg-search\" title=\"Consultar\"></i></button>" +
                            "<button name=\"\" type=\"button\" class=\"btn text-primary hover-gradient-primary\" title=\"Asignar\" onclick=\"validaObjeto(" + idmiobj + ",`" + json.objetonombre + "`); \"><i class=\"gg-toolbox\"></i></button>" +
                            "</td></tr>";
                });

                setTimeout(function () {
                    modalcarga.modal('hide');
                }, 800);
            } else if (rs === 'not') {
                $('#lbldeleteobj').text('No tiene permisos').addClass('text-danger alert alert-warning');
            } else if (rs === 'false') {
                $('#lbldeleteobj').text('No se elimino el objeto del evento').addClass('text-danger alert alert-warning');
            } else {
                $('#lbldeleteobj').text('No se elimino el objeto').addClass('text-danger alert alert-warning');
            }
        });
    } catch (e) {
        console.log('Error en deletemiobj en js');
    }
}

//============================================================= EVENTOS

//=============== CREAR UN EVENTO  

function creaEvento() {
    try {

        var dta = new Date(); // Fecha actual
        dta.setHours(0, 0, 0, 0);

        nmevento = document.getElementById('txtnombreevt').value;
        dtinicioev = document.getElementById('dtinicio').value;
        dtfinev = document.getElementById('dtfin').value;
        hrinicioev = document.getElementById('timeinicio').value;
        hrfinev = document.getElementById('timefin').value;

        dtii = new Date(dtinicioev);
        dtff = new Date(dtfinev);

        dtii.setHours(0, 0, 0, 0);
        dtff.setHours(0, 0, 0, 0);

        dtii.setDate(dtii.getDate() + 1);
        dtff.setDate(dtff.getDate() + 1);
      //  dta.setDate(dta.getDate() + 1);


        $('#clsevent').addClass('alert alert-danger');

        if (nmevento === '' || nmevento === null) {
            $('#lblevent').text('Ingrese nombre del evento');
        } else if (dtinicioev === '' || dtinicioev === null) {
            $('#lblevent').text('Ingrese la fecha de inicio');
        } else if (dtfinev === '' || dtfinev === null) {
            $('#lblevent').text('Ingrese la fecha final');
        } else if (dtii < dta) {
            $('#lblevent').text('Fecha de inicio invalida');
        } else if (dtff < dta || dtff < dtii) {
            $('#lblevent').text('Fecha final invalida');
        } else if (hrinicioev === null || hrinicioev === '') {
            $('#lblevent').text('Ingrese hora inicial');
        } else if (hrfinev === null || hrfinev === '') {
            $('#lblevent').text('Ingrese hora final');
        } else if (hrfinev < hrinicioev) {
            $('#lblevent').text('Horas invalidas');
        } else {
            $('#clsevent').removeClass('alert alert-danger');
            $.post("events?accion=Createevent", {nmevento: nmevento, dtinicio: dtinicioev, dtfin: dtfinev, hrinicio: hrinicioev, hrfin: hrfinev}, function (rs) {

                if (rs === 'true') {

                    $('#txtnombreevt').val(null);
                    $('#dtinicio').val(null);
                    $('#dtfin').val(null);
                    $('#timeinicio').val(null);
                    $('#timefin').val(null);
                    $('#clsevent').removeClass('alert alert-danger').text('');

                    $('#class').addClass('').removeClass('alert alert-success');
                    $('#alert').text(null);

                    var $mdCrearEvento = $('#mdCrearEvento');
                    $mdCrearEvento.modal('hide');

                    $('#labelmodalcarga').text('Creando tu evento');

                    var $modalcarga = $('#modalcarga');
                    $modalcarga.modal('show');

                    setTimeout(function () {
                        eventosUsuario();
                        $modalcarga.modal('hide');
                    }, 1000);

                } else if (rs === 'campos') {
                    $('#clsevent').addClass('alert alert-danger');
                    $('#lblevent').text('Campos incompletos.');
                } else if (rs === 'dtiniciofalse') {
                    $('#clsevent').addClass('alert alert-danger');
                    $('#lblevent').text('Fecha de inicio invalida.');
                } else if (rs === 'dtfinalfalse') {
                    $('#clsevent').addClass('alert alert-danger');
                    $('#lblevent').text('Fecha final invalida.');
                } else if (rs === 'false') {
                    $('#clsevent').addClass('alert alert-danger');
                    $('#lblevent').text('Tu evento no se creo.');
                } else {
                    $('#clsevent').addClass('alert alert-danger');
                    $('#lblevent').text('El evento no se creo.');
                }
            });
        }

    } catch (e) {
        console.log('Error en crear evento en js');
    }
}


function listarEventos() {
    limpiarEventos();
    $.post("events?", {accion: 'Listeventos'}, function (rs) {
        var json = JSON.parse(rs);

        for (let item of json) {

            document.getElementById('tbody').innerHTML += "<tr id='tr" + item.idevento + "tr' class=' bg-fond-transparent'>" +
                    "<td class='col-lg-4'>" + item.nombreevento + "</td>" +
                    "<td class='col-lg-2'>" + item.dateinicio + "</td>" +
                    "<td class='col-lg-2'>" + item.datefin + "</td>" +
                    "<td class='col-lg-1'>" + item.horainicio + "</td>" +
                    "<td class='col-lg-1'> " + item.horafin + "</td>" +
                    "<th scope='row'>" +
                    "<button  class='btn text-primary' onclick='editEvent(" + item.idevento + ",`" + item.nombreevento + "`,`" + item.dateinicio + "`,`" + item.datefin + "`,`" + item.horainicio + "`,`" + item.horafin + "`);' ><i class='fas fa-edit'></i></i>" +
                    " <button class='btn text-danger' onclick='confirmDeleteEven(" + item.idevento + ",`" + item.nombreevento + "`);'><i class='gg-trash'></i></button>" +
                    "</th></tr>";
        }
        var mdeventosus = $('#mdeventosus');
        mdeventosus.modal('show');

    });
}


function editEvent(idev, nm, dti, dtf, hri, hrf) {
    try {
        // Muestra el div para editar el evento
        $("#eyefalse").removeClass('eye');

        // Envia los datos a editar
        $('#btnupdevent').val(idev);
        $('#txtnombreupd').val(nm);
        $('#dtinicioupd').val(dti);
        $('#dtfinupd').val(dtf);
        $('#timeinicioupd').val(hri);
        $('#timefinupd').val(hrf);

    } catch (e) {
        console.log(e);
    }
}

function updateEvent() {

    var fcact = new Date();
    fcact.setHours(0, 0, 0, 0);

    idev = document.getElementById('btnupdevent').value;
    nmevent = document.getElementById('txtnombreupd').value;
    dtiniup = document.getElementById('dtinicioupd').value;
    dtfupd = document.getElementById('dtfinupd').value;
    hriniupd = document.getElementById('timeinicioupd').value;
    hrfinupd = document.getElementById('timefinupd').value;

    if (idev > 0) {

        dti = new Date(dtiniup);
        dtf = new Date(dtfupd);

        dti.setHours(0, 0, 0, 0);
        dtf.setHours(0, 0, 0, 0);
        dti.setDate(dti.getDate() + 1);
        dtf.setDate(dtf.getDate() + 1);

        if (nmevent === '' || nmevent === null) {
            $('#lbleventupd').text('Ingrese el nombre').addClass('alert alert-danger');
        } else if (dtiniup === '' || dtiniup === null) {
            $('#lbleventupd').text('Ingrese fecha inicial').addClass('alert alert-danger');
        } else if (dti < fcact) {
            $('#lbleventupd').text('Fecha inicial invalida').addClass('alert alert-danger');
        } else if (dtfupd === null || dtfupd === '') {
            $('#lbleventupd').text('Ingrese fecha final').addClass('alert alert-danger');
        } else if (dtf < fcact || dtf < dti) {
            $('#lbleventupd').text('Fecha final invalida').addClass('alert alert-danger');
        } else if (hriniupd === null || hriniupd === '') {
            $('#lbleventupd').text('Ingrese hora de incio').addClass('alert alert-danger');
        } else if (hrfinupd === null || hrfinupd === '') {
            $('#lbleventupd').text('Ingrese hora final').addClass('alert alert-danger');
        } else if (hriniupd > hrfinupd) {
            $('#lbleventupd').text('Horas invalidas').addClass('alert alert-danger');
        } else {
            $.post("events?accion=Updevent", {idev: idev, nmevento: nmevent, dtinicio: dtiniup, dtfin: dtfupd, hrinicio: hriniupd, hrfin: hrfinupd}, function (rs) {
                if (rs === 'true') {

                    $('#eyefalse').addClass('eye');
                    $('#txtnombreupd').val('');
                    $('#dtinicioupd').val('');
                    $('#dtfinupd').val('');
                    $('#timeinicioupd').val('');
                    $('#timefinupd').val('');
                    $('#btnupdevent').val(0);

                    $('#lbleventupd').removeClass('alert alert-danger').text('');

                    var $mdeventosus = $('#mdeventosus');
                    $mdeventosus.modal('hide');

                    $('#labelmodalcarga').text('Actualizando tu evento');

                    var $modalcarga = $('#modalcarga');
                    $modalcarga.modal('show');

                    setTimeout(function () {
                        $('#btnevento' + idev).text(nmevent);
                        $modalcarga.modal('hide');
                    }, 1000);

                } else if (rs === 'campos') {
                    $('#lbleventupd').text('Llene los campos').addClass('alert alert-danger');
                } else if (rs === 'false') {
                    $('#lbleventupd').text('No se actualizo el evento').addClass('alert alert-danger');
                } else if (rs === 'dtiniciofalse') {
                    $('#lbleventupd').text('Fecha de inicio invalida').addClass('alert alert-danger');
                } else if (rs === 'dtfinalfalse') {
                    $('#lbleventupd').text('Fecha final invalida').addClass('alert alert-danger');
                } else {
                    $('#lbleventupd').text('No se actualizo su evento').addClass('alert alert-danger');
                }
            });
        }

    } else {
        $('#lbleventupd').text('No selecciono un evento').addClass('alert alert-danger');
    }
}

// Confirma eliminar evento

function confirmDeleteEven(idev, nmeven) {
    try {
        $('#btndeleteev').val(idev);
        $('#st').text(nmeven);

        var $mdupdateobj = $('#deleteEvento');
        $mdupdateobj.modal('show');
    } catch (e) {
        console.log('Error en confirmdeleteev en js');
    }


}

// Eliminar un evento
function deleteEvent() {
    try {
        idev = document.getElementById('btndeleteev').value;

        $.post("events?accion=Deleteeven", {idev: idev}, function (rs) {
            if (rs === 'true') {

                var $mdeventosus = $('#mdeventosus');
                $mdeventosus.modal('hide');

                var $deleteEvento = $('#deleteEvento');
                $deleteEvento.modal('hide');

                $('#labelmodalcarga').text('Eliminando tu evento');

                var $modalcarga = $('#modalcarga');
                $modalcarga.modal('show');

                setTimeout(function () {
                    $('#tr' + idev + 'tr').empty();
                    $('#collapse' + idev).empty();
                    $modalcarga.modal('hide');
                }, 1000);

            } else if (rs === 'not') {
                $('#lbldelet').text('No tiene permisos').addClass('text-danger');
            } else if (rs === 'false') {
                $('#lbldelet').text('No se elimino el evento').addClass('text-danger');
            } else {
                $('#lbldelet').text('No se logro eliminar').addClass('text-danger');
            }
        });

    } catch (e) {
        console.log('Error en eliminar el evento');
    }

}










