/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloVO.CAreas;
import ModeloDAO.CAreasDAO;
import ModeloDAO.CChatsDAO;
import ModeloVO.CRoles;
import ModeloDAO.CRolesDAO;
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
public class roleandaddres extends HttpServlet {

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
    CRoles roles = new CRoles();
    CRolesDAO daorol = new CRolesDAO();
    //======================== INSTANCIAS DE AREA =============================//
    CAreas areas = new CAreas();
    CAreasDAO daoarea = new CAreasDAO();
    //======================== INSTANCIAS DE CHAT ===========================//
    CChatsDAO daochat = new CChatsDAO();
    //======================== VARIABLES ======================================//
    // IDS SOLO PARA USUARIO QUE INGRESAR AL SISTEMA
    int myid, idmyarea, idmyrol;

    //== VARIABLE DE USO
    int idarea, idrol;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        //========================== ASIGNACION AL VALOR DE LAS VARIABLES CON DATOS DEL USUARIO  ============================//
        try {

            try {
                //======== INSTANCIA SESSION PARA EL ALMACENAR DATOS UNICOS DE USUARIO QUE INGRESA AL SISTEMA ===========/
                myid = (int) session.getAttribute("ID");
                idmyarea = (int) session.getAttribute("IDAREA");
                idmyrol = (int) session.getAttribute("IDROL");
                request.setAttribute("miname", session.getAttribute("NOMBRE"));

            } catch (Exception e) {
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error en process de roles y areas" + e);
        }

        if (idmyrol == 1 && session.getAttribute("ID") != null && session.getAttribute("IDAREA") != null && session.getAttribute("IDROL") != null) {

            // Lista los roles y areas
            try {
                List lisroles = daorol.listarRoles();
                request.setAttribute("roles", lisroles);

                List listarea = daoarea.listarAreas();
                request.setAttribute("areas", listarea);

                // Lista los mensajes de todos
                List listchatstr = daochat.listChats();
                request.setAttribute("listchat", listchatstr);

            } catch (Exception e) {
                e.getMessage();
            }

            request.getRequestDispatcher("areaandrole.jsp").forward(request, response);
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

        String nombre = request.getParameter("nombre");
        String accion = request.getParameter("accion");
        int opcion = Integer.parseInt(request.getParameter("opcion"));

        if ((int) session.getAttribute("IDROL") == 1 && session.getAttribute("ID") != null) {
            switch (accion) {

                case "Registrar": // Registrar roles o area
                    
                try {

                    if (nombre != null) {

                        switch (opcion) {

                            case 1:
                                areas = daoarea.validarArea(nombre);
                                if (areas.getIdArea() == 0 && areas.getAreaNombre() == null) {
                                    areas.setAreaNombre(nombre);
                                    if (daoarea.registrarArea(areas) == true) {
                                        response.getWriter().write("truearea");
                                    } else {
                                        response.getWriter().write("falsearea");
                                    }
                                } else {
                                    response.getWriter().write("existearea");
                                }
                                break;

                            case 2:

                                roles = daorol.validarRol(nombre);
                                if (roles.getIdRol() == 0 && roles.getRolNombre() == null) {
                                    roles.setRolNombre(nombre);
                                    if (daorol.registrarRol(roles) == true) {
                                        response.getWriter().write("truerol");
                                    } else {
                                        response.getWriter().write("falserol");
                                    }
                                } else {
                                    response.getWriter().write("existerol");
                                }
                                break;
                            default:
                                throw new AssertionError();
                        }

                    } else {
                        response.getWriter().write("campos");
                    }

                } catch (IOException e) {
                    System.out.println("Error al registrar en post" + e);
                }

                break;

                case "Actualizar": // Actualizar rol o area
                    
                try {

                    switch (opcion) {

                        case 1: // Areas
                            
                            try {

                            idarea = Integer.parseInt(request.getParameter("area"));

                            if (idarea > 1) {

                                if (idarea > 0 && nombre != null) {

                                    areas = daoarea.validarArea(nombre);
                                    if (areas.getIdArea() == idarea) {
                                       response.getWriter().write("igual");
                                    } else {
                                        if (areas.getAreaNombre() == null && areas.getIdArea() == 0) {
                                            areas.setIdArea(idarea);
                                            areas.setAreaNombre(nombre);

                                            if (daoarea.actualizarArea(areas) == true) {
                                                response.getWriter().write("true");
                                            } else {
                                                response.getWriter().write("false");
                                            }
                                        } else {
                                            response.getWriter().write("existe");
                                        }
                                    }
                                } else {
                                    response.getWriter().write("campos");
                                }

                            } else {
                                response.getWriter().write("nopermitido");
                            }

                        } catch (IOException | NumberFormatException e) {
                            System.out.println("Error al actualizar area en post" + e);
                        }

                        break;

                        case 2: // Rol
                            
                          try {

                            idrol = Integer.parseInt(request.getParameter("rol"));

                            if (idrol > 2) {

                                if (idrol > 0 && nombre != null) {

                                    roles = daorol.validarRol(nombre);

                                    if (roles.getIdRol() == idrol) {
                                        response.getWriter().write("igual");
                                    } else {
                                        if (roles.getIdRol() == 0 && roles.getRolNombre() == null) {
                                            roles.setIdRol(idrol);
                                            roles.setRolNombre(nombre);

                                            if (daorol.actualizarRol(roles) == true) {
                                                response.getWriter().write("true");
                                            } else {
                                                response.getWriter().write("false");
                                            }
                                        } else {
                                            response.getWriter().write("existe");
                                        }
                                    }
                                } else {
                                    response.getWriter().write("campos");
                                }
                            } else {
                                response.getWriter().write("nopermitido");
                            }
                        } catch (IOException | NumberFormatException e) {
                            System.out.println("Error al actualizar rol en post" + e);
                        }

                        break;

                        default:
                            throw new AssertionError();
                    }

                } catch (Exception e) {
                    System.out.println("Error al actualizar en post " + e);
                }
                break;

                case "Eliminar":

                    switch (opcion) {
                        case 1:

                            
                            try {
                            // ID DEL AREA QUE SE VA ELIMINAR
                            idarea = Integer.parseInt(request.getParameter("eliminar"));

                            if (idarea > 1) {

                                if (daoarea.comprobarAreaUsada(idarea) == false) {
                                    if (daoarea.eliminarArea(idarea) == true) {
                                        response.getWriter().write("true");
                                    } else {
                                        response.getWriter().write("false");
                                    }
                                } else {
                                    response.getWriter().write("use");
                                }
                            } else {
                                response.getWriter().write("nopermitido");
                            }
                        } catch (IOException | NumberFormatException e) {
                            System.out.println("Error al eliminar area en post " + e);
                        }

                        break;

                        case 2:
                            try {
                            // id del rol que se va eliminar
                            idrol = Integer.parseInt(request.getParameter("eliminar"));

                            if (idrol > 2) {

                                if (daorol.comprobarRolUsado(idrol) == false) {
                                    if (daorol.eliminarRol(idrol) == true) {
                                        response.getWriter().write("true");
                                    }
                                } else {
                                    response.getWriter().write("use");
                                }
                            } else {
                                response.getWriter().write("nopermitido");
                            }

                        } catch (IOException | NumberFormatException e) {
                            System.out.println("Error al eliminar en post " + e);
                        }
                        break;
                        default:
//                            throw new AssertionError();
                    }

                    break;
                default:
            }
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
