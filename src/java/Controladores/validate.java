/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloDAO.CModificacionDAO;
import Utilidades.CEmail;
import ModeloVO.CUsuario;
import ModeloDAO.CUsuarioDAO;
import Utilidades.CExpregulares;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALEJANDRO
 */
public class validate extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //==================  VALIDACION DE USUARIO PARA INGRESAR AL SISTEMA=============================//
    //==========================================================================//
    //====================  INSTANCIAS PARA VALIDACION DEL USUARIO QUE INGRESAR AL SISTEMA============================//
    CUsuario usuario = new CUsuario();
    CUsuarioDAO daouser = new CUsuarioDAO();
    //==========================================================================//
    CEmail mail = new CEmail();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (request.getParameterMap().containsKey("accion")) {
            switch (accion) {

                case "activateaccount":

                try {
                    HttpSession ssnewuser = request.getSession();

                    ssnewuser.setAttribute("NOMBRES", request.getParameter("n").replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2"));
                    ssnewuser.setAttribute("APELLIDOS", request.getParameter("l").replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2"));
                    ssnewuser.setAttribute("TELEFONO", request.getParameter("t"));
                    ssnewuser.setAttribute("CORREO", request.getParameter("e"));
                    ssnewuser.setAttribute("AREA", Integer.parseInt(request.getParameter("a")));
                    ssnewuser.setAttribute("ROL", Integer.parseInt(request.getParameter("r")));

                    request.setAttribute("nombres", request.getParameter("n").replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2"));
                    request.setAttribute("apellidos", request.getParameter("l").replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2"));
                    request.setAttribute("telefono", request.getParameter("t"));
                    request.setAttribute("correo", request.getParameter("e"));

                    request.getRequestDispatcher("activateaccount.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    System.out.println("Error en process en activacion de cuenta" + e);
                    request.getRequestDispatcher("error404.jsp").forward(request, response);
                }

                break;

                default:
                    request.getRequestDispatcher("error404.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("error404.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();

        if (request.getParameterMap().containsKey("accion")) {

            if (accion.equals("Ingresar")) {
                try {

                    usuario.setEstado(0);
                    usuario.setID(0);
                    usuario.setCorreo(null);
                    usuario.setClave(null);
                    // Recibe datos de los txt del login correo y contraseña
                    String correo = request.getParameter("correo");
                    String clave = request.getParameter("pass");

                    if (CExpregulares.formatoCorreo(correo) == true) {

                        // En usuario estan los get y set de usuario
                        // Los get seran igual a lo que se trajo de la DB en validar ingreso
                        usuario = daouser.validarIngreso(correo, clave);

                        // Validacion de datos.
                        if (usuario.getCorreo() != null && usuario.getClave() != null && usuario.getEstado() == 1 && usuario.getCorreo().equals(correo) && usuario.getClave().equals(clave)) {

                            try {
                                // Si el usuario existe se crean las sessiones con los datos 
                                session.setAttribute("ID", usuario.getID());
                                session.setAttribute("NOMBRE", usuario.getNombres());
                                session.setAttribute("IDROL", usuario.getRol());
                                session.setAttribute("IDAREA", usuario.getArea());

                                usuario.setCorreo(null);
                                usuario.setClave(null);
                                usuario.setEstado(0);

                                request.getRequestDispatcher("allindex?").forward(request, response);

                            } catch (IOException | ServletException e) {
                                System.out.println("Error en validar" + e);
                                Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, e);
                                request.getRequestDispatcher("error404.jsp").forward(request, response);
                            }
                            // Envio al controlador usuarios donde el menu sera entra al perfil del usuario

                        } else {

                            usuario.setEstado(0);
                            usuario.setID(0);
                            usuario.setCorreo(null);
                            usuario.setClave(null);

                            request.setAttribute("alert", "Datos invalidos");
                            request.setAttribute("correo", correo);
                            request.getRequestDispatcher("index.jsp").forward(request, response);

                        }
                    } else {
                        request.setAttribute("alert", "Correo invalido");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                } catch (IOException | ServletException e) {
                    Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, e);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else if (accion.equals("Cerrar")) {
                try {

                    session.removeAttribute("ID");
                    session.removeAttribute("NOMBRE");
                    session.removeAttribute("IDROL");
                    session.removeAttribute("IDAREA");
                    session.invalidate();

                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (IOException | ServletException e) {

                    session.invalidate();
                    Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, e);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } else if (accion.equals("Confirmar")) { // Confirma el email que se ingresa para validar si existe en la bdb para cambio de pass

                CUsuarioDAO daous = new CUsuarioDAO();
                String email = request.getParameter("correoconfirmar");

                try {

                    if (CExpregulares.formatoCorreo(email) == true) {

                        if (!email.equals("")) {
                            usuario.setCorreo(email);
                            usuario = daous.validarEmail(email);

                            if (usuario.getID() > 0 && usuario.getCorreo().equalsIgnoreCase(email)) {

                                if (usuario.getCorreoalterno() == null) {
                                    usuario.setCorreoalterno(usuario.getCorreo());
                                }

                                session.setAttribute("codigoemail", mail.enviarEmail(usuario, "Recovery"));
                                session.setAttribute("Idusuario", usuario.getID());

                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("emailvacio");
                        }

                    } else {
                        response.getWriter().write("emailfalse");
                    }

                } catch (IOException e) {
                    System.out.println("Error al confirmar correo en post" + e);
                } catch (NamingException ex) {
                    Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (accion.equals("Codigo")) { // Confirmar el codigo enviado al correo electronico

                try {
                    int txtcodigo = Integer.parseInt(request.getParameter("codigo"));

                    if (CExpregulares.formatoNumero(Integer.toString(txtcodigo)) == true) {
                        if (txtcodigo > 0) {

                            int codigoemail = Integer.parseInt(session.getAttribute("codigoemail").toString());

                            if (txtcodigo == codigoemail) {
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("codfalse");
                            }
                        } else {
                            response.getWriter().write("codvacio");
                        }

                    } else {
                        response.getWriter().write("codinvalido");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error en la confirmacion del codigo en post" + e);
                    Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, e);
                    request.getRequestDispatcher("error404.jsp").forward(request, response);
                }

            } else if (accion.equals("Actualizar")) {
                try {
                    int idusuario = (int) (session.getAttribute("Idusuario")); // ID DEL USUARIO QUE ACTUALIZA LA CLAVE
                    String pass = request.getParameter("passone");
                    String passconfirm = request.getParameter("passconfirma");

                    if (pass.equals(passconfirm)) {
                        try {

                            usuario.setID(idusuario);
                            usuario.setClave(pass);

                            Date fechaactual = new Date(System.currentTimeMillis());
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

                            if (daouser.actualizarDatos(usuario, "Clave") == true) {
                                CModificacionDAO.modificaUsuario(idusuario, dateformat.format(fechaactual), "Actualizaste tu clave desde la recuperacion de contraseña.");
                                session.removeAttribute("Id");
                                session.invalidate();
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }

                            // request.getRequestDispatcher("index.jsp").forward(request, response);
                        } catch (IOException e) {
                            System.out.println("Error al actualizar la clave en post" + e);
                        }
                    } else {
                        response.getWriter().write("password"); // Respuesta de que las claves no coinciden
                    }

                } catch (IOException e) {
                    System.out.println("Error en la actualizacion de la clave en post" + e);
                    Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, e);
                    request.getRequestDispatcher("error404.jsp").forward(request, response);
                }
            } else if (accion.equals("Confirmar datos")) { // Confirma 
                try {
                    usuario.setCorreo(null);
                    usuario.setCorreoalterno(null);
                    usuario = daouser.validarEmail((String) session.getAttribute("CORREO"));

                    if (usuario.getCorreo() == null && usuario.getCorreoalterno() == null) {

                        if (session.getAttribute("NOMBRES") != null && session.getAttribute("APELLIDOS") != null && session.getAttribute("TELEFONO") != null && session.getAttribute("CORREO") != null && session.getAttribute("AREA") != null && session.getAttribute("ROL") != null) {

                            String pass = "";
                            Random rndpass = new Random();

                            for (int i = 0; i < 4; i++) {
                                pass += rndpass.nextInt(10);
                                pass += (char) (rndpass.nextInt(10) + 65);
                            }

                            usuario.setNombres((String) session.getAttribute("NOMBRES"));
                            usuario.setApellidos((String) session.getAttribute("APELLIDOS"));
                            usuario.setCorreo((String) session.getAttribute("CORREO"));
                            usuario.setTelefono((String) session.getAttribute("TELEFONO"));

                            usuario.setClave(pass);

                            usuario.setRol((int) session.getAttribute("ROL"));
                            usuario.setArea((int) session.getAttribute("AREA"));

                            if (daouser.registrarUsuario(usuario, "Registrar") != false) {

                                mail.enviarEmail(usuario, "AddUser");

                                session.removeAttribute("NOMBRES");
                                session.removeAttribute("APELLIDOS");
                                session.removeAttribute("TELEFONO");
                                session.removeAttribute("CORREO");
                                session.removeAttribute("AREA");
                                session.removeAttribute("ROL");
                                session.invalidate();

                                response.getWriter().write("true");

                            } else {
                                response.getWriter().write("false");
                            }

                        }
                    } else {
                        response.getWriter().write("correo"); // Correo ya registrado
                    }
                } catch (NamingException | NullPointerException e) {
                    session.invalidate();
                    System.out.println("Error al confirmar datos de usuario " + e);
                    Logger.getLogger(validate.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                response.getWriter().write("index");
            }

        } else {
            response.getWriter().write("error404");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
