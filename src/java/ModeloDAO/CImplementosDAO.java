/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CImplementos;
import ModeloVO.CEventos;
import Conexion.CConexion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ALEJANDRO
 */
public class CImplementosDAO {

    private boolean sentencia = false;
    CConexion cn = new CConexion();
    ResultSet rs = null;
    Connection conn = null;

    //========= METDOO PARA REGISTRAR UN OBJETO
    public boolean registrarObjeto(CImplementos implemento) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement addobjeto = conn.prepareCall("CALL Ob_AddObjeto (?,?,?,?,?)");

            addobjeto.setString(1, implemento.getObjetonombre());
            addobjeto.setString(2, implemento.getMarca());
            addobjeto.setString(3, implemento.getSerial());
            addobjeto.setString(4, implemento.getCaracteristicas());
            addobjeto.setString(5, implemento.getEstado());

            addobjeto.executeUpdate();

            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error DAO al agregar implemento" + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ==================== METODO PARA VALIDAR QUE EL SERIAL DE UN OBJETO NO ESTE EN USO
    public boolean comprobarSerial(CImplementos obj) {
        sentencia = false;
        try {

            conn = cn.getConexion();

            CallableStatement spserial = conn.prepareCall("CALL Ob_ValidarSerial (?)");
            spserial.setString(1, obj.getSerial());

            rs = spserial.executeQuery();

            if (rs.next()) {
                // Si es true ya hay un objeto con ese serial
                sentencia = true;
                sentencia = (rs.getInt(1) == obj.getIdobjeto()) ? false : sentencia;
            }

        } catch (SQLException e) {
            System.out.println("Error en DAO al comprobar el serial " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // =================== METODO PARA COMPROBAR EL ESTADO QUE SE DA A UN OBJETO PARA COMPROBAR SI SON LOS QUE LE PERTENECEN A LOS OBJETOS AL REGISTRARLO
    public int comprobarEstado(String estado) {

        int idestado = 0;
        try {
            conn = cn.getConexion();

            CallableStatement estados = conn.prepareCall("CALL Ob_Comprobarestado (?)");
            estados.setString(1, estado);

            rs = estados.executeQuery();

            idestado = (rs.next()) ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            System.out.println("Error al comprobar estados para un objeto " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return idestado;
    }

    //================  METODO PARA ACTUALIZAR DATOS DE UN OBJETO
    public boolean actualizarObjeto(CImplementos obj) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement actualizar = conn.prepareCall("CALL Ob_ActualizarObjeto (?,?,?,?,?,?)");
            actualizar.setInt(1, obj.getIdobjeto());
            actualizar.setString(2, obj.getObjetonombre());
            actualizar.setString(3, obj.getMarca());
            actualizar.setString(4, obj.getSerial());
            actualizar.setString(5, obj.getCaracteristicas());
            actualizar.setString(6, obj.getEstado());

            actualizar.executeUpdate();
            sentencia = true;

        } catch (SQLException e) {
            System.out.println("Error en DAO al actualizar el objeto: " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    // ================= METODO PARA LISTAR TODOS LOS OBJETOS EN ALLOBJECTS.JSP
    public List listarTodosObjetos() {

        List<CImplementos> listobjetos = new ArrayList<>();

        try {
            conn = cn.getConexion();

            CallableStatement obj = conn.prepareCall("CALL Ob_ListTodosObjetos");
            rs = obj.executeQuery();

            while (rs.next()) {
                CImplementos objs = new CImplementos();

                objs.setIdobjeto(rs.getInt(1));
                objs.setObjetonombre(rs.getString(2));
                objs.setMarca(rs.getString(3));
                objs.setEstado(rs.getString(4));

                listobjetos.add(objs);
            }
        } catch (SQLException e) {
            System.out.println("Error en DAO al listar todos los objetos " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return listobjetos;
    }

    public List listaObjetosReporte() {

        List<CImplementos> listdisponibles = new ArrayList<>();

        try {
            conn = cn.getConexion();
            CallableStatement proclistobjetos = conn.prepareCall("CALL Ob_ListObjetosReportes");
            rs = proclistobjetos.executeQuery();

            while (rs.next()) {
                CImplementos obj = new CImplementos();

                obj.setIdobjeto(rs.getInt(1));
                obj.setObjetonombre(rs.getString(2));
                obj.setMarca(rs.getString(3));
                obj.setEstado(rs.getString(4));
                obj.setSerial(rs.getString(5));
                obj.setCaracteristicas(rs.getString(6));

                listdisponibles.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar objetos: " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listdisponibles;
    }

    //============ LISTAR SOLO LOS OBJETOS QUE ESTAN EN DISPONIBILIDAD EN implementsevents.jsp
    public List listaObjetosDisponibles() {

        List<CImplementos> listdisponibles = new ArrayList<>();

        try {
            conn = cn.getConexion();
            CallableStatement proclistobjetos = conn.prepareCall("CALL Ob_ListDisponibles");
            rs = proclistobjetos.executeQuery();

            while (rs.next()) {
                CImplementos obj = new CImplementos();

                obj.setIdobjeto(rs.getInt(1));
                obj.setObjetonombre(rs.getString(2));
                obj.setMarca(rs.getString(3));
                obj.setEstado(rs.getString(4));

                listdisponibles.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar objetos: " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listdisponibles;
    }

    //============ METODO PARA LISTAR LOS OBJETOS DE UN EVENTO DE UN USUARIO ==============//
    public List listaObjetosEvento(int evento) {
        List<CImplementos> listobjetos = new ArrayList();
        try {
            conn = cn.getConexion();
            CallableStatement objevento = conn.prepareCall("CALL Ob_VerObjetosDeEvento (?)");
            objevento.setInt(1, evento);

            rs = objevento.executeQuery();

            while (rs.next()) {
                CImplementos obj = new CImplementos();
                obj.setIdobjeto(rs.getInt(1));
                obj.setObjetonombre(rs.getString(2));

                listobjetos.add(obj);
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al listar objetos del evento : " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listobjetos;
    }

    // ================= METODO PARA VER INFORMACION DE UN OBJETO ========================//
    public CImplementos verInfoObjeto(int objeto) {
        CImplementos objetos = new CImplementos();
        try {
            conn = cn.getConexion();
            CallableStatement informacion = conn.prepareCall("CALL Ob_VerInformacion (?)");
            informacion.setInt(1, objeto);

            rs = informacion.executeQuery();

            if (rs.next()) {

                objetos.setObjetonombre(rs.getString(1));
                objetos.setMarca(rs.getString(2));
                objetos.setSerial(rs.getString(3));
                objetos.setCaracteristicas(rs.getString(4));
                objetos.setEstado(rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println("Error DAO listar objetos us: " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return objetos;
    }

    //======= METODO PARA SEPARAR OBJETOS POR UN USUARIO
    public int separarObjeto(CImplementos obj, CEventos even, String accion) {

        int valido = 0;

        try {
            conn = cn.getConexion();

            switch (accion) {

                case "Validar": // Valida si el objeto esta disponible : 1 si esta usado y 0 esta disponible

                    CallableStatement disponibilidad = conn.prepareCall("CALL Ob_Disponibilidad (?)");
                    disponibilidad.setInt(1, obj.getIdobjeto());

                    rs = disponibilidad.executeQuery();

                    valido = (rs.next()) ? 1 : 0;

                    break;

                case "Separar": // Si esta disponible va ingresar desde metodo a este case de separar

                    CallableStatement add = conn.prepareCall("CALL Ob_AddObjetoEnEvento (?,?)");

                    add.setInt(1, even.getIdevento());
                    add.setInt(2, obj.getIdobjeto());

                    add.execute();

                    valido++;

                    break;

                case "Traspaso":

                    CallableStatement traspaso = conn.prepareCall("CALL Ob_TraspasoEvento (?,?)");
                    traspaso.setInt(1, obj.getIdobjeto());
                    traspaso.setInt(2, even.getIdevento());

                    traspaso.execute();

                    valido++;

                    break;

                default:
                    throw new AssertionError();
            }

        } catch (SQLException e) {
            System.out.println("Error DAO al separar objeto: " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }

        return valido;
    }

    // ======== METODO PARA DESHACER UN OBJETO DE UN EVENTO
    public boolean deshacerObjetodeevento(CImplementos obj) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement delete = conn.prepareCall("CALL Ob_Quitar (?)");
            delete.setInt(1, obj.getIdobjeto());

            delete.execute();

            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error DAO al eliminar objeto: " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return sentencia;
    }

    // === VALIDA SI EL OBJETO A ELIMINAR DE EVENTOS PERTENCE A UN EVENTO CREADO POR EL 
    // USUARIO QUE LO VA ELIMINAR DE LA LISTA DE SU OBJETOS
    public CEventos validaObjetoUser(CImplementos obj) {

        CEventos evento = new CEventos();

        try {
            conn = cn.getConexion();
            CallableStatement validaobj = conn.prepareCall("CALL Ob_ValidaObjUser (?)");
            validaobj.setInt(1, obj.getIdobjeto());

            rs = validaobj.executeQuery();

            if (rs.next()) {
                evento.setIdevento(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("Error DAO al validar objeto:" + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return evento;
    }

    // ========= METODO PARA ELIMINAR UN OBJETO POR COMPLETO
    public boolean eliminarObjeto(CImplementos obj) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            CallableStatement eliminar = conn.prepareCall("CALL Ob_EliminarObjeto (?)");
            eliminar.setInt(1, obj.getIdobjeto());

            eliminar.executeUpdate();
            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error en DAO al eliminar el objeto " + e);
            Logger.getLogger(CImplementosDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return sentencia;
    }

}
