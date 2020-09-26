/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import ModeloDAO.CEventosDAO;
import ModeloDAO.CReunionesDAO;
import ModeloDAO.CTareasDAO;
import ModeloVO.CEventos;
import ModeloVO.CReuniones;
import ModeloVO.CTareas;
import ModeloVO.CUsuario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
public class allindex extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //=== INSTANCIAS === //
    CUsuario usuario = new CUsuario();
    CTareas tareas = new CTareas();
    CTareasDAO daotarea = new CTareasDAO();
    CReunionesDAO daoreunion = new CReunionesDAO();
    CEventosDAO daoevento = new CEventosDAO();

    int myid, idmyarea, idmyrol;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {

            if (session.getAttribute("ID") != null && session.getAttribute("IDAREA") != null && session.getAttribute("IDROL") != null) {

                Date fechactual = new Date(System.currentTimeMillis());
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                Date dateactual = date.parse(date.format(fechactual));
                Date fechainicio, fechafin;

                int diasparaterminar, diasparaempezar;
                Iterator iterador;

                try {
                    myid = (int) session.getAttribute("ID");
                    idmyarea = (int) session.getAttribute("IDAREA");
                    idmyrol = (int) session.getAttribute("IDROL");
                    request.setAttribute("miname", session.getAttribute("NOMBRE"));
                } catch (Exception e) {
                    session.removeAttribute("ID");
                    session.invalidate();
                    System.out.println("Error en inicio de todo se cerro la session " + e);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                tareas.setIdusasigna(myid);
                tareas.setIddesarrolla(myid);

                // ===== LISTA DE TAREAS QUE ESTAN A PUNTO DE TERMINAR CON 8 O MENOS DIAS FALTANTES POR ACABAR ===== //
                CTareas trs;

                List trsporacabar = daotarea.listarTareas(tareas, "2", 1);
                iterador = trsporacabar.iterator();
                List listareasporacabar = new ArrayList();
                while (iterador.hasNext()) {
                    trs = (CTareas) iterador.next();

                    fechafin = date.parse(trs.getFechafinal());
                    diasparaterminar = (int) ((fechafin.getTime() - dateactual.getTime()) / 86400000);

                    if (diasparaterminar < 8) {
                        listareasporacabar.add(trs);
                    }
                }
                request.setAttribute("trsaterminar", listareasporacabar);

                // ===== LISTA DE TAREAS QUE ESTAN A PUNTO DE EMPEZAR QUE FALTAN 5 DIAS PARA EMPEZAR ===== //
                List tareasaempezar = daotarea.listarTareas(tareas, "3", 1);
                iterador = tareasaempezar.iterator();
                List listareasaempezar = new ArrayList();

                while (iterador.hasNext()) {
                    trs = (CTareas) iterador.next();

                    fechainicio = date.parse(trs.getFechainicio());
                    diasparaempezar = (int) ((fechainicio.getTime() - dateactual.getTime()) / 86400000);

                    if (diasparaempezar < 6) {
                        listareasaempezar.add(trs);
                    }
                }

                request.setAttribute("tareasaempezar", listareasaempezar);

                // ==== LISTA DE TAREAS DESAPROBADAS ===========//
                List listareasdesaprobadas = daotarea.listarTareas(tareas, "5", 1);
                if (listareasdesaprobadas.size() > 0) {
                    request.setAttribute("desaprobadas", 1);
                    request.setAttribute("trsdesaprobadas", listareasdesaprobadas);
                } else {
                    request.setAttribute("desaprobadas", 0);
                }

                // === LISTA DE EVENTOS PROXIMOS A EMPEZAR ===============//
                CEventos evn;

                usuario.setID(myid);
                List miseventos = daoevento.listEventosUser(usuario);
                iterador = miseventos.iterator();
                List listeventos = new ArrayList();

                while (iterador.hasNext()) {
                    evn = (CEventos) iterador.next();
                    fechainicio = date.parse(evn.getDateinicio());
                    diasparaempezar = (int) ((fechainicio.getTime() - dateactual.getTime()) / 86400000);

                    if (diasparaempezar < 6) {
                        listeventos.add(evn);
                    }
                }

                request.setAttribute("eventosproximos", listeventos);

                // ===== LISTA DE PROXIMIAS REUNIONES A EMPEZAR ====//
                CReuniones reu;

                usuario.setID(myid);
                List reuniones = daoreunion.listReuniones(usuario);
                iterador = reuniones.iterator();
                List listreuniones = new ArrayList();

                while (iterador.hasNext()) {
                    reu = (CReuniones) iterador.next();
                    fechainicio = date.parse(reu.getFechainicio());
                    diasparaempezar = (int) ((fechainicio.getTime() - dateactual.getTime()) / 86400000);

                    if (diasparaempezar < 5 && diasparaempezar > -1) {
                        listreuniones.add(reu);
                    }
                }

                request.setAttribute("reuniones", listreuniones);

                request.getRequestDispatcher("allindex.jsp").forward(request, response);
            } else {
                System.out.println("Cierre de session inicio de todo");
                session.removeAttribute("ID");
                session.removeAttribute("NOMBRE");
                session.removeAttribute("IDROL");
                session.removeAttribute("IDAREA");
                session.invalidate();
                request.getSession().invalidate();
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (IOException | ParseException | ServletException e) {
            session.removeAttribute("ID");
            session.invalidate();
            System.out.println("Error en todo del index se cerro la session " + e);
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
        processRequest(request, response);
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
