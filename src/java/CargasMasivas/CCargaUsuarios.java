/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CargasMasivas;

import Utilidades.CEmail;
import ModeloVO.CAreas;
import ModeloDAO.CAreasDAO;
import ModeloVO.CRoles;
import ModeloDAO.CRolesDAO;
import ModeloVO.CUsuario;
import ModeloDAO.CUsuarioDAO;
import Utilidades.CExpregulares;
import java.util.Random;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALEJANDRO
 */
public class CCargaUsuarios {

    CUsuario usuario = new CUsuario();
    CUsuarioDAO daousuario = new CUsuarioDAO();
    CAreas area = new CAreas();
    CAreasDAO daoarea = new CAreasDAO();
    CRoles rol = new CRoles();
    CRolesDAO daorol = new CRolesDAO();
    CEmail email = new CEmail();
    private int disponible;

    // ====== METODO PARA ENVIAR DATOS A LOS SET PARA ENVIAR AL METODO ADDUSUARIO EN DAOUSUARIO
    // Y CARGARLOS EN LA BASE DE DATOS CON LOS DAROS QUE SE LEEN EN EL EXCEL
    public int cargarUsuario(String dato, int caso) {

        try {
            switch (caso) {

                case 1: // Nombres
                    usuario.setNombres(dato);
                    break;

                case 2: // Apellidos
                    usuario.setApellidos(dato);
                    break;

                case 3: // Telefono

                    CExpregulares.formatoNumero(dato);

                    usuario.setTelefono(dato);
                    break;

                case 4: // Correo

                    if (CExpregulares.formatoCorreo(dato) == true) {

                        usuario.setCorreo(dato);
                        // Valida si el email esta disponible
                        if (daousuario.registrarUsuario(usuario, "Validaremail") == true) {
                            disponible = 0;  // 0 es que no encontro nada y esta disponible
                        } else {
                            disponible = 1;   // 1 es que si encontro ese email y no esta disponible
                        }
                    } else {
                        disponible = 1;   // El correo no cumple el formato entonces no se debe registrar
                    }

                    break;

                case 5: // Area

                    if (disponible == 0) {
                        area = daoarea.validarArea(dato);

                        if (area.getIdArea() > 0) {
                            usuario.setArea(area.getIdArea());
                        } else {
                            area.setAreaNombre(dato);
                            if (daoarea.registrarArea(area) == true) {
                                area = daoarea.validarArea(dato);
                                usuario.setArea(area.getIdArea());
                            } else {
                                usuario.setArea(1);
                            }
                        }
                    }
                    break;

                case 6: // Rol
                    if (disponible == 0) {

                        rol = daorol.validarRol(dato);

                        if (rol.getIdRol() > 0) {
                            usuario.setRol(rol.getIdRol());
                        } else {
                            rol.setRolNombre(dato);

                            if (daorol.registrarRol(rol) == true) {
                                rol = daorol.validarRol(dato);
                                usuario.setRol(rol.getIdRol());
                            } else {
                                usuario.setRol(1);
                            }
                        }

                        String pass = "";
                        Random rndpass = new Random();

                        for (int i = 0; i < 10; i++) {
                            if (i < 4) {
                                pass += rndpass.nextInt(10);
                            } else {
                                pass += (char) (rndpass.nextInt(10) + 65);
                            }
                        }

                        usuario.setClave(pass);

                        if (daousuario.registrarUsuario(usuario, "Registrar") == true) {
                            email.enviarEmail(usuario, "AddUser");
                        }
                    }
                    break;

                default:
                // throw new AssertionError();
            }
        } catch (NamingException e) {
            System.out.println("Error en la carga masiva de usuarios " + e);
            Logger.getLogger(CCargaUsuarios.class.getName()).log(Level.SEVERE, null, e);
        }
        return disponible;
    }

}
