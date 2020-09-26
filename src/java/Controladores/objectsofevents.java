/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Utilidades.CAccionesdoc;
import CargasMasivas.CLeerExcel;
import ModeloDAO.CChatsDAO;
import ModeloDAO.CEstadosDAO;
import ModeloVO.CEventos;
import ModeloDAO.CEventosDAO;
import ModeloVO.CImplementos;
import ModeloDAO.CImplementosDAO;
import ModeloDAO.CModificacionDAO;
import ModeloVO.CUsuario;
import Reportes.CReportexcel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class objectsofevents extends HttpServlet {

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
    //======================= INSTANCIAS DE IMPLEMENTOS  ======================//
    CImplementos objetos = new CImplementos();
    CImplementosDAO daoobjetos = new CImplementosDAO();
    //=====================  INSTANCIAS DE ESTADOS ============================//
    CEstadosDAO daoestado = new CEstadosDAO();
    //==================== INSTRANCIA DE EVENTOS =============================//
    CEventos evento = new CEventos();
    CEventosDAO daoevento = new CEventosDAO();
    //======================== INSTANCIAS DE CHAT ===========================//
    CChatsDAO daochat = new CChatsDAO();
    //======================== VARIABLES ======================================//
    // IDS SOLO PARA USUARIO QUE INGRESAR AL SISTEMA
    int myid, idmyrol, idobjeto, idevento;
    String dateinicio, datefin;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

        try {
            //======== INSTANCIA SESSION PARA EL ALMACENAR DATOS UNICOS DE USUARIO QUE INGRESA AL SISTEMA ===========/
            HttpSession session = request.getSession();

            try {
                myid = (int) session.getAttribute("ID");
                idmyrol = (int) session.getAttribute("IDROL");
                request.setAttribute("miname", session.getAttribute("NOMBRE"));
            } catch (Exception e) {
                System.out.println("Error en objetos de eventos se cerro session" + e);
                session.removeAttribute("ID");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

            // Valida que las sessiones no esten nulas para permitir el ingreso a las acciones
            if (session.getAttribute("ID") != null && session.getAttribute("IDROL") != null) {

                String accion = request.getParameter("accion"); // Accion de los botones o lo que se pase

                if (request.getParameterMap().containsKey("accion") == true) {

                    if (accion.equals("Listar") || accion.equals("Allobjects") || accion.equals("ReporteExcel")) {

                        switch (accion) {

                            case "Listar":

                     try {

                                // Lista objetos disponibles
                                List listobjetos = daoobjetos.listaObjetosDisponibles();
                                request.setAttribute("listobjetos", listobjetos);

                                // Lista los mensajes de todos
                                List listchatstr = daochat.listChats();
                                request.setAttribute("listchat", listchatstr);

                            } catch (NumberFormatException e) {
                                System.out.println("Error al listar en eventos process" + e);
                            }

                            request.getRequestDispatcher("implementsevents.jsp").forward(request, response);

                            break;

                            case "Allobjects":
                    
                        try {

                                if (idmyrol < 3) {

                                    // Lista estados para objetos en allobjects.jsp
                                    List listestados = daoestado.listarEstados();
                                    request.setAttribute("estados", listestados);

                                    List listodosobjetos = daoobjetos.listarTodosObjetos();
                                    request.setAttribute("objstodos", listodosobjetos);

                                    request.getRequestDispatcher("allobjects.jsp").forward(request, response);
                                }

                            } catch (IOException | ServletException e) {
                                System.out.println("Error en proccess al listar todos los objetos" + e);
                            }

                            break;

                            case "ReporteExcel":
                        
                        try {
                                if (CReportexcel.crearExcelObjetos() == true) {
                                    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                                    response.addHeader("Content-Disposition", "attachment;filename=Reporteobjetos.xlsx");

                                    try (BufferedOutputStream bfout = new BufferedOutputStream(response.getOutputStream());
                                          BufferedInputStream infile = new BufferedInputStream(new FileInputStream(CAccionesdoc.rutaReporteobjetos()))) {
                                        byte[] tmp = new byte[8192];
                                        int c;
                                        while ((c = infile.read(tmp)) > 0) {
                                            bfout.write(tmp, 0, c);
                                        }
                                        bfout.flush();
                                    }
                                }

                                CAccionesdoc.eliminarDocumento(CAccionesdoc.rutaReporteobjetos());
                            } catch (IOException e) {
                                System.out.println("Error en process al descargar el excel " + e);
                            }

                            break;

                            default:
                        }
                    } else {
                        request.getRequestDispatcher("error404.jsp").forward(request, response);
                    }
                } else {
                    request.getRequestDispatcher("error404.jsp").forward(request, response);
                }

            } else {

                System.out.println("Cierre sesion en  objetos y eventos proccrequest");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (IOException | ServletException e) {
            System.out.println("Errores objetos y eventos proccrequest" + e);
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

        String nombreobj, marcaobj, serialobj, caracteristicasobj;
        int idestado;

        //===============================================================================================//
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        //================== EN ESTE METODO SOLO SE RALIZACION ACCIONES PARA OBJETOS ====================//
        try {
            //======== INSTANCIA SESSION PARA EL ALMACENAR DATOS UNICOS DE USUARIO QUE INGRESA AL SISTEMA ===========/
            HttpSession session = request.getSession();

            myid = (int) session.getAttribute("ID");
            //   idmyrol = (int) session.getAttribute("IDROL");
            request.setAttribute("miname", session.getAttribute("NOMBRE"));
            // Validacion de sessiones
            if (session.getAttribute("ID") != null && session.getAttribute("IDROL") != null) { // Validacion de sessiones.

                String accion = request.getParameter("accion"); // Accion de los botones o lo que se pase

                switch (accion) {

                    case "Separar": // Separar objetos con un evento existente

                    try {
                        // Recibe el id del objeto y id del evento al que se asignara el objeto
                        idobjeto = Integer.parseInt(request.getParameter("idobj"));
                        idevento = Integer.parseInt(request.getParameter("idevent"));

                        // Valida si se enviaron los id del objeto y evento
                        if (idobjeto > 0 && idevento > 0) {

                            objetos.setIdobjeto(idobjeto);
                            // Valida que no este en uso si retorna 0 nadie lo usa
                            if (daoobjetos.separarObjeto(objetos, evento, "Validar") == 0) {
                                // Envia el id del usuario y del evento
                                usuario.setID(myid);
                                evento.setIdevento(idevento);
                                // Valida si el evento seleccionado pertence a el usuario 
                                if (daoevento.validarEvento(evento, usuario) > 0) {
                                    objetos.setIdobjeto(idobjeto);
                                    // Envia el id del obj y el id del evento para separar y comprueba que se separo correctamente
                                    if (daoobjetos.separarObjeto(objetos, evento, "Separar") > 0) {
                                        // resultado de la accion
                                        response.getWriter().write("true"); // Implemento separado en evento
                                    } else {
                                        response.getWriter().write("false");// No se asigno
                                    }
                                } else {
                                    response.getWriter().write("sinevent");// No tiene evento
                                }
                            } else {
                                System.out.println("No disponible");
                                response.getWriter().write("not");// No disponible
                            }

                        } else {
                            response.getWriter().write("notid"); // No se enviaron datos de obj o evevto
                        }

                    } catch (NumberFormatException e) {
                        e.getMessage();
                    }

                    break;

                    case "Registrar": // Registra objetos. (solo usuario con permisos))

                    try {
                        // Recibe datos pare registor de objeto
                        nombreobj = request.getParameter("nombreobj");
                        serialobj = request.getParameter("serialobj").replaceAll(" ", "");
                        marcaobj = request.getParameter("marcaobj");
                        caracteristicasobj = request.getParameter("caracteobj");

                        idestado = Integer.parseInt(request.getParameter("estadoobj"));

                        // Validacion de campos
                        if (!nombreobj.equals("") && !serialobj.equals("") && !marcaobj.equals("") && idestado > 6 && idestado < 10) {
                            objetos.setIdobjeto(0);
                            objetos.setSerial(serialobj);
                            if (daoobjetos.comprobarSerial(objetos) == false) {
                                // Envia datos a lo setter
                                objetos.setObjetonombre(nombreobj);
                                objetos.setMarca(marcaobj);
                                objetos.setEstado(Integer.toString(idestado));
                                objetos.setCaracteristicas(caracteristicasobj);

                                // Envia el objeto con datos al metodo y valida si se agrego el objeto
                                if (daoobjetos.registrarObjeto(objetos) == true) {
                                    response.getWriter().write("true"); // Implemento registrado
                                } else {
                                    response.getWriter().write("false");// Implemento no registrado
                                }
                            } else {
                                response.getWriter().write("serial");// Respuesta de serial en usu
                            }
                        } else {
                            response.getWriter().write("campos");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al registrar el objeto en post " + e);
                    }

                    break;

                    case "Subir": // Sube el archivo al servidor para realizar la carga masiva de objetos.
                        
                    try {
                        DiskFileItemFactory factory = new DiskFileItemFactory();

                        factory.setSizeThreshold(1024);
                        factory.setRepository(new File(CAccionesdoc.rutaExcelcargamasiva("")));
                        ServletFileUpload upload = new ServletFileUpload(factory);

                        List<FileItem> partes = upload.parseRequest(request);
                        for (FileItem items : partes) {
                            // Obtiene la ruta para subir el documento al servidor
                            File file = new File(CAccionesdoc.rutaExcelcargamasiva(""), items.getName());
                            items.write(file);
                            break;
                        }
                        // Respuesta para JQuery de que se completo la accion del documento dejando en la ruta indicada
                        response.getWriter().write("true");

                    } catch (Exception e) {
                        System.out.println("Error al hacer la carga del archivo al servidor en post " + e);
                    }

                    break;

                    case "Carga":
                        
                        try {
                        // Instancia que tiene los metodos para lectura de datos en un excel.
                        CLeerExcel excelobjetos = new CLeerExcel();

                        // Obtiene el nombre del archivo
                        File file = new File(request.getParameter("nombrefile"));
                        String filename = file.getName();

                        // Envia el nombre del archivo al metodo para la lectura de los datos.
                        List listnodisponibles = excelobjetos.leerExcelObjetos(filename);

                        // Valida si retorna una lista con datos que no se agregaron.
                        if (listnodisponibles.size() < 1) {
                            response.getWriter().write("true");
                        } else if (listnodisponibles.size() > 0) {
                            // Si encuentra datos en la vista los envia de respuesta
                            response.getWriter().write(CAccionesdoc.crearListaJson(listnodisponibles));
                        } else {
                            // Si no se ejecuto enviar respuesta en false
                            response.getWriter().write("false");
                        }

                    } catch (IOException e) {
                        System.out.println("Error en la carga de objetos en post " + e);
                    }

                    break;

                    case "Actualizar": // Actualizar el objeto
                        
                    try {

                        // Recibe los datos del objeto
                        idobjeto = Integer.parseInt(request.getParameter("objeto")); // ID del objetos
                        nombreobj = request.getParameter("nombreobj");
                        marcaobj = request.getParameter("marcaobj");
                        serialobj = request.getParameter("serialobj").replaceAll(" ", "");
                        caracteristicasobj = request.getParameter("caracteristicasobj");
                        idestado = Integer.parseInt(request.getParameter("estadoobj"));

                        objetos.setIdobjeto(idobjeto);
                        if (daoobjetos.separarObjeto(objetos, evento, "Validar") == 0) {

                            if (idestado > 6 && idestado < 10) {

                                // Envia los datos del objeto a los set
                                //objetos.setIdobjeto(0);
                                //objetos.setIdobjeto(idobjeto);
                                
                                objetos = daoobjetos.verInfoObjeto(idobjeto);
                                CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Cambio de " + objetos.getObjetonombre() + " a " + nombreobj + " y el serial de " + objetos.getSerial() + " a " + serialobj);
                              
                                objetos.setIdobjeto(idobjeto);
                                objetos.setObjetonombre(nombreobj);
                                objetos.setMarca(marcaobj);
                                objetos.setSerial(serialobj);
                                objetos.setCaracteristicas(caracteristicasobj);
                                objetos.setEstado(Integer.toString(idestado));

                                // Si es falso es por que ese serial le pertenece al objeto que esta siendo actualizado
                                // o se cambio el serial y el serial nuevo esta disponible
                                if (daoobjetos.comprobarSerial(objetos) == false) {
                                    // Valida que los campos no lleguen vacios o null
                                    if (idobjeto > 0 && !nombreobj.equals("") && !marcaobj.equals("") && !serialobj.equals("") && !caracteristicasobj.equals("") && idestado > 0) {
                                        // Envia el objeto con sus datos al metodo para actualizar y valida que se cumpla correctamente
                                        if (daoobjetos.actualizarObjeto(objetos) == true) {
                                            response.getWriter().write("true"); // Respuesta
                                        } else {
                                            response.getWriter().write("false");
                                        }
                                    } else {
                                        response.getWriter().write("campos"); // Respuesta de que llegaron los campos vacios
                                    }
                                } else {
                                    response.getWriter().write("serial");
                                }

                            } else {
                                response.getWriter().write("estadoinvalido");
                            }
                        } else {
                            response.getWriter().write("usado");
                        }

                    } catch (IOException | NumberFormatException e) {
                        System.out.println("Error en objetos y eventos al actualizar" + e);
                    }

                    break;

                    case "Eliminar":
                        
                        try {

                        idobjeto = Integer.parseInt(request.getParameter("objeto"));
                        objetos.setIdobjeto(idobjeto);

                        // Valida que el objeto no este usado si retorna 0 nadie lo tiene en uso
                        if (daoobjetos.separarObjeto(objetos, evento, "Validar") == 0) {

                            objetos = daoobjetos.verInfoObjeto(idobjeto);
                            objetos.setIdobjeto(idobjeto);
                            if (daoobjetos.eliminarObjeto(objetos) == true) {
                                CModificacionDAO.modificaUsuario(myid, dateformat.format(fechaactual), "Eliminaste el objeto " + objetos.getObjetonombre() + " con serial " + objetos.getSerial());
                                response.getWriter().write("true");
                            } else {
                                response.getWriter().write("false");
                            }
                        } else {
                            response.getWriter().write("enuso");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al eliminar el objeto en post" + e);
                    }

                    break;

                    case "Traspasoobj": // Envia el objeto a otro evento 
                        
                        try {

                        idobjeto = Integer.parseInt(request.getParameter("idobj"));
                        idevento = Integer.parseInt(request.getParameter("ideven"));

                        // Valida si llegaron datos
                        if (idobjeto > 0 && idevento > 0) {

                            usuario.setID(myid);
                            evento.setIdevento(idevento);

                            // Valida si el evento pertenece al suaurio
                            if (daoevento.validarEvento(evento, usuario) > 0) {

                                objetos.setIdobjeto(idobjeto);
                                evento = daoobjetos.validaObjetoUser(objetos);

                                // objetos.setIdobjeto(idobjeto);
                                // evento = daoobjetos.validaObjetoUser(objetos);
                                if (daoevento.validarEvento(evento, usuario) > 0) {
                                    evento.setIdevento(idevento);
                                    // Envia al metodo los objetos con datos y valida la accion en el metodo
                                    if (daoobjetos.separarObjeto(objetos, evento, "Traspaso") > 0) {
                                        response.getWriter().write("true");
                                    } else {
                                        response.getWriter().write("false");
                                    }
                                } else {
                                    response.getWriter().write("false");
                                }

                            } else {
                                response.getWriter().write("notevent");
                            }
                        } else {
                            response.getWriter().write("notid");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al hacer traspaso del objeto en post: " + e);
                    }

                    break;

                    case "Removeobj": // Remover objeto de un evento
                        try {

                        // Recibe el id del objeto a remover 
                        idobjeto = Integer.parseInt(request.getParameter("idobj"));
                        // Comprueba que se recibio el id
                        if (idobjeto > 0) {

                            objetos.setIdobjeto(idobjeto);

                            // Valida si el objeto a remover pertenece al usuario que requiere la accion
                            evento = daoobjetos.validaObjetoUser(objetos);
                            usuario.setID(myid);
                            // Valida si el evento pertenece a ese usuario ( si retorna mayor a 0 si le pertence )
                            if (daoevento.validarEvento(evento, usuario) > 0) {
                                // Quita el objeto del evento y comprueba que se complete correctamente
                                if (daoobjetos.deshacerObjetodeevento(objetos) == true) {
                                    response.getWriter().write("true");// Envia la respuesta de que se cumpplio todo 
                                } else {
                                    response.getWriter().write("false");
                                }
                            } else {
                                // Envia respuesta de que ese objeto no pertenece al evento de ese usuario
                                response.getWriter().write("not");
                            }
                        } else {
                            response.getWriter().write("notid");// Envia respuesta que no llego ningun dato
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Error al remover el objeto en post " + e);
                    }
                    break;

                    case "Verinfo": // Caso para ver la informacion sobre un objeto selecionado

                        idobjeto = Integer.parseInt(request.getParameter("objeto"));

                        objetos = daoobjetos.verInfoObjeto(idobjeto);

                        response.getWriter().write(CAccionesdoc.crearObjetoJson(objetos));
                        break;

                    case "Objetosevento": // Accion para ver os objetos que pertenecen a un evento
                        
                        try {
                        idevento = Integer.parseInt(request.getParameter("evento"));
                        evento.setIdevento(idevento);
                        usuario.setID(myid);

                        // Valida que el evento le pertenezca al usuario que desea verlo
                        if (daoevento.validarEvento(evento, usuario) == 1) {

                            List objetosevento = daoobjetos.listaObjetosEvento(idevento);
                            response.getWriter().write(CAccionesdoc.crearListaJson(objetosevento));
                        } else {
                            response.getWriter().write("nopertenece");
                        }

                    } catch (IOException | NumberFormatException e) {
                        System.out.println("Error al ver los objetos del evento en post  " + e);
                    }

                    break;

                    default:
                    //throw new AssertionError();
                }

            } else {
                System.out.println("Cierre sesion en  objetos y eventos post");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (IOException e) {
            System.out.println("Error en  objetos y eventos post" + e);
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
