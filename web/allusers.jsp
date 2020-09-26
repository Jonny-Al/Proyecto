<%-- 
    Document   : alluser
    Created on : 3/03/2020, 8:33:06 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <%  response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0); %>
        <c:set var="nombre" value="${miname}"></c:set>
        <c:if test="${nombre == null}">
            <% request.getRequestDispatcher("users?menu=Consultar&accion=Listar").forward(request, response); %>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Personal</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navlateral.jsp" flush="true"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <!-- Main Content -->
                    <div id="content" class="bg-gray-200">
                        <!-- Topbar -->
                    <jsp:include page="navsuperficie.jsp" flush="true"></jsp:include>
                        <div class="container-fluid">
                            <!--Contenedor de las demas paginas--> 
                            <div class="card shadow mb-3">
                                <div class="card-body rounded bg-blanco">
                                    <div id="accordion">
                                        <div class="card p-2 bg-white text-white" id="headingOne">
                                            <h5 class="mb-0">
                                            <% if ((int) session.getAttribute("IDROL") < 3) { %>
                                            <button id="usinactivos" class="btn bg-gradient-blue text-white hover-aqua" data-toggle="modal" data-target="#modalAddUsuario" title="Registrar usuario">
                                                <svg width="22" height="22" viewBox="0 0 16 16" class="bi bi-person-plus" fill="currentColor">
                                                <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zM13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
                                                </svg>
                                            </button> 
                                            <button id="usinactivos" class="btn bg-gradient-blue text-white hover-aqua" data-toggle="modal" data-target="#modalCargar" title="Cargar usuarios">
                                                <svg width="21" height="21" viewBox="0 0 16 16" class="bi bi-cloud-arrow-up " fill="currentColor">
                                                <path fill-rule="evenodd" d="M4.406 3.342A5.53 5.53 0 0 1 8 2c2.69 0 4.923 2 5.166 4.579C14.758 6.804 16 8.137 16 9.773 16 11.569 14.502 13 12.687 13H3.781C1.708 13 0 11.366 0 9.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383zm.653.757c-.757.653-1.153 1.44-1.153 2.056v.448l-.445.049C2.064 6.805 1 7.952 1 9.318 1 10.785 2.23 12 3.781 12h8.906C13.98 12 15 10.988 15 9.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 4.825 10.328 3 8 3a4.53 4.53 0 0 0-2.941 1.1z"/>
                                                <path fill-rule="evenodd" d="M7.646 5.146a.5.5 0 0 1 .708 0l2 2a.5.5 0 0 1-.708.708L8.5 6.707V10.5a.5.5 0 0 1-1 0V6.707L6.354 7.854a.5.5 0 1 1-.708-.708l2-2z"/>
                                                </svg>
                                            </button> 
                                            <button type="button" class="btn bg-gradient-blue text-white hover-aqua"  data-toggle="modal" data-target="#mdReporte" title="Generar reporte">
                                                <svg width="20" height="20" viewBox="0 0 16 16" class="bi bi-cloud-download" fill="currentColor">
                                                <path fill-rule="evenodd" d="M4.406 1.342A5.53 5.53 0 0 1 8 0c2.69 0 4.923 2 5.166 4.579C14.758 4.804 16 6.137 16 7.773 16 9.569 14.502 11 12.687 11H10a.5.5 0 0 1 0-1h2.688C13.979 10 15 8.988 15 7.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 2.825 10.328 1 8 1a4.53 4.53 0 0 0-2.941 1.1c-.757.652-1.153 1.438-1.153 2.055v.448l-.445.049C2.064 4.805 1 5.952 1 7.318 1 8.785 2.23 10 3.781 10H6a.5.5 0 0 1 0 1H3.781C1.708 11 0 9.366 0 7.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383z"/>
                                                <path fill-rule="evenodd" d="M7.646 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V5.5a.5.5 0 0 0-1 0v8.793l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>
                                                </svg>
                                            </button> 
                                            <% } %>
                                            <button id="usactivos" class="btn bg-gradient-primary text-gray-200" data-toggle="collapse" data-target="#collapseActivos" aria-expanded="true" aria-controls="collapseOne">
                                                Activos
                                            </button>
                                            <button class="btn bg-gradient-info collapsed text-gray-200" data-toggle="collapse" data-target="#collapseInactivos" aria-expanded="false" aria-controls="collapseThree">
                                                Inactivos
                                            </button>  
                                        </h5>
                                    </div>  
                                    <div class="card">
                                        <!-- USUARIOS ACTIVOS -->
                                        <div id="collapseActivos" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                                            <div class="card-body">
                                                <div class="table-striped">
                                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                                        <thead class="thead-dark text-gray-100">
                                                            <tr>
                                                                <th>Nombres</th>
                                                                <th>Apellidos</th>
                                                                <th>Teléfono</th>
                                                                <th>Correo</th>
                                                                <th>Cargo</th>
                                                                <th>Área</th>
                                                                    <% if ((int) session.getAttribute("IDROL") < 3) { %>
                                                                <th></th> 
                                                                    <% } %>
                                                            </tr>
                                                        </thead>
                                                        <tfoot>
                                                            <tr>
                                                                <th>Nombres</th>
                                                                <th>Apellidos</th>
                                                                <th>Teléfono</th>
                                                                <th>Correo</th>
                                                                <th>Cargo</th>
                                                                <th>Área</th>
                                                                    <% if ((int) session.getAttribute("IDROL") < 3) { %>
                                                                <th></th>  
                                                                    <% }%>
                                                            </tr>
                                                        </tfoot>
                                                        <tbody>
                                                            <c:forEach var="us" items="${usactivos}">
                                                                <tr id="tra${us.getID()}tra">
                                                                    <td>${us.getNombres()}</td>
                                                                    <td>${us.getApellidos()}</td>
                                                                    <td>${us.getTelefono()}</td>
                                                                    <td>${us.getCorreo()}</td>
                                                                    <td>${us.getRolNombre()}</td>
                                                                    <td>${us.getAreaNombre()}</td>
                                                                    <% if ((int) session.getAttribute("IDROL") < 3) { %>
                                                                    <td>
                                                                        <button  type="button"  class="btn text-info hover-gradient-aqua" title="Editar" onclick="editarUsuario('${us.getID()}');">
                                                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pen" fill="currentColor">
                                                                            <path fill-rule="evenodd" d="M13.498.795l.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"/>
                                                                            </svg>
                                                                        </button>
                                                                        <button  type="button"  class="btn text-warning hover-gradient-warning" title="Deshabilitar" onclick="confirmarInactivar('${us.getID()}', '${us.getNombres()}');" >
                                                                            <svg  width="20" height="20" viewBox="0 0 16 16" class="bi bi-person-dash" fill="currentColor">
                                                                            <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zM11 7.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
                                                                            </svg>
                                                                        </button>
                                                                        <button  type="button"  class="btn text-danger hover-gradient-danger" title="Eliminar" onclick="confirmaEliminar('${us.getID()}', '${us.getNombres()}')">
                                                                            <svg width="20" height="20" viewBox="0 0 16 16" class="bi bi-person-x" fill="currentColor">
                                                                            <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zm1.146-7.85a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                                                            </svg>
                                                                        </button>
                                                                    </td>
                                                                    <% } %>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- USUARIOS INCATIVOS -->
                                    <div class="card">
                                        <div id="collapseInactivos" class="collapse" aria-labelledby="headingThree" data-parent="#accordion">
                                            <div class="card-body">
                                                <div class="table-striped">
                                                    <table class="table table-bordered" id="dataTabletwo" width="100%" cellspacing="0">
                                                        <thead class="thead-dark text-gray-100">
                                                            <tr>
                                                                <th>Nombres</th>
                                                                <th>Apellidos</th>
                                                                <th>Teléfono</th>
                                                                <th>Correo</th>
                                                                <th>Cargo</th>
                                                                <th>Área</th>
                                                                    <% if ((int) session.getAttribute("IDROL") < 3) { %>
                                                                <th> </th> 
                                                                    <% } %>
                                                            </tr>
                                                        </thead>
                                                        <tfoot>
                                                            <tr>
                                                                <th>Nombres</th>
                                                                <th>Apellidos</th>
                                                                <th>Teléfono</th>
                                                                <th>Correo</th>
                                                                <th>Cargo</th>
                                                                <th>Área</th>
                                                                    <% if ((int) session.getAttribute("IDROL") < 3) { %>
                                                                <th></th>  
                                                                    <% }%>
                                                            </tr>
                                                        </tfoot>
                                                        <tbody >
                                                            <c:forEach var="us" items="${usinactivos}">
                                                                <tr id="trd${us.getID()}trd" >
                                                                    <td>${us.getNombres()}</td>
                                                                    <td>${us.getApellidos()}</td>
                                                                    <td>${us.getTelefono()}</td>
                                                                    <td>${us.getCorreo()}</td>
                                                                    <td>${us.getRolNombre()}</td>
                                                                    <td>${us.getAreaNombre()}</td>
                                                                    <% if ((int) session.getAttribute("IDROL") < 3) { %>
                                                                    <td>
                                                                        <button  type="button"  class="btn text-info hover-gradient-aqua" title="Editar" onclick="editarUsuario('${us.getID()}');">
                                                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pen" fill="currentColor">
                                                                            <path fill-rule="evenodd" d="M13.498.795l.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"/>
                                                                            </svg>
                                                                        </button>
                                                                        <button  type="button"  class="btn text-success hover-gradient-success" title="Habilitar" onclick="confirmaActivar('${us.getID()}', '${us.getNombres()}');" >
                                                                            <svg width="20" height="20" viewBox="0 0 16 16" class="bi bi-person-check" fill="currentColor">
                                                                            <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zm4.854-7.85a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L12.5 7.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                                                                            </svg>
                                                                        </button>
                                                                        <button  type="button"  class="btn text-danger hover-gradient-danger" title="Eliminar" onclick="confirmaEliminar('${us.getID()}', '${us.getNombres()}')">
                                                                            <svg width="20" height="20" viewBox="0 0 16 16" class="bi bi-person-x" fill="currentColor">
                                                                            <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zm1.146-7.85a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                                                            </svg>
                                                                        </button>
                                                                    </td>
                                                                    <% } %>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--------------------------  MODALES  ---------------------------->
                                <div id="modalalert" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-warning fas fa-exclamation-triangle">
                                                <h5 class="modal-title text-center" id="exampleModalLongTitle"></h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <h5 class="sansserif text-gray-700" style="font-size:14px;"> Usted no cuenta con permisos</h5> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" id="hdus">
                                <div class="modal fade" id="mdeliminaruser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-danger">
                                                <h5 class="modal-title text-gray-200" id="exampleModalLabel">Eliminar usuario</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                ¿ Confirma eliminar a <strong id="nmuserelimina"></strong> ? no podra deshacer los cambios.</h5>
                                            </div>
                                            <label id="labelelimina" class=""></label>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                                <button id="btneliminar" type="button" class="btn btn-danger text-gray-100">Eliminar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!----  MODAL PARA CONFIRMACION INACTIVAR USUARIO------->
                                <div class="modal fade" id="inactivarUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-gradient-warning">
                                                <h5 class="modal-title text-gray-200" id="exampleModalLabel">Deshabilitar usuario</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                ¿ Confirma deshabilitar a <strong id="nmusdeshabilita"></strong> ?</h5>
                                            </div>
                                            <div class="modal-footer">
                                                <button class="btn btn-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar</button>
                                                <button id="btninactiva"  type="button" class="btn btn-warning text-gray-100">Deshabilitar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!----  MODAL PARA CONFIRMACION INACTIVAR USUARIO------->
                                <div class="modal fade" id="activarUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-success">
                                                <h5 class="modal-title text-gray-200" id="exampleModalLabel">Activar usuario</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                ¿ Confirma habilitar a <strong id="nmushabilita"></strong> ?</h5>
                                            </div>
                                            <div class="modal-footer">
                                                <button class="btn bg-gradient-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar.</button>
                                                <button id="btnactiva"  type="button" class="btn btn-success text-gray-100" data-dismiss="modal">Habilitar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!------------------------------------------------------------------------------------->
                                <div id="modalAddUsuario" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-lg modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header bg-sidevar">
                                                <svg width="30" height="30" viewBox="0 0 16 16" class="bi bi-person-plus text-gray-300" fill="currentColor">
                                                <path fill-rule="evenodd" d="M8 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H1s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C9.516 10.68 8.289 10 6 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10zM13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5z"/>
                                                </svg>
                                                <h4 class="text-gray-300">  &nbsp; <span class="sansserif" style="font-size:18px;"> Registrar usuario </span> </h4>
                                                <div class="text-center text-right">
                                                    <div id="loading1" class="" role="status"> </div>
                                                    <div id="loading2" class="" role="status"> </div>
                                                    <div id="loading3" class="" role="status"> </div>
                                                    <div id="loading4" class="" role="status"> </div>
                                                </div>
                                                <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="p-5  rounded-bottom">
                                                <!--  action="users?menu=Empleado" method="POST" -->
                                                <div class="form-group row">
                                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                                        <div class=" validate-input m-b-23">
                                                            <input id="txtnombres" class="form-control form-control-user input100 border "   name="txtnombres" placeholder="Nombres" pattern="[a-zA-Z ñáó]{2,50}">
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class=" validate-input m-b-23 ">
                                                            <input id="txtapellidos" class="form-control form-control-user input100"  name="txtapellidos" placeholder="Apellidos" pattern="[a-zA-Z  ñáó]{2,50}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                                        <div class=" validate-input m-b-23" >
                                                            <input id="txttelefono" type="text" class="form-control form-control-user  input100"  name="txttelefono" placeholder="Teléfono" pattern="[0-9 + ]{2,50}">
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <div class=" validate-input m-b-23" data-validate="El correo es obligatorio">
                                                            <input id="txtcorreo" type="email" class="form-control form-control-user  input100" name="txtcorreo" placeholder="Correo electrónico" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                                        <select  class="browser-default custom-select" name="ddlarea" id="ddlarea" >
                                                            <option  value="0">Áreas</option>
                                                            <c:forEach var="area" items="${areas}">
                                                                <option  value="${area.getIdArea()}" > ${area.getAreaNombre()} </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-sm-6">
                                                        <select required class="browser-default custom-select"  name="ddlrol" id="ddlrol">
                                                            <option  value="0">Cargos</option>
                                                            <c:forEach var="rol" items="${roles}">
                                                                <option  value="${rol.getIdRol()}" > ${rol.getRolNombre()} </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div id="alertcampos">
                                                </div>
                                                <div id="mail">
                                                    <label id="lblmail"></label>
                                                </div>
                                                <div class="float-right">
                                                    <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                                                    <input id="btnadd" type="submit" name="accion" value="Agregar" class="btn bg-sidevar text-gray-100" >
                                                </div>
                                            </div> 
                                        </div>
                                    </div>
                                </div>
                                <div id="modalUpdate" class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-info">
                                                <h5 class="fas fa-history fa-1x modal-title text-gray-200" id="exampleModalLabel" >  <span class="sansserif" style="font-size:16px;">Actualización de datos </span></h5>
                                                <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="col-sm-9 mb-2">
                                                    <input id="txtcorreoedit" type="email" class="form-control"   name="txtcorreo" placeholder="Correo electrónico">
                                                </div>
                                                <div class="col-sm-9 mb-3 mb-sm-0">
                                                    <select id="rolnew"  class="browser-default custom-select"  name="rolnew">
                                                        <option value="0">Cargos</option>
                                                        <c:forEach var="rol" items="${roles}">
                                                            <option  value="${rol.getIdRol()}" > ${rol.getRolNombre()} </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="mb-2"></div>
                                                <div class="col-sm-9 mb-2">
                                                    <select id="areanew"  class="browser-default custom-select" name="areanew">
                                                        <option value="0">Áreas</option>
                                                        <c:forEach var="area" items="${areas}">
                                                            <option  value="${area.getIdArea()}" > ${area.getAreaNombre()} </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div id="usupdate">
                                            </div>
                                            <div class="modal-footer">
                                                <button id=""  type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                                                <button id="btnupdate" type="button" name="accion"  class="btn btn-info text-gray-100">Actualizar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--- modal para carga masiva de usuarios  --->
                                <div id="modalCargar" class="modal" tabindex="-1" role="dialog">
                                    <div class="modal-dialog modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-sidevar">
                                                <h5 class="modal-title text-gray-100">Cargar usuarios</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p class="mb-4 text-gray-700">Para realizar una carga de usuarios desde un documento excel debe tener en cuenta las siguientes recomendaciones
                                                    <a href="#"  data-toggle="modal" data-target="#mdejemploexcelus">aqui.</a> </p>
                                                <div class="form-group">
                                                    <input  name="file" id="file" type="file" class="form-control-file">
                                                </div> 
                                            </div>
                                            <label id="lblcarga"></label>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                                                <button id="btncargausuarios" type="button" class="btn bg-sidevar text-white">Cargar</button> 
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <div id="mdejemploexcelus" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content ">
                                            <p class="mb-2 m-2"> Para realizar una carga de usuarios desde un documento excel debe tener en cuenta la
                                                extensión xlsx ejemplo: Excelusuarios.xlsx, y mantener el orden de las celdas como se indica en la imagen, en caso contrario
                                                a las recomendaciones podrian crearse los usuarios con datos incorrectos.
                                                <br> </p>
                                            <img src="dashboard/Imagenes/Ejmexceluser.png">
                                        </div>
                                        <div class="modal-footer bg-blanco">
                                            <button type="button" class="btn btn-success text-gray-100" data-dismiss="modal">Aceptar</button>
                                        </div>
                                    </div>
                                </div>
                                <!-- MODAL QUE DESPLIEGA LISTA DE LOS USUARIOS QUE NO SE AGREGARON -->
                                <!-- Modal -->
                                <div class="modal fade" id="mdnoagregados" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-danger">
                                                <h5 class="modal-title fas text-gray-100 sansserif" style="font-size:18px" id="exampleModalLongTitle">Información de carga</h5>
                                                <button type="button" id="btnrefresh" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="mb-2 fas text-gray-700 sansserif">
                                                    Los usuarios que no esten en la lista recibieron código de confirmación en sus correos electrónicos.
                                                </div>
                                                <hr>
                                                <div class="mb-2 fas text-gray-800">
                                                    <div class="mb-2 sansserif">
                                                        Los siguientes datos no se registraron por uno de los siguientes motivos.
                                                    </div>
                                                    <div class="text-gray-500">
                                                        <span class="fas text-gray-600 mb-1 fa-1x col-sm-8 sansserif"> No cumple con el formato correcto </span> 
                                                        <span class="fas text-gray-600 mb-1 fa-1x col-sm-6 sansserif"> Ya esta registrado el correo </span>
                                                    </div>
                                                </div>
                                                <div class="fas text-danger">
                                                    <div id="listnoagregados" class="list-group" style=" font-size: 18px"></div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal" id="btnrefreshh">Cerrar</button>
                                                <button type="button" class="btn btn-danger text-gray-100" id="btnrefreshhh">Aceptar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- MODAL DE REPORTE -->
                                <div class="modal fade" id="mdReporte" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                    <div class="modal-dialog " role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-sidevar">
                                                <h5 class="modal-title text-gray-200 sansserif" id="exampleModalLongTitle" style="font-size:18px">Reporte de usuarios</h5>
                                                <button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body text-center">
                                                <div class="mb-2">
                                                    <label class="text-gray-700">Selecione la opción en que desea el reportes de los usuarios.</label>
                                                </div>
                                                <div class="custom-control custom-radio custom-control-inline">
                                                    <input type="radio" id="customRadioInline1" name="customRadioInline1" class="custom-control-input"  onclick="opcionRadio('Todos')">
                                                    <label class="custom-control-label" for="customRadioInline1">Todos</label>
                                                </div>
                                                <div class="custom-control custom-radio custom-control-inline">
                                                    <input type="radio" id="customRadioInline2" name="customRadioInline1" class="custom-control-input" onclick="opcionRadio('Activos')">
                                                    <label class="custom-control-label" for="customRadioInline2">Activos</label>
                                                </div>
                                                <div class="custom-control custom-radio custom-control-inline mb-2">
                                                    <input type="radio" id="customRadioInline3" name="customRadioInline1" class="custom-control-input" onclick="opcionRadio('Inactivos')">
                                                    <label class="custom-control-label" for="customRadioInline3">Inactivos</label>
                                                </div>
                                                <!-- <div  class="eye">
                                                      <hr>
                                                      <div class="mb-2">
                                                          <label class="text-gray-700">Seleccione el documento en el que desea el reporte.</label>
                                                      </div>
                                                      <a title="Descargar">
                                                          <img src="dashboard/Imagenes/icons/Pdf.png" alt="..." class="rounded bg-gradient-danger p-2">
                                                      </a>
                                                      <button  class="btn">
                                                          <img src="dashboard/Imagenes/icons/Excel.png" alt="..." class="rounded bg-gradient-success p-2">
                                                      </button>
                                                <a title="Descargar" href="" onclick="reporteUsuarios()">
                                                     <img src="dashboard/Imagenes/icons/Excel.png" alt="..." class="rounded bg-gradient-success p-2">
                                                 </a>
                                            </div> -->
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary text-gray-200" data-dismiss="modal">Cerrar</button>
                                                <button id="btnexcel" type="button" class="btn bg-sidevar text-gray-200 eye">Generar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="modalcodigoalterno" class="modal" tabindex="-1" role="dialog">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-sidevar">
                                                <h5 class="modal-title text-gray-200">Confirmación de código</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="form-group">
                                                    <div id="divcodigo" class="col-sm-12">
                                                        <div class="text-gray-700 mb-2">
                                                            Realizo un cambio en el correo electrónico, ingresa el código que se envío al correo que ingresaste para continuar con la actualización de información.
                                                        </div>
                                                        <div class=" validate-input m-b-23" >
                                                            <input id="txtcodupdate" type="text" title="Correo alternativo" class="form-control form-control-user input100"  placeholder="Codigo de confirmacion">
                                                        </div>
                                                    </div>
                                                </div> 
                                            </div>
                                            <label id="lblcodigo" class="mb-1"></label>
                                            <div class="modal-footer">
                                                <button id="btnupdatedatoscod" type="button" class="btn bg-sidevar text-gray-100">Comprobar</button> 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal fade" id="modalinfousuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header bg-info">
                                                <h5 class="modal-title text-gray-200" id="exampleModalLabel">Información de registro</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div id="mensajeusuarios" class="mb-4">
                                                    <br>
                                                </div>
                                                <label class="text-right float-right"> Gracias. </label>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-info" data-dismiss="modal">Aceptar</button>
                                            </div>
                                        </div>
                                    </div>
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
        <script src="dashboard/js/jquery-3.5.1.js"></script>
        <script src="dashboard/js/users.js"></script>
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
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>
