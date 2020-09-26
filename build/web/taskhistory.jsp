<%-- 
    Document   : taskhistory
    Created on : 3/03/2020, 9:04:35 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="nombre" value="${miname}"></c:set>
<c:if test="${nombre == null}">
    <%  if ((int) session.getAttribute("IDROL") <= 2) {
            request.getRequestDispatcher("taskshistory?accion=Listargrupo").forward(request, response);
        } else if ((int) session.getAttribute("IDROL") > 2) {
            request.getRequestDispatcher("taskshistory?accion=ListTareas").forward(request, response);
        } %>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <%
            response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0);
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Historial de tareas</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="login/fonts/font-awesome-4.7borrar/css/font-awesome.min.css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navlateral.jsp" flush="true"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content"  class="bg-gray-100">
                    <jsp:include page="navsuperficie.jsp" flush="true"></jsp:include>
                        <div class="container-fluid">
                            <input type="hidden" id="hdtr">
                            <div class="card col-lg-12 mb-2">
                                <div class="mb-2"></div>
                                <div class="row bg-blanco rounded">

                                <c:set var="miname" value="${miname}"></c:set>
                                <c:forEach var="listus" items="${listmigrupo}">
                                    <c:set var="usuario" value="${listus.getNombres()}"></c:set>
                                    <c:if test="${miname == usuario}">
                                        <c:set var="color" value="bg-gradient-sidevar"></c:set>
                                    </c:if>
                                    <c:if test="${miname != usuario}">
                                        <c:set var="color" value="bg-encabezados"></c:set>
                                    </c:if> <!-- taskshistory?accion=ListTareas&usuario=${listus.getID()} -->
                                    <a class="col-xl-2 col-md-4 mb-2" href="#" onclick="listarTareasUsuario('${listus.getID()}')"> 
                                        <div class="card shadow h-100 ${color}">
                                            <div class="card-body">
                                                <div class="row no-gutters align-items-center">
                                                    <div class="mb-0 font-weight-bold text-gray-200 sansserif">${listus.getNombres()}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>

                            </div>
                            <div class="row" id="tareashistorial">
                                <!-- TAREAS DEL USUARIO -->

                            </div>
                        </div>
                        <div class="row col-lg-12">
                            <div class="card-body rounded bg-blanco">
                                <!-- Default Card Example -->
                                <div class="card mb-4">
                                    <div class="card-header py-2 bg-gray-200 text-white">
                                        <h6 class="sansserif m-0 text-gray-700">Nombre</h6>
                                    </div>
                                    <div id="nombretarea" class="rounded-bottom  card-body text-center p-3 border"> </div>
                                </div>
                                <div class="card shadow mb-4">
                                    <div class="card card-header py-2 d-flex flex-row align-items-center justify-content-between bg-gray-200">
                                        <h6 class="sansserif  m-0 font-weight-bold text-gray-700">Anotación</h6>
                                    </div>
                                    <div id="anotacion" class="rounded-bottom card-body border text-center">  </div>
                                </div>
                                <!-- Basic Card Example -->
                                <div class="card mb-2">
                                    <div class="card-header py-2 bg-gray-200 text-white">
                                        <h6 class="sansserif m-0 text-gray-700 ">Características</h6>
                                    </div>
                                    <div id="caracteristicas" class="rounded-bottom  card-body text-center p-3 border"> </div>
                                </div>
                            </div>
                            <div class="col-lg-7">
                                <div class="card shadow mb-4">
                                    <div class="card-body rounded bg-blanco">
                                        <div class="card p-1 mb-0">
                                            <div class="card p-1 mb-1 bg-gray-200">
                                                <h6  class="m-1 font-weight-bold text-gray-700 fas fa-calendar-alt ">&nbsp; <span class="sansserif"> Se asigno :</span>  <label id="fechaasignada"></label></h6>
                                            </div>
                                            <div class="card p-1 mb-1 bg-gray-200">
                                                <h6  class="m-1 font-weight-bold text-gray-700 fas fa-calendar-alt ">&nbsp;  <span class="sansserif">Inicio de la tarea :</span>  <label id="fechainicio"></label> </h6>
                                            </div>
                                            <div class="card p-1 mb-1 bg-gray-200">
                                                <h6  class="m-1 font-weight-bold text-gray-700 fas fa-calendar-alt ">&nbsp;  <span class="sansserif">Fecha de entrega :</span>  <label id="fechafinal"></label></h6>
                                            </div>
                                            <div class="card p-1 mb-1 bg-gray-200">
                                                <h6 class="m-1 font-weight-bold text-gray-700 fas fa-calendar-alt ">&nbsp;  <span class="sansserif">Fecha en que se aprobo :</span>  <label id="fechaaprobacion"></label></h6>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <label id="infohistorial"></label>
                                <div id="divbotones" class="col-sm-4 p-2 m-0 eye">
                                    <a href="#"  data-toggle="modal"  data-target="#recoveryTarea" class="btn bg-gradient-blue btn-icon-split mb-2">
                                        <span class="icon text-white" data-toggle="tooltip" title="Eliminar" >
                                            <i class="fas fa-redo"></i>
                                            Restaurar
                                        </span>
                                    </a>
                                    <a href="#"  data-toggle="modal"  data-target="#deleteTarea" class="btn bg-gradient-danger btn-icon-split mb-2">
                                        <span class="icon text-white" data-toggle="tooltip" title="Eliminar" >
                                            <i class="fas fa-trash"></i>
                                            Eliminar
                                        </span>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <!-- Contenedor para confirmacion sobre aprobacion de la tarea  -->


                        <!------- CONTENEDOR MODAL --------->
                        <!-- Contenedor para confirmacion de eliminar una tarea  -->
                        <div class="modal fade" id="deleteTarea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content ">
                                    <div class="modal-header bg-gradient-danger">
                                        <h5 class="modal-title text-gray-100" id="exampleModalLabel">Desea eliminar  <label id="nombretarea"></label> ?</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn bg-gradient-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar</button>
                                        <button class="btn bg-gradient-danger text-gray-100" type="button" onclick="eliminarHistorial()"  data-dismiss="modal">Eliminar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!---->
                        <div class="modal fade" id="recoveryTarea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content ">
                                    <div class="modal-header bg-gradient-success">
                                        <h5 class="modal-title text-gray-100" id="exampleModalLabel">¿ Confirma la restauración de  <label id="nombretarea"></label> ?</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-footer">
                                        <button class="btn bg-gradient-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar.</button>
                                        <button class="btn bg-gradient-success text-gray-100" type="button"  data-dismiss="modal" onclick="restaurarTarea()">Restaurar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!----- FIN CONTEBNEDORES MODAL ------------>
                    </div>
                </div>
            </div>
        </div>
        <!-- Bootstrap core JavaScript-->
        <!--
        <script src="dashboard/js/demo/chart-area-demo.js"></script>
        <script src="dashboard/js/demo/chart-pie-demo.js"></script>
        <script src="dashboard/vendor/jquery-easing/jquery.easing.min.js"></script>
        <script src="dashboard/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="dashboard/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="dashboard/js/demo/datatables-demo.js"></script>
        -->
        <!--JavaScript para barra nav nav lateral-->
        <script src="dashboard/js/tasks.js"></script>
        <script src="dashboard/vendor/jquery/jquery.min.js"></script>
        <script src="dashboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="dashboard/js/sb-admin-2.min.js"></script>
    </body>
</html>
<% } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>
