/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.util.List;
import ModeloVO.CChats;
import ModeloDAO.CChatsDAO;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ALEJANDRO
 */
public class messages extends HttpServlet {

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
            out.println("<title>Servlet messages</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet messages at " + request.getContextPath() + "</h1>");
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

        //========== INSTANCIA DE LA FECHA ACTUAL  =============//
        Date fechaactual = new Date(System.currentTimeMillis());
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        //========= INSTANCIA PARA HORA ACTUAL =============//
        DateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
        Date hour = new Date();

        CChats chat = new CChats();
        CChatsDAO daochat = new CChatsDAO();
        HttpSession session = request.getSession();

        String accion = request.getParameter("accion");

        if (session.getAttribute("ID") != null) {

            switch (accion) {

                case "Listarchats":

                    // Lista los mensajes de todos
                    List listchatstr = daochat.listChats();
                    request.setAttribute("listchat", listchatstr);

                    break;

                case "Chatear":
                                
             try {
                    // Recibe los datos del chat
                    String mensaje = request.getParameter("msj");

                    chat.setFechamensaje(dateformat.format(fechaactual));
                    chat.setHoramensaje(timeformat.format(hour));
                    chat.setMensaje(mensaje);
                    chat.setIdsuario((int) session.getAttribute("ID"));

                    // Envia al metodo el objeto con datos y valida la accion 
                    if (!mensaje.equals("")) {
                        if (daochat.mensajeria(chat) == true) {
                            response.getWriter().write("true");
                        } else {
                            response.getWriter().write("false");
                        }
                    }

                } catch (IOException e) {
                    System.out.println("Error en messajes post " + e);
                }

                break;

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
