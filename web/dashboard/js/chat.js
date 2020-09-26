/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global txtchat, nm , id*/

var websocket = new WebSocket("ws://localhost:8080/WooClic/Chats/" + nm.value + "/" + id.value);

websocket.onmessage = function (message) {

    var json = JSON.parse(message.data);
    if (json.msj !== null) {


        $("#plantilla").clone().appendTo(".chat");
        $('.chat #plantilla').show(1);
        //$('.chat .Nombre').html("Jonny");
        $('.chat #plantilla .msj').html(json.msj);
        $('.chat #plantilla #nombreusuario').html(json.nombreusuario);

        var formattedDate = new Date();
        var h = formattedDate.getHours();
        var min = formattedDate.getMinutes();

        Fecha = h + ":" + min;

        $('.chat #plantilla #tiempo').html(Fecha);

        $('.chat #plantilla').attr("id", "");

        $('.ch').text(1);
        $("#chatsgrup").animate({scrollTop: $('#chatsgrup')[0].scrollHeight}, 360);
        // chats.value += jsonData.message + "\n";


    }
};


function chatAll() {
    msj = document.getElementById('txtchat').value;

    $.post("messages?accion=Chatear", {msj: msj}, function (rs) {
        if (rs === 'true') {
            websocket.send(txtchat.value);
            txtchat.value = "";
        }
    });
}






