/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloDAO.CChatsDAO;
import ModeloDAO.CNotasDAO;
import ModeloVO.CTareas;
import ModeloDAO.CTareasDAO;
import ModeloVO.CUsuario;
import ModeloVO.CNotas;
import Utilidades.CAccionesdoc;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALEJANDRO
 */
public class tasks extends HttpServlet {

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
    //======================== INSTANCIAS DE TAREAS ===========================//
    CTareas tareas = new CTareas();
    CTareasDAO daotarea = new CTareasDAO();
    //======================== INSTANCIAS DE CHAT ===========================//
    CChatsDAO daochat = new CChatsDAO();
    //======================== INSTANCIAS DE BLOC DE NOTAS ===========================//
    CNotas nota = new CNotas();
    CNotasDAO daonota = new CNotasDAO();

    //======================== VARIABLES ======================================//
    // IDS SOLO PARA USUARIO QUE INGRESAR AL SISTEMA
    int myid, idmyarea, idmyrol;

    // Variables para demas uso de objetos
    int idusuario, idtarea;
    String fechainicio, fechafin;
    Date dateinicio, datefin; // Para convertir la sfechas de string a date y evaluar fechas.
    //==========================================================================//

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

        //=== METODO SOLO PARA VER USUARIOS, TAREAS DE USUARIO Y PROPIEDADES DE UNA TAREA O DAR ESTADOS ====/
        //========================== ASIGNACION AL VALOR DE LAS VARIABLES CON DATOS DEL USUARIO  ============================//
        try {
            //======== INSTANCIA SESSION PARA EL ALMACENAR DATOS UNICOS DE USUARIO QUE INGRESA AL SISTEMA ===========/
            HttpSession session = request.getSession();
            
            try {
                myid = (int) session.getAttribute("ID");
                idmyarea = (int) session.getAttribute("IDAREA");
                idmyrol = (int) session.getAttribute("IDROL");
                request.setAttribute("miname", session.getAttribute("NOMBRE"));
                
            } catch (Exception e) {
                session.removeAttribute("ID");
                session.invalidate();
                System.out.println("Error en tareas se cerro la session " + e);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                response.getWriter().write("index");
            }
            
            try {
                
                if (session.getAttribute("ID") != null && session.getAttribute("IDAREA") != null && session.getAttribute("IDROL") != null) {

                    // Variables para acciones en hojas html
                    String accion = request.getParameter("accion"); // Accion de los botones o lo que se pase

                    if (request.getParameterMap().containsKey("accion") == true) {
                        
                        if (accion.equals("VerTarea") || accion.equals("ListarTareas")) {
                            
                            switch (accion) {
                                case "ListarTareas": // Lista las tareas del usuario que ingreso al sistema 
                     
                       try {

                                    // Se envia al set myid del usuario que ingreso al sistema para listar sus tareas
                                    tareas.setIddesarrolla(myid);
                                    tareas.setIdusasigna(myid);
                                    
                                    List listTareas = null;
                                    
                                    if (myid > 1) {
                                        // Si el id del us es mayor a 1 tiene las opciones de estos listados
                                        // Recibe la lista que recive de los metodo
                                        listTareas = daotarea.listarTareas(tareas, "1", 1);
                                    } else if (myid == 1) { // Si el id es del gerente solo recibe la lista personal ya que nadie le asigna tareas a el
                                        listTareas = daotarea.listarTareas(tareas, "1", 2);
                                    }
                                    // Se envia al htmlla opcion que se desea de ala lista para seguir mostrando esas mismas sin que el no solicite cambio
                                    // Se envia la lista de tareas a la vista
                                    request.setAttribute("mistareas", listTareas);

                                    // Lista los mensajes de todos
                                    List listchatstr = daochat.listChats();
                                    request.setAttribute("listchat", listchatstr);

                                    // Lista las notas del usuario con el id del usuario que ingreso al sistema
                                    usuario.setID(myid);
                                    // Recibe la lista de las notas que le pertenecen
                                    List listnotas = daonota.listaNotas(usuario);
                                    // Envia la lista de notas a la vista
                                    request.setAttribute("listnotas", listnotas);
                                    
                                } catch (NumberFormatException e) {
                                    System.out.println("Error al listar las tareas " + e);
                                }
                                
                                break;
                                
                                default:
                                //   throw new AssertionError();
                            }
                            request.getRequestDispatcher("mytasks.jsp").forward(request, response);
                            
                        } else {
                            request.getRequestDispatcher("error404.jsp").forward(request, response);
                        }
                        
                    } else {
                        request.getRequestDispatcher("error404.jsp").forward(request, response);
                    }
                    
                } else {
                    System.out.println("Cierre de session en tareas process");
                    session.removeAttribute("ID");
                    session.removeAttribute("NOMBRE");
                    session.removeAttribute("IDROL");
                    session.removeAttribute("IDAREA");
                    session.invalidate();
                    request.getSession().invalidate();
                    response.getWriter().write("index");
                }
                
            } catch (IOException | NumberFormatException | ServletException e) {
                request.getRequestDispatcher("error404.jsp").forward(request, response);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Error en tareas process" + e);
            request.getSession().invalidate();
            request.getRequestDispatcher("index.jsp").forward(request, response);
            response.getWriter().write("index");
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
        //============ METODO SOLO PARA ENVIAR DATOS DE TAREAS ==========//
        HttpSession session = request.getSession();
        try {

            //=========== SESSIONES ==============//
            myid = (int) session.getAttribute("ID");
            idmyarea = (int) session.getAttribute("IDAREA");
            idmyrol = (int) session.getAttribute("IDROL");
            
            if (session.getAttribute("ID") != null || session.getAttribute("IDAREA") != null || session.getAttribute("IDROL") != null) {

                // LISTA PARA RETORNAR EN FORMATO A JSON A JS
                Date fechaactual = new Date(System.currentTimeMillis());
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

                // Variables sobre las tareas y notas
                int idnota = 0;
                String nombre, anotacion, caracteristicas, comentario, fechainicial, fechafinal;
                String accion = request.getParameter("accion");
                
                switch (accion) {
                    case "Comentar": // Crear comentarios sobre una tarea

                      try {

                        // SE envia el id de la tarea que seleeciono para agregarle comentarios y para validar que esa tarea le pertenezca a ese usuario
                        tareas.setIdtarea((int) session.getAttribute("IDMYTAREA"));

                        // Recibe el id del usuario que tiene la tarea que se solicito ver
                        tareas = daotarea.validaTarea(tareas);
                        System.out.println("aca llega");

                        // Comprueba que el id de la tarea no este nulo
                        if (session.getAttribute("IDMYTAREA") != null) {

                            // Comprueba si el el id del usuario que tiene esa tarea es igual al id del usuario que solicita verla
                            if (tareas.getIddesarrolla() == myid) {
                                // Recibe los comentarios del usuario 
                                comentario = request.getParameter("comments");
                                tareas.setComentarios(comentario);
                                usuario.setID(myid);

                                // Se envia al metodo el objeto con sus datos y valida la accion del metodo
                                if (daotarea.crearComentario(tareas, usuario) == true) {
                                    // Resultado del metodo
                                    response.getWriter().write("true");
                                } else {
                                    response.getWriter().write("false");
                                }
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("noselecciono");
                        }
                        
                    } catch (IOException e) {
                        e.getMessage();
                    }
                    
                    break;
                    
                    case "Addtareapersonal": // Solo tareas personales que se agrega el usuario mismo.
                
                try {

                        // Recibe los datos de la nota que va crear
                        nombre = request.getParameter("nombretarea"); // Nombre de la tarea
                        fechainicial = request.getParameter("fechainicio"); // Cuando inicia la tarea
                        fechafinal = request.getParameter("fechafinal"); // Cuando finaliza la tarea
                        anotacion = request.getParameter("anotacion"); // Anotacion de la tarea 
                        caracteristicas = request.getParameter("caracteristicas");

                        // Envia los datos
                        tareas.setNombre(nombre);
                        tareas.setFechaasignada(dateformat.format(fechaactual));
                        tareas.setFechainicio(fechainicial);
                        tareas.setFechafinal(fechafinal);
                        tareas.setAnotacion(anotacion);
                        tareas.setCaracteristicas(caracteristicas);
                        tareas.setIdusasigna(myid);
                        tareas.setIddesarrolla(myid);

                        // Envia el objeto con los datos y comprueba que se cree
                        if (daotarea.crearTareaPersonal(tareas) == true) {
                            response.getWriter().write("true");// Envia respuesta
                        } else {
                            response.getWriter().write("false");
                        }
                        
                    } catch (IOException e) {
                        System.out.println("Error ctr al agregar la tarea" + e);
                    }
                    break;
                    
                    case "Archivar": // Envia la tarea seleccionada del usuario al historial

                        try {
                        
                        tareas.setIdtarea((int) session.getAttribute("IDMYTAREA"));
                        tareas.setIdusasigna(myid);
                        
                        tareas = daotarea.validaTarea(tareas);
                        
                        if (tareas.getIdusasigna() == myid) {
                            
                            if (session.getAttribute("IDMYTAREA") != null) {
                                if (daotarea.actualizarTarea(tareas, "Archivar") == true) {
                                    response.getWriter().write("true");
                                } else {
                                    response.getWriter().write("false");
                                }
                            } else {
                                response.getWriter().write("sintarea");
                            }
                        }
                        
                    } catch (IOException e) {
                        System.out.println("Error al archivar la tarea en post " + e);
                    }
                    
                    break;
                    
                    case "Eliminatarea": // Accion para eliminar tareas
                        
                        try {

                        // Envia el id de la tarea a eliminar que fue una selecionada
                        tareas.setIdtarea((int) session.getAttribute("IDMYTAREA"));
                        // Trae el id del usuario que la creo
                        tareas = daotarea.validaTarea(tareas);

                        // Valida si el id del usuario que retorno la tarea es igual al id del usuario que la desea eliminar
                        if (tareas.getIdusasigna() == myid) {
                            // Si la tarea le pertenece la puede eliminar
                            if (daotarea.eliminarTarea(tareas) == true) {
                                response.getWriter().write("true");// Envia respuesta de que si se elimino
                            } else {
                                response.getWriter().write("false");// Envia respuesta de que no se elimino
                            }
                        } else {
                            response.getWriter().write("notpertenece");// Envia respuesta de que la tarea no la puede eliminar
                        }
                        
                    } catch (IOException e) {
                        System.out.println("Error al eliminar la tarea en post" + e);
                    }
                    
                    break;
                    
                    case "Actualizarmitr": // Actualizacion de la tarea del usuario 
                        
                        try {
                        
                        tareas.setIdtarea((int) session.getAttribute("IDMYTAREA"));
                        
                        tareas = daotarea.validaTarea(tareas);
                        
                        if (tareas.getIdusasigna() == myid) {
                            // Recibe los datos de la nota que va crear
                            nombre = request.getParameter("nombrenew"); // Nombre de la tarea
                            anotacion = request.getParameter("anotacionnew"); // Anotacion de la tarea 
                            caracteristicas = request.getParameter("caractenew");
                            fechainicial = request.getParameter("fechainicionew"); // Cuando inicia la tarea
                            fechafinal = request.getParameter("fechafinnew"); // Cuando finaliza la tarea

                            tareas.setNombre(nombre);
                            tareas.setAnotacion(anotacion);
                            tareas.setCaracteristicas(caracteristicas);
                            tareas.setFechainicio(fechainicial);
                            tareas.setFechafinal(fechafinal);
                            
                            if (daotarea.actualizarTarea(tareas, "Propiedades") == true) {
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                            
                        } else {
                            response.getWriter().write("nopermisos");
                        }
                        
                    } catch (IOException e) {
                        System.out.println("Error al actualizar la tarea personal en post " + e);
                    }
                    
                    break;
                    
                    case "Vertarea": // CODIGO PARA TRAER DATOS DE DB Y ENVIARLOS A JS EN FORMATO JSON
                        
                          try {
                        // Recibe el id de la tarea que selecciono el usuario.

                        idtarea = Integer.parseInt(request.getParameter("mytarea"));
                        // Envia al setter el id de la tarea para validar
                        tareas.setIdtarea(idtarea);

                        // Recibe el id del usuario que tiene la tarea que se solicito ver
                        tareas = daotarea.validaTarea(tareas);

                        // Comprueba si el el id del usuario que tiene esa tarea es igual al id del usuario que solicita verla
                        if (tareas.getIddesarrolla() == myid) {
                            
                            session.setAttribute("IDMYTAREA", tareas.getIdtarea());
                            tareas = daotarea.verTarea(tareas);
                            
                            response.getWriter().write(CAccionesdoc.crearObjetoJson(tareas));
                            
                        } else {
                               response.getWriter().write("null");
                        }
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error al ver la tarea en process se recibe " + e);
                    }
                    
                    break;
                    
                    case "Comentarios":

                        try {
                        
                        tareas.setIdtarea((int) session.getAttribute("IDMYTAREA"));
                        
                        List listcomentarios = daotarea.listarComentarios(tareas);
                        response.getWriter().write(CAccionesdoc.crearListaJson(listcomentarios));
                        
                    } catch (IOException e) {
                        System.out.println("Error en listar los comentarios en post " + e);
                    }
                    
                    break;
                    
                    case "ActualizarComentario":
                        
                        try {
                        int idcomentario = Integer.parseInt(request.getParameter("idcomentario"));
                        comentario = request.getParameter("comentario");
                        
                        tareas.setIdcomentario(idcomentario);
                        usuario.setID(myid);

                        // Valida que esa tarea le pertenezca igual que el comentario
                        if (daotarea.validarComentario(tareas, usuario) == true) {
                            tareas.setComentarios(comentario);
                            if (daotarea.actualizarComentario(tareas) == true) {
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        }
                        
                    } catch (IOException | NumberFormatException e) {
                        System.out.println("Error en post al actualizar el comentario en post " + e);
                    }
                    
                    break;

                    //====================  CODIGOS PARA EL BLOC DE NOTAS ==================//
                    case "Crearnota": // Crear nota
                                
                    try {

                        // Recibe los datos del chat //
                        nombre = request.getParameter("nombrenota"); // Nombre de la nota
                        anotacion = request.getParameter("textonota"); // Anotacion de la nota

                        // Comprueba que los datos de la nota nueva no esten vacios
                        if (!nombre.equals("") && !anotacion.equals("")) {
                            
                            nota.setIdusuario(myid);
                            nota.setNombrenota(nombre);
                            nota.setTextonota(anotacion);
                            
                            if (daonota.crearNota(nota) == true) {
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("campos");
                        }
                        
                    } catch (IOException e) {
                        System.out.println("Error al crear la nota en post " + e);
                    }
                    
                    break;
                    
                    case "Listarnotas":
                        
                        usuario.setID(myid);
                        List listnotas = daonota.listaNotas(usuario);
                        response.getWriter().write(CAccionesdoc.crearListaJson(listnotas));
                        
                        break;
                    
                    case "Vernota":
                        
                        idnota = Integer.parseInt(request.getParameter("nota"));
                        
                        nota.setIdnota(idnota);
                        nota.setIdusuario(myid);
                        
                        if (daonota.validarNota(nota) == true) {
                            nota = daonota.verTextoNota(idnota);
                            response.getWriter().write(CAccionesdoc.crearObjetoJson(nota));
                        } else {
                            response.getWriter().write("nopertenece");
                        }
                        
                        break;
                    
                    case "Actualizanota": // Actualizacion de una nota

                try {

                        // Recibe los datos de la nota a actualizar
                        idnota = Integer.parseInt(request.getParameter("idenota"));
                        nombre = request.getParameter("nombrenota"); // Nombre de la nota
                        anotacion = request.getParameter("textonota"); // Anotacion de la nota

                        // Envia el id de la nota y el id del usuario para validacion y actualizacion
                        nota.setIdnota(idnota);
                        nota.setIdusuario(myid);

                        // Compruena que los datos no lleguen nulos
                        if (idnota > 0 && !nombre.equals("") && !anotacion.equals("")) {
                            // Valida que la nota pertenezca a ese usuario que solicita actualizacion
                            if (daonota.validarNota(nota) == true) {

                                // Si le pertence  procede a la actualizacion de la nota
                                nota.setNombrenota(nombre);
                                nota.setTextonota(anotacion);

                                // Envia el objeto con los datos y el comprueba que se cumpla
                                if (daonota.actualizarNota(nota) == true) {
                                    response.getWriter().write("true");// Envia la respuesta
                                } else {
                                    response.getWriter().write("false");
                                }
                            } else {
                                response.getWriter().write("invalid");
                            }
                        } else {
                            response.getWriter().write("campos");
                        }
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error al actualizar la nota en el post " + e);
                    }
                    
                    break;
                    
                    case "Eliminanota":
                
                try {
                        // Recibe el id de la nota a eliminar
                        idnota = Integer.parseInt(request.getParameter("idenota"));
                        
                        if (idnota > 0) { // Comprueba que si llegue id

                            // Envia los datos de la nota para validar y eliminar
                            nota.setIdnota(idnota);
                            nota.setIdusuario(myid);

                            // Valida que la nota a eliminar le pertenezca a el usuario que la va eliminar
                            if (daonota.validarNota(nota) == true) {
                                // Elimina la nota y comprueba que se cumpla
                                if (daonota.eliminarNota(nota) == true) {
                                    response.getWriter().write("true");// Envia la respuesta
                                } else {
                                    response.getWriter().write("false");
                                }
                            } else {
                                response.getWriter().write("false");
                            }
                            
                        } else {
                            response.getWriter().write("notnota");// No se envio id de la nota
                        }
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error al eliminar la nota en post " + e);
                    }
                    
                    break;
                    
                    case "Filtrar":
                        String opcion = request.getParameter("opcion");
                        tareas.setIddesarrolla(myid);
                        tareas.setIdusasigna(myid);
                        
                        List listTareas = null;
                        
                        if (myid > 1) {  // Si el id del us es mayor a 1 tiene las opciones de estos listados

                            switch (opcion) {
                                // Recibe la lista que recive de los metodo
                                case "1": // Lista todas
                                    listTareas = daotarea.listarTareas(tareas, opcion, 1);
                                    break;
                                case "2": // Lista actuales
                                    listTareas = daotarea.listarTareas(tareas, opcion, 1);
                                    break;
                                case "3": // Lista a futuro
                                    listTareas = daotarea.listarTareas(tareas, opcion, 1);
                                    break;
                                case "4": // Lista terminadas
                                    listTareas = daotarea.listarTareas(tareas, opcion, 1);
                                    break;
                                case "5": // Lista desaprobadas
                                    listTareas = daotarea.listarTareas(tareas, opcion, 1);
                                    break;
                                case "6": // Lista restauradass
                                    listTareas = daotarea.listarTareas(tareas, opcion, 1);
                                    break;
                                case "7": // Lista personales
                                    listTareas = daotarea.listarTareas(tareas, "1", 2);
                                    break;
                                default: // Si no encuentra opcion lista todas las asignadas
                                    listTareas = daotarea.listarTareas(tareas, "1", 1);
                            }
                            
                        } else if (myid == 1) { // Si el id es del gerente solo recibe la lista personal ya que nadie le asigna tareas a el
                            listTareas = daotarea.listarTareas(tareas, "1", 2);
                        }
                        
                        response.getWriter().write(CAccionesdoc.crearListaJson(listTareas));
                        
                        break;
                    
                    default:
                    // throw new AssertionError();
                }
                
            } else {
                System.out.println("Cierre de session en tareas post");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            System.out.println("Error en tareas en tareas post" + e);
            session.invalidate();
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
