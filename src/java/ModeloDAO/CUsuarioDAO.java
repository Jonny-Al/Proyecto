/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDAO;

import ModeloVO.CUsuario;
import Conexion.CConexion;
import Utilidades.CEncript;
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
public class CUsuarioDAO {
    
    private boolean sentencia = false;
    
    Connection conn = null;
    CConexion cn = new CConexion();
    CUsuario usuario = new CUsuario();
    ResultSet rs = null;

    //============= VALIDAR USUARIO QUE VA INGRESAR AL SISTEMA EN INDEX.JSP
    public CUsuario validarIngreso(String correo, String clave) {
        try {
            conn = cn.getConexion();
            
            CallableStatement ingresar = conn.prepareCall("CALL Us_IngresarLogin (?,?)");
            ingresar.setString(1, correo);
            try { // Encripta la contraseña que recibe para compararla con la encriptada en la db
                ingresar.setString(2, CEncript.encriptar(clave));
            } catch (SQLException e) {
                System.out.println("Error al desencriptar pass en dao " + e);
            }
            
            rs = ingresar.executeQuery();
            
            if (rs.next()) {
                
                usuario.setID(rs.getInt(1));
                usuario.setNombres(rs.getString(2));
                usuario.setCorreo(rs.getString(3));
                
                try { // Al leer la clave la desencripta para validarla con la que ingreso el usuario
                    usuario.setClave(CEncript.desencriptar(rs.getString(4)));
                } catch (SQLException e) {
                    System.out.println("Error al desencriptar la clave en dao " + e);
                }
                
                usuario.setEstado(rs.getInt(5));
                usuario.setRol(rs.getInt(6));
                usuario.setArea(rs.getInt(7));
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al validar ingreso: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return usuario;
    }

    //============= METODO PARA REGISTRAR USUARIOS EN ALLUSERS.JSP
    public boolean registrarUsuario(CUsuario usuario, String accion) {
        
        sentencia = false;
        
        try {
            conn = cn.getConexion();
            
            switch (accion) {
                
                case "Validaremail": // Antes del registro valida que el email este disponible ( no exista en la db)

                    CallableStatement validaemail = conn.prepareCall("CALL Us_ValidarEmail (?)");
                    validaemail.setString(1, usuario.getCorreo());
                    
                    rs = validaemail.executeQuery();

                    // Si exite correo es falso y no permite registro de lo contrario es true = si esta disponible el correo
                    sentencia = (rs.next()) ? sentencia : true;
                    
                    break;
                
                case "Registrar": // Registra el usuario solo si el correo estaba disponible

                    CallableStatement add = conn.prepareCall("CALL Us_Add (?,?,?,?,?,?,?)");
                    
                    add.setString(1, usuario.getNombres());
                    add.setString(2, usuario.getApellidos());
                    add.setString(3, usuario.getTelefono());
                    add.setString(4, usuario.getCorreo());
                    
                    try { // Encripta contraseña para enviarla a la db
                        add.setString(5, CEncript.encriptar(usuario.getClave()));
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                    
                    add.setInt(6, usuario.getArea());
                    add.setInt(7, usuario.getRol());
                    
                    add.executeUpdate();
                    
                    sentencia = true;
                    break;
                
                default:
                    sentencia = false;
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al agregar usuario: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //============= METODO PARA COMPROBAR SI EL EMAIL EXISTE EN DB O VER A QUIEN LE PERTENECE
    public CUsuario validarEmail(String correo) {
        
        try {
            conn = cn.getConexion();
            
            CallableStatement validaemail = conn.prepareCall("CALL Us_ValidarEmail (?)");
            validaemail.setString(1, correo);
            
            rs = validaemail.executeQuery();
            
            if (rs.next()) { // Si lee datos envia los datos que encontro 
                usuario.setID(rs.getInt(1));
                usuario.setCorreo(rs.getString(2));
                usuario.setCorreoalterno(rs.getString(3));

            } else {
                usuario.setID(0); // Si no hace que el id del usuario sea 0 para que no permita cambios
            }
        } catch (SQLException e) {
            System.out.println("Error DAO al validar email: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return usuario;
    }

    //============= METODO PARA TRAER LOS DATOS DEL USUARIO QUE SE VA A EDITAR EN ALLUSERS.JSP
    //============= METODO PARA TRAER LOS DATOS DEL USUARIO QUE INGRESO AL SISTEMA Y ENVIAR DATOS AL PERFIL DEL USUARIO EN PROFILE.JSP
    public CUsuario infoPerfil(CUsuario idUsuario) {
        
        try {
            conn = cn.getConexion();
            CallableStatement llamarus = conn.prepareCall("CALL Us_InfoPerfil (?)");
            llamarus.setInt(1, idUsuario.getID());
            rs = llamarus.executeQuery();
            
            if (rs.next()) {
                
                usuario.setID(rs.getInt(1));
                usuario.setNombres(rs.getString(2));
                usuario.setApellidos(rs.getString(3));
                usuario.setCorreo(rs.getString(4));
                usuario.setCorreoalterno(rs.getString(5));
                usuario.setTelefono(rs.getString(6));
                usuario.setFotoperfil(rs.getString(7));
                usuario.setRolNombre(rs.getString(8));
                usuario.setAreaNombre(rs.getString(9));
                usuario.setRol(rs.getInt(10));
                usuario.setArea(rs.getInt(11));
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al llamar usuario: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return usuario;
    }

    // Metodo para traer los datos del usuario a editar en allusers.jsp
    public CUsuario infoUsuario(CUsuario idUsuario) {
        
        try {
            conn = cn.getConexion();
            CallableStatement llamarus = conn.prepareCall("CALL Us_InfoPerfil (?)");
            llamarus.setInt(1, idUsuario.getID());
            rs = llamarus.executeQuery();
            
            if (rs.next()) {
                
                usuario.setCorreo(rs.getString(4));
                usuario.setRol(rs.getInt(10));
                usuario.setArea(rs.getInt(11));
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al llamar usuario: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return usuario;
    }

    // ============ METODO PARA AGREGAR FOTO DE PERFIL EN PROFILE.JSP
    public boolean insertarFotoPerfil(CUsuario usuario) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            
            CallableStatement fotoperfil = conn.prepareCall("CALL Us_AgregarFotoPerfil (?,?)");
            fotoperfil.setInt(1, usuario.getID());
            fotoperfil.setString(2, usuario.getFotoperfil()); // Ruta de la foto de perfil

            fotoperfil.executeUpdate();
            
            sentencia = true;
        } catch (SQLException e) {
            System.out.println("Error en DAO al agregar la ruta de foto de perfil" + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //============METODO PARA LISTAR USUARIOS EN ALLUSERS.JSP 
    public List listarUsuarios(String caso) {
        List<CUsuario> listuser = new ArrayList<>();
        
        try {
            conn = cn.getConexion();
            
            switch (caso) {
                
                case "Todos": // LIsta de todos los usuarios
                    CallableStatement listodos = conn.prepareCall("CALL Us_ListUsuariosTodos");
                    rs = listodos.executeQuery();
                    
                    break;
                
                case "Activos": // Lista usuarios activos en estado 1

                    CallableStatement listactivos = conn.prepareCall("CALL Us_ListUsuariosActivos");
                    rs = listactivos.executeQuery();
                    
                    break;
                
                case "Inactivos": // Lista usuarios activos en estado 2

                    CallableStatement listinactivos = conn.prepareCall("CALL Us_ListUsuariosInactivos");
                    rs = listinactivos.executeQuery();
                    
                    break;
                
                default:
                // throw new AssertionError();
            }
            
            while (rs.next()) { // LLena el objeto con los datos
                CUsuario us = new CUsuario();
                
                us.setID(rs.getInt(1));
                us.setNombres(rs.getString(2));
                us.setApellidos(rs.getString(3));
                us.setCorreo(rs.getString(4));
                us.setTelefono(rs.getString(5));
                us.setRolNombre(rs.getString(7));
                us.setAreaNombre(rs.getString(8));
                
                listuser.add(us); // Agrega a la lista el obj que tiene los datos enviados
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al listar usuarios: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listuser;
    }

    //============== LISTAR USUARIOS DE AREA DEL USUARIO QUE INGRESAR AL SISTEMA EN TAREASGRUPO.JSP
    public List listarGrupo(CUsuario usuario, int caso, int idusuariosoicita) {
        List<CUsuario> listagrupo = new ArrayList<>();
        try {
            conn = cn.getConexion();
            
            switch (caso) {
                case 1: // Listar directores - este es para solo el gerente
                    CallableStatement listdirectores = conn.prepareCall("CALL Us_ListDirectores");
                    rs = listdirectores.executeQuery();
                    
                    break;
                
                case 2: // Lista usuarios del area solo para el director de area y en parametro envia el id del area
                    CallableStatement listusers = conn.prepareCall("CALL Us_ListarGrupo (?)");
                    listusers.setInt(1, usuario.getArea());
                    rs = listusers.executeQuery();
                    
                    break;
                
                default:
                //  throw new AssertionError();
            }
            
            while (rs.next()) {
                CUsuario us = new CUsuario();
                
                us.setID(rs.getInt(1));

                // Si el id del usuario -> director es diferente a los que lee en la db los trae
                // de lo contrario no para no mostrarse a si mismo en la lista de grupo de tareas grupales
                if (us.getID() != idusuariosoicita) {
                    us.setNombres(rs.getString(2));
                    us.setArea(rs.getInt(4));
                    us.setRol(rs.getInt(5));
                    
                    listagrupo.add(us); // Agrega a la lista el obj que tiene los datos enviados
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al listar grupo: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return listagrupo;
    }

    //============= METODO PARA VALIDAR QUE LA CLAVE INGRESADA ES LA MISMA QUE SOLICITA CAMBIAR EN EL PERFIL DEL USUARIO.
    public CUsuario confirmarClave(CUsuario usuario) {
        
        try {
            
            conn = cn.getConexion();
            
            CallableStatement confirmpass = conn.prepareCall("CALL Us_ConfirmaPass (?, ?)");
            confirmpass.setInt(1, usuario.getID());
            try { // Encripta la clave para consultarla en la db
                confirmpass.setString(2, CEncript.encriptar(usuario.getClave()));
            } catch (SQLException e) {
                e.getMessage();
            }
            
            rs = confirmpass.executeQuery();
            
            if (rs.next()) { // Si lee los datos desencripta la clave para validarla con la ingresada

                try {
                    usuario.setClave(CEncript.desencriptar(rs.getString(1)));
                } catch (SQLException e) {
                    e.getMessage();
                }
                
            } else {
                usuario.setClave(null);
            }
        } catch (SQLException e) {
            System.out.println("Error DAO al confirmar clave: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return usuario;
    }

    //=========== ACTUALIZAR DATOS DE USUARIOS.
    public boolean actualizarDatos(CUsuario usuario, String opcion) {
        sentencia = false;
        try {
            
            conn = cn.getConexion();
            
            switch (opcion) {
                case "Datos": // Actualiza los datos solo el usuario que ingreso al sistema desde el perfil.

                    CallableStatement update = conn.prepareCall("CALL Us_UpdateDatosPersonales (?,?,?,?,?)");
                    
                    update.setInt(1, usuario.getID());
                    update.setString(2, usuario.getNombres());
                    update.setString(3, usuario.getApellidos());
                    update.setString(4, usuario.getCorreoalterno());
                    update.setString(5, usuario.getTelefono());
                    
                    update.executeUpdate();
                    sentencia = true;
                    
                    break;
                
                case "Infotrabajo": // Actualiza los datos de un usuario solo para director de area en allusers.jsp

                    CallableStatement updtrabajo = conn.prepareCall("CALL Us_UpdateInfoTrabajo (?,?,?,?)");
                    
                    updtrabajo.setInt(1, usuario.getID());
                    updtrabajo.setString(2, usuario.getCorreo());
                    updtrabajo.setInt(3, usuario.getArea());
                    updtrabajo.setInt(4, usuario.getRol());
                    
                    updtrabajo.executeUpdate();
                    sentencia = true;
                    break;
                
                case "Clave":
                    // Despues de ejecutar el metodo  confirmarClave dependiendo lo que retorne 
                    // Entra aca para hacer el cambio de clave ingresado

                    CallableStatement passupd = conn.prepareCall("CALL Us_UpdatePassword (?,?)");
                    passupd.setInt(1, usuario.getID());
                    
                    try { // Encripta la clave nueva para enviar a la db
                        passupd.setString(2, CEncript.encriptar(usuario.getClave()));
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                    
                    passupd.executeUpdate();
                    sentencia = true;
                    break;
                
                default:
                    sentencia = false;
                //  throw new AssertionError();
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al actualizar datos: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //=== 1. METODO PARA PARA VALIDAR SI UN USUARIO PERTENECE A EL AREA DEL DIRECTOR QUE DESEA HACER UN ACCION
    // PARA OTORGAR PERMISOS YA QUE NO PODRA REALIZAR ACCIONES SOBRE USUARIOS DE OTRA AREA
    //=== 2. METODO TAN BIEN PARA VALIDAR SI AL USUARIO QUE SE LE DESEA VER LAS TAREAS PERTENECE A EL AREA DEL DIRECTOR
    // QUE SOLICITA VER ESAS TAREAS 
    public CUsuario validaIdArea(CUsuario idus) {
        
        try {
            conn = cn.getConexion();
            
            CallableStatement procdelete = conn.prepareCall("CALL Us_ValidarIdArea (?)");
            procdelete.setInt(1, idus.getID());
            
            rs = procdelete.executeQuery();
            
            if (rs.next()) {
                usuario.setArea(rs.getInt(1));
            }
            
        } catch (SQLException e) {
            System.out.println("Error DAO al validar area: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return usuario;
    }

    //========== PROCEDIMIENTO DARLE UN ESTADO A UN USUARIO
    public boolean estadoUsuario(CUsuario us, int estado) {
        sentencia = false;
        try {
            conn = cn.getConexion();

            // Estado 1 activa, estado 2 deshabilita
            CallableStatement activar = conn.prepareCall("CALL Us_AddEstadoUsuario (?,?)");
            activar.setInt(1, us.getID());
            activar.setInt(2, estado);
            
            activar.executeUpdate();
            sentencia = true;
            
        } catch (SQLException e) {
            System.out.println("Error DAO al dar estado a usuario: " + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }

    //===== METODO PARA  ELIMINAR USUARIO DESDE ALLUSERS.JSP SOLO DEBE ELIMINAR EL DIRECTOR O GERENTE
    public boolean eliminarUsuario(CUsuario idus) {
        sentencia = false;
        try {
            conn = cn.getConexion();
            
            CallableStatement delete = conn.prepareCall("CALL Us_Eliminar (?)");
            delete.setInt(1, idus.getID());
            
            delete.executeUpdate();
            
            sentencia = true;
            
        } catch (SQLException e) {
            System.out.println("Error DAO al eliminar usuario" + e);
            Logger.getLogger(CUsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            conn = cn.closeConn();
        }
        return sentencia;
    }
    
}
