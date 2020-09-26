<%-- 
    Document   : allobjects
    Created on : 22/05/2020, 6:01:19 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<% if ((int) session.getAttribute("IDROL") < 3) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%  response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0); %>
        <c:set var="nombre" value="${miname}"></c:set>
        <c:if test="${nombre == null}">
            <% request.getRequestDispatcher("objectsofevents?accion=Allobjects").forward(request, response); %>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Implementos</title>
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

                        <!-- Topbar -->
                    <jsp:include page="navsuperficie.jsp" flush="true"></jsp:include>
                        <!--Contenedor fluid-->
                        <div class="container-fluid">
                            <div class="dropdown no-arrow bg-blanco rounded mb-2 col-sm-12 ">
                                <div class="card p-2 bg-white" id="headingOne">
                                    <div class="mb-0">
                                        <button id="usinactivos" class="btn bg-gradient-sidevar text-white hover-aqua " data-toggle="modal" data-target="#mdaddObjeto">
                                            <svg width="20" height="20" viewBox="0 0 16 16" class="bi bi-plus-circle " fill="currentColor">
                                            <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"></path>
                                            <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"></path>
                                            </svg>
                                        </button> 
                                        <button type="button" class="btn bg-gradient-sidevar text-gray-100 hover-aqua" data-toggle="modal" data-target="#mdcargaobjetos" >
                                            <svg width="21" height="21" viewBox="0 0 16 16" class="bi bi-cloud-arrow-up" fill="currentColor">
                                            <path fill-rule="evenodd" d="M4.406 3.342A5.53 5.53 0 0 1 8 2c2.69 0 4.923 2 5.166 4.579C14.758 6.804 16 8.137 16 9.773 16 11.569 14.502 13 12.687 13H3.781C1.708 13 0 11.366 0 9.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383zm.653.757c-.757.653-1.153 1.44-1.153 2.056v.448l-.445.049C2.064 6.805 1 7.952 1 9.318 1 10.785 2.23 12 3.781 12h8.906C13.98 12 15 10.988 15 9.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 4.825 10.328 3 8 3a4.53 4.53 0 0 0-2.941 1.1z"/>
                                            <path fill-rule="evenodd" d="M7.646 5.146a.5.5 0 0 1 .708 0l2 2a.5.5 0 0 1-.708.708L8.5 6.707V10.5a.5.5 0 0 1-1 0V6.707L6.354 7.854a.5.5 0 1 1-.708-.708l2-2z"/>
                                            </svg>
                                        </button> 
                                        <button type="button" class="btn bg-gradient-sidevar text-gray-100 hover-aqua" data-toggle="modal" data-target="#mdreporte" >
                                            <svg width="20" height="20" viewBox="0 0 16 16" class="bi bi-cloud-download" fill="currentColor">
                                            <path fill-rule="evenodd" d="M4.406 1.342A5.53 5.53 0 0 1 8 0c2.69 0 4.923 2 5.166 4.579C14.758 4.804 16 6.137 16 7.773 16 9.569 14.502 11 12.687 11H10a.5.5 0 0 1 0-1h2.688C13.979 10 15 8.988 15 7.773c0-1.216-1.02-2.228-2.313-2.228h-.5v-.5C12.188 2.825 10.328 1 8 1a4.53 4.53 0 0 0-2.941 1.1c-.757.652-1.153 1.438-1.153 2.055v.448l-.445.049C2.064 4.805 1 5.952 1 7.318 1 8.785 2.23 10 3.781 10H6a.5.5 0 0 1 0 1H3.781C1.708 11 0 9.366 0 7.318c0-1.763 1.266-3.223 2.942-3.593.143-.863.698-1.723 1.464-2.383z"/>
                                            <path fill-rule="evenodd" d="M7.646 15.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 14.293V5.5a.5.5 0 0 0-1 0v8.793l-2.146-2.147a.5.5 0 0 0-.708.708l3 3z"/>
                                            </svg>
                                        </button> 
                                    </div>
                                </div>
                            </div>
                            <!-- Page Heading -->
                            <div class="card">
                                <div class="card-body rounded bg-blanco">
                                    <div class="table-striped">
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <thead class="thead-gradientblue text-gray-100">
                                                <tr>
                                                    <th>Objeto</th>
                                                    <th>Marca</th>
                                                    <th>Estado</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tfoot>
                                                <tr>
                                                    <th>Objeto</th>
                                                    <th>Marca</th>
                                                    <th>Estado</th>
                                                    <th></th>
                                                </tr>
                                            </tfoot>
                                            <tbody>  
                                            <c:forEach var="obj" items="${objstodos}">
                                                <tr id="trobjeto${obj.getIdobjeto()}">
                                                    <td>${obj.getObjetonombre()}</td>
                                                    <td>${obj.getMarca()}</td>
                                                    <td>${obj.getEstado()} </td>
                                                    <td>
                                                        <button type="button" class="btn rounded  text-info hover-gradient-aqua" onclick="editarObjeto('${obj.getIdobjeto()}')">
                                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pen" fill="currentColor">
                                                            <path fill-rule="evenodd" d="M13.498.795l.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z"></path>
                                                            </svg>
                                                        </button>
                                                        <button type="button" class="btn hover-gradient-danger text-danger" onclick="comfirmaEliminarObj('${obj.getIdobjeto()}', '${obj.getObjetonombre()}')">
                                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-trash" fill="currentColor">
                                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                                            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                                            </svg>
                                                        </button>
                                                    </td>
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
        <!-- MODALES -->

        <!--- modal para rgistrar un objeto -->
        <div class="modal fade" id="mdaddObjeto" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="modal-title text-gray-200" id="exampleModalLongTitle">Registrar objeto</h5>
                        <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="col-sm-12 mb-1">
                            <div class="m-b-23">
                                <span class="text-danger float-right">*</span> 
                                <input id="txtnombreobj" class="form-control form-control-user input100"  name="txtnombreobj" placeholder="Nombre de objeto" pattern="[a-zA-Z ñáó]{2,50}">
                            </div>
                        </div>
                        <div class="col-sm-12 mb-1">
                            <div class="m-b-23 ">
                                <span class="text-danger float-right">*</span> 
                                <input id="txtmarcaobj" class="form-control form-control-user input100"  name="txtmarcaobj" placeholder="Marca">
                            </div>
                        </div>
                        <div class="col-sm-12 mb-1">
                            <div class="m-b-23">
                                <span class="text-danger float-right">*</span> 
                                <input id="txtserialobj" class="form-control form-control-user input100"  name="txtserialobj" placeholder="Serial">
                            </div>
                        </div>
                        <div class="card mb-1 col-sm-11 sansserif">
                           
                            <div class="text-gray-700"> Características  <span class="text-danger">*</span>  </div> 
                            <textarea id="txtcaracte" name="txtcaracte" class="card border border-purple m-1" ></textarea>
                        </div>

                        <div class="col-sm-5 mb-2">
                            <div class="m-b-23">
                                <select id="ddlestado" class="browser-default custom-select" name="ddlestado">
                                    <option value="0">Estado</option>
                                    <c:forEach var="est" items="${estados}">
                                        <option  value="${est.getIdestado()}" > ${est.getEstado()} </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div id="addobjnew"></div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cerrar</button>
                        <input  type="submit"  value="Registrar" class="btn btn-primary text-gray-200" onclick="addOjeto()">
                    </div>
                </div>
            </div>
        </div>
        <!-- modal para carga masiva de objeto -->

        <div id="mdcargaobjetos" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="modal-title text-gray-200">Cargar objetos</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p class="mb-4 sansserif">Para realizar una carga de objetos desde un documento excel debe tener las siguientes recomendaciones
                            <a href="#"  data-toggle="modal" data-target="#mdejemploexcelobjetos" class="sansserif">aqui.</a> </p>
                        <div class="form-group">
                            <input  name="file" id="file" type="file" class="form-control-file">
                        </div> 
                    </div>
                    <label id="lblcarga"></label>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary text-gray-100" onclick="cargarObjetos();">Cargar objetos</button> 
                    </div>

                </div>
            </div>
        </div>
        <!-- MODAL QUE MUESTRA EL EJEMPLO DE UN EXCEL DE OBJETO -->
        <div id="mdejemploexcelobjetos" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">

                <div class="modal-content ">
                    <p class="mb-2 m-2 sansserif"> Para realizar una carga de objetos desde un documento excel debe tener en cuenta la
                        extensión xlsx como ejemplo: Excelobjetos.xlsx y mantener el orden de las celdas como se indica en la imagen, en caso contrario
                        a las recomendaciones se podria hacer el registro de forma incorrecta.
                        <br> </p>
                    <img src="dashboard/Imagenes/Ejmexcelobj.png">
                </div>
                <div class="modal-footer bg-blanco">
                    <button type="button" class="btn btn-success text-gray-100" data-dismiss="modal">Aceptar</button>
                </div>
            </div>
        </div>

        <!-- modal para actualizar un objeto -->
        <div class="modal fade" id="mdactualizarobj" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-info">
                        <h5 class="modal-title text-gray-200" id="exampleModalLongTitle">Actualizar objeto</h5>
                        <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="col-sm-12 mb-2">
                            <div class="m-b-23">
                                <input id="txtnombreobjupd" type="text" class="form-control form-control-user input100"  name="txtnombreobj" placeholder="Nombre de objeto">
                            </div>
                        </div>
                        <div class="col-sm-12 mb-2">
                            <div class="m-b-23 ">
                                <input id="txtmarcaobjupd" type="text" class="form-control form-control-user input100"  name="txtmarcaobj" placeholder="Marca">
                            </div>
                        </div>
                        <div class="col-sm-12 mb-2">
                            <div class="m-b-23 ">
                                <input id="txtserialobjupd" type="text" class="form-control form-control-user input100"  name="txtserialobj" placeholder="Serial">
                            </div>
                        </div>
                        <div class="card mb-2 col-sm-11">
                            <div class="text-gray-800 sansserif">Características</div>
                            <textarea id="txtcaracteristicasupd" name="txtcaracte" class="card border border-purple m-1" ></textarea>
                        </div>

                        <div class="col-sm-5 mb-2">
                            <div class="m-b-23">
                                <select id="ddlestadoupd" class="browser-default custom-select" name="ddlestado">
                                    <option value="0">Estado</option>
                                    <c:forEach var="est" items="${estados}">
                                        <option  value="${est.getIdestado()}" > ${est.getEstado()} </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div id="updateobjeto"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cerrar</button>
                        <button id="btnactualizarobj" type="button" class="btn btn-info text-gray-200" onclick="actualizarObjeto();">Actualizar</button>
                    </div>
                </div>
            </div>
        </div>


        <!-- MODAL PARA ELIMINAR OBJETO POR COMPLETO -->
        <div id="mdeliminarobjeto" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-danger">
                        <h5 class="modal-title text-gray-200">Eliminar objeto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body sansserif" style="font-size:14px;">
                        <p>Confirma eliminar el objeto &nbsp;<strong id="obj"></strong></p>
                        <div class="mb-1">
                            <label id="lbleliminarobj"></label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                        <button id="btneliminarobj" type="button" class="btn btn-danger text-gray-100" onclick="eliminarObjeto()">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- MODAL DE OBJETOS NO AGREGADOS -->
        <div class="modal fade" id="mdnoagregados" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-danger">
                        <h5 class="modal-title fas text-gray-100" id="exampleModalLongTitle">Informacion carga</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="refrescarPagina()">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body sansserif">
                        <div class="mb-2 fas text-gray-700" style=" font-size: 18px">
                            Los siguientes seriales ya estan registrados.
                        </div>
                        <div class="fas text-danger sansserif">
                            <div id="listnoagregados" class="list-group" style=" font-size: 18px"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal" onclick="refrescarPagina()">Cerrar</button>
                        <button type="button" class="btn btn-danger text-gray-100" onclick="refrescarPagina()">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="mdreporte" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="modal-title text-gray-200" id="exampleModalLongTitle">Reporte de implementos</h5>
                        <button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <div class="mb-2">
                            <label class="text-gray-700 sansserif">Descargue el reporte en un excel de todos los implementos.</label>
                        </div>
                        <!-- <a title="Descargar">
                             <img src="dashboard/Imagenes/icons/Pdf.png" alt="..." class="rounded bg-gradient-danger p-2">
                         </a>
                         <button id="btnexcelobjetos" class="btn" >
                             <img src="dashboard/Imagenes/icons/Excel.png" alt="..." class="rounded bg-gradient-success p-2">
                         </button> -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary text-gray-200" data-dismiss="modal">Cerrar</button>
                        <button type="button" class="btn btn-primary text-gray-200" onclick="reporteObjetos()" >Descargar</button>
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

