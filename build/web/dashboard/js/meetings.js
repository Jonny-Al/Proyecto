/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//============================================================= REUNIONES

//=============== REGISTRA UNA REUNION EN MEETINGS.JSP

function addReunion() {

    try {
        var f = new Date();

        nmreu = document.getElementById('txtevento').value;
        dteinicio = document.getElementById('dateinicio').value;
        dtefin = document.getElementById('datefin').value;
        timeinicio = document.getElementById('timeinicio').value;
        timefin = document.getElementById('timefin').value;
        comentarios = document.getElementById('comentreunion').value;

        dti = new Date(dteinicio);
        dtf = new Date(dtefin);

        dti.setDate(dti.getDate() + 1);
        dtf.setDate(dtf.getDate() + 1);

        f.setHours(0, 0, 0, 0);
        dti.setHours(0, 0, 0, 0);
        dtf.setHours(0, 0, 0, 0);


        if (nmreu === '' || nmreu === null) {
            $('#dtinvalid').text('Ingrese el nombre de la reunión').addClass('alert alert-danger');
        } else if (dteinicio === null || dteinicio === '') {
            $('#dtinvalid').text('Seleccione una fecha de inicio').addClass('alert alert-danger');
        } else if (dti < f) {
            $('#dtinvalid').text('Fecha de inicio invalida').addClass('alert alert-danger');
        } else if (dtefin === null || dtefin === '') {
            $('#dtinvalid').text('Seleccione una fecha final').addClass('alert alert-danger');
        } else if (dtf < f || dtf < dti) {
            $('#dtinvalid').text('Fecha final invalida');
        } else if (timeinicio === null || timeinicio === '') {
            $('#dtinvalid').text('Hora de inicio invalida').addClass('alert alert-danger');
        } else if (timefin === null || timefin === '') {
            $('#dtinvalid').text('Hora final invalida').addClass('alert alert-danger');
        } else if (timefin < timeinicio) {
            $('#dtinvalid').text('Horas invalidas').addClass('alert alert-danger');
        } else if (timeinicio < '06:59:00') {
            $('#dtinvalid').text('Hora inicial no disponible').addClass('alert alert-danger');
        } else if (timefin > '18:00:00') {
            $('#dtinvalid').text('Hora final no disponible').addClass('alert alert-danger');
        } else {
            $('#dtinvalid').text('').removeClass('alert alert-danger');

            $.post("meetings?accion=Asignar", {namereunion: nmreu, dteinicio: dteinicio, dtefin: dtefin, timeinicio: timeinicio, timefin: timefin, comentarios: comentarios}, function (rs) {
                if (rs === 'true') {
                    $('#labelmodalcarga').text('Asignando sala');

                    var modalcarga = $('#modalcarga');
                    modalcarga.modal('show');

                    setTimeout("location.reload(true);", 1000);
                } else if (rs === 'dtiniciofalse') {
                    $('#dtinvalid').text('Fecha de inicio invalida').addClass('alert alert-danger');
                } else if (rs === 'dtfinfalse') {
                    $('#dtinvalid').text('Fecha final invalida').addClass('alert alert-danger');
                } else if (rs === 'campos') {
                    $('#dtinvalid').text('Ingrese los campos completos').addClass('alert alert-danger');
                } else if (rs === 'false') {
                    $('#dtinvalid').text('No se asigno la reunión').addClass('alert alert-danger');
                } else {
                    $('#dtinvalid').text('No se asigno la reunión').addClass('alert alert-danger');
                }
            });
        }
    } catch (e) {
        console.log('Error en addreunion en js');
    }
}


//=============== CONFIRMACION SI DESEA ELIMINAR UNA REUNION EN MEETINGS.JSP
function confimEliminar(idreunion) {
    try {
        $('#btndelete').val(idreunion);
        var $mdobj = $('#modalDelete');
        $mdobj.modal('show');
    } catch (e) {
        console.log('Error en confirmEliminar en js');
    }
}

//=============== ELIMINA UNA REUNION 

function deleteReunion() {
    try {
        idr = document.getElementById('btndelete').value;

        $.post("meetings?accion=Eliminar", {idreunion: idr}, function (rs) {
            $('#alertupdd').addClass('alert alert-danger');
            if (rs === 'true') {
                $('#labelmodalcarga').text('Eliminando mi reunión');

                var mdUpdate = $('#mdUpdate');
                mdUpdate.modal('hide');

                var modalDelete = $('#modalDelete');
                modalDelete.modal('hide');

                var modalcarga = $('#modalcarga');
                modalcarga.modal('show');

                setTimeout("location.reload(true);", 1500);
            } else if (rs === 'false') {
                $('#eliminareunion').text('No se elimino').addClass('alert alert-danger');
            } else if (rs === 'nopertenece') {
                $('#eliminareunion').text('Seleccione la que desea eliminar').addClass('alert alert-danger');
            }
        });
    } catch (e) {
        console.log('Error en deletereunion en js');
    }

}

function verComentarios(num) {
    $('.y').addClass('eyereuniones');
    $('#pcomentarios' + num).removeClass('eyereuniones');
}

//=============== TRAE LOS DATOS A LOS CAMPOS DE LA REUNION SELECCIONADA AL MODAL UPDATEREUNION

function traerReunion(idreunion, nmreunion, dtinicio, dtfin, hrinicio, hrfin, comentarios) {
    try {

        $(".eye").removeClass('eye');

        $('#btnupd').val(idreunion);
        $('#nmreunion').val(nmreunion);
        $('#dtinicio').val(dtinicio);
        $('#dtfin').val(dtfin);
        $('#hrinicio').val(hrinicio);
        $('#hrfin').val(hrfin);
        $('#txtcomentarios').val(comentarios);

    } catch (e) {
        console.log('Error en traer reunion en js');
    }
}

//=============== ACTUALIZA LOS DATOS DE LA REUNION SELECCIONADA

function updateReunion() {

    try {
        var fc = new Date();

        idreunionn = document.getElementById('btnupd').value;
        nmreunion = document.getElementById('nmreunion').value;
        dtinicio = document.getElementById('dtinicio').value;
        dtfin = document.getElementById('dtfin').value;
        hrinicio = document.getElementById('hrinicio').value;
        hrfin = document.getElementById('hrfin').value;
        comentarios = document.getElementById('txtcomentarios').value;


        dtii = new Date(dtinicio);
        dtff = new Date(dtfin);

        dtii.setDate(dtii.getDate() + 1);
        dtff.setDate(dtff.getDate() + 1);

        fc.setHours(0, 0, 0, 0);
        dtii.setHours(0, 0, 0, 0);
        dtff.setHours(0, 0, 0, 0);

        $('#alertupdd').addClass('alert alert-danger');

        if (idreunionn === null || idreunionn === '') {
            $('#alertupdd').text('Seleccione una reunión');
        } else if (dtinicio === null || dtinicio === '') {
            $('#alertupdd').text('Seleccione fecha de inicio');
        } else if (dtii < fc) {
            $('#alertupdd').text('Fecha de inicio invalida');
        } else if (dtfin === null || dtfin === '') {
            $('#alertupdd').text('Seleccione fecha final');
        } else if (dtff < fc || dtff < dtii) {
            $('#alertupdd').text('Fecha final invalida');
        } else if (hrinicio === null || hrinicio === '') {
            $('#alertupdd').text('Indique hora de inicio');
        } else if (hrfin === null || hrfin === '') {
            $('#alertupdd').text('Indique hora final');
        } else if (hrinicio < '06:59:00') {
            $('#alertupdd').text('Hora inicial no disponible');
        } else if (hrfin > '18:00:00') {
            $('#alertupdd').text('Hora final no disponible');
        } else if (hrfin < hrinicio) {
            $('#alertupdd').text('Horas invalidas');
        } else {
            $('#alertupdd').text('').addClass('alert alert-danger');
            $.post("meetings?accion=Actualizar", {idreunion: idreunionn, nameupd: nmreunion, dtinicioupd: dtinicio, dtfinupd: dtfin, hrinicioupd: hrinicio, hrfinupd: hrfin, comentarios: comentarios}, function (rs) {
                if (rs === 'true') {

                    $('#labelmodalcarga').text('Actualizando ' + nmreunion);

                    var mdUpdate = $('#mdUpdate');
                    mdUpdate.modal('hide');

                    var modalcarga = $('#modalcarga');
                    modalcarga.modal('show');

                    setTimeout("location.reload(true);", 1500);

                } else if (rs === "dtiniciofalse") {
                    $('#alertupdd').text('Fecha inicial invalida').addClass('alert alert-danger');
                } else if (rs === 'dtfinfalse') {
                    $('#alertupdd').text('Fecha final invalida').addClass('alert alert-danger');
                } else if (rs === 'false') {
                    $('#alertupdd').text('No se actualizo').addClass('alert alert-danger');
                } else if (rs === 'nopertenece') {
                    $('#alertupdd').text('Seleciono una de sus reuniones').addClass('alert alert-danger');
                } else {
                    $('#alertupdd').text('No se actualizo').addClass('alert alert-danger');
                }
            });
        }
    } catch (e) {
        console.log('Error updatereunion en js');
    }
}
