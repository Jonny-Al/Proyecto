<%-- 
    Document   : modification
    Created on : 22/07/2020, 8:35:22 p. m.
    Author     : jonny
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%if ((int) session.getAttribute("IDROL") < 3) { %> 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificaciones</title>
        <%  response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0); %>
        <c:set var="nombre" value="${miname}"></c:set>
        <c:if test="${nombre == null}">
            <% request.getRequestDispatcher("users?menu=modificaciones&accion=modificaciones").forward(request, response); %>
        </c:if>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">

            <jsp:include page="navlateral.jsp" flush="true"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <!-- Main Content -->
                    <div id="content" class="bg-gradient-fond">

                        <!-- Topbar -->
                    <jsp:include page="navsuperficie.jsp" flush="true"></jsp:include>
                        <!--Contenedor fluid-->
                        <div class="container-fluid">
                            <div class="card">
                                <div class="card-body">
                                    <div class="table-striped">
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <thead class="bg-fond-transparent text-gray-900">
                                                <tr>
                                                    <th>Usuario</th>
                                                    <th>Fecha acción</th>
                                                    <th>Informacion</th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>Usuario</th>
                                                    <th>Fecha acción</th>
                                                    <th>Informacion</th>
                                                </tr>
                                            </tfoot>
                                            <tbody>  
                                            <c:forEach var="modi" items="${modificaciones}">
                                                <tr>
                                                    <td>${modi.getUsuariomodifico()} </td>
                                                    <td>${modi.getFechamodificacion()}</td>
                                                    <td>${modi.getInfomodificacion()}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <!--
        <script src="dashboard/js/demo/chart-area-demo.js"></script>
        <script src="dashboard/js/demo/chart-pie-demo.js"></script>
        <script src="dashboard/vendor/jquery-easing/jquery.easing.min.js"></script>
        -->
        <!--JavaScript para barra nav nav lateral-->
        <script src="dashboard/js/events.js"></script><!-- Script para objetos y eventos -->
        <script src="dashboard/vendor/jquery/jquery.min.js"></script>
        <script src="dashboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="dashboard/js/sb-admin-2.min.js"></script>
        <!-- JavaScript para Tabla Html -->
        <script src="dashboard/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="dashboard/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="dashboard/js/demo/datatables-demo.js"></script>
    </body>
</html>
<% } else {
        request.getRequestDispatcher("error404.jsp").forward(request, response);
    }%> 
<% } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%> 
