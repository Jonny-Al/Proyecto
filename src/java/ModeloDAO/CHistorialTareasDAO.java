/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CHistorialTareas;
import java.util.List;
import Conexion.CConexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ALEJANDRO
 */
public class CHistorialTareasDAO {

    private boolean sentencia = false;

    Connection conn = null;
    CConexion cn = new CConexion();
    ResultSet rs = null;

    //  ======= LISTA HISTORIAL DE TAREAS DE UN USUARIO SELECCIONADO EN EL HISTORIAL DE TAREAS O DEL MISMO USUARIO QUE INGRESA AL SISTEMA
    public List listaHistorial(CHistorialTareas hstareas) {
        List<CHistorialTareas> listhistorial = new ArrayList<>();

        try {

            conn = cn.getConexion();

            CallableStatement tareasus = conn.prepareCall("CALL Hs_ListTareasUsuario (?,?)");
            tareasus.setInt(1, hstareas.getIdUsdesarrollo());
            tareasus.setInt(2, hstareas.getIdusasigna());

            rs = tareasus.executeQuery();

            while (rs.next()) {
                CHistorialTareas historial = new CHistorialTareas();

                historial.setIdtarea(rs.getInt(1));
                historial.setNombreTarea(rs.getString(2));

                listhistorial.add(historial);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar historial: " + e);
            Logger.getLogger(CHistorialTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listhistorial;
    }

    // ========= METODO PARA VER UNA TAREA SELECCIONADA EN EL HISTORIAL
    public CHistorialTareas verTarea(CHistorialTareas idTarea) {

        CHistorialTareas historytarea = new CHistorialTareas();

        try {

            conn = cn.getConexion();

            CallableStatement procvertarea = conn.prepareCall("CALL Hs_VerTarea (?)");
            procvertarea.setInt(1, idTarea.getIdtarea());

            rs = procvertarea.executeQuery();

            while (rs.next()) {

                historytarea.setIdtarea(rs.getInt(1));
                historytarea.setNombreTarea(rs.getString(2));
                historytarea.setAnotacion(rs.getString(3));
                historytarea.setFechaaprobacion(rs.getString(4));
                historytarea.setFechaasignada(rs.getString(5));
                historytarea.setFechainicio(rs.getString(6));
                historytarea.setFechafinal(rs.getString(7));
                historytarea.setCaracteristicas(rs.getString(8));
                historytarea.setIdUsdesarrollo(rs.getInt(9));
                historytarea.setIdusasigna(rs.getInt(10));
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al ver la tarea:  " + e);
            Logger.getLogger(CHistorialTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return historytarea;
    }

    // ======== METODO PARA RESTAURAR UNA TAREA DEL HISTORIAL PARA VOLVER A REALIZAR ETC...
    public boolean restaurarTarea(CHistorialTareas idtarea) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement recovery = conn.prepareCall("CALL Hs_RestaurarTarea (?,?)");
            recovery.setInt(1, idtarea.getIdtarea());
            recovery.setString(2, idtarea.getFechainicio());

            recovery.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al restaurar tarea: " + e);
            Logger.getLogger(CHistorialTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ============ METODO QUE ELIMINA UNA TAREA DEL HISTORIAL
    public boolean eliminarHistorial(CHistorialTareas idtarea) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement delete = conn.prepareCall("CALL Hs_EliminarTarea (?)");
            delete.setInt(1, idtarea.getIdtarea());

            delete.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al eliminar Historial " + e);
            Logger.getLogger(CHistorialTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    public boolean validarTarea(int tarea, int usuario) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement comprobar = conn.prepareCall("CALL Hs_ValidarTarea (?,?)");
            comprobar.setInt(1, tarea);
            comprobar.setInt(2, usuario);

            rs = comprobar.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;

        } catch (SQLException e) {
            System.out.println("Error DAO validar tarea " + e);
            Logger.getLogger(CHistorialTareasDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return sentencia;
    }
}
