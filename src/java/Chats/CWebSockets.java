/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chats;

import java.io.IOException;
import java.io.StringWriter;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.Session;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;

/**
 *
 * @author ALEJANDRO
 */
@ApplicationScoped
@ServerEndpoint("/Chats/{username}/{idusuario}") // Recibe nombre y id del usuario
public class CWebSockets {

    // === VARIABLES
    // Nombre de usuario y id de usuario.
    String nameuser, idusuario;

    // === Session del chat
    static Set<Session> chat = Collections.synchronizedSet(new HashSet<Session>());

    // === METODOS DE WEBSOCKETS
    @OnOpen // Maneja la conexion (Abre conexion))
    public void abrirConexion(Session userSession, @PathParam("username") String username, @PathParam("idusuario") String id) {
        chat.add(userSession);

        // Crea las sessiones con nombre y id de usuario
        userSession.getUserProperties().put("username", username);
        userSession.getUserProperties().put("idusuario", id);

        // Asigna a las variables las sessiones especificadas
        nameuser = (String) userSession.getUserProperties().get("username");
        idusuario = (String) userSession.getUserProperties().get("idusuario");
        System.out.println("cac");
        System.out.println(idusuario);
        System.out.println(nameuser);
    }

    @OnClose // Manejador de  remover session
    public void cerrarConexion(Session userSession) {
        chat.remove(userSession);
    }

    @OnMessage // Manejador de mensajes enviados
    public void manejarMensaje(String mensaje) throws IOException {
        if (!mensaje.equals("")) {
            Iterator<Session> iterator = chat.iterator();
            String msj, nombrenevia,  sessionrecibe;
            while (iterator.hasNext()) {
                msj = crearMensajeJson("msj", mensaje);
                nombrenevia = crearMensajeJson("nombreusuario", nameuser);
                sessionrecibe = crearMensajeJson("area", idusuario);
                System.out.println("{" + msj + "," + nombrenevia + "," + sessionrecibe + "}");
                iterator.next().getBasicRemote().sendText("{" + msj + "," + nombrenevia + "," + sessionrecibe + "}");
            }
        }
    }

    // Construye los datos en formato json mensaje y nombre de usuario.
    private String crearMensajeJson(String nombrevar, String dato) {
        JsonObject jsonmensaje = Json.createObjectBuilder().add(nombrevar, dato).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.write(jsonmensaje);
        }
        return stringWriter.toString().replaceAll("[{]", "").replaceAll("[}]", "");
    }

    //============================================================
    //============================================================
    @OnError
    public void onError(Throwable t) {
    }

}
