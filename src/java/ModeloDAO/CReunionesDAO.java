/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CUsuario;
import ModeloVO.CReuniones;
import java.sql.Connection;
import Conexion.CConexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ALEJANDRO
 */
public class CReunionesDAO {

    private boolean sentencia = false;

    Connection conn = null;
    CConexion cn = new CConexion();
    ResultSet rs;

    // === METODO PARA CREAR UNA REUNION Y ASIGNAR LA SALA DE JUNTAS
    public boolean crearReunion(CReuniones reunion) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement separar = conn.prepareCall("CALL Ru_CrearReunion  (?,?,?,?,?,?,?)");

            separar.setString(1, reunion.getNombrereunion());
            separar.setString(2, reunion.getFechainicio());
            separar.setString(3, reunion.getFechafinal());
            separar.setString(4, reunion.getHorainicio());
            separar.setString(5, reunion.getHorafin());
            separar.setString(6, reunion.getComentarios());
            separar.setInt(7, reunion.getIdUsuarioseparo());

            separar.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al separar sala" + e);
            Logger.getLogger(CReunionesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // === METODO PARA ACTUALIZAR UNA REUNION
    public boolean actualizarReunion(CReuniones reunion) {
        sentencia = false;
        try {

            conn = cn.getConexion();

            CallableStatement update = conn.prepareCall("CALL Ru_UpdateReunion  (?,?,?,?,?,?,?)");

            update.setInt(1, reunion.getIdReunion());
            update.setString(2, reunion.getNombrereunion());
            update.setString(3, reunion.getFechainicio());
            update.setString(4, reunion.getFechafinal());
            update.setString(5, reunion.getHorainicio());
            update.setString(6, reunion.getHorafin());
            update.setString(7, reunion.getComentarios());

            update.executeUpdate();
            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al actualizar la reunion " + e);
            Logger.getLogger(CReunionesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //======== METODO PARA  LISTAR LOS DIAS SEPARADOS EN EL CALENDARIO MOSTRANDO LAS REUNIONES DE TODOS EN EL CALENDARIO 
    public List listAsignaciones() {

        List<CReuniones> listseparados = new ArrayList<>();

        try {
            conn = cn.getConexion();

            CallableStatement proclisasignaciones = conn.prepareCall("CALL Ru_ListAsignaciones");
            rs = proclisasignaciones.executeQuery();

            while (rs.next()) {

                CReuniones reuniones = new CReuniones();

                reuniones.setIdReunion(rs.getInt(1));
                reuniones.setNombrereunion(rs.getString(2));
                reuniones.setFechainicio(rs.getString(3));
                reuniones.setFechafinal(rs.getString(4));
                reuniones.setHorainicio(rs.getString(5));
                reuniones.setHorafin(rs.getString(6));
                reuniones.setComentarios(rs.getString(7));
                reuniones.setNombres(rs.getString(9));

                listseparados.add(reuniones);
            }

        } catch (SQLException e) {
            System.out.println("Erro DAO al listar asignaciones " + e);
            Logger.getLogger(CReunionesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listseparados;
    }

    // ====== METODO PARA LISTAR LAS REUNIONES DEL USUARIO QUE INGRESO AL SISTEMA
    public List listReuniones(CUsuario idusuario) {

        List<CReuniones> listmisreuniones = new ArrayList<>();

        try {
            conn = cn.getConexion();

            CallableStatement procreunionesus = conn.prepareCall("CALL Ru_ListarReunionesUsuario(?)");
            procreunionesus.setInt(1, idusuario.getID());

            rs = procreunionesus.executeQuery();

            while (rs.next()) {

                CReuniones reunion = new CReuniones();

                reunion.setIdReunion(rs.getInt(1));
                reunion.setNombrereunion(rs.getString(2));
                reunion.setFechainicio(rs.getString(3));
                reunion.setFechafinal(rs.getString(4));
                reunion.setHorainicio(rs.getString(5));
                reunion.setHorafin(rs.getString(6));
                reunion.setComentarios(rs.getString(7));

                listmisreuniones.add(reunion);
            }
        } catch (SQLException e) {
            System.out.println("Erro DAO al listar reuniones " + e);
            Logger.getLogger(CReunionesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listmisreuniones;
    }

    // ==== METODO PARA VALIDAR SI UNA REUNION PERTENCE A UN USUARIO PARA ELIMINAR O ACTUALIZAR
    public boolean validaReunionUser(CReuniones reunion) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement valida = conn.prepareCall("CALL Ru_ValidaReunionUser (?,?)");
            valida.setInt(1, reunion.getIdUsuarioseparo());
            valida.setInt(2, reunion.getIdReunion());

            rs = valida.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;

        } catch (SQLException e) {
            System.out.println("Error DAO al validar la reuniones de usuario " + e);
            Logger.getLogger(CReunionesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ====== METODO PARA ELIMINAR UNA REUNION DESPUES DE HABER EJECUTA EL METODO VALIDAREUNIONUSER
    public boolean eliminarReunion(CReuniones idreunion) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement delete = conn.prepareCall("CALL Ru_EliminarReunion (?)");
            delete.setInt(1, idreunion.getIdReunion());

            delete.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al eliminar reunion " + e);
            Logger.getLogger(CReunionesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }
}
