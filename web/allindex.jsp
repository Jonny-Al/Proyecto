<%-- 
    Document   : allindex
    Created on : 26/04/2020, 1:05:24 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0);
        %>
        <c:set var="nombre" value="${miname}"></c:set>
        <c:if test="${nombre == null}">
            <% request.getRequestDispatcher("allindex?").forward(request, response); %>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Inicio</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <!-- Sidebar -->
            <jsp:include page="navlateral.jsp" flush="true"></jsp:include>
                <!-- End of Sidebar -->
                <!-- Content Wrapper -->
                <div id="content-wrapper" class="d-flex flex-column">
                    <!-- Main Content -->
                    <div id="content" class="bg-gray-100">
                        <!-- Topbar -->
                    <jsp:include page="navsuperficie.jsp" flush="true"></jsp:include>
                        <!--Contenedor de las demas paginas--> 
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-lg-2 card bg-fond-transparent">
                                    <div class="card-body card bg-blanco">
                                        <div class="bg-white mb-5 text-gray-700 form-group row">
                                            <div class="col-sm-1">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pie-chart text-warning" fill="currentColor" >
                                                <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path fill-rule="evenodd" d="M7.5 7.793V1h1v6.5H15v1H8.207l-4.853 4.854-.708-.708L7.5 7.793z"/>
                                                </svg>
                                            </div>
                                            <div class="col-auto sansserif">
                                                Tareas por entregar
                                            </div>
                                        </div>
                                    <c:forEach var="trsterminar" items="${trsaterminar}">
                                        <div class="card  amarillo mb-1">
                                            <div class="card-body text-warning sansserif" >
                                                ${trsterminar.getNombre()}
                                                <div class=" small" style="font-size: 13px">Entrega: ${trsterminar.getFechafinal()} </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <!-- fin foreach listado de usuarios-->
                            <!------Inicio foreach tareas de usuario seleccionado-------->
                            <div class="col-lg-2 card bg-fond-transparent ">
                                <div class="card-body bg-blanco rounded">
                                    <div class="mb-5 sansserif">
                                        <div class="bg-white mb-3 text-gray-700 form-group row sansserif ">
                                            <div class="col-sm-2">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pie-chart text-danger" fill="currentColor" >
                                                <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path fill-rule="evenodd" d="M7.5 7.793V1h1v6.5H15v1H8.207l-4.853 4.854-.708-.708L7.5 7.793z"/>
                                                </svg>
                                            </div>Tareas desaprobadas
                                        </div>
                                    </div>
                                    <div  class="sansserif">
                                        <c:set var="desaprobada" value="${desaprobadas}"></c:set>
                                        <c:if test="${desaprobada > 0}">
                                            <c:forEach var="trsdesaprobadas" items="${trsdesaprobadas}">
                                                <div class="card bg-rojo text-white shadow mb-1">
                                                    <div class="card-body text-danger">
                                                        <div class="sansserif">
                                                            ${trsdesaprobadas.getNombre()}
                                                        </div>
                                                        <div class="small" style="font-size: 13px">Finaliza: ${trsdesaprobadas.getFechafinal()}</div>
                                                    </div>
                                                </div> 
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-2 bg-fond-transparent card ">
                                <div class="card-body bg-blanco rounded">
                                    <div class="mb-5 sansserif">
                                        <div class="bg-white mb-3 text-gray-700 form-group row sansserif">
                                            <div class="col-sm-2">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pie-chart text-success" fill="currentColor" >
                                                <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path fill-rule="evenodd" d="M7.5 7.793V1h1v6.5H15v1H8.207l-4.853 4.854-.708-.708L7.5 7.793z"/>
                                                </svg>
                                            </div>Tareas por empezar
                                        </div>
                                    </div>
                                    <div  class="sansserif">
                                        <c:forEach var="trsempezar" items="${tareasaempezar}">
                                            <div class="card  alert-success mb-1">
                                                <div class="card-body text-success">
                                                    <div class="sansserif">
                                                        ${trsempezar.getNombre()}
                                                    </div>
                                                    <div class="small" style="font-size: 13px">Inicia: ${trsempezar.getFechainicio()} </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <!-------Columna actualizacion de tarea-------------->
                            <div class="card col-lg-3 bg-fond-transparent">
                                <div class="card-body bg-blanco rounded">
                                    <div class="mb-5 sansserif">
                                        <div class="bg-white mb-3 text-gray-700 form-group row sansserif">
                                            <div class="col-sm-2 text-primary">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-joystick text-primary" fill="currentColor" >
                                                <path d="M7.106 15.553L.553 12.276A1 1 0 0 1 0 11.382V9.471a1 1 0 0 1 .606-.89L6 6.269v1.088L1 9.5l5.658 2.83a3 3 0 0 0 2.684 0L15 9.5l-5-2.143V6.27l5.394 2.312a1 1 0 0 1 .606.89v1.911a1 1 0 0 1-.553.894l-6.553 3.277a2 2 0 0 1-1.788 0z"/>
                                                <path fill-rule="evenodd" d="M7.5 9.5v-6h1v6h-1z"/>
                                                <path d="M10 9.75c0 .414-.895.75-2 .75s-2-.336-2-.75S6.895 9 8 9s2 .336 2 .75zM10 2a2 2 0 1 1-4 0 2 2 0 0 1 4 0z"/>
                                                </svg>
                                            </div>Eventos
                                        </div>
                                    </div>
                                    <div  class="sansserif">
                                        <c:forEach var="eventos" items="${eventosproximos}">
                                            <div class="card azul-claro text-white shadow mb-1">
                                                <div class="card-body text-primary">
                                                    <div class="sansserif">
                                                        ${eventos.getNombreevento()}
                                                    </div>
                                                    <div class="small" style="font-size: 13px">Inicia: ${eventos.getDateinicio()} - ${eventos.getHorainicio()} a ${eventos.getHorafin()}</div>
                                                </div>
                                            </div> 
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <div class="card col-lg-3 bg-fond-transparent">
                                <div class="card-body bg-blanco rounded">
                                    <div class="mb-5 sansserif">
                                        <div class="bg-white mb-3 text-gray-700 form-group row sansserif ">
                                            <div class="col-sm-2">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pie-chart text-purple" fill="currentColor" >
                                                <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path fill-rule="evenodd" d="M7.5 7.793V1h1v6.5H15v1H8.207l-4.853 4.854-.708-.708L7.5 7.793z"/>
                                                </svg>
                                            </div>Reuniones
                                        </div>
                                    </div>
                                    <div  class="sansserif">
                                        <c:forEach var="reu" items="${reuniones}">
                                            <div class="card lilaclaro text-purple shadow mb-1">
                                                <div class="card-body">
                                                    <div class="sansserif">
                                                        ${reu.getNombrereunion()}
                                                    </div>
                                                    <div class="small" style="font-size: 13px">Inicia: ${reu.getFechainicio()} - ${reu.getHorainicio()} a ${reu.getHorafin()}</div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 


                    <!-- end container de confirmacion de eliminacion de una tarea  -->

                </div>
            </div>
        </div>

        <script src="dashboard/vendor/jquery/jquery.min.js"></script>
        <script src="dashboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="dashboard/js/sb-admin-2.min.js"></script>
    </body>
</html>
<%  } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>
