/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloVO.CEventos;
import ModeloDAO.CEventosDAO;
import ModeloVO.CUsuario;
import Utilidades.CAccionesdoc;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ALEJANDRO
 */
public class events extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet events</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet events at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //======================== INSTANCIAS DE USUARIO ==========================//
    CUsuario usuario = new CUsuario();
    //==================== INSTRANCIA DE EVENTOS =============================//
    CEventos evento = new CEventos();
    CEventosDAO daoevento = new CEventosDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        //==================================================================================//
        //================== EN ESTE METODO SOLO SE CREAN, ACTUALIZAN Y ELIMINAN EVENTOS =========================//

        String nmevento, fechainicio, fechafin, horainicio, horafin;
        Date dtinicio, dtfin, dtactual;

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        // Obtiene la fecha actual
        Date fechaactual = new Date(System.currentTimeMillis());
        // Para dar formato de fechas 
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        try {

            HttpSession session = request.getSession();

            int myid = (int) session.getAttribute("ID");
            // int idmyarea = (int) session.getAttribute("IDAREA");
            // int idmyrol = (int) session.getAttribute("IDROL");

            // Validacion si las sessiones no son nulas
            if (session.getAttribute("ID") != null && session.getAttribute("IDAREA") != null && session.getAttribute("IDROL") != null) {

                String accion = request.getParameter("accion");
                int idevento;

                switch (accion) {

                    case "Listeventos":

                        // Lista eventos de un usuario 
                        usuario.setID(myid);
                        List listmisevents = daoevento.listEventosUser(usuario);
                        request.setAttribute("miseventos", listmisevents);
                        
                        response.getWriter().write(CAccionesdoc.crearListaJson(listmisevents));

                        break;

                    case "Createevent": // Creacion de un evento

                try {

                        // Recibe los datos de la vista para crear evento
                        nmevento = request.getParameter("nmevento");
                        fechainicio = request.getParameter("dtinicio");
                        fechafin = request.getParameter("dtfin");
                        horainicio = request.getParameter("hrinicio");
                        horafin = request.getParameter("hrfin");

                        // Validacion de campos.
                        if (!nmevento.equals("") && !fechainicio.equals("") && !fechafin.equals("") && !horainicio.equals("") && !horafin.equals("")) {

                            // Conversion de fechas de string a date.
                            dtinicio = date.parse(fechainicio);
                            dtfin = date.parse(fechafin);
                            dtactual = date.parse(date.format(fechaactual));

                            System.out.println("Fecha inicio " + dtinicio);
                            System.out.println("Fecha final " + dtfin);
                            System.out.println("Fecha actual " + dtactual);

                            // Validacion de fechas
                            if (dtinicio.after(dtactual) && dtfin.after(dtinicio) || dtfin.equals(dtinicio)) {

                                // Envio de datos a setter
                                evento.setNombreevento(nmevento);
                                evento.setDateinicio(fechainicio);
                                evento.setDatefin(fechafin);
                                evento.setHorainicio(horainicio);
                                evento.setHorafin(horafin);
                                evento.setIdusuario(myid);

                                // Envio de objeto para metodo con datos y valida la accion en el metodo
                                if (daoevento.crearEvento(evento) == true) {
                                    // Envia la respuesta
                                    response.getWriter().write("true");
                                } else {
                                    response.getWriter().write("false");
                                }

                            } else if (dtinicio.before(dtactual)) {
                                // Envia respuesta si las fechas estan mal 
                                response.getWriter().write("dtiniciofalse");
                            } else if (dtfin.before(dtactual) || dtfin.before(dtinicio)) {
                                // Envia respuesta si las fechas estan mal 
                                response.getWriter().write("dtfinalfalse");
                            }
                        } else {
                            response.getWriter().write("campos");
                        }

                    } catch (ParseException e) {
                        System.out.println("Error al crear evento en el post: " + e);
                    }

                    break;

                    case "Updevent": // Accion para actualizar el evento

                      try {
                        // Recibe los datos para actualizar el evento
                        idevento = Integer.parseInt(request.getParameter("idev"));
                        nmevento = request.getParameter("nmevento");
                        fechainicio = request.getParameter("dtinicio");
                        fechafin = request.getParameter("dtfin");
                        horainicio = request.getParameter("hrinicio");
                        horafin = request.getParameter("hrfin");

                        if (idevento > 0 && !nmevento.equals("") && !fechainicio.equals("") && !fechafin.equals("") && !horainicio.equals("") && !horafin.equals("")) {

                            // Conversion de fechas de string a date.
                            dtinicio = date.parse(fechainicio);
                            dtfin = date.parse(fechafin);
                            dtactual = date.parse(date.format(fechaactual));

                            if (dtinicio.after(dtactual) && dtfin.after(dtinicio) || dtfin.equals(dtinicio)) {

                                evento.setIdevento(idevento);
                                evento.setNombreevento(nmevento);
                                evento.setDateinicio(fechainicio);
                                evento.setDatefin(fechafin);
                                evento.setHorainicio(horainicio);
                                evento.setHorafin(horafin);
                                usuario.setID(myid);

                                // Valida si el evento que se va actualizar pertenece al usuario que lo va actualizar
                                if (daoevento.validarEvento(evento, usuario) > 0) {
                                    // Valida si el metodo de actualizar se ejecuto bn
                                    if (daoevento.actualizarEvento(evento) == true) {
                                        // Envia respuesta
                                        response.getWriter().write("true");
                                    } else {
                                        response.getWriter().write("fase");
                                    }
                                } else {
                                    // El evento no pertenece a ese usuario
                                    response.getWriter().write("not");
                                }
                            } else if (dtinicio.before(dtactual)) {
                                // Envia respuesta si las fechas estan mal 
                                response.getWriter().write("dtiniciofalse");
                            } else if (dtfin.before(dtactual) || dtfin.before(dtinicio)) {
                                // Envia respuesta si las fechas estan mal 
                                response.getWriter().write("dtfinalfalse");
                            }
                        } else {
                            // Envia respuesta de que los campos estan vacios
                            response.getWriter().write("campos");
                        }

                    } catch (NumberFormatException | ParseException e) {
                        System.out.println("Error al actualizar el eventos en post " + e);
                    }

                    break;

                    case "Deleteeven": // Eliminar evento
                        
                        try {
                        // Recibe el id del evento a eliminar
                        idevento = Integer.parseInt(request.getParameter("idev"));

                        // Envia los datos del usuario y el id del evento para validar primero
                        usuario.setID(myid);
                        evento.setIdevento(idevento);

                        // Valida si el evento pertence a ese usuario para lograr eliminarlo
                        if (daoevento.validarEvento(evento, usuario) > 0) {
                            // Valida si el evento se elimino correctamente
                            if (daoevento.eliminarEvento(evento) == true) {
                                // Envia respuesta de que se elimino
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            // Envia respuesta por que ese evento no le pertenece
                            response.getWriter().write("not");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al eliminar el eventos en post " + e);
                    }

                    break;

                    default:
                }
            } else {
                System.out.println("Cierra sesion en ctr eventos");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error en ctr eventos" + e);
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
