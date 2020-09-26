<%-- 
    Document   : mytasks
    Created on : 3/03/2020, 8:59:36 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head >
        <%
            response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0);
        %>
        <c:set var="nombre" value="${miname}"></c:set>
        <c:if test="${nombre == null}">
            <% request.getRequestDispatcher("tasks?accion=ListarTareas").forward(request, response); %>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Mis tareas</title>
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
                        <div class="container-fluid">
                            <input id="htr" type="hidden">
                            <div class="row">
                                <div class="col-lg-3 card">
                                    <div class="card-body rounded bg-blanco">
                                        <div class="p-2 mb-2 bg-white">
                                            <div class="mb-3 sansserif">
                                                <div class="bg-white mb-3 text-gray-700 form-group row sansserif">
                                                    <div class="col-sm-2">
                                                        <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pie-chart text-aqua" fill="currentColor">
                                                        <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                        <path fill-rule="evenodd" d="M7.5 7.793V1h1v6.5H15v1H8.207l-4.853 4.854-.708-.708L7.5 7.793z"/>
                                                        </svg>
                                                    </div>
                                                    <div class="sansserif hover-aqua ">Funciones
                                                        <i class="fas text-gray-700 sansserif">
                                                            <a class="dropdown-toggle  text-gray-600 ml-1 float-right sansserif hover-aqua" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            </a>
                                                            <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in col-sm-4 text-decoration-none sansserif" aria-labelledby="dropdownMenuLink">
                                                                <div class="text-center">
                                                                    <label class="text-primary sansserif text-gray-600">Personal</label>
                                                                </div>
                                                                <a class="dropdown-item text-gray-800 sansserif" href="#" data-toggle="modal" data-target="#newtarea">Crear tarea</a>
                                                            <%if ((int) session.getAttribute("ID") > 1) { %>
                                                            <a class="dropdown-item text-gray-700 sansserif" href="#" onclick="filtrarmisTareas(7)">Personales</a>
                                                            <hr>
                                                            <div class="text-center">
                                                                <label class="text-primary sansserif text-gray-600">Asignadas</label>
                                                            </div>
                                                            <div class="sansserif">
                                                                <a class="dropdown-item text-gray-700" href="#" onclick="filtrarmisTareas(1)">Todas</a>
                                                                <a class="dropdown-item text-gray-700" href="#" onclick="filtrarmisTareas(2)">En proceso</a>
                                                                <a class="dropdown-item text-gray-700" href="#" onclick="filtrarmisTareas(3)">A futuro</a>
                                                                <a class="dropdown-item text-gray-700" href="#" onclick="filtrarmisTareas(4)">Terminadas</a>
                                                                <a class="dropdown-item text-gray-700" href="#" onclick="filtrarmisTareas(5)">Desaprobadas</a>
                                                                <a class="dropdown-item text-gray-700" href="#" onclick="filtrarmisTareas(6)">Restauradas</a>
                                                            </div>
                                                            <% } %>
                                                        </div>
                                                    </i>
                                                </div>
                                            </div>
                                        </div>
                                    </div> 

                                    <div class="card p-1 m-0 bg-fond-transparent">
                                        <div id="listadetareas">
                                            <c:forEach var="trs" items="${mistareas}">
                                                <c:set var="progreso" value="${trs.getProgreso()}"></c:set>
                                                <c:if test="${progreso <= 15}">
                                                    <c:set var="color" value="bg-danger"></c:set>
                                                </c:if>
                                                <c:if test="${progreso > 15 && progreso <= 39}">
                                                    <c:set var="color" value="bg-warning"></c:set>
                                                </c:if>
                                                <c:if test="${progreso > 39 &&  progreso <= 70}">
                                                    <c:set var="color" value="bg-info"></c:set>
                                                </c:if>
                                                <c:if test="${progreso > 70}">
                                                    <c:set var="color" value="bg-success"></c:set>
                                                </c:if>
                                                <a class="card shadow h-100 py-1 bg-gray-200 mb-1" href="#" onclick="verTarea('${trs.getIdtarea()}')" >
                                                    <div class="card-body">
                                                        <div class="text-xs font-weight-normal text-black-50 text-uppercase mb-1"> ${trs.getNombre()}</div>
                                                        <div class="row no-gutters align-items-center">
                                                            <div class="col mr-2">
                                                                <div class="row no-gutters align-items-center">
                                                                    <div class="progress progress-bar progress-bar-striped ${color}" role="progressbar" style="width:${trs.getProgreso()}%; height:11px";  aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"> ${trs.getProgreso()}%</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </a>
                                            </c:forEach>
                                        </div>
                                        <div id="tareasusuario"></div> <!-- id para listar trs con json -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4-max bg-fond-transparent card">
                                <div class="card-body rounded bg-blanco">
                                    <div class="card p-2 mb-2 bg-white text-white">
                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <i class="text-gray-700">
                                                    <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-receipt-cutoff text-aqua" fill="currentColor">
                                                    <path fill-rule="evenodd" d="M1.92.506a.5.5 0 0 1 .434.14L3 1.293l.646-.647a.5.5 0 0 1 .708 0L5 1.293l.646-.647a.5.5 0 0 1 .708 0L7 1.293l.646-.647a.5.5 0 0 1 .708 0L9 1.293l.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .801.13l.5 1A.5.5 0 0 1 15 2v13h-1V2.118l-.137-.274-.51.51a.5.5 0 0 1-.707 0L12 1.707l-.646.647a.5.5 0 0 1-.708 0L10 1.707l-.646.647a.5.5 0 0 1-.708 0L8 1.707l-.646.647a.5.5 0 0 1-.708 0L6 1.707l-.646.647a.5.5 0 0 1-.708 0L4 1.707l-.646.647a.5.5 0 0 1-.708 0l-.509-.51L2 2.118V15H1V2a.5.5 0 0 1 .053-.224l.5-1a.5.5 0 0 1 .367-.27zM0 15.5a.5.5 0 0 1 .5-.5h15a.5.5 0 0 1 0 1H.5a.5.5 0 0 1-.5-.5z"></path>
                                                    <path fill-rule="evenodd" d="M3 4.5a.5.5 0 0 1 .5-.5h6a.5.5 0 1 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 1 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 1 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5zm8-8a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5z"></path>
                                                    </svg>
                                                </i>
                                            </div>
                                            <div class="col-sm-2">
                                                <span class="sansserif text-gray-700">Información</span>
                                            </div>
                                        </div>
                                    </div> 
                                    <div class="card p-1 m-0 bg-fond-transparent">
                                        <div class="card col-lg-12 bg-gray-200 text-gray-600 sansserif mb-2">
                                            <div class="card-body">
                                                <h6  class="m-0 sansserif">&nbsp;<label id="nombretarea"> </label> <label id="estado"></label> </h6>
                                                <span> Creada: <label id="asignada"> </label></span> -
                                                <span> &nbsp;Incia: &nbsp  <label id="inicia"> </label> - &nbsp  Entrega: &nbsp; <label id="entrega"> </label></span>
                                                <div id="btns" class="bg-gray-200"></div>
                                            </div>
                                        </div>
                                        <div class="card mb-1">
                                            <div class="mb-2 bg-blanco">
                                                <h6 class="m-1  sansserif text-gray-700">Características</h6> 
                                            </div>
                                            <div id="caracteristicas" class="rounded card-body p-3 text-black-100 border bg-white"></div>
                                        </div>
                                        <div class="card mb-1">
                                            <div class="mb-2 bg-blanco ">
                                                <h6 class="m-1  sansserif text-gray-700">Anotación</h6>
                                            </div>
                                            <div id="anotacion" class="rounded  card-body p-3 text-black-100 border bg-white"></div>
                                        </div>
                                        <div class="card mb-1">
                                            <div id="contcomentarios" class="rounded-bottom border-0 text-black-100 my-custom-scrollbar bg-fond-transparent m-1 eye">
                                                <div id="comentariostr" class=""></div>
                                            </div>
                                            <div id="toastt" class="eye">
                                                <div class="toast" role="alert" aria-live="assertive" data-autohide="false" id="toastcomentario">
                                                    <div class="toast-header">
                                                        <strong class="mr-auto">Editar comentario</strong>
                                                        <button type="button" class="ml-2 mb-1 close" onclick="closeEdit();" >
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="toast-body">
                                                        <textarea id="txtcomnew" class="card col-lg-12 border"></textarea>
                                                    </div>
                                                    <div class="col text-center">
                                                        <button id="btnupdcom" class="btn-primary card" onclick="actualizarComentario('1')"><i class="fa fa-send">Enviar</i></button>
                                                    </div>
                                                </div> 
                                            </div>
                                            <div id="enviarcomentario">
                                                <div class="input-group mb-3">
                                                    <textarea type="text" id="txtcomentarionuevo"  class="form-control border-gray-300"></textarea>
                                                    <div class="input-group-append">
                                                        <button class="btn btn-outline-purple bor border-gray-300" type="button"  onclick="sendComent();"><i class="text-primary fab fa-telegram-plane "></i></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="centralnotas" class="col-lg-4-max card">
                                <div class="card-body rounded bg-blanco">
                                    <div class="p-2 mb-2">
                                        <i class="text-gray-700">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-file-earmark text-warning" fill="currentColor">
                                            <path d="M4 0h5.5v1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h1V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2z"/>
                                            <path d="M9.5 3V0L14 4.5h-3A1.5 1.5 0 0 1 9.5 3z"/>
                                            </svg>
                                        </i> &nbsp;<span class="sansserif text-gray-700">Bloc de notas</span>
                                    </div> 
                                    <ul class="list-group mb-2 bg-fond-transparent">
                                        <li class="list-group-item d-flex justify-content-between align-items-center bg-fond-transparent border-0 ">
                                            <input id="namenota" type="text" class="form-control form-control-user input100 border-0 alert-warning col-10" placeholder="Nombre de la nota">
                                            <span class="badge badge-pill" onclick="crearNota();" title="Crear nota"><span class="fas fa-clipboard-list btn-circle bg-gradient-warning text-gray-700"></span></span>
                                        </li>
                                    </ul>
                                    <div class="row">
                                        <div  class="col-lg-4 mb-3">
                                            <div class="card"> 
                                                <div class="list-group" id="list-tab" role="tablist">
                                                    <div  class="card list-group" id="list-tab" role="tablist">
                                                        <input id="hidden" type="hidden">
                                                        <div id="notanueva">
                                                            <div id="newnota" onclick="renovarNota();"></div>
                                                        </div>
                                                        <div id="listadenotas">
                                                            <div id="notasuauario">
                                                                <c:forEach var="notas" items="${listnotas}">
                                                                    <a id="nota${notas.getIdnota()}"  class="border-0 list-group-item list-group-item-action list-group-item-warning" data-toggle="list" href="#list-home" role="tab" aria-controls="home" onclick="verNota('${notas.getIdnota()}');">${notas.getNombrenota()}</a>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div  class="col-lg-7">
                                            <div id="textonotas" class="tab-content" id="nav-tabContent"> </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--------- MODALES ---------->
                            <div class="modal fade" id="mdEliminanota" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-gradient-danger">
                                            <h5 class="modal-title text-gray-100" id="exampleModalLabel">Eliminar nota</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body sansserif">
                                          ¿ Confirma eliminar la nota ?
                                        </div>
                                        <div id="alerteliminanota"></div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn bg-secondary text-white" data-dismiss="modal">Cerrar</button>
                                            <button type="button" id="btneliminanota" class="btn bg-gradient-danger text-white" onclick="eliminaNota();">Eliminar</button>
                                        </div>
                                    </div>
                                </div>
                            </div> 

                            <div class="modal fade" id="modalhistorial" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-gradient-info">
                                            <h5 class="modal-title text-gray-100" id="exampleModalLabel">Archivar en el historial</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <span class=" sansserif">  ¿ Confirma archivar la tarea ? </span>
                                        </div>
                                        <div id="alertarchivar" class="sansserif"></div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn bg-secondary text-white" data-dismiss="modal">Cerrar</button>
                                            <button type="button" id="btneliminanota" class="btn bg-gradient-info text-white" onclick="archivarTarea();">Archivar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="modal fade" id="newtarea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-gradient-blue">
                                            <h5 class="modal-title text-gray-200" id="exampleModalLabel">Crear tarea</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div id="mdbody" class="modal-body sansserif">
                                            <form>
                                                <div class="form-group">
                                                    <label for="recipient-name" class="col-form-label">Nombre de la tarea<span class="text-danger">*</span> </label>
                                                    <input id="txtnametarea" type="text" class="form-control" id="recipient-name">
                                                </div>
                                                <div class="row">
                                                    <div class="col-lg-6 mb-1">
                                                        <label class="control-label text-gray-700" for="datepicker-start">&nbsp; Inicio <span class="text-danger">*</span></label>
                                                        <input id="dtinicio" class="form-control"  type="date" name="addtimeinicio">
                                                    </div>
                                                    <div class="col-lg-6 mb-2">
                                                        <label class="control-label text-gray-700" for="datepicker-end">&nbsp; Finaliza <span class="text-danger">*</span></label>
                                                        <input id="dtfinal" class="form-control"  type="date" name="addtimefinal">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="message-text" class="col-form-label">Anotación <span class="text-danger">*</span></label>
                                                    <textarea id="txtanotaciones" class="form-control" id="message-text"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label for="message-text" class="col-form-label">Características <span class="text-danger">*</span></label>
                                                    <textarea id="txtcaracteristicas" class="form-control" id="message-text"></textarea>
                                                </div>
                                                <label id="lblnewtarea"></label>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn bg-gradient-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                                            <button type="button" class="btn bg-gradient-blue text-gray-100 " onclick="addTareapersonal()">Crear tarea</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--  modal para editar tareas---->
                            <div class="modal fade" id="modaledit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-primary">
                                            <h5 class="modal-title text-gray-100" id="exampleModalLabel">Actualizar tarea <strong></strong></h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body sansserif"> 
                                            <div class="row">
                                                <div class="col-lg-6 mb-1">
                                                    <label class="control-label text-gray-700" for="datepicker-start">&nbsp; Inicio <span class="text-danger">*</span></label>
                                                    <input id="dtinicionew" class="form-control"  type="date"  value="">
                                                </div>
                                                <div class="col-lg-6 mb-2">
                                                    <label class="control-label text-gray-700" for="datepicker-end">&nbsp; Finaliza <span class="text-danger">*</span></label>
                                                    <input id="dtfinalnew" class="form-control"  type="date"  value="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="recipient-name" class="col-form-label">Nombre de la tarea <span class="text-danger">*</span></label>
                                                <input id="txtnombrenew" type="text" class="form-control" id="recipient-name" value="">
                                            </div>
                                            <div class="form-group">
                                                <label for="message-text" class="col-form-label">Anotación <span class="text-danger">*</span></label>
                                                <textarea id="txtanotacionnew" class="form-control" id="message-text" ></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="message-text" class="col-form-label">Características <span class="text-danger">*</span></label>
                                                <textarea id="txtcaracnew" class="form-control" id="message-text"></textarea>
                                            </div>
                                        </div>
                                        <div class="mb-1">
                                            <label id="updatetr"></label>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                            <button type="button" class="btn btn-primary" onclick="actualizarMitarea()">Actualizar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- MODAL PARA ELIMINAR UNA TAREA -->
                            <div id="modaleliminar" class="modal" tabindex="-1" role="dialog" >
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-gradient-danger">
                                            <h5 class="modal-title text-gray-100">Eliminar tarea</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body sansserif">
                                            <p>¿ Confirma eliminar la tarea ?</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn bg-gradient-danger text-gray-100" onclick="eliminarmiTarea()">Eliminar</button>
                                            <button type="button" class="btn btn-secondary text-gray-100" data-dismiss="modal">Cerrar</button>
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
        <script src="dashboard/vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="dashboard/vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="dashboard/js/demo/datatables-demo.js"></script>
        -->
        <!--JavaScript para barra nav nav lateral-->
        <script src="dashboard/js/notas.js"></script>
        <script src="dashboard/js/tasks.js"></script>
        <script src="dashboard/vendor/jquery/jquery.min.js"></script>
        <script src="dashboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="dashboard/js/sb-admin-2.min.js"></script>
    </body>
</html>
<% } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>