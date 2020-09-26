/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import Conexion.CConexion;
import ModeloVO.CNotas;
import ModeloVO.CUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ALEJANDRO
 */
public class CNotasDAO {

    private boolean sentencia = false;

    CNotas blocdenotas = new CNotas();
    CConexion cn = new CConexion();
    Connection conn = null;
    ResultSet rs = null;

    // ==== METODO PARA LISTAR LAS NOTAS DE UN USUARIO EN ESPECIFICO = EL QUE INGRESA AL SISTEMA
    public List listaNotas(CUsuario us) {
        List<Object> listnotas = new ArrayList<>();

        try {

            conn = cn.getConexion();
            CallableStatement notasusuario = conn.prepareCall("CALL Bn_ListNotas (?)");
            notasusuario.setInt(1, us.getID());

            rs = notasusuario.executeQuery();

            while (rs.next()) {
                CNotas notasus = new CNotas();
                notasus.setIdnota(rs.getInt(1));
                notasus.setNombrenota(rs.getString(2));
                notasus.setTextonota(rs.getString(3));

                listnotas.add(notasus);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar notas" + e);
            Logger.getLogger(CNotasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listnotas;
    }

    // ======= METODO PARA CREAR NOTAS NUEVAS
    public boolean crearNota(CNotas nota) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement addnota = conn.prepareCall("CALL Bn_CrearNota (?,?,?)");

            addnota.setInt(1, nota.getIdusuario());
            addnota.setString(2, nota.getNombrenota());
            addnota.setString(3, nota.getTextonota());

            addnota.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al crear nota" + e);
            Logger.getLogger(CNotasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ===== VALIDA LAS NOTAS QUE SE VAN A ACTUALIZAR O ELIMINAR PARA CONFIRMAR
    // SI EL USUARIO QUE HACE LA ACCION SI LE PERTENECE O NO LA NOTA QUE ELIMINA O EDITA
    public boolean validarNota(CNotas nota) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement validar = conn.prepareCall("CALL Bn_ValidaNotaUsuario (?,?)");
            validar.setInt(1, nota.getIdnota());
            validar.setInt(2, nota.getIdusuario());

            rs = validar.executeQuery();

            sentencia = (rs.next()) ? true : sentencia;

        } catch (SQLException e) {
            System.out.println("Error DAO al validar nota " + e);
            Logger.getLogger(CNotasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    public CNotas verTextoNota(int nota) {
        try {
            conn = cn.getConexion();
            CallableStatement vernota = conn.prepareCall("CALL Bn_VerTextoNota (?)");
            vernota.setInt(1, nota);
            rs = vernota.executeQuery();

            if (rs.next()) {
                blocdenotas.setIdnota(rs.getInt(1));
                blocdenotas.setNombrenota(rs.getString(2));
                blocdenotas.setTextonota(rs.getString(3));
            }
        } catch (SQLException e) {
            System.out.println("Error DAO al ver el texto de la nota " + e);
            Logger.getLogger(CNotasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally{
            conn = cn.closeConn();
        }
        return blocdenotas;
    }

    // ======= METODO PARA ACTUALIZAR LA NOTA DESPUES DE VALIDAR SI LE PERTENECE LA NOTA
    public boolean actualizarNota(CNotas nota) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            CallableStatement update = conn.prepareCall("CALL Bn_ActualizarNota (?,?,?)");

            update.setInt(1, nota.getIdnota());
            update.setString(2, nota.getNombrenota());
            update.setString(3, nota.getTextonota());

            update.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al actualizar nota");
            Logger.getLogger(CNotasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    public boolean eliminarNota(CNotas nota) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement eliminar = conn.prepareCall("CALL Bn_EliminarNota (?)");
            eliminar.setInt(1, nota.getIdnota());

            eliminar.executeUpdate();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al eliminar nota " + e);
            Logger.getLogger(CNotasDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

}
