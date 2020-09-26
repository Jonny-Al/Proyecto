/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CRoles;
import Conexion.CConexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ALEJANDRO
 */
public class CRolesDAO {

    private boolean sentencia = false;
    ResultSet rs;
    Connection conn = null;
    CConexion cn = new CConexion();

    // ===== METODO PARA LISTAR 
    public List listarRoles() {

        List<CRoles> listroles = new ArrayList<>();
        try {
            conn = cn.getConexion();
            CallableStatement procroles = conn.prepareCall("CALL Rol_ListarRoles");

            rs = procroles.executeQuery();

            while (rs.next()) {
                CRoles rol = new CRoles();
                rol.setIdRol(rs.getInt(1));
                rol.setRolNombre(rs.getString(2));

                listroles.add(rol);
            }
        } catch (SQLException e) {
            System.out.println("Error DAO al listar roles " + e);
            Logger.getLogger(CRolesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listroles;
    }

    //=== AGREGAR ROL, SOLO LO PUEDE REALIZAR EL GERENTE
    public boolean registrarRol(CRoles rol) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement add = conn.prepareCall("CALL Rol_Add (?)");
            add.setString(1, rol.getRolNombre());

            add.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al listar agregar rol " + e);
            Logger.getLogger(CRolesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // === METODO PARA VALIDAR SI UN ROL YA EXISTE CON EL NOMBRE QUE RECIBE
    public CRoles validarRol(String rolnew) {

        CRoles rol = new CRoles();

        try {
            conn = cn.getConexion();

            CallableStatement validar = conn.prepareCall("CALL Rol_ValidarRol(?)");
            validar.setString(1, rolnew);

            rs = validar.executeQuery();

            if (rs.next()) {
                rol.setIdRol(rs.getInt(1));
                rol.setRolNombre(rs.getString(2));
            } else {
                rol.setIdRol(0);
                rol.setRolNombre(null);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al validar rol " + e);
            Logger.getLogger(CRolesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return rol;
    }

    // ======== METODO PARA COMPROBAR SI UN ROL INGRESADO ES VALIDO CON EL ID QUE RECIBE
    public boolean comprobarRolUsado(int rol) {
        // Consulta con la tabla usuarios
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement comprobar = conn.prepareCall("CALL Rol_ComprobarRolUsado (?)");
            comprobar.setInt(1, rol);

            rs = comprobar.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;

        } catch (SQLException e) {
            System.out.println("Error en DAO al copmprobar el rol" + e);
            Logger.getLogger(CRolesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    public boolean validarRolExiste(int rol) {
        // Consulta con la tabla rol
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement comprobar = conn.prepareCall("CALL Rol_ValidarRolExiste (?)");
            comprobar.setInt(1, rol);

            rs = comprobar.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;

        } catch (SQLException e) {
            System.out.println("Error en DAO al copmprobar el rol" + e);
            Logger.getLogger(CRolesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ======= METODO PARA ELIMINAR UN ROL SOLO LO PUEDE HACER EL GERENTE
    public boolean eliminarRol(int rol) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement eliminarol = conn.prepareCall("CALL Rol_EliminarRol (?)");
            eliminarol.setInt(1, rol);

            eliminarol.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error en DAO al eliminar el rol" + e);
            Logger.getLogger(CRolesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //===== METODO PARA ACTUALIZAR EL ROL SOLO LO PUEDE REALIZAR EL GERENTE
    public boolean actualizarRol(CRoles rol) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement update = conn.prepareCall("CALL Rol_Update (?,?)");
            update.setInt(1, rol.getIdRol());
            update.setString(2, rol.getRolNombre());

            update.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al actualizarr rol " + e);
            Logger.getLogger(CRolesDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }
}
