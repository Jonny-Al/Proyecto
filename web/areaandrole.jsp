<%-- 
    Document   : areaandrole
    Created on : 3/03/2020, 8:34:59 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<% if ((int) session.getAttribute("IDROL") == 1) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
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
            <% request.getRequestDispatcher("roleandaddres?").forward(request, response);%>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Areas y roles</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navlateral.jsp" flush="true"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <!-- Main Content -->
                    <div id="content" class="bg-gray-100">
                    <jsp:include page="navsuperficie.jsp" flush="true"></jsp:include>

                        <div class="container">
                            <div class="card o-hidden border-0 shadow-lg my-5 bg-fond-transparent">
                                <div class="border-bottom-purple"></div>
                                <div class="card-body p-0">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="p-5 border border-purple rounded bg-blanco">
                                                <div class="card-body ">
                                                    <div class="col-lg-12">
                                                        <div class="form-group row">
                                                            <div class="col-sm-6 mb-3 mb-sm-0">
                                                                <i class="fas fa-2x sansserif text-decoration-none" style="font-size: 22px" >Areas existentes</i>
                                                                <div class="mb-2"></div>
                                                                <select id="areas" name="area" class="custom-select p-3 mb-2 sansserif" multiple>
                                                                <c:forEach var="area" items="${areas}">
                                                                    <option value="${area.getIdArea()}" onclick="llamarArea('${area.getAreaNombre()}');">${area.getAreaNombre()}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <div class="form-group row eye">
                                                                <div class="col-sm-12 mb-3 mb-sm-0">
                                                                    <input id="hd" type="hidden">
                                                                    <input id="txtarearol" type="text" class="form-control form-control-user input100" name="txtupdatearea"  pattern="[a-zA-Z  - () ρασ]{2,100}">
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-sm-6">
                                                            <i class="fas sansserif text-decoration-none" style="font-size: 22px">Roles existentes</i>
                                                            <div class="mb-2"></div>
                                                            <select id="roles" name="rol" class="custom-select p-3 mb-2 sansserif" multiple>
                                                                <c:forEach var="rol" items="${roles}">
                                                                    <option value="${rol.getIdRol()}" onclick="llamarRol('${rol.getRolNombre()}');" >${rol.getRolNombre()}</option>
                                                                </c:forEach>
                                                            </select>
                                                            <div class="form-group row eye">
                                                                <div class="col-sm-3 mb-2 mb-sm-0">
                                                                    <button id="btnupdate" type="button" class="btn bg-gradient-primary text-white" onclick="updateAreaORol();">Actualizar</button>
                                                                </div>
                                                                <div class="col-sm-1">
                                                                    <button id="btneliminar" type="button" class="btn bg-gradient-danger text-white" data-toggle="modal" data-target="#modaleliminar">Eliminar</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="mb-2"></div>
                                                    <div id="inforolesyareas" > </div>
                                                </div>
                                            </div>
                                        </div> 
                                    </div>
                                </div>
                            </div>
                            <div class="border-bottom-purple"></div>
                        </div>
                        <div class="card o-hidden border-0 shadow-lg my-5 bg-fond-transparent">
                            <div class="border-bottom-purple"></div>
                            <div class="card-body p-0">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="p-5 border border-purple rounded bg-blanco">
                                            <div class="col-lg-12">
                                                <i class="fas sansserif text-decoration-none" style="font-size: 22px">Registro de areas y roles</i>
                                                <div class="mb-3"></div>
                                                <div class="form-group row">
                                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                                        <select class="browser-default custom-select mb-2 sansserif" name="ddlarea" id="ddlopcion" >
                                                            <option  value="0">Seleccione lo que desea registrar</option>
                                                            <option  value="1" > Area </option>
                                                            <option  value="2" > Rol </option>
                                                        </select>
                                                        <div class="col-sm-6 mb-3 mb-sm-0">
                                                            <label id="inforegistro"></label>
                                                        </div>
                                                    </div>
                                                    <!------------------------>
                                                    <div class="col-sm-6">
                                                        <div class=" validate-input m-b-23">
                                                            <input id="txtnewregistro" type="text"  class="form-control form-control-user  input100" name="txtnewrol"   pattern="[a-zA-Z - () ρασ ]{2,100}">
                                                        </div>
                                                        <div class="mb-3"></div>
                                                        <div class="mb-3 mb-sm-0 float-right">
                                                            <button  type="button" class="btn btn-primary" onclick="registrar();">Registrar</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div> 
                                    </div>
                                </div>
                            </div>
                            <div class="border-bottom-purple"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- MODALES -->

        <div id="modaleliminar" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-gradient-danger">
                        <h5 class="modal-title text-white">Eliminar</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body mb-1">
                        <p>Confirma eliminar</p>  <label id="lblmodal"></label>
                    </div>
                    <label id="lblmodaleliminar"></label>
                    <div class="modal-footer">
                        <button id="btnmodaleliminar" type="button" class="btn bg-gradient-danger text-white" onclick="eliminar()">Eliminar</button>
                        <button type="button" class="btn bg-gradient-secondary text-white" data-dismiss="modal">Cancelar</button>
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

        <script src="dashboard/js/rolandareas.js"></script>
        <script src="dashboard/vendor/jquery/jquery.min.js"></script>
        <script src="dashboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="dashboard/js/sb-admin-2.min.js"></script>
    </body>
</html>
<% } else {
        request.getRequestDispatcher("error404.jsp").forward(request, response);
    }%> 
<% } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%> 

