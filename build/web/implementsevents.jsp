<%-- 
    Document   : implementsevents
    Created on : 3/03/2020, 8:56:09 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <% request.getRequestDispatcher("objectsofevents?accion=Listar").forward(request, response); %>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Implementos</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
        <style>
            tr:hover{
                background-image: linear-gradient(180deg, #c1c0c0 20%, #cbcbcb 100%);
                background-size: cover;}
            </style>
        </head>
        <body onload="eventosUsuario()">
            <div id="wrapper">
            <jsp:include page="navlateral.jsp" flush="true"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <!-- Main Content -->
                    <div id="content" class="bg-gray-100">
                    <jsp:include page="navsuperficie.jsp" flush="true"></jsp:include>
                        <div class="container-fluid">
                            <input type="hidden" id="eventoseleccionado">
                            <div class="row">
                                <div class="col-lg-8">
                                    <div class="card">
                                        <div class="p-2 mb-1 bg-white  text-gray-700" >
                                            <i class="fas sansserif" style=" font-size: 13px">Implementos disponibles</i>
                                        </div>
                                        <div class="card-body rounded bg-blanco">
                                            <div class="table-striped">
                                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                                    <thead class="thead-light text-gray-900">
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
                                                    <tbody id="tbodydisponibles">
                                                    <c:forEach var="obj" items="${listobjetos}">
                                                        <tr id="trobjeto${obj.getIdobjeto()}">
                                                            <td>${obj.getObjetonombre()}</td>
                                                            <td>${obj.getMarca()}</td>
                                                            <td>${obj.getEstado()} </td>
                                                            <td>
                                                                <button type="button" class="btn  text-info hover-gradient-aqua" onclick="verCaracteristicas('${obj.getIdobjeto()}');"><i class="gg-search" title="Consultar"></i></button>
                                                                <button name="" type="button"  class="btn text-primary hover-gradient-primary" title="Asignar" onclick="validaObjeto('${obj.getIdobjeto()}', '${obj.getObjetonombre()}');"><i class="gg-toolbox"></i></button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <div id="alert" class="mb-b-2" role="alert" >
                                                <label id="lbl"></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4">
                                <div class="card">
                                    <div class="card p-2 mb-3 bg-white">
                                        <i class="fas sansserif text-gray-700" style=" font-size: 13px">Mis eventos</i>
                                    </div> 
                                    <div class="card-body rounded bg-blanco">
                                        <div class="btn-group mb-1" role="group" aria-label="Basic example">
                                            <button id="btneventos" type="button" class="btn hover-gradient-aqua text-aqua rounded" onclick="listarEventos()"><i class="fas fa-map-marked-alt"></i></button>
                                            <button type="button" class="btn hover-gradient-success text-success fas fa-plus-square  rounded" title="Crear evento" data-toggle="modal" data-target="#mdCrearEvento"></button>
                                        </div>
                                        <div class="card shadow mb-4">
                                            <div id="accordion">
                                                <div class="card">
                                                    <div id="listeventosusuario"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                        <div id="infoobjeto" class="card mb-1 eye">
                                            <div  class="card  text-center p-1 border text-black-100 mb-1">
                                                <label  class="text-gray-900" id="nombredelobjeto"></label>
                                                <strong class="text-gray-600"> Serial:</strong> <label id="txtserial"  class="text-gray-600"></label><br>
                                                <strong  class="text-gray-600">Características:</strong>
                                                <label  class="text-gray-600 " id="txtcarac"></label>
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
        <!-- MODALES -->
        <!-- MODAL PARA CAMBIAR UN OBJETO DE EVENTO -->
        <div class="modal fade" id="mdCambiarObjeto" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="fas fa-toolbox modal-title text-gray-200" id="exampleModalLongTitle">&nbsp; <span class="sansserif" style="font-size: 18px">Traspasar objeto a otro evento</span> </h5>
                        <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-2">
                            <label id="nmobjeto" class="fas"></label>
                        </div>
                        <div class="mb-2">
                            <label  class="fas sansserif">Selecione el evento al que desea traspasar el objeto.</label>
                        </div>
                        <div  class="col-lg-12 mb-2">
                            <input type="hidden" id="objetoide">
                            <div class="col-lg-12">
                                <div  class="list-group" id="list-tab" role="tablist">
                                </div>
                            </div>
                        </div>
                    </div>
                    <label id="objupdevento"></label>
                </div>
            </div>
        </div>
        <!-- MODAL PARA SEPARAR IMPLMENTOS -->
        <div class="modal fade" id="modalSeparar" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-gradient-primary">
                        <h5 class="fas fa-toolbox modal-title text-gray-200" id="exampleModalLongTitle">&nbsp;<span class="sansserif"> Asignación de objetos</span></h5>
                        <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-2">
                            <label id="nmobjetos" class="sansserif"></label>
                        </div>
                        <div class="mb-2">
                            <label  class="sansserif">Selecione el evento al que desea agregar el objeto.</label>
                        </div>
                        <div  class="col-lg-12 mb-2">
                            <div class="col-lg-12">
                                <div  class="list-group" id="list-tab" role="tablist">
                                    <div id="listmiseventos" class="sansserif"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <label id="objinfo"></label>
                </div>
            </div>
        </div>
        <!-- MODAL PARA CREAR EVENTO -->
        <div class="modal fade" id="mdCrearEvento" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="modal-title text-gray-200" id="exampleModalLongTitle">Crear evento</h5>
                        <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body sansserif" >
                        <div class="mb-1 ">
                            <label id="infobj" class="sansserif" style="font-size:16px"></label>  
                        </div>
                        <div class="col-sm-12 mb-2">
                            <div class="m-b-23">
                                <span class="text-danger float-right">*</span> 
                                <input id="txtnombreevt" class="form-control  input100" placeholder="Nombre del evento">
                            </div>
                        </div>
                        <div class=" form-group row">
                            <div class="col-sm-6 mb-2">
                                <div class="m-b-23 ">
                                    Fecha de inicio <span class="text-danger">*</span> 
                                    <input id="dtinicio" type="date" class="form-control form-control-user input100">
                                </div>
                            </div>
                            <div class="col-sm-6 mb-2">
                                <div class="m-b-23 ">
                                    Fecha final <span class="text-danger">*</span> 
                                    <input id="dtfin" type="date" class="form-control form-control-user input100">
                                </div>
                            </div>
                        </div>
                        <div class=" form-group row ">
                            <div class="col-sm-6 mb-2">
                                <div class="">
                                    Hora de inicio <span class="text-danger">*</span> 
                                    <input id="timeinicio" type="time" class="form-control form-control-user input100">
                                </div>
                            </div>
                            <div class="col-sm-6 mb-2">
                                <div class="">
                                    Hora final <span class="text-danger">*</span> 
                                    <input id="timefin" type="time" class="form-control form-control-user input100">
                                </div>
                            </div>
                        </div>
                        <div class="mb-1">
                            <div id="clsevent">
                                <label id="lblevent"></label>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-primary " onclick="creaEvento()">Crear evento</button>
                    </div>
                </div>
            </div>
        </div>

        <!----------------->
        <!-- MODAL LISTA DE EVENTOS -->
        <div id="mdeventosus" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header bg-primary">
                        <h5 class="modal-title text-gray-200" id="exampleModalLongTitle">Mis eventos</h5>
                        <button type="button" class="close text-gray-200" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped bg-fond-transparent">
                            <thead class=" bg-fond-transparent">
                                <tr>
                                    <th>Evento</th>
                                    <th>Fecha Inicial</th>
                                    <th>Fecha final</th>
                                    <th>Inicia</th>
                                    <th>Finaliza</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody id="tbody">
                            </tbody>
                        </table>
                        <div id="eyefalse" class="eye">
                            <hr>
                            <div class="col-sm-12  mb-2">
                                <div class="m-b-23">
                                    <span class="text-danger float-right">*</span> 
                                    <input id="txtnombreupd" class="form-control form-control-user input100" placeholder="Nombre del evento">
                                </div>
                            </div>
                            <div class=" form-group row">
                                <div class="col-sm-6 mb-2">
                                    <div class="m-b-23 ">
                                        Fecha de inicio <span class="text-danger">*</span> 
                                        <input id="dtinicioupd" type="date" class="form-control form-control-user input100">
                                    </div>
                                </div>
                                <div class="col-sm-6 mb-2">
                                    <div class="m-b-23 ">
                                        Fecha final <span class="text-danger">*</span> 
                                        <input id="dtfinupd" type="date" class="form-control form-control-user input100">
                                    </div>
                                </div>
                            </div>
                            <div class=" form-group row ">
                                <div class="col-sm-6 mb-2">
                                    <div class="">
                                        Hora de inicio <span class="text-danger">*</span> 
                                        <input id="timeinicioupd" type="time" class="form-control form-control-user input100">
                                    </div>
                                </div>
                                <div class="col-sm-6 mb-2">
                                    <div class="">
                                        Hora final <span class="text-danger">*</span> 
                                        <input id="timefinupd" type="time" class="form-control form-control-user input100">
                                    </div>
                                </div>
                            </div>
                            <div class="mb-1">
                                <label id="lbleventupd"></label>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                                <button id="btnupdevent" type="button" class="btn btn-primary text-gray-200" onclick="updateEvent()">Actualizar</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="deleteEvento" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-gradient-danger">
                        <h5 class="modal-title text-gray-200">Eliminar evento</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p class="sansserif">¿ Confirma eliminar el evento ? &nbsp; <strong id="st"></strong></p>
                        <div class="mb-1">
                            <label id="lbldelet"></label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button id="btndeleteev" type="button" class="btn bg-gradient-danger text-gray-100" onclick="deleteEvent()">Eliminar</button>
                        <button type="button" class="btn bg-gradient-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="mbdeleteobj" class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-danger">
                        <h5 class="modal-title text-gray-200">Deshacer objeto</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Confirma deshacer el objeto &nbsp;<strong id="stnmobj"></strong> del evento.</p>
                        <div class="mb-1">
                            <label id="lbldelet"></label>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                        <button id="btndeleteobj" type="button" class="btn btn-danger text-gray-100" onclick="deleteMiObj()">Aceptar</button>
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
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>

