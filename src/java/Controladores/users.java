/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Utilidades.CAccionesdoc;
import Reportes.CReportexcel;
import CargasMasivas.CLeerExcel;
import ModeloDAO.CAreasDAO;
import ModeloDAO.CRolesDAO;
import ModeloVO.CUsuario;
import ModeloDAO.CUsuarioDAO;
import Utilidades.CEmail;
import Utilidades.CExpregulares;
import ModeloDAO.CChatsDAO;
import ModeloDAO.CModificacionDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ALEJANDRO
 */
public class users extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //======================== INSTANCIAS DE ROLES ============================//
    CRolesDAO daorol = new CRolesDAO();
    //======================== INSTANCIAS DE AREA =============================//
    CAreasDAO daoarea = new CAreasDAO();
    //======================== INSTANCIAS DE USUARIO ==========================//
    CUsuario usuario = new CUsuario();
    CUsuarioDAO daouser = new CUsuarioDAO();
    //======================= INSTANCIAS DE CHAT  ======================//
    CChatsDAO daochat = new CChatsDAO();
    //======================= INSTANCIAS DE EMAIL  ======================//
    CEmail email = new CEmail();
    //======================== VARIABLES ======================================//
    // IDS SOLO PARA USUARIO QUE INGRESAR AL SISTEMA
    int myid, idmyarea, idmyrol;

    // Variables para demas uso de objetos
    int idusuario;
    int idarea, idrol;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  ServletContext contexto = request.getServletContext();
        // String passcorreo = contexto.getInitParameter("passcorreo");
        HttpSession session = request.getSession();

        //========================== ASIGNACION AL VALOR DE LAS VARIABLES CON DATOS DEL USUARIO  ============================//
        try {
            try {
                // Asigna sessiones a variablales
                myid = (int) session.getAttribute("ID"); // ID del usuario que ingresa al sistema
                idmyarea = (int) session.getAttribute("IDAREA"); // ID del area del usuario que ingresa al sistema
                idmyrol = (int) session.getAttribute("IDROL"); // ID del rol del usuario que ingresa al sistema
                request.setAttribute("miname", session.getAttribute("NOMBRE")); // Envio a la vista el nombre del usuario en el menu html
            } catch (Exception e) {
                // Si hay errores en las sessiones remueve los atributos y lo envia al index.jsp
                session.removeAttribute("ID");
                session.removeAttribute("IDAREA");
                session.removeAttribute("IDROL");
                session.invalidate(); // Invalida las sessiones
                System.out.println("Error en usuarios se cerro session" + e);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            // Variables para recibir del jsp jsp
            String menu = request.getParameter("menu"); // Opciones de menus que el usuario desea ver en la vista
            String accion = request.getParameter("accion"); // Accion de los botones 
            String alert = request.getParameter("alert"); // Varable para recibir mensajes de JQUERY si una accion se cumple

            if (request.getParameterMap().containsKey("menu") == true && request.getParameterMap().containsKey("accion") == true) {

                if (menu.equals("Perfil") || menu.equals("Consultar") || menu.equals("modificaciones")) {

                    if (accion.equals("Miperfil") || accion.equals("Listar") || accion.equals("ReporteExcel") || accion.equals("modificaciones")) {

                        // Valida si las sessiones no son nulas para permitir ingresar a informacion del sistema de lo contrario envia al index.jsp
                        if (session.getAttribute("ID") != null || session.getAttribute("IDAREA") != null || session.getAttribute("IDROL") != null || accion.equals("modificaciones")) {

                            // Recibe la lista de chats
                            List listchatstr = daochat.listChats();
                            // Envia lña lista de chats a la vista
                            request.setAttribute("listchat", listchatstr);

                            try {

                                try {
                                    //=============================== PROFILE.JSP ===========================================//
                                    if (menu.equals("Perfil")) {
                                        switch (accion) {

                                            case "Miperfil":

                                       try {
                                                // Envia el id del usuario para traer su informacion    
                                                usuario.setID(myid);
                                                // Recibe la informacion de ese usuario
                                                usuario = daouser.infoPerfil(usuario);
                                                if (usuario.getCorreoalterno() == null || usuario.getCorreoalterno().equals("")) {
                                                    request.setAttribute("clase", "alert alert-warning");
                                                    request.setAttribute("alert", "Registra un correo alternativo <a class=text-primary href=# data-toggle=\"modal\" data-target=\"#updateDatos\">aqui</a> ");
                                                }

                                            } catch (Exception e) {
                                                e.getMessage();
                                            }
                                            // Envia los datos del usuario al perfil
                                            request.setAttribute("misdatos", usuario);
                                            request.getRequestDispatcher("profile.jsp").forward(request, response);

                                            break;
                                        }

                                    }
                                } catch (IOException | ServletException e) {
                                    System.out.println("Error en el perfil del usuario: " + e);
                                }

                                try {
                                    if (menu.equals("modificaciones")) {
                                        List modificaciones = CModificacionDAO.listarModificaciones(myid);
                                        request.setAttribute("modificaciones", modificaciones);

                                        request.getRequestDispatcher("modification.jsp").forward(request, response);
                                    }
                                } catch (IOException | ServletException e) {
                                    System.out.println("Error en request al listar las modificaciones " + e);
                                }

                                try {
                                    //===============================TABLA DE USUARIOS DE LA ORGANIZACION ALLUSERS.JSP ===========================================//
                                    if (menu.equals("Consultar")) {

                                        switch (accion) {

                                            case "Listar": // === Accion para solo listar informacion de usuarios, roles y area

                                       try {

                                                // Recibe la lista de los roles 
                                                List listrol = daorol.listarRoles();
                                                // Envia la lista de roles en el modal de agregar usuario
                                                request.setAttribute("roles", listrol);

                                                // Recibe la lista de los areas 
                                                List listarea = daoarea.listarAreas();
                                                // Envia la lista e areas en el modal de agregar usuario
                                                request.setAttribute("areas", listarea);

                                                alert = request.getParameter("alert");
                                                if (alert != null) {
                                                    // Este alert es para enviar mensajes solo cuando se cumple un accion
                                                    // y jquery envia respuesta a este alert
                                                    request.setAttribute("class", "alert alert-success");
                                                    request.setAttribute("alert", alert);
                                                }

                                            } catch (Exception e) {
                                                System.out.println("Error al listar los usuarios " + e);
                                            }

                                            try {

                                                if (idmyrol < 3) {
                                                    // Si el rol tiene permisos lista los roles o area para agregar usuarios.
                                                    // de igual manera el modal no lo permite abrir a usuarios que no tiene permisos
                                                    // Pero esto evita sobre cargar datos no necesarios de mostrar
                                                    List listroles = daorol.listarRoles();
                                                    request.setAttribute("roles", listroles);

                                                    List listareas = daoarea.listarAreas();
                                                    request.setAttribute("areas", listareas);
                                                }

                                                // Recibe la lista de usuarios activos
                                                List usersactivos = daouser.listarUsuarios("Activos");
                                                // Envia la lista de usuarios activos a la vista
                                                request.setAttribute("usactivos", usersactivos);

                                                // Recibe la lista de usuarios inactivos
                                                List usersinactivos = daouser.listarUsuarios("Inactivos");
                                                // Envia la lista de usuarios inactivos a la vista
                                                request.setAttribute("usinactivos", usersinactivos);

                                            } catch (NumberFormatException e) {
                                                e.getMessage();
                                                System.out.println("Error al listar usuarios: " + e);
                                            }

                                            break;

                                            case "ReporteExcel": // === Accion para crear reporte de excel sobre usuarios

                                                String opcionusuarios = request.getParameter("opcionusuarios");

                                                if (opcionusuarios.equals("Todos") || opcionusuarios.equals("Activos") || opcionusuarios.equals("Inactivos")) {

                                                    try {
                                                        if (CReportexcel.crearExcelusuarios(opcionusuarios) == true) {
                                                            // Si el excel se creo correctamente envia a la vista la opcion para descargarlo
                                                            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                                                            response.addHeader("Content-Disposition", "attachment;filename=Reporteusuarios.xlsx");

                                                            try (BufferedOutputStream bfout = new BufferedOutputStream(response.getOutputStream());
                                                                    BufferedInputStream infile = new BufferedInputStream(new FileInputStream(CAccionesdoc.rutaReporteusuarios()))) {
                                                                byte[] tmp = new byte[8192];
                                                                int c;
                                                                while ((c = infile.read(tmp)) > 0) {
                                                                    bfout.write(tmp, 0, c);
                                                                }
                                                                bfout.flush();
                                                            }
                                                            CAccionesdoc.eliminarDocumento(CAccionesdoc.rutaReporteusuarios());
                                                        }

                                                    } catch (IOException e) {
                                                        System.out.println("Error en el reporte de excel de usuarios al descargar: " + e);
                                                    }
                                                } else {
                                                    request.getRequestDispatcher("error404.jsp").forward(request, response);
                                                }

                                                break;

                                            default:
                                            //  throw new AssertionError();
                                        }

                                        if (!accion.equalsIgnoreCase("ReporteExcel")) {
                                            request.getRequestDispatcher("allusers.jsp").forward(request, response);
                                        }
                                    }
                                } catch (IOException | ServletException e) {
                                    System.out.println("Errores en usuarios process " + e);
                                }

                            } catch (Exception e) {
                                System.out.println("Error en usuarios process" + e);
                                request.getRequestDispatcher("error404.jsp").forward(request, response);
                            }

                        } else {
                            System.out.println("Cierre de sesion en usuario process");
                            session.removeAttribute("ID");
                            session.removeAttribute("NOMBRE");
                            session.removeAttribute("IDROL");
                            session.removeAttribute("IDAREA");
                            session.invalidate();
                            request.getSession().invalidate();
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }

                    } else {
                        request.getRequestDispatcher("error404.jsp").forward(request, response);
                    }

                } else {
                    request.getRequestDispatcher("error404.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("error404.jsp").forward(request, response);
            }

        } catch (IOException | ServletException e) {
            System.out.println("Errores en usuarios process" + e);
            e.getMessage();
            request.getSession().invalidate();
            request.getSession().invalidate();
            request.getRequestDispatcher("index.jsp").forward(request, response);
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

        Date fechaactual = new Date(System.currentTimeMillis());
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        try {

            // Valida si las sessiones no son nulas para permitir ingreso a acciones
            if (session.getAttribute("ID") != null || session.getAttribute("IDAREA") != null || session.getAttribute("IDROL") != null) {
                // Asigno valores a las variables con las sessiones que se creo en el controlador validate
                myid = (int) session.getAttribute("ID");
                idmyarea = (int) session.getAttribute("IDAREA");
                idmyrol = (int) session.getAttribute("IDROL");

                String accion = request.getParameter("accion");
                // VARIABLE SESSION DEL USUARIO QUE INGRESA AL SISTEMA
                switch (accion) {

                    case "Eliminarfoto":
                        
                    try {
                        // Elimina la foto de perfil anterior de ese usuario
                        usuario.setID(myid);
                        usuario = daouser.infoPerfil(usuario);

                        if (!usuario.getFotoperfil().equalsIgnoreCase("fotodefecto.png")) {
                            CAccionesdoc.eliminarDocumento(CAccionesdoc.rutaFotoperfil(usuario.getFotoperfil()));

                            usuario.setFotoperfil("fotodefecto.png");
                            daouser.insertarFotoPerfil(usuario);

                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("sinfoto");
                        }

                    } catch (IOException e) {
                        System.out.println("Error al eliminar la foto desde post : " + e);
                    }
                    break;

                    case "Subirfoto":

                        // Elimina la foto de perfil anterior de ese usuario
                        usuario.setID(myid);
                        usuario = daouser.infoPerfil(usuario);
                        if (!usuario.getFotoperfil().equalsIgnoreCase("fotodefecto.png")) {
                            CAccionesdoc.eliminarDocumento(CAccionesdoc.rutaFotoperfil(usuario.getFotoperfil()));
                        }

                        // Luego envia la foto nueva 
                        try {

                            // Ruta de la foto a subir
                            String rutafoto = CAccionesdoc.rutaFotoperfil("");
                            DiskFileItemFactory filefactory = new DiskFileItemFactory();
                            filefactory.setSizeThreshold(1024);
                            filefactory.setRepository(new File(rutafoto));
                            ServletFileUpload uploadfoto = new ServletFileUpload(filefactory);

                            List<FileItem> partesfoto = uploadfoto.parseRequest(request);

                            for (FileItem items : partesfoto) {
                                File file = new File(rutafoto, items.getName());
                                items.write(file);
                                break;
                            }

                            response.getWriter().write("true");

                        } catch (Exception e) {
                            System.out.println("Error al subir la foto en post " + e);
                        }

                        break;

                    case "InsertarFoto": // Inserta el nombre de la foto en la db

                    try {
                        String nombrefoto = request.getParameter("nombrefoto").replaceAll("C:\\\\fakepath\\\\", "");
                        usuario.setID(myid);
                        usuario.setFotoperfil(nombrefoto);
                        if (daouser.insertarFotoPerfil(usuario) == true) {
                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("false");
                        }
                    } catch (IOException e) {
                        System.out.println("Error al enviar nombre de foto en post " + e);
                    }

                    break;

                    case "Subirdoc": // Sube el archivo al servidor para realizar la carga masiva de usuarios.
                        
                    try {

                        if (idmyrol < 3) {

                            // Ruta donde se va subir el documento 
                            DiskFileItemFactory factory = new DiskFileItemFactory();

                            factory.setSizeThreshold(1024);
                            factory.setRepository(new File(CAccionesdoc.rutaExcelcargamasiva("")));
                            ServletFileUpload uploaddoc = new ServletFileUpload(factory);

                            List<FileItem> partesdoc = uploaddoc.parseRequest(request);

                            for (FileItem items : partesdoc) {
                                File file = new File(CAccionesdoc.rutaExcelcargamasiva(""), items.getName());
                                items.write(file);
                                break;
                            }

                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("refresh");
                        }

                    } catch (Exception e) {
                        System.out.println("Error al subir el documento desde post " + e);
                    }

                    break;

                    case "Carga": // Accion para carga masiva de usuarios

                        if (idmyrol < 3) {
                            // Recibira una lista en caso de que encuentre correos que no estan disponibles
                            // Para dar informe de usuarios que no se lograron cargar

                            File file = new File(request.getParameter("nombrefile"));
                            String filename = file.getName();
                            // Se crea una lista en caso de resibir usuarios que no se agregaron para enviarlos a la vista
                            List listnodisponibles = CLeerExcel.leerExcelUsuarios(filename);

                            if (listnodisponibles.isEmpty()) {
                                response.getWriter().write("true");
                            } else if (!listnodisponibles.isEmpty()) {
                                // Si encuentra datos en la vista los envia de respuesta
                                response.getWriter().write(CAccionesdoc.crearListaJson(listnodisponibles));

                            } else if (listnodisponibles.isEmpty()) {
                                response.getWriter().write("vacio");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("refresh");
                        }
                        break;

                    // ========== Actualizar la contraseña desde el perfil del usuario
                    case "Updpass":

                 try {

                        String passactual = request.getParameter("passact"); // Recibe la clave
                        usuario.setClave(passactual); // Envia la clave del usuario
                        usuario.setID(myid); // Envia el id del usuario

                        // Recibe los datos que retorno el metodo que valida la contraseña actual
                        usuario = daouser.confirmarClave(usuario);

                        // Si la clave que retorno es diferente a null es porque la clave actual si es valida
                        if (usuario.getClave() != null) {

                            usuario.setClave(null); // Borra la clave que quedo en el setter

                            String pass = request.getParameter("pass"); // Recibe la pass nueva
                            String passconfirma = request.getParameter("passtwo"); // Pass para confirmar que sean iguales a la nueva

                            if (pass.equals(passconfirma)) { // Validacion si las claves son iguales

                                // Envia los datos del usuario para cambiar la clave
                                // usuario.setID(myid);
                                usuario.setClave(pass);

                                // Valida si se cambio correctamemte la clave
                                if (daouser.actualizarDatos(usuario, "Clave") == true) {
                                    usuario = daouser.infoPerfil(usuario);
                                    usuario.setClave(null);
                                    email.enviarEmail(usuario, "Claveactualizada");
                                    CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Actualizaste tu clave");

                                    response.getWriter().write("true");
                                } else {
                                    response.getWriter().write("false");// Envia false a JQuery
                                }

                            } else {
                                response.getWriter().write("psinvalidnew"); // Envia que las claves no son validas
                            }

                        } else {
                            response.getWriter().write("psinvalidact"); // Envia que la clave actual es invalida
                        }

                    } catch (NamingException e) {
                        System.out.println("Error al actualizar la clave en post " + e);
                    }

                    break;

                    case "Updmidatos": // Actualizacion datos usuario en el perfil del usuario
                        
                        try {

                        String nombres = request.getParameter("names"); // Nombres
                        String apellidos = request.getParameter("last"); // Apellidos
                        String telefono = request.getParameter("telep"); // Telefono
                        String correoalterno = request.getParameter("correoalterno");
                        String codigo = request.getParameter("codalterno");

                        if (!nombres.equals("") && !apellidos.equals("") && !correoalterno.equals("")) { // Valida que no envie valores nulos

                            if (CExpregulares.formatoCorreo(correoalterno) == true) { // Valida que el correo cumpla con el formato correcto

                                if (CExpregulares.formatoNumero(telefono) == true) { // Valida que el telefono cumpla con el formato correcto

                                    usuario = daouser.validarEmail(correoalterno);

                                    if (usuario.getID() == 0 || usuario.getID() == myid) {

                                        // Limpiar los set y get del vo para comprobar con clase limpia
                                        usuario.setID(0);
                                        usuario.setCorreo(null);
                                        usuario.setCorreoalterno(null);
                                        usuario.setID(myid);
                                        usuario = daouser.infoPerfil(usuario);

                                        // Si no hay codigo valida datos del perfil y envia al set de correo un dato para dejar pasar en el proximo if
                                        if (!codigo.equals("0")) {
                                            usuario.setCorreoalterno("llenarcorreo");
                                        }
                                        if (!usuario.getCorreo().equalsIgnoreCase(correoalterno)) {

                                            if (usuario.getCorreoalterno() != null) {
                                                // Valida si el correo alterno que llega del parametro sigue siendo igual para permitir la actualizacion de lo contrario
                                                // valida que ese correo electronico si exista ya que realizo cambio en el correo
                                                if (!codigo.equalsIgnoreCase("0")) {
                                                    int codigoemail = (int) session.getAttribute("CODIGOEMAILALTERNO");

                                                    if (codigo.equals(Integer.toString(codigoemail))) {
                                                        // Si codiggo el codigo es valido en el set correoalterno se pone el correo alterno para dejar pasar en el if
                                                        usuario.setCorreoalterno(correoalterno);
                                                        codigo = "0"; // Se deja codigo en 0 para que pueda ingresar al siguiente if -> if (codigo.equals("0") para actualizacion de datos
                                                    } else {
                                                        response.getWriter().write("codinvalido");
                                                        codigo = "1"; // Codigo en 1 para no dejar pasar en el siguiente if
                                                    }
                                                }
                                                if (codigo.equals("0")) {
                                                    if (usuario.getCorreoalterno().equalsIgnoreCase(correoalterno)) {
                                                        // Envio el id del usuario y los datos que se van actualizar
                                                        usuario.setID(myid);
                                                        usuario.setNombres(nombres);
                                                        usuario.setApellidos(apellidos);
                                                        usuario.setCorreoalterno(correoalterno);
                                                        usuario.setTelefono(telefono);

                                                        // Valida si se actualizo correctamente los datos
                                                        if (daouser.actualizarDatos(usuario, "Datos") == true) {
                                                            session.setAttribute("NOMBRE", usuario.getNombres());
                                                            session.removeAttribute("CODIGOEMAILALTERNO");
                                                            // Envia true de respuesta
                                                            CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Actualizaste tus datos con nombres : " + nombres + " " + apellidos + " ,telefono " + telefono + " ,correo alterno" + correoalterno);
                                                            response.getWriter().write("true");
                                                        } else {
                                                            // Envia false de respuesta
                                                            response.getWriter().write("false");
                                                        }
                                                    } else {
                                                        try { // Aca entra si la celda esta total mente vacia
                                                            usuario.setCorreo(correoalterno);
                                                            session.setAttribute("CODIGOEMAILALTERNO", email.enviarEmail(usuario, "Validaremail"));
                                                            response.getWriter().write("validaralterno");
                                                        } catch (IOException | NamingException e) {
                                                            System.out.println("Error en el envio al metodo el correo alternativo " + e);
                                                        }
                                                    }
                                                }

                                            } else {
                                                try { // Aca entra si la celda esta asi (null)
                                                    usuario.setCorreo(correoalterno);
                                                    session.setAttribute("CODIGOEMAILALTERNO", email.enviarEmail(usuario, "Validaremail"));
                                                    response.getWriter().write("validaralterno");
                                                } catch (IOException | NamingException e) {
                                                    System.out.println("Error en el envio al metodo el correo alternativo " + e);
                                                }
                                            }

                                        } else {
                                            response.getWriter().write("emailigual");
                                        }

                                    } else {
                                        response.getWriter().write("emailnodisponible");
                                    }

                                } else {
                                    response.getWriter().write("numeroinvalido");
                                }
                            } else {
                                response.getWriter().write("correoinvalido");
                            }

                        } else {
                            response.getWriter().write("campos");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al actualizar los datos del usuario en post " + e);
                    }

                    break;

                    case "Agregar": // Accion para registrar usuario en allusers.jsp solo permitodo para directores o gerente
                
                     try {

                        if (idmyrol <= 2) { // Valida si el rol tiene permitido registrar usuarios

                            int area = Integer.parseInt(request.getParameter("ddlarea"));
                            int rol = Integer.parseInt(request.getParameter("ddlrol"));

                            if (rol > 2 && area > 1 || idmyrol == 1) {

                                // Se reciben los campos de texto desde js
                                String correo = request.getParameter("correo");
                                // Asignacion de valor a ssuseradd
                                String nombres = request.getParameter("nombres");
                                String apellidos = request.getParameter("apellidos");
                                String telefono = request.getParameter("telefono");

                                if (daoarea.validarAreExiste(area) == true && daorol.validarRolExiste(rol) == true) {

                                    if (!correo.equals("") && !nombres.equals("") && !apellidos.equals("") && !telefono.equals("") && area > 0 && rol > 0) {
                                        // Se valida el formatio del correo
                                        if (CExpregulares.formatoCorreo(correo) == true) {

                                            if (CExpregulares.formatoNumero(telefono) == true) {

                                                // Valida que los campos no lleguen vacios
                                                try {

                                                    // Primero envia el correo para validarlo si esta disponible
                                                    usuario.setCorreo(correo);
                                                    if (daouser.registrarUsuario(usuario, "Validaremail") == true) {
                                                        // Si el email esta disponible recibe los demas datos para hacer el registro
                                                        usuario.setNombres(nombres);
                                                        usuario.setApellidos(apellidos);
                                                        usuario.setCorreo(correo);
                                                        usuario.setTelefono(telefono);
                                                        usuario.setArea(area);
                                                        usuario.setRol(rol);

                                                        // Valida si el email se envio correctamente al usuario
                                                        try {
                                                            if (email.enviarEmail(usuario, "Activar cuenta") > 0) {
                                                                response.getWriter().write("true"); // Respuesta para JQuery
                                                            }

                                                        } catch (NamingException e) {
                                                            System.out.println("No se envio email" + e);
                                                        }
                                                    } else {
                                                        response.getWriter().write("mail"); // Envia respuesta si el mail ingresado ya existe.
                                                    }
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Error en post al registrar usuario " + e);
                                                }

                                            } else {
                                                response.getWriter().write("numeroinvalido");
                                            }
                                        } else {
                                            response.getWriter().write("correoinvalido");
                                        }
                                    } else {
                                        response.getWriter().write("campos"); // Envia respuesta de que los campos llegaron nulos
                                    }
                                } else {
                                    response.getWriter().write("rolareainvalid");
                                }

                            } else {
                                response.getWriter().write("rolnopermitido"); // Respuesta si intentan registrar otro usuario como gerente
                            }

                        } else {
                            response.getWriter().write("refresh");
                        }
                        // Se envian los datos por parametro a los datos encapsulados en CUsuario
                    } catch (NumberFormatException e) {
                        System.out.println("Error al agregar usuarios en el post " + e);
                    }

                    break;

                    case "Eliminar": // === Accion para eliminar un usuario // ESTA ACCION SIRVE PERO AUN NO ESTA FUNCION NO SE HA PUESTO EL BOTON DE ELIMINAR

                     try {

                        if (idmyrol <= 2) { // Valida que el rol tenga permisos para eliminar un usuario
                            try {
                                // Id de usuario que se va eliminar
                                // Recibe el Id del area a la que pertenece el US que ingreso al sistema
                                if ((int) session.getAttribute("IDUSUARIOEDITA") == myid) {
                                    response.getWriter().write("sinpermisos");
                                } else {
                                    usuario.setID((int) session.getAttribute("IDUSUARIOEDITA"));
                                    usuario = daouser.validaIdArea(usuario);

                                    if (usuario.getArea() == idmyarea || idmyrol == 1) {
                                        usuario = daouser.infoPerfil(usuario);
                                        if (daouser.eliminarUsuario(usuario) == true) {
                                            CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Elimino a: " + usuario.getNombres() + usuario.getApellidos() + " del area " + usuario.getAreaNombre());
                                            response.getWriter().write("true");

                                        } else {
                                            response.getWriter().write("false");
                                        }

                                    } else {
                                        response.getWriter().write("sinpermisos");
                                    }
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Error al eliminar el usuario desde post " + e);
                            }
                        } else {
                            response.getWriter().write("sinpermisos");
                        }
                    } catch (IOException e) {
                        System.out.println("Error al eliminar el usuario en post " + e);
                    }

                    break;

                    case "Activar": // ==== Accion para activar usuario 

                    try {
                        if (idmyrol <= 2) { // == Valida si usuario que realiza la accion tiene permisos 
                            // Recibe el id del usuario que se desea activar
                            // Valida que el id del director no sea igual al que desea activar ya que el no se puede activar a el mismo
                            // o que el id del rol que solicita sea el gerente para dar permisos
                            if ((int) session.getAttribute("IDUSUARIOEDITA") != myid || idmyrol == 1) {
                                try {
                                    // Envia el id del usuario primero para validacion
                                    usuario.setID((int) session.getAttribute("IDUSUARIOEDITA"));
                                    // Valida que el usuario que se va activar pertenezca a el area del director que va
                                    // realizar la acciom
                                    // Recibe el area del usuario que se va activar
                                    usuario = daouser.validaIdArea(usuario);

                                    // Compara si el id del area es la misma del id del area del director para que de permisos
                                    if (usuario.getArea() == idmyarea || idmyrol == 1) {
                                        // Valida si se cumplio la acion de activar el usuario
                                        if (daouser.estadoUsuario(usuario, 1) == true) {
                                            // Envia respuesta en true que si se cumplio
                                            usuario = daouser.infoPerfil(usuario);
                                            CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Activo a: " + usuario.getNombres() + usuario.getApellidos() + " del area " + usuario.getAreaNombre());
                                            response.getWriter().write("true");

                                        }
                                    } else {
                                        response.getWriter().write("pr"); // Envia respuesta que no tiene permisos
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error en usuarios al dar estado a el usuario en post " + e);
                                }
                            } else {
                                response.getWriter().write("pr"); // Envia respuesta que no tiene permisos
                            }
                        } else {
                            response.getWriter().write("refresh");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error en usuarios para dar estado a el usuario  " + e);
                    }

                    break;

                    case "Inactivar": // Accion para deshabilitar un usuario 
                        try {

                        if (idmyrol <= 2) { // Valida que el rol tenga permisos 

                            // Valida si el id del usuario en session es diferente al que desea deshabilitar
                            // Ya que el mismo director no tiene acciones sobre el 
                            if ((int) session.getAttribute("IDUSUARIOEDITA") != myid || idmyrol == 1) {

                                usuario.setID((int) session.getAttribute("IDUSUARIOEDITA"));
                                usuario = daouser.validaIdArea(usuario);

                                // Valida que el area del usuario pertenezca a el area del director o que sea gerente para dar permisos
                                if (usuario.getArea() == idmyarea || idmyrol == 1) {
                                    if (daouser.estadoUsuario(usuario, 2) == true) {
                                        usuario = daouser.infoPerfil(usuario);
                                        CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Desactivo a: " + usuario.getNombres() + usuario.getApellidos() + " del area " + usuario.getAreaNombre());
                                        response.getWriter().write("true");

                                    } else {
                                        response.getWriter().write("false");
                                    }
                                } else {
                                    response.getWriter().write("false");
                                }
                            } else {
                                response.getWriter().write("pr");
                            }
                        } else {
                            response.getWriter().write("refresh");
                        }

                    } catch (NumberFormatException e) {
                        e.getMessage();
                    }

                    break;

                    case "Editar":

                        // Recibe el id del usuario que se va editar en allusers.jsp
                        idusuario = Integer.parseInt(request.getParameter("usuario"));
                        if (idusuario != myid || idusuario == 1) {
                            // Valida que el area del usuario pertenezca al area del director que va editar.
                            usuario.setID(idusuario);
                            usuario = daouser.validaIdArea(usuario);
                            // Si el area del usuario es la misma del director permite editar o si el rol del que edita
                            // es 1 ya que es el gerente y tiene todos los permisos
                            if (usuario.getArea() == idmyarea || idmyrol == 1) {
                                // Envia el id del usuario para realizar la edicion
                                usuario.setID(idusuario);
                                // en el usuario quedan los datos del perfil del usuario
                                usuario = daouser.infoUsuario(usuario);
                                // Session para usuario que se va editar, eliminar, activar,deshabilitar despues de haber comprobado que tiene permisos
                                session.setAttribute("IDUSUARIOEDITA", idusuario);
                                // Se envia a js el resultado de la cadena en formato json que devuleve el createlistaJson

                                response.getWriter().write(CAccionesdoc.crearObjetoJson(usuario));

                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("false");
                        }

                        break;

                    case "Actualizar": // Actualizar datos del usuario en allusers.jsp

                    try {
                        //****** AL ACTUALIZAR DATOS DEBE VALIDAR SI ESE CORREO QUE INGRESO ESTA VALIDO SOLO SI LO CAMBIO - SI NO LO CAMBIO NO DEBERIA VALIDARLO

                        if (idmyrol <= 2) {

                            // Valida si el id del usuario en session es diferente al que desea deshabilitar
                            // Ya que el mismo director no tiene acciones sobre el director no tiene estos permisos sobre el
                            if ((int) session.getAttribute("IDUSUARIOEDITA") != myid || idmyrol == 1) {

                                String correo = request.getParameter("correo");
                                int rol = Integer.parseInt(request.getParameter("rol"));
                                int area = Integer.parseInt(request.getParameter("area"));
                                String codigo = request.getParameter("codigocorreo");
                                String roltexto = request.getParameter("roltexto");
                                String areatexto = request.getParameter("areatexto");

                                if (rol > 1 && area > 1) {

                                    if (CExpregulares.formatoCorreo(correo) == true) {
                                        // Validar que el rol y area que se reciben en verdad existen 
                                        if (daoarea.validarAreExiste(area) == true && daorol.validarRolExiste(rol) == true) {
                                            usuario.setID((int) session.getAttribute("IDUSUARIOEDITA"));
                                            usuario = daouser.infoPerfil(usuario);

                                            // Limpiar los set y get del vo para comprobar con clase limpia
                                            usuario.setID(0);
                                            usuario.setCorreo(null);
                                            usuario.setCorreoalterno(null);

                                            usuario.setID((int) session.getAttribute("IDUSUARIOEDITA"));
                                            usuario = daouser.validaIdArea(usuario);
                                            // Valida que el area del usuario pertenezca a el area del director o que sea gerente para dar permisos
                                            if (idmyarea == usuario.getArea() || idmyrol == 1) {

                                                if (!correo.equals("") && rol > 0 && area > 0) {
                                                    usuario = daouser.validarEmail(correo);

                                                    // Si los get no tienen nada se llena para dejar pasar en el proximo if para completar la validacion
                                                    if (usuario.getCorreo() == null) {
                                                        usuario.setCorreo("correopricipal");
                                                    }
                                                    if (usuario.getCorreoalterno() == null) {
                                                        usuario.setCorreoalterno("correoalterno");
                                                    }

                                                    // Valida que el correo no este en uso por otro usuario, permite la actualizacion solo si el correo que se recibe si pertenece
                                                    // a el usuario que se le va actualizar los datos o correo electronico
                                                    if (!usuario.getCorreo().equalsIgnoreCase(correo) && !usuario.getCorreoalterno().equalsIgnoreCase(correo) || usuario.getID() == (int) session.getAttribute("IDUSUARIOEDITA")) {

                                                        if (!usuario.getCorreoalterno().equals(correo)) {
                                                            if (!codigo.equalsIgnoreCase("0")) {
                                                                int codcorreoupdate = (int) session.getAttribute("CODCORREOUPDATE");
                                                                if (codigo.equals(Integer.toString(codcorreoupdate))) {
                                                                    codigo = "0";
                                                                    usuario.setCorreo(correo);
                                                                } else {
                                                                    codigo = "1";
                                                                    response.getWriter().write("codigoinvalido");
                                                                }
                                                            }
                                                            if (codigo.equals("0")) {
                                                                if (usuario.getCorreo().equals(correo)) {

                                                                    // Valida que el correo rol y area no sean nulos 
                                                                    try {
                                                                        usuario.setID((int) session.getAttribute("IDUSUARIOEDITA"));

                                                                        usuario.setCorreo(correo);
                                                                        usuario.setRol(rol);
                                                                        usuario.setArea(area);

                                                                        // Valida que la actualizacion de datos se complete y retorne true
                                                                        if (daouser.actualizarDatos(usuario, "Infotrabajo") == true) {

                                                                            CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Actualizo a:  " + usuario.getNombres() + " " + usuario.getApellidos() + " con correo " + correo + ", area  " + areatexto + " y cargo " + roltexto);
                                                                            response.getWriter().write("true");
                                                                        } else {
                                                                            response.getWriter().write("false"); // Respuesta si sale mal en la actualizacion
                                                                        }
                                                                    } catch (IOException e) {
                                                                        response.getWriter().write("false"); // Respuesta si sale mal en la actualizacion
                                                                        System.out.println("Error al actualizar la informacion de usuario en post " + e);
                                                                    }
                                                                } else {
                                                                    try {
                                                                        // SI realizo cambio de correo se debe validar enviando un codigo de verificacion
                                                                        usuario.setCorreo(correo);
                                                                        session.setAttribute("CODCORREOUPDATE", email.enviarEmail(usuario, "Validaremail"));
                                                                        usuario.setCorreo(null);

                                                                        response.getWriter().write("cambiocorreo");
                                                                    } catch (NamingException e) {
                                                                        System.out.println("Error al enviar al metodo solicitud de correo " + e);
                                                                    }
                                                                }
                                                            }

                                                        } else {
                                                            // Respuesta si el correo principal es igual a un correo alterno
                                                            response.getWriter().write("nosepuedeusar");
                                                        }
                                                    } else {
                                                        // Respueta si el correo no esta disponible porque ya lo tiene otro usuario
                                                        response.getWriter().write("correonodisponible");
                                                    }
                                                } else {
                                                    // Respuesta si hay campos vacios
                                                    response.getWriter().write("campos");
                                                }

                                            } else {
                                                response.getWriter().write("pr"); // Pr = Permisos
                                            }
                                        } else {
                                            response.getWriter().write("rolareainvalid");
                                        }
                                    } else {
                                        response.getWriter().write("correoinvalido");
                                    }
                                } else {
                                    response.getWriter().write("rolareainvalida");
                                }
                            } else {
                                response.getWriter().write("pr"); // Pr = Permisos
                            }
                        } else {
                            response.getWriter().write("pr"); // Pr = Permisos
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error en la actualizacion de datos en post usuarios " + e);
                    }

                    break;

                    default:

                }

            } else {
                System.out.println("Cierre de session en usuarios post");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();

                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (IOException | ServletException e) {
            System.out.println("Errores en usuarios post" + e);
            request.getSession().invalidate();
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
