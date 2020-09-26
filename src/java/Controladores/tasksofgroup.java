/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloDAO.CChatsDAO;
import ModeloVO.CTareas;
import ModeloDAO.CTareasDAO;
import ModeloVO.CUsuario;
import ModeloDAO.CUsuarioDAO;
import Utilidades.CAccionesdoc;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALEJANDRO
 */
public class tasksofgroup extends HttpServlet {

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
    //======================== INSTANCIAS DE TAREAS ===========================//
    CTareas tareas = new CTareas();
    CTareasDAO daotarea = new CTareasDAO();
    //======================== INSTANCIAS DE CHAT ===========================//
    CChatsDAO daochat = new CChatsDAO();
    //======================== VARIABLES ======================================//
    // IDS SOLO PARA USUARIO QUE INGRESAR AL SISTEMA
    int myid, idmyarea, idmyrol;

    int idusuario, idtarea;
    String fechainicio, fechafin;
    Date dtinicio, dtfin, dtactual; // Para convertir la sfechas de string a date y evaluar fechas.

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

        try {
            //======= VARIABLES DE SESSION ================//
            HttpSession session = request.getSession();

            try {
                myid = (int) session.getAttribute("ID");
                idmyarea = (int) session.getAttribute("IDAREA");
                idmyrol = (int) session.getAttribute("IDROL");
                request.setAttribute("miname", session.getAttribute("NOMBRE"));

            } catch (Exception e) {
                session.removeAttribute("ID");
                session.invalidate();
                System.out.println("Error en tareas  grupales se cerro la session " + e);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            // Variables para acciones en hojas html
            String accion = request.getParameter("accion"); // Accion de los botones o lo que se pase
            //======== INSTANCIA SESSION PARA EL ALMACENAR DATOS UNICOS DE USUARIO QUE INGRESA AL SISTEMA ===========/
            // String opcion = request.getParameter("opcion"); // Opcion para saber la lista de tareas que desea ver

            if (request.getParameterMap().containsKey("accion") == true) {

                if (accion.equals("Listargrupo")) {

                    if (session.getAttribute("ID") != null && session.getAttribute("IDAREA") != null && session.getAttribute("IDROL") != null && (int) session.getAttribute("IDROL") < 3) {

                        switch (accion) {

                            // ===== LISTA USUARIOS DE AREA DEL DIRECTOR DE AREA QUE INGRESO AL SISTEMA
                            case "Listargrupo": 
               
                   try {

                                if (idmyrol == 2) { // Si el rol es 2 lista usuarios que estan a cargo de un director
                                    // Lista los usuarios que pertenecen a el area del director que ingresa al sistema
                                    usuario.setArea(idmyarea);
                                    usuario.setID(myid);
                                    // listgrupo = lista que retorna con el grupo
                                    List listgrupo = daouser.listarGrupo(usuario, 2, myid);
                                    // Envia la lista a la vista = de director
                                    request.setAttribute("migrupo", listgrupo);

                                } else if (idmyrol == 1) { // Si el rol es 1 gerente lista los directores de area
                                    // Lista de solo directores de area
                                    List listdirectores = daouser.listarGrupo(usuario, 1, myid);
                                    // Envia la lista a la vista = de gerente
                                    request.setAttribute("migrupo", listdirectores);
                                }

                                // Lista los mensajes de todos
                                List listchatstr = daochat.listChats();
                                request.setAttribute("listchat", listchatstr);

                            } catch (NumberFormatException e) {
                                System.out.println("Error al listar en process " + e);
                            }

                            break;

                            default:
                                throw new AssertionError();
                        }

                        request.getRequestDispatcher("tasksofgroup.jsp").forward(request, response);

                    } else {
                        System.out.println("Cierre de session en tareas grupales ");
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
            System.out.println("Errores en tareas grupales " + e);
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

        String comentario;
        HttpSession session = request.getSession();
        //========== INSTANCIA DE LA FECHA ACTUAL  =============//
        Date fechaactual = new Date(System.currentTimeMillis());
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            if (session.getAttribute("ID") != null && session.getAttribute("IDROL") != null && idmyrol <= 2) {

                myid = (int) session.getAttribute("ID");
                idmyarea = (int) session.getAttribute("IDAREA");
                idmyrol = (int) session.getAttribute("IDROL");

                // VARIABLE PARA RECIBIR LA ACCION DE LA VISTA.
                String accion = request.getParameter("accion");

                switch (accion) {

                    case "tareasusuario":


                   try {
                        // Recibe id del usuario seleccionado.
                        idusuario = Integer.parseInt(request.getParameter("usuario"));
                        usuario.setID(idusuario);
                        // Recibe los datos del usuario que seleeciono el iddelarea de ese usuario
                        usuario = daouser.validaIdArea(usuario);

                        // Valida si el idarea del usuario selecionado pertenece a el area del que solicita
                        if (usuario.getArea() == idmyarea || myid == 1) {

                            session.setAttribute("IDUSUARIO", idusuario);
                            // Envia el is del usuario seleccionado para ver tareas de ese usuario.
                            tareas.setIddesarrolla((int) session.getAttribute("IDUSUARIO"));
                            tareas.setIdusasigna(myid);
                            // Recibe la lista que retorna con esas tareas.
                            List listTareas = daotarea.listarTareas(tareas, "1", 2);

                            if (!listTareas.isEmpty()) {
                                // Envia la lista a la vista.
                                response.getWriter().write(CAccionesdoc.crearListaJson(listTareas));
                            } else {
                                response.getWriter().write("null");
                            }
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al listar tareas " + e);
                    }

                    break;

                    case "infotarea": 
                try {

                        idtarea = Integer.parseInt(request.getParameter("tarea"));
                        // Recibe el id de la tarea seleccionada
                        // Envia el id de la tarea al set
                        tareas.setIdtarea(idtarea);
                        // Valida si la tarea pertenece a un usuario de su area
                        tareas = daotarea.validaTarea(tareas);

                        // Valida si la tarea que va ver pertenece a un usuario de su area
                        if (tareas.getIdusasigna() == myid && tareas.getIddesarrolla() == (int) session.getAttribute("IDUSUARIO")) {

                            session.setAttribute("IDTAREA", tareas.getIdtarea());
                            // Los get en tareas tendran los datos que retorno el metodo verTarea()

                            String opc = request.getParameter("opc");
                            if (opc.equalsIgnoreCase("info")) { // Informacion de la tarea
                                tareas = daotarea.verTarea(tareas);
                                response.getWriter().write(CAccionesdoc.crearObjetoJson(tareas));
                            } else if (opc.equalsIgnoreCase("com")) { // Comentarios de la tarea
                                List listcomentarios = daotarea.listarComentarios(tareas);
                                response.getWriter().write(CAccionesdoc.crearListaJson(listcomentarios));
                            }

                        } else {
                            response.getWriter().write("null");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al ver la tarea " + e);
                    }

                    break;

                    case "Filtrar":

                        List Listtareas; // = null
                        // Envia el id del usuario seleccionado para ver tareas de ese usuario.
                        String opcion = request.getParameter("opcion");
                        tareas.setIddesarrolla((int) session.getAttribute("IDUSUARIO"));
                        tareas.setIdusasigna(myid);

                        switch (opcion) {
                            case "1": // LIsta todas las tareas
                                Listtareas = daotarea.listarTareas(tareas, opcion, 2);
                                break;
                            case "2": // Lista las actuales
                                Listtareas = daotarea.listarTareas(tareas, opcion, 2);
                                break;
                            case "3": // Lista las tareas a futuro = > a fechaactual
                                Listtareas = daotarea.listarTareas(tareas, opcion, 2);
                                break;
                            case "4": // Lista las tareas que estan terminadas en 100%
                                Listtareas = daotarea.listarTareas(tareas, opcion, 2);
                                break;
                            case "5":// Lista tareas que fueron desaprobadas
                                Listtareas = daotarea.listarTareas(tareas, opcion, 2);
                                break;
                            case "6": // Lista las tareas que fueron restauradas desde el historial de 
                                Listtareas = daotarea.listarTareas(tareas, opcion, 2);
                                break;
                            default: // Lista todas
                                Listtareas = daotarea.listarTareas(tareas, "1", 2);
                        }
                        response.getWriter().write(CAccionesdoc.crearListaJson(Listtareas));

                        break;

                    case "Aprobar":

                    try {

                        if (session.getAttribute("IDTAREA") != null && idmyrol <= 2) {
                            // Envio del id de la tarea.
                            tareas.setIdtarea((int) session.getAttribute("IDTAREA"));
                            // Envia la fecha de aprobacion
                            tareas.setFechaaprobacion(dateformat.format(fechaactual));
                            if (daotarea.actualizarTarea(tareas, "Aprobacion") == true) {
                                // daotarea.eliminarTarea(tareas); // !important
                                // Envia mensaje a la vista
                                session.removeAttribute("IDTAREA");
                                response.getWriter().write("true");
                            }
                        } else {
                            response.getWriter().write("noselect");
                        }

                        // Envia al metodo y valida la accion.
                    } catch (IOException e) {
                        System.out.println("Error al filtrar ");
                    }

                    break;

                    // ==== ELIMINA TAREA SELECIONADA. 
                    case "Eliminar":
                
                try {

                        if (session.getAttribute("IDTAREA") != null && idmyrol <= 2) {
                            // Envia el id de la tera.   
                            tareas.setIdtarea((int) session.getAttribute("IDTAREA"));

                            // Valida la accion del metodo
                            if (daotarea.eliminarTarea(tareas) == true) {
                                response.getWriter().write("true");
                                // session.removeAttribute("IDTAREA");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("noselect");
                        }

                    } catch (NumberFormatException e) {
                        e.getMessage();
                    }

                    break;

                    case "Asignar": // Entra a esta accion cuando va asignar una tarea

                try {

                        //Recibe el  Id del usuario que se le agrega la tarea
                        idusuario = Integer.parseInt(request.getParameter("idus")); // Recibe el id del usuario al que se asigna la tarea

                        // Se envia este id al setter del usuario que debe desarrollar la tarea
                        tareas.setIddesarrolla(idusuario);

                        // Recibe los datos que componen la tarea
                        String nombretr = request.getParameter("nombretr"); // Nombre de la tarea
                        String caract = request.getParameter("caractr"); // Caracteristicas de la tarea
                        String anotacion = request.getParameter("anotacion");
                        String comdir = request.getParameter("comdir"); // Comentarios del director

                        // Fecha inicio y fin de latarea
                        fechainicio = request.getParameter("addtimeinicio");
                        fechafin = request.getParameter("addtimefinal");

                        // Validacion de campos
                        if (!nombretr.equals("") && !anotacion.equals("") && !caract.equals("") && !fechainicio.equals("") && !fechafin.equals("") && idusuario > 0) {

                            usuario.setID(idusuario);
                            usuario = daouser.validaIdArea(usuario);

                            if (usuario.getArea() == idmyarea || myid == 1) {

                                // Se hace conversion de fechas para validacion de tiempos
                                dtinicio = dateformat.parse(fechainicio);
                                dtfin = dateformat.parse(fechafin);
                                dtactual = dateformat.parse(dateformat.format(fechaactual));

                                if (dtinicio.after(dtactual) && dtfin.after(dtinicio) || dtfin.equals(dtinicio)) {

                                    // Pasar datos a los setter de CTareas.
                                    tareas.setNombre(nombretr);
                                    tareas.setAnotacion(anotacion);
                                    tareas.setCaracteristicas(caract);
                                    tareas.setComentarios(comdir);
                                    tareas.setFechaasignada(dateformat.format(fechaactual));
                                    tareas.setFechainicio(fechainicio);
                                    tareas.setFechafinal(fechafin);
                                    tareas.setIdusasigna(myid);

                                    // Envia al metodo el objeto con los datos y valida la accion del metodo
                                    if (daotarea.asignarTarea(tareas) == true) {
                                        response.getWriter().write("true");// Resultado del metodo
                                    } else {
                                        response.getWriter().write("false");
                                    }

                                } else if (dtinicio.before(fechaactual)) {
                                   
                                    response.getWriter().write("dtiniciofalse");// Resultado
                                } else if (dtfin.before(fechaactual) || dtfin.before(dtinicio)) {
                                    response.getWriter().write("dtfinfalse");
                                }
                            } else {
                                response.getWriter().write("notper"); // No tiene permisos para agregar esta tarea porque no el idusuario no pertenece al area de ese director
                            }

                        } else {
                            response.getWriter().write("campos");
                        }

                    } catch (ParseException ex) {
                        System.out.println("Error en la asignacion de la tarea en post " + ex);
                    }

                    break;

                    // ==== ACTUALIZAR PROPIEDADES DE LA TAREA.
                    case "Actualizar":

                try {

                        if (session.getAttribute("IDTAREA") != null) { // Validacion si una tarea fue selecionada y tiene session

                            // Recibe las propiedades de la tarea 
                            String nombre = request.getParameter("nombre");  // Nombre tarea
                            String caracteristicas = request.getParameter("caract"); // Caracteristicas
                            String anotacion = request.getParameter("anotacion"); // Comentarios director
                            fechainicio = request.getParameter("dtinicio");
                            fechafin = request.getParameter("dtfinal");

                            if (!nombre.equals("") && !caracteristicas.equals("") && !anotacion.equals("")) {
                                // Se envia el id de la tarea a actualizar
                                tareas.setIdtarea((int) session.getAttribute("IDTAREA"));

                                // Envio de datos a los set para recibir en metodo updateTarea()
                                tareas.setNombre(nombre);
                                tareas.setCaracteristicas(caracteristicas);
                                tareas.setAnotacion(anotacion);

                                // Valida que las fechas no esten vacias
                                //fechainicio = (!fechainicio.equals("")) ? fechainicio : null;
                                tareas.setFechainicio(fechainicio);

                                //fechafin = (!fechafin.equals("")) ? fechafin : null;
                                tareas.setFechafinal(fechafin);

                                // Envia el objeto con los datos y valida la accion en el metodo
                                if (daotarea.actualizarTarea(tareas, "Propiedades") == true) {
                                    response.getWriter().write("true");// Envia respuesta
                                } else {
                                    response.getWriter().write("false");
                                }

                            } else {
                                response.getWriter().write("campos");
                            }

                        } else {
                            response.getWriter().write("notselect");
                        }

                    } catch (IOException e) {
                        System.out.println("Error en post en la actualizacion de la tarea " + e);
                    }
                    break;

                    case "Comentar":

                        if (session.getAttribute("IDTAREA") != null) {

                            comentario = request.getParameter("comentario");

                            tareas.setComentarios(comentario);
                            usuario.setID(myid);
                            tareas.setIdtarea((int) session.getAttribute("IDTAREA"));

                            if (daotarea.crearComentario(tareas, usuario) == true) {
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("sintarea");
                        }

                        break;

                    case "ActualizarComentario":
                        
                         try {
                        int idcomentario = Integer.parseInt(request.getParameter("idcomentario"));
                        comentario = request.getParameter("comentario");

                        tareas.setIdcomentario(idcomentario);
                        usuario.setID(myid);

                        if (daotarea.validarComentario(tareas, usuario) == true) {
                            tareas.setComentarios(comentario);
                            if (daotarea.actualizarComentario(tareas) == true) {
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        }

                    } catch (IOException | NumberFormatException e) {
                        System.out.println("Error en post al actualizar el comentario " + e);
                    }

                    break;

                    case "Estado":
                try {
                        // Valida si en session hay una tarea ya seleccionada para dar estado
                        if (session.getAttribute("IDTAREA") != null) {
                            // Recibe el estado a asignar
                            int estado = Integer.parseInt(request.getParameter("estado"));

                            // Valida si el estado si es permitido para esa tarea.
                            if (estado == 3 || estado == 4) {
                                // Envia el estado 
                                tareas.setEstado(Integer.toString(estado));
                                // === Envia ese estado a tarea seleccionada anteriormente.

                                try {
                                    tareas.setIdtarea((int) session.getAttribute("IDTAREA"));

                                    if (daotarea.actualizarTarea(tareas, "Estado") == true) {
                                        // Envio de mensaje a la vista
                                        response.getWriter().write("true");
                                    } else {
                                        response.getWriter().write("false");
                                    }

                                } catch (IOException e) {
                                    System.out.println("Error al dar estado a la tarea " + e);
                                }

                            } else {
                                response.getWriter().write("estadofalse");
                            }
                        } else {
                            response.getWriter().write("sintarea");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error en dar estado a la tarea en post " + e);
                    }

                    break;

                    default:
                    //  throw new AssertionError();
                }

            } else {
                System.out.println("Cierre de session en tareas grupales post");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                response.getWriter().write("index");
            }

        } catch (IOException e) {
            session.invalidate();
            System.out.println("Error en tareas grupales post" + e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            response.getWriter().write("index");
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
