/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// =============================================== BLOC DE NOTAS 


function listarNotas() {

    $('#textonotas').empty();
    $('#notanueva').empty();
    $('#listadenotas').empty();

    $.post("tasks?", {accion: 'Listarnotas'}, function (rs) {
        var json = JSON.parse(rs);
        console.log(json);
        for (let item of json) {
            document.getElementById('listadenotas').innerHTML += " <a id='nota" + item.idnota + "' class='border-0 list-group-item list-group-item-action list-group-item-warning' data-toggle='list' href='#list-home' role='tab' aria-controls='home' onclick='verNota(`" + item.idnota + "`);'>" + item.nombrenota + "</a>";
        }
    });
}

function verNota(nota) {
    $('.list-group-item').removeClass('active');
    $('#textonotas').empty();
    $.post("tasks?", {accion: 'Vernota', nota: nota}, function (rs) {
        var json = JSON.parse(rs);

        document.getElementById('textonotas').innerHTML = " <div class=\"text-right alert-brown\">" +
                " <button id=\"editnota\" type=\"button\" class=\"btn btn-circle text-warning\" title=\"Editar\" value=\"" + json.idnota + "\" onclick=\"editNota()\"><i class=\"fas fa-pen\"></i></button>" +
                " <button id=\"deletenota\" type=\"button\" class=\"fas fa-trash-alt btn btn-circle  text-warning \" title=\"Eliminar\"  value=\"" + json.idnota + "\" onclick=\"confirmaeliminarNota();\"></button>" +
                "</div>" +
                "<div id=\"alertnota\" class=\"mb-1 alertnota\"></div>" +
                " <textarea id=\"minota\" class=\"card col-lg-12 text-gray-800 alert alert-warning \" disabled >" + json.textonota + "</textarea>" +
                "  <div class=\"d-flex flex-row-reverse\">" +
                "<button id=\"btnupdatenota\" class=\"m-2  btn bg-gradient-warning p-2 text-gray-100\" value=\"" + json.idnota + "\" onclick=\"actualizaNota();\">Actualizar</button>" +
                "</div>";

        $('#hidden').val(json.nombrenota);
        $('#minota').val(json.textonota);

    });

}

function crearHtmlcrearNota() {
    $('#textonotas').empty();
    document.getElementById('textonotas').innerHTML = " <div class=\"text-right\"> " +
            " <div  class=\"card col-lg-12 mb-1\">  <textarea id=\"textarea\" class='card border'></textarea> <div id=\"txtnewnota\"></div></div> <div id=\"alertnota\" class=\"mb-1 alertnota\"></div>" +
            "<div class=\"d-flex flex-row-reverse\"> <button id=\"btnnota\" class=\"m-2  btn bg-gradient-blue p-2 text-gray-100\" onclick=\"agregarNota();\">Crear nota</button></div>" +
            "</div>";
}

function editNota() { // Cuando oprime el boton de editar nota del lapiz
    textonota = document.getElementById('minota').value;
    note = document.getElementById('editnota').value; // valor de boton editar
    crearHtmleditNota(textonota, note);

}

function crearHtmleditNota(textonota, nota) {

    $('#textonotas').empty();
    document.getElementById('textonotas').innerHTML = " <div class=\"text-right alert-brown\">" +
            " <button id=\"editnota\" type=\"button\" class=\"btn btn-circle text-warning\" title=\"Editar\" onclick=\"editNota()\"><i class=\"fas fa-pen\"></i></button>" +
            " <button id=\"deletenota\" type=\"button\" class=\"fas fa-trash-alt btn btn-circle  text-warning \" title=\"Eliminar\" onclick=\"confirmaeliminarNota();\"></button>" +
            "</div>" +
            "<div id=\"alertnota\" class=\"mb - 1 alertnota\"></div>" +
            " <textarea id=\"minota\" class=\"card col-lg-12 text-gray-800 alert alert-warning \" >" + textonota + "</textarea>" +
            "  <div class=\"d-flex flex-row-reverse\">" +
            "<button id=\"btnupdatenota\" class=\"m-2  btn bg-gradient-warning p-2 text-gray-100\" value=\"" + nota + "\" onclick=\"actualizaNota();\">Actualizar</button>" +
            "</div>";

    nombrenota = document.getElementById('hidden').value;
    $('#namenota').val(nombrenota);
}

function crearNota() { // Crea la nota agregando un campo mas de html con el nombre
    name = document.getElementById('namenota').value;
    //$('#newnota').empty();
    if (name === null || name === '') {
        $('#alertnota').text('Ingrese nombre a su nota').addClass('alert alert-warning');
    } else {
        crearHtmlcrearNota();
       // $('#btnnota').val(name);
        document.getElementById('listadenotas').innerHTML += "<a class='list-group-item list-group-item-action' id='nwnote' data-toggle='list' href='#list-profile' role='tab' aria-controls='profile'  onclick='renovarNota();'>" + name + "</a>";

    }
}

function agregarNota() {
    texttonota = document.getElementById('textarea').value;
    nombrenota = document.getElementById('namenota').value;

    if (texttonota === null || texttonota === '') {
        $('#alertnota').text('Ingrese un texto a su nota').addClass('alert alert-danger');
    } else if (nombrenota === null || nombrenota === '') {
        $('#alertnota').text('Ingrese el nombre de su nota').addClass('alert alert-danger');
    } else {
        $.post("tasks?accion=Crearnota", {nombrenota: nombrenota, textonota: texttonota}, function (rs) {
            if (rs === 'true') {
                $('#namenota').val(null);
                $('#alertnota').text('').addClass('eye');
                listarNotas();
            } else if (rs === 'false') {
                $('#alertnota').text('Nota no agregada').addClass('alert alert-danger');
            } else if (rs === 'notus') {
                $('#alertnota').text('No se creo la nota').addClass('alert alert-danger');
            } else if (rs === 'campos') {
                $('#alertnota').text('Ingrese campos completos').addClass('alert alert-danger');
            } else {
                $('#alertnota').text(rs).addClass('alert alert-danger');
            }
        });
    }
}


function confirmaeliminarNota() {

    try {
        nota = document.getElementById('deletenota').value;
        $('#btneliminanota').val(nota);

        var $mdnota = $('#mdEliminanota');
        $mdnota.modal('show');
    } catch (e) {
        console.log('Error en confirmaEliminarNota');
    }
}

function eliminaNota() {
    try {
        nota = document.getElementById('btneliminanota').value;
        $.post("tasks?accion=Eliminanota", {idenota: nota}, function (rs) {
            if (rs === 'true') {

                var mdEliminanota = $('#mdEliminanota');
                mdEliminanota.modal('hide');

                $('#nota' + nota).empty().addClass('eye');
                $('#textonotas').empty();
            } else if (rs === 'false') {
                $('#alerteliminanota').text('Nota no eliminada').addClass('alert alert-danger');
            } else if (rs === 'notnota') {
                $('#alerteliminanota').text('Selecione una nota').addClass('alert alert-danger');
            } else {
                $('#alerteliminanota').text('Nota no eliminada').addClass('alert alert-danger');
            }
        });
    } catch (e) {
        console.log('Error en eliminaNota');
    }
}

// Cuando creo nota pero oprime otra cosa sigue quedando la lista nueva 
// que se agrego cuando la creo donde sale el nombre nuevo de esa nota
function renovarNota() {
    crearHtmlcrearNota();
}


function actualizaNota() {
    notanew = document.getElementById('minota').value; // Texto de la nota
    identinota = document.getElementById('btnupdatenota').value;  // identi de la nota
    nombrenew = document.getElementById('namenota').value;

    console.log(notanew);
    console.log(identinota);
    console.log(nombrenew);

    if (notanew === '' || notanew === null) {
        $('#alertnota').text('Ingrese texto a su nota').addClass('alert alert-warning');
    } else if (identinota === null || identinota === '' || identinota === 0 || identinota === '0') {
        $('#alertnota').text('Seleccione una nota').addClass('alert alert-warning');
    } else if (nombrenew === null || nombrenew === '') {
        $('#alertnota').text('Ingrese nombre a su nota').addClass('alert alert-warning');
    } else {
        $.post("tasks?accion=Actualizanota", {idenota: identinota, nombrenota: nombrenew, textonota: notanew}, function (rs) {
            if (rs === 'true') {
                $('#minota').val(notanew);
                $('#namenota').val(null);
                $('#nota' + identinota).text(nombrenew);
            } else if (rs === 'false') {
                $('#alertnota').text('Nota no actualizada').addClass('alert alert-danger');
            } else if (rs === 'invalid') {
                $('#alertnota').text('Selecione una nota').addClass('alert alert-danger');
            } else if (rs === 'campos') {
                $('#alertnota').text('Campos incompletos').addClass('alert alert-danger');
            } else {
                $('#alertnota').text('Error al actualizar la nota').addClass('alert alert-danger');
            }
        });
    }
}

