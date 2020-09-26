/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloDAO.CChatsDAO;
import ModeloVO.CHistorialTareas;
import ModeloDAO.CHistorialTareasDAO;
import ModeloVO.CUsuario;
import ModeloDAO.CUsuarioDAO;
import Utilidades.CAccionesdoc;
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
public class taskshistory extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //======================== INSTANCIAS DE USUARIO ==========================//
    CUsuario usuario = new CUsuario();
    CUsuarioDAO daouser = new CUsuarioDAO();
    //======================== INSTANCIAS DE HISTORIAL DE TAREAS ==============//
    CHistorialTareas tareashistory = new CHistorialTareas();
    CHistorialTareasDAO daohistorytr = new CHistorialTareasDAO();
    //======================== VARIABLES ======================================//
    //======================== INSTANCIAS DE CHAT ===========================//
    CChatsDAO daochat = new CChatsDAO();
    // IDS SOLO PARA USUARIO QUE INGRESAR AL SISTEMA
    int myid, idmyarea, idmyrol;
    //==========================================================================//

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
        //======= VARIABLES DE SESSION ================//
        HttpSession session = request.getSession();
        // Recibir hora del sistema
        Date fechaactual = new Date(System.currentTimeMillis());
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        // Variables para acciones en hojas html
        //========================== ASIGNACION AL VALOR DE LAS VARIABLES CON DATOS DEL USUARIO  ============================//
        try {
            //======== INSTANCIA SESSION PARA EL ALMACENAR DATOS UNICOS DE USUARIO QUE INGRESA AL SISTEMA ===========/

            try {
                myid = (int) session.getAttribute("ID");
                idmyarea = (int) session.getAttribute("IDAREA");
                idmyrol = (int) session.getAttribute("IDROL");
                request.setAttribute("miname", session.getAttribute("NOMBRE"));

            } catch (Exception e) {
                session.removeAttribute("ID");
                session.invalidate();
                System.out.println("Error en historial de tareas se cerro la session " + e);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            if (session.getAttribute("ID") != null && session.getAttribute("IDAREA") != null && session.getAttribute("IDROL") != null) {

                String accion = request.getParameter("accion"); // Accion de los botones o lo que se pase

                if (request.getParameterMap().containsKey("accion") == true) {

                    if (accion.equals("Listargrupo") || accion.equals("ListTareas")) {

                        switch (accion) {

                            case "Listargrupo": // SI EL ROL ES GERENTE O DIRECTOR INICIA PRIMERO DESDE ESTA LINEA LISTANDO USUARIOS A CARGO

                       try {                                // Lista los usuarios que pertenecen al area con idmyarea = mi area.
                                usuario.setArea(idmyarea);

                                switch (idmyrol) {
                                    case 1:

                                        // Lista de directores de area
                                        List listdirectores = daouser.listarGrupo(null, 1, 0);
                                        // Lista de gerentes
                                        List listgerentes = daouser.listarGrupo(usuario, 2, 0);

                                        // A la lista de gerentes se agrega la lista de directores
                                        listgerentes.addAll(listdirectores);
                                        // Se envia la lista a la vista = de gerente
                                        request.setAttribute("listmigrupo", listgerentes);

                                        break;

                                    case 2:

                                        // listagrupo = la lista que retorna el metodo listGrup con los usuarios de esa area
                                        List listagrupo = daouser.listarGrupo(usuario, 2, 0);
                                        // Se envia la lista a la vista = de director
                                        request.setAttribute("listmigrupo", listagrupo);

                                        break;

                                    default:
                                        break;
                                }

                                // Lista los mensajes de todos
                                List listchatstr = daochat.listChats();
                                request.setAttribute("listchat", listchatstr);

                            } catch (Exception e) {
                                System.out.println("Error al listar el grupo en el historial de tareas" + e);
                            }

                            break;

                        }
                        request.getRequestDispatcher("taskhistory.jsp").forward(request, response);

                    } else {
                        request.getRequestDispatcher("error404.jsp").forward(request, response);
                    }

                } else {
                    request.getRequestDispatcher("error404.jsp").forward(request, response);
                }

            } else {
                System.out.println("Cierre de session en historial de tareas process");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error en en historial de tareas process " + e);
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

        HttpSession session = request.getSession();
        String accion = request.getParameter("accion");
        try {
            myid = (int) session.getAttribute("ID");
            idmyarea = (int) session.getAttribute("IDAREA");
            idmyrol = (int) session.getAttribute("IDROL");
            request.setAttribute("miname", session.getAttribute("NOMBRE"));

        } catch (Exception e) {
            session.invalidate();
            System.out.println("Error en historial de tareas se cerro la session " + e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            response.getWriter().write("index");
        }
        switch (accion) {

            case "tareasusuario":

              try {
                // Si el rol es mayor  a 2 debe listar las tareas personales de el ya que no tiene grupo a cargo
                if (idmyrol > 2) {
                    tareashistory.setIdUsdesarrollo(myid);
                } else if (idmyrol < 3) {
                    // Si el rol es menor a 3 si tiene grupo a cargo
                    // razon de que si debe recibir un usuario para mostrarle las tareas de ese usuario que seleeciono
                    session.setAttribute("IDUSUARIO", Integer.parseInt(request.getParameter("usuario")));

                    usuario.setID((int) session.getAttribute("IDUSUARIO"));
                    usuario = daouser.validaIdArea(usuario);
                    if (usuario.getArea() == idmyarea || myid == 1) {
                        tareashistory.setIdUsdesarrollo((int) session.getAttribute("IDUSUARIO"));
                    }
                }
                // Para ambos roles el id del usuario que creo o asigno esa tarea es el mismo usuario que ingresa al sistema
                // se envia ese id para que lista las que asigno el mismo
                tareashistory.setIdusasigna(myid);

                // Recibe la lista de tareas y la envia a la vist
                List listhisroy = daohistorytr.listaHistorial(tareashistory);
                if (!listhisroy.isEmpty()) {
                    response.getWriter().write(CAccionesdoc.crearListaJson(listhisroy));
                } else {
                    response.getWriter().write("null");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error al listar historial de tareas en post: " + e);
            }

            break;

            case "VerTarea":

         try {

                int tarea = Integer.parseInt(request.getParameter("tarea"));

                if (daohistorytr.validarTarea(tarea, myid) == true) {

                    tareashistory.setIdtarea(tarea);
                    tareashistory = daohistorytr.verTarea(tareashistory);

                    System.out.println();

                    session.setAttribute("IDTAREA", tareashistory.getIdtarea());
                    response.getWriter().write(CAccionesdoc.crearObjetoJson(tareashistory));
                } else {
                    System.out.println("No le pertenece");
                }

                // Se envia a ls vista las caracteristicas de la tarea
            } catch (NumberFormatException e) {
                System.out.println("Error al ver la tarea del historial en post " + e);
            }

            break;
            case "Eliminar":

            try {

                if (session.getAttribute("IDTAREA") != null) {
                    if (daohistorytr.validarTarea((int) session.getAttribute("IDTAREA"), myid) == true) {
                        tareashistory.setIdtarea((int) session.getAttribute("IDTAREA"));
                        if (daohistorytr.eliminarHistorial(tareashistory) == true) {
                            session.removeAttribute("IDTAREA");
                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("false");
                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("Error al eliminar la tarea en process de historial de tareas " + e);
            }

            break;

            case "Restaurar":
                
            try {

                if (session.getAttribute("IDTAREA") != null) {
                    if (daohistorytr.validarTarea((int) session.getAttribute("IDTAREA"), myid) == true) {
                        tareashistory.setIdtarea((int) session.getAttribute("IDTAREA"));
                        if (daohistorytr.restaurarTarea(tareashistory) == true) {
                            session.removeAttribute("IDTAREA");
                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("false");
                        }
                    }

                }
            } catch (IOException e) {
                System.out.println("Error al eliminar la tarea en process de historial de tareas " + e);
            }

            break;

            default:
                
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
