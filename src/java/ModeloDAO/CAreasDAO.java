/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CAreas;
import Conexion.CConexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEJANDRO
 */
public class CAreasDAO {

    CConexion cn = new CConexion();
    Connection conn = null;
    ResultSet rs;
    private boolean sentencia = false;

    //======= LISTAR TODAS LAS AREAS DE LA EMPRESA
    public List listarAreas() {
        List<CAreas> listareas = new ArrayList<>();

        try {
            conn = cn.getConexion();
            CallableStatement proclistareas = conn.prepareCall("CALL Area_ListarAreas");

            rs = proclistareas.executeQuery();

            while (rs.next()) {

                CAreas areas = new CAreas();
                areas.setIdArea(rs.getInt(1));
                areas.setAreaNombre(rs.getString(2));

                listareas.add(areas);
            }
        } catch (SQLException e) {
            System.out.println("Error DAO listar areas: " + e);
            Logger.getLogger(CAreasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listareas;
    }

    //========== AGREGAR NUEVA AREA SOLO LO DEBE HACER EL GERENTE
    public boolean registrarArea(CAreas area) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement add = conn.prepareCall("CALL Area_Add (?)");
            add.setString(1, area.getAreaNombre());

            add.executeUpdate();
            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO agregar tarea: " + e);
            Logger.getLogger(CAreasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ======== VALIDA UN AREA SI UN AREA EXISTE CON EL NOMBRE DE LA CADENA QUE RECIBE
    public CAreas validarArea(String areanew) {
        sentencia = false;

        CAreas area = new CAreas();

        try {
            conn = cn.getConexion();

            CallableStatement validar = conn.prepareCall("CALL Area_ValidarArea (?)");
            validar.setString(1, areanew);

            rs = validar.executeQuery();

            if (rs.next()) {
                area.setIdArea(rs.getInt(1));
                area.setAreaNombre(rs.getString(2));
            } else {
                area.setIdArea(0);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al validar area: " + e);
            Logger.getLogger(CAreasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return area;
    }

    // ======== METODO PARA COMPROBAR SI UN AREA INGRESADA ES VALIDA CON EL ID QUE RECIBE
    public boolean comprobarAreaUsada(int area) {
        // Consulta con la tabla usuario
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement comprobar = conn.prepareCall("CALL Area_ComprobarAreaUsada (?)");
            comprobar.setInt(1, area);
            rs = comprobar.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;
        } catch (SQLException e) {
            System.out.println("Error en DAO al comprobar el area " + e);
            Logger.getLogger(CAreasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    public boolean validarAreExiste(int area) {
        // Consulta con la tabla area
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement comprobar = conn.prepareCall("CALL Ar_ValidarAreaExiste (?)");
            comprobar.setInt(1, area);
            rs = comprobar.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;

        } catch (SQLException e) {
            System.out.println("Error en DAO al comprobar el area " + e);
            Logger.getLogger(CAreasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ======= METODO PARA ELIMINAR UN AREA SOLO LO PUEDE HACER EL GERENTE
    public boolean eliminarArea(int area) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement eliminararea = conn.prepareCall("CALL Area_EliminaraArea (?)");
            eliminararea.setInt(1, area);

            eliminararea.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error en DAO al eliminar el area " + e);
            Logger.getLogger(CAreasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //=========== ACTUALIZACION DEL AREA SOLO LO PUEDE REALIZAR EL GERENTE
    public boolean actualizarArea(CAreas area) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement update = conn.prepareCall("CALL Area_Update (?,?)");
            update.setInt(1, area.getIdArea());
            update.setString(2, area.getAreaNombre());

            update.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO actualiar area: " + e);
            Logger.getLogger(CAreasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }
}
