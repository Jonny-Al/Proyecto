/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloDAO.CChatsDAO;
import ModeloVO.CReuniones;
import ModeloDAO.CReunionesDAO;
import ModeloVO.CUsuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALEJANDRO
 */
public class meetings extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    int myid, idmyrol;

    // Variables para demas uso de objetos
    String fechainicio, fechafin, horainicio, horafin;
    Date dtinicio, dtfin, dtactual; // Para convertir la sfechas de string a date y evaluar fechas.
    //==========================================================================//
    //======================== INSTANCIAS DE USUARIO ==========================//
    CUsuario usuario = new CUsuario();
    //======================== INSTANCIAS DE REUNIONES ======================//
    CReuniones reunion = new CReuniones();
    CReunionesDAO daoreunion = new CReunionesDAO();
    //======================== INSTANCIAS DE CHAT ===========================//
    CChatsDAO daochat = new CChatsDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            //======== INSTANCIA SESSION PARA EL ALMACENAR DATOS UNICOS DE USUARIO QUE INGRESA AL SISTEMA ===========/

            HttpSession session = request.getSession();

            try {
                myid = (int) session.getAttribute("ID");
                request.setAttribute("miname", session.getAttribute("NOMBRE"));
            } catch (Exception e) {
                session.removeAttribute("ID");
                session.invalidate();
                System.out.println("Error en reuniones se cerro session" + e);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            String menu = request.getParameter("menu");

            try {

                if (request.getParameterMap().containsKey("menu") == true) {

                    if (menu.equals("Salajuntas")) {
                        // Validacion de sessiones
                        if (session.getAttribute("ID") != null && session.getAttribute("IDROL") != null) {

                            // Lista los mensajes de todos
                            List listchatstr = daochat.listChats();
                            request.setAttribute("listchat", listchatstr);

                            // Envia al setIdusuariosepara myid para ver mis reuniones = las del usuario que ingresa al sistema
                            usuario.setID(myid);
                            // Lista las reuniones que estan separas con myid en el MODAL
                            List listreuniones = daoreunion.listReuniones(usuario);
                            request.setAttribute("reuniones", listreuniones);

                            // List para mostrar las asignaciones de todos en el calendario
                            List listespacio = daoreunion.listAsignaciones();
                            request.setAttribute("espacios", listespacio);

                            request.getRequestDispatcher("meetings.jsp").forward(request, response);

                        } else {
                            System.out.println("Cierra sesion en ctr reuniones request");
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

            } catch (IOException | ServletException e) {
                System.out.println("Errores en ctr reuniones " + e);
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error en ctr reuniones " + e);
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

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try {

            HttpSession session = request.getSession();
            String nombrereunion, comentariosreunion;

            // Variables 
            myid = (int) session.getAttribute("ID");

            if (session.getAttribute("ID") != null && session.getAttribute("IDROL") != null) {

                int idreunion;

                // Obtiene la fecha actual
                Date fechaactual = new Date(System.currentTimeMillis());
                // Para dar formato de fechas 
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

                String accion = request.getParameter("accion"); // Accion de los botones o lo que se pase

                switch (accion) {
                    case "Asignar":

                try {
                        // Recibe los datos del html para la asignacion de la reunion.
                        nombrereunion = request.getParameter("namereunion");
                        fechainicio = request.getParameter("dteinicio");
                        fechafin = request.getParameter("dtefin");
                        horainicio = request.getParameter("timeinicio");
                        horafin = request.getParameter("timefin");
                        comentariosreunion = request.getParameter("comentarios");

                        try {

                            // Conversion de fchas a formato date
                            dtinicio = date.parse(fechainicio);
                            dtfin = date.parse(fechafin);
                            dtactual = date.parse(date.format(fechaactual));

                            // Validacion de campos
                            if (!nombrereunion.equals("") && !fechainicio.equals("") && !fechafin.equals("") && !horainicio.equals("") && !horafin.equals("")) {

                                // Validacion de fechas
                                if (dtinicio.equals(dtactual) || dtinicio.after(dtactual) && dtfin.equals(dtinicio) || dtfin.after(dtactual) || dtfin.after(dtinicio)) {

                                    // Envio de datos a los setter
                                    reunion.setNombrereunion(nombrereunion);
                                    reunion.setFechainicio(fechainicio);
                                    reunion.setFechafinal(fechafin);
                                    reunion.setHorainicio(horainicio);
                                    reunion.setHorafin(horafin);
                                    reunion.setComentarios(comentariosreunion);
                                    reunion.setIdUsuarioseparo(myid);

                                    // Envia al metodo el objeto con sus datos  y valida el procedimiento en el metodo
                                    if (daoreunion.crearReunion(reunion) == true) {
                                        // rs = resultado de la accion
                                        response.getWriter().write("true");
                                    } else {
                                        response.getWriter().write("false");
                                    }
                                } else if (dtinicio.before(fechaactual)) {
                                    response.getWriter().write("dtiniciofalse");
                                } else if (dtfin.before(fechaactual) || dtfin.before(dtinicio)) {
                                    response.getWriter().write("dtfinfalse");
                                }
                            } else {
                                response.getWriter().write("campos");
                            }

                        } catch (ParseException e) {
                            e.getMessage();
                        }

                    } catch (NumberFormatException e) {
                        e.getMessage();
                    }

                    break;

                    case "Actualizar":

                    try {

                        // Recibe los datos del html para la asignacion de la tarea
                        nombrereunion = request.getParameter("nameupd");
                        fechainicio = request.getParameter("dtinicioupd");
                        fechafin = request.getParameter("dtfinupd");
                        horainicio = request.getParameter("hrinicioupd");
                        horafin = request.getParameter("hrfinupd");
                        comentariosreunion = request.getParameter("comentarios");

                        dtinicio = date.parse(fechainicio);
                        dtfin = date.parse(fechafin);
                        dtactual = date.parse(date.format(fechaactual));

                        // Validacion de fechas
                        if (dtinicio.equals(dtactual) || dtinicio.after(dtactual) && dtfin.equals(dtinicio) || dtfin.after(dtactual) || dtfin.after(dtinicio)) {

                            try {
                                // Recibe el id de la reunion a actualizar
                                idreunion = Integer.parseInt(request.getParameter("idreunion"));

                                // Envio de daros a los setter
                                reunion.setIdReunion(idreunion);
                                reunion.setIdUsuarioseparo(myid);

                                // Valida si la reunion pertenece al usuario que la va actualizar
                                if (daoreunion.validaReunionUser(reunion) == true) {
                                    // Envia los datos recibidos para la actualizacion
                                    reunion.setNombrereunion(nombrereunion);
                                    reunion.setFechainicio(fechainicio);
                                    reunion.setFechafinal(fechafin);
                                    reunion.setHorainicio(horainicio);
                                    reunion.setHorafin(horafin);
                                    reunion.setComentarios(comentariosreunion);

                                    // Envia al metodo el obj con los datos y valida si se actualizo
                                    if (daoreunion.actualizarReunion(reunion) == true) {
                                        // Envia respuesta de que se actualizo correctamente
                                        response.getWriter().write("true");
                                    } else {
                                        // Envia respuesta de que no se actualizo
                                        response.getWriter().write("false");
                                    }
                                } else {
                                    // Envia respuesta de que la reunion no le pertenece
                                    response.getWriter().write("nopertenece");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Error al actuaizar la reunion en post: " + e);
                                response.getWriter().write("false");
                            }

                        } else if (dtinicio.before(fechaactual)) {
                            response.getWriter().write("dtiniciofalse");// Envia respuesta de que la fecha es invalida
                        } else if (dtfin.before(fechaactual) || dtfin.before(dtinicio)) {
                            response.getWriter().write("dtfinfalse");// Envia respuesta de que la fecha es invalida
                        }

                    } catch (ParseException e) {
                        e.getMessage();
                    }

                    break;

                    case "Eliminar":

            try {
                        // Recibe el id de la reunuion a eliminar
                        idreunion = Integer.parseInt(request.getParameter("idreunion"));

                        // Envia al set el id de la reunion
                        reunion.setIdReunion(idreunion);
                        reunion.setIdUsuarioseparo(myid);

                        if (daoreunion.validaReunionUser(reunion)) {

                            // Envia el objeto al metodo con sus datos y valida la accion en el metodo
                            if (daoreunion.eliminarReunion(reunion) == true) {
                                // Evnia respuesta 
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("nopertenece");
                        }
                    } catch (NumberFormatException e) {
                        e.getMessage();
                    }

                    break;
                    default:
                }
            } else {
                System.out.println("Cierre sesion en ctr reuniones post");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (IOException e) {

            System.out.println("Error en  reuniones post");
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
