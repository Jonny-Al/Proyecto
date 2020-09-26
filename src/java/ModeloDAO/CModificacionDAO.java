/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import Conexion.CConexion;
import ModeloVO.CModificacion;
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
 * @author jonny
 */
public class CModificacionDAO {

    boolean sentencia = false;
    Connection conn;
    CConexion cn = new CConexion();
    ResultSet rs = null;

    // ====== METODO PARA AGREGAR LA MODIFICACION QUE SE REALIZO SOBRE UN USUARIO
    public static boolean modificaUsuario(int usuario, String fecha, String dato) {
        boolean sentencia = false;
        CConexion cn = new CConexion();
        Connection conn = cn.getConexion();
       
        try {

            CallableStatement modificacion = conn.prepareCall("CALL Md_AddModificacion(?,?,?)");
            modificacion.setInt(1, usuario);
            modificacion.setString(2, fecha);
            modificacion.setString(3, dato);

            modificacion.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al add modificacion " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    public static List listarModificaciones(int usuario) {

        CConexion cn = new CConexion();
        Connection conn = cn.getConexion();
        List<CModificacion> listmodificaciones = new ArrayList();

        try {
            CallableStatement modificaciones = conn.prepareCall("CALL Md_ListModificaciones (?)");
            modificaciones.setInt(1, usuario);
            ResultSet rs = modificaciones.executeQuery();

            while (rs.next()) {
                CModificacion mod = new CModificacion();
                
                mod.setFechamodificacion(rs.getString(1));
                mod.setInfomodificacion(rs.getString(2));
                mod.setUsuariomodifico(rs.getString(3) + "  " + rs.getString(4));

                listmodificaciones.add(mod);
                System.out.println("Si las hay");
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar modificaciones " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listmodificaciones;
    }
}
