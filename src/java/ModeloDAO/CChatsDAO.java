/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CChats;
import Conexion.CConexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ALEJANDRO
 */
public class CChatsDAO {

    private boolean sentencia = false;
    CConexion cn = new CConexion();
    Connection conn = null;
    ResultSet rs = null;

    // ========= METODO QUE ENVIA MENSAJES DE CHAT DE TODOS
    public boolean mensajeria(CChats ch) {
         sentencia = false;
        try {

            conn = cn.getConexion();

            CallableStatement chat = conn.prepareCall("CALL Ch_Mensajeria (?,?,?,?)");
            chat.setString(1, ch.getFechamensaje());
            chat.setString(2, ch.getHoramensaje());
            chat.setString(3, ch.getMensaje());
            chat.setInt(4, ch.getIdsuario());

            chat.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO mensajeria: " + e);
            Logger.getLogger(CChatsDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ====== LISTA LOS CHATS DE TODOS
    public List listChats() {
        List<CChats> listchat = new ArrayList<>();

        try {
            conn = cn.getConexion();

            CallableStatement list = conn.prepareCall("CALL Ch_ListChats");

            rs = list.executeQuery();

            while (rs.next()) {
                CChats ch = new CChats();

                ch.setFechamensaje(rs.getString(1));
                ch.setHoramensaje(rs.getString(2));
                ch.setMensaje(rs.getString(3));
                ch.setUsuario(rs.getString(4));

                listchat.add(ch);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO listar chats: " + e);
            Logger.getLogger(CChatsDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listchat;
    }

   

}
