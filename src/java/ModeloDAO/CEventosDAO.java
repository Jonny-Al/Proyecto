/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CUsuario;
import ModeloVO.CEventos;
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
public class CEventosDAO {

    CConexion cn = new CConexion();
    Connection conn = null;
    ResultSet rs = null;
    private boolean sentencia = false;

    // ======  METODO PARA LISTAR EVENTOS DE UN USUARIO EN ESPECIFICO DEL QUE INGRESA AL SISTEMA.
    public List listEventosUser(CUsuario us) {

        List<CEventos> listeventos = new ArrayList<>();

        try {
            conn = cn.getConexion();

            CallableStatement evt = conn.prepareCall("CALL Ev_ListEventosUsuario (?)");
            evt.setInt(1, us.getID());

            rs = evt.executeQuery();

            while (rs.next()) {
                CEventos eventos = new CEventos();

                eventos.setIdevento(rs.getInt(1));
                eventos.setNombreevento(rs.getString(2));
                eventos.setDateinicio(rs.getString(3));
                eventos.setDatefin(rs.getString(4));
                eventos.setHorainicio(rs.getString(5));
                eventos.setHorafin(rs.getString(6));

                listeventos.add(eventos);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar eventos de usuario: " + e);
            Logger.getLogger(CEventosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listeventos;
    }

    // ========= METODO PARA CREAR EVENTOS POR EL USUARIO QUE ESTA EN EL SISTEMA
    public boolean crearEvento(CEventos evento) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement add = conn.prepareCall("CALL Ev_CrearEvento (?,?,?,?,?,?)");
            add.setString(1, evento.getNombreevento());
            add.setString(2, evento.getDateinicio());
            add.setString(3, evento.getDatefin());
            add.setString(4, evento.getHorainicio());
            add.setString(5, evento.getHorafin());
            add.setInt(6, evento.getIdusuario());

            add.executeUpdate();
            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al crear evento: " + e);
            Logger.getLogger(CEventosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ======= METODO PARA ACTUALIZAR UN EVENTO DE EL USUARIO
    public boolean actualizarEvento(CEventos ev) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement updevent = conn.prepareCall("CALL Ev_Update (?,?,?,?,?,?)");

            updevent.setInt(1, ev.getIdevento());
            updevent.setString(2, ev.getNombreevento());
            updevent.setString(3, ev.getDateinicio());
            updevent.setString(4, ev.getDatefin());
            updevent.setString(5, ev.getHorainicio());
            updevent.setString(6, ev.getHorafin());

            updevent.executeUpdate();
            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al actualizar evento:  " + e);
            Logger.getLogger(CEventosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ======= METODO PARA VALIDAR SI UN USUARIO TIENE EVENTOS PARA SEPARAR OBJETOS
    // ======= METODO PARA VALIDAR SI UN EVENTO LE PERTENCE A UN USUARIO PARA VERIFICAR SI LO PUEDE ELIMINAR O ACTUALIZAR
    public int validarEvento(CEventos ev, CUsuario us) {
        int valido = 0;

        try {

            conn = cn.getConexion();
            CallableStatement validar = conn.prepareCall("CALL Ev_ValidarEvento (?,?)");

            validar.setInt(1, ev.getIdevento());
            validar.setInt(2, us.getID());

            rs = validar.executeQuery();

            // Si tiene eventos es 1 de lo contrario es 0
            valido = (rs.next()) ? 1 : 0;

        } catch (SQLException e) {
            System.out.println("Error DAO al validar evento: " + e);
            Logger.getLogger(CEventosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return valido;
    }

    // ====== METODO PARA ELIMINAR UN EVENTO
    public boolean eliminarEvento(CEventos ev) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement delete = conn.prepareCall("CALL Ev_Delete (?)");
            delete.setInt(1, ev.getIdevento());

            delete.executeUpdate();
            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al eliminar evento: " + e);
            Logger.getLogger(CEventosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }
}
