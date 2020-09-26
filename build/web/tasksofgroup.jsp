<%-- 
    Document   : tasksofgroup
    Created on : 3/03/2020, 9:06:30 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%if ((int) session.getAttribute("IDROL") < 3) { %> 
<html>
    <head>
        <%  response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0);  %>
        <c:set var="nombre" value="${miname}"></c:set>
        <c:if test="${nombre == null}">
            <% request.getRequestDispatcher("tasksofgroup?accion=Listargrupo").forward(request, response); %>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Tareas grupales</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <!--  <link rel="stylesheet" type="text/css" href="login/fonts/font-awesome-4.7borrar/css/font-awesome.min.css"> -->
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <input id="htr" type="hidden">
            <input id="hus" type="hidden">
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
                                        <div class="bg-white mb-3 text-gray-700 form-group row">
                                            <div class="col-sm-1">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-people " fill="currentColor">
                                                <path fill-rule="evenodd" d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1h7.956a.274.274 0 0 0 .014-.002l.008-.002c-.002-.264-.167-1.03-.76-1.72C13.688 10.629 12.718 10 11 10c-1.717 0-2.687.63-3.24 1.276-.593.69-.759 1.457-.76 1.72a1.05 1.05 0 0 0 .022.004zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10c-1.668.02-2.615.64-3.16 1.276C1.163 11.97 1 12.739 1 13h3c0-1.045.323-2.086.92-3zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"></path>
                                                </svg>
                                            </div>
                                            <div class="col-sm-6 sansserif">
                                                Equipo
                                            </div>
                                        </div>
                                    <c:forEach var="grupo" items="${migrupo}"> <!-- href="tasksofgroup?accion=Listtareasus&usuario=&opcion=1"   -->
                                        <a class="card bg-aqua text-gray-200 shadow  mb-1 sansserif" href="#" onclick="verTareasUsuario('${grupo.getID()}', '1')">
                                            <div class="card-body ">
                                                &nbsp;${grupo.getNombres()}
                                            </div>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                            <!-- fin foreach listado de usuarios-->
                            <!------Inicio foreach tareas de usuario seleccionado-------->
                            <div class="col-lg-2 card bg-fond-transparent ">
                                <div class="card-body bg-blanco rounded">
                                    <div class="mb-3 sansserif">
                                        <div class="bg-white mb-3 text-gray-700 form-group row sansserif hover-aqua">
                                            <div class="col-sm-2">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-pie-chart" fill="currentColor" >
                                                <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                                <path fill-rule="evenodd" d="M7.5 7.793V1h1v6.5H15v1H8.207l-4.853 4.854-.708-.708L7.5 7.793z"/>
                                                </svg>
                                            </div>Funciones
                                            <div class="sansserif hover-aqua">
                                                <i class="fas text-gray-700 sansserif">
                                                    <a class="dropdown-toggle  text-gray-600 ml-1 float-right sansserif hover-aqua" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                    </a>
                                                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in col-sm-6 text-decoration-none sansserif" aria-labelledby="dropdownMenuLink">
                                                        <a class="dropdown-item sansserif" href="#" onclick="filtrarTareas('1')">Todas</a>
                                                        <a class="dropdown-item sansserif" href="#" onclick="filtrarTareas('2')">En proceso</a>
                                                        <a class="dropdown-item sansserif" href="#" onclick="filtrarTareas('3')">A futuro</a>
                                                        <a class="dropdown-item sansserif" href="#" onclick="filtrarTareas('4')">Terminadas</a>
                                                        <a class="dropdown-item sansserif" href="#" onclick="filtrarTareas('5')">Desaprobadas</a>
                                                        <a class="dropdown-item sansserif" href="#" onclick="filtrarTareas('6')">Restauradas</a>
                                                    </div>
                                                </i>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="tareas" class="sansserif"></div>
                                </div>
                            </div>

                            <div class="col-lg-4 bg-fond-transparent card ">
                                <div class="card-body bg-blanco rounded">
                                    <!-- Eliminar tarea -->
                                    <div class="btn-group  col-lg-12 mb-2" role="group" aria-label="Second group">
                                        <button href="#" class="btn  btn-icon-split text-danger  rounded" onclick="mdDeeleteTarea()" title="Eliminar">
                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-trash" fill="currentColor">
                                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                            </svg>
                                        </button> 
                                        <!-- Aprobada -->
                                        <button href="#" class="btn  btn-icon-split text-success rounded " onclick="mdaprobarTarea()" title="Aprobar">
                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-check2-circle" fill="currentColor">
                                            <path fill-rule="evenodd" d="M15.354 2.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3-3a.5.5 0 1 1 .708-.708L8 9.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
                                            <path fill-rule="evenodd" d="M8 2.5A5.5 5.5 0 1 0 13.5 8a.5.5 0 0 1 1 0 6.5 6.5 0 1 1-3.25-5.63.5.5 0 1 1-.5.865A5.472 5.472 0 0 0 8 2.5z"/>
                                            </svg>
                                        </button> 
                                        <!-- Desaprobar -->
                                        <button href="#" class="btn text-warning btn-icon-split rounded " data-toggle="tooltip"  title="Desaprobar" onclick="dprTarea()" title="Desaprobar">
                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-x-circle " fill="currentColor">
                                            <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                            <path fill-rule="evenodd" d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                                            </svg>
                                        </button>
                                        <!--En espera-->
                                        <button href="#" class="btn btn-icon-split text-primary rounded " onclick="esperaTarea()" title="En espera">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-alarm " fill="currentColor">
                                            <path fill-rule="evenodd" d="M6.5 0a.5.5 0 0 0 0 1H7v1.07a7.001 7.001 0 0 0-3.273 12.474l-.602.602a.5.5 0 0 0 .707.708l.746-.746A6.97 6.97 0 0 0 8 16a6.97 6.97 0 0 0 3.422-.892l.746.746a.5.5 0 0 0 .707-.708l-.601-.602A7.001 7.001 0 0 0 9 2.07V1h.5a.5.5 0 0 0 0-1h-3zm1.038 3.018a6.093 6.093 0 0 1 .924 0 6 6 0 1 1-.924 0zM8.5 5.5a.5.5 0 0 0-1 0v3.362l-1.429 2.38a.5.5 0 1 0 .858.515l1.5-2.5A.5.5 0 0 0 8.5 9V5.5zM0 3.5c0 .753.333 1.429.86 1.887A8.035 8.035 0 0 1 4.387 1.86 2.5 2.5 0 0 0 0 3.5zM13.5 1c-.753 0-1.429.333-1.887.86a8.035 8.035 0 0 1 3.527 3.527A2.5 2.5 0 0 0 13.5 1z"/>
                                            </svg>
                                        </button> 
                                        <button href="#" class="btn btn-icon-split text-info rounded" data-toggle="modal" data-target="#modaltarea" title="Crear tarea">
                                            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-plus-circle" fill="currentColor">
                                            <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                            <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                            </svg>
                                        </button>
                                    </div>
                                    <div id="progresotarea" class="mb-2"></div>
                                    <input id="dinicio" type="hidden" value="">
                                    <div class="card bg-gray-200 sansserif mb-2 text-gray-600">
                                        <div class="card-body">
                                            <div class="p-1 mb-1 "> 
                                                </span> <label id="nombretarea"></label> - Estado: </span> <label id="labelestado"></label>
                                            </div>
                                            <div class="p-1 mb-1">
                                                Se asigno : <label id="labelfechaasignada"> </label></h6>
                                            </div>
                                            <div class="p-1 mb-1">
                                                Inicio:  <label id="fechainicio"> </label> - Entrega: <label id="fechafinal" ></label>
                                            </div>
                                        </div>
                                    </div>
                                    <div  class="card mb-1">
                                        <div id="contenedorcomentarios" class="border-0 my-custom-scrollbar m-1 eye">
                                            <div id="comentarios" class="sansserif"></div>
                                        </div>
                                        <div id="toastt" class="eye">
                                            <div class="toast" role="alert" aria-live="assertive" data-autohide="false" id="toastcomentario">
                                                <div class="toast-header">
                                                    <strong class="mr-auto">Editar comentario</strong>
                                                    <button type="button" class="ml-2 mb-1 close" onclick="closeEdit();">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="toast-body">
                                                    <textarea id="txtcomnew" class="card col-lg-12 border"></textarea>
                                                </div>
                                                <div class="col text-center">
                                                    <button id="btnupdcom" class="btn-primary card" onclick="actualizarComentario('2')"><i class="fa fa-send">Enviar</i></button>
                                                </div>
                                            </div> 
                                        </div>
                                        <div id="enviarcomentario">
                                            <div class="input-group mb-3">
                                                <textarea type="text" id="txtcomentarionuevo" class="form-control border-gray-300"></textarea>
                                                <div class="input-group-append hover-gradient-primary rounded">
                                                    <button class="btn btn-outline bor border-gray-300 " type="button"  onclick="enviarComentario();"><i class="text-primary fab fa-telegram-plane border-0"></i></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-------Columna actualizacion de tarea-------------->
                            <div class="card col-lg-4 bg-fond-transparent">
                                <div class="card-body bg-blanco rounded">
                                    <div class="mb-2 bg-white text-white">
                                        <div class="form-group row">
                                            <div class="col-sm-1">
                                                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-receipt-cutoff text-gray-700" fill="currentColor">
                                                <path fill-rule="evenodd" d="M1.92.506a.5.5 0 0 1 .434.14L3 1.293l.646-.647a.5.5 0 0 1 .708 0L5 1.293l.646-.647a.5.5 0 0 1 .708 0L7 1.293l.646-.647a.5.5 0 0 1 .708 0L9 1.293l.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .801.13l.5 1A.5.5 0 0 1 15 2v13h-1V2.118l-.137-.274-.51.51a.5.5 0 0 1-.707 0L12 1.707l-.646.647a.5.5 0 0 1-.708 0L10 1.707l-.646.647a.5.5 0 0 1-.708 0L8 1.707l-.646.647a.5.5 0 0 1-.708 0L6 1.707l-.646.647a.5.5 0 0 1-.708 0L4 1.707l-.646.647a.5.5 0 0 1-.708 0l-.509-.51L2 2.118V15H1V2a.5.5 0 0 1 .053-.224l.5-1a.5.5 0 0 1 .367-.27zM0 15.5a.5.5 0 0 1 .5-.5h15a.5.5 0 0 1 0 1H.5a.5.5 0 0 1-.5-.5z"/>
                                                <path fill-rule="evenodd" d="M3 4.5a.5.5 0 0 1 .5-.5h6a.5.5 0 1 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 1 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 1 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h6a.5.5 0 0 1 0 1h-6a.5.5 0 0 1-.5-.5zm8-8a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 0 1h-1a.5.5 0 0 1-.5-.5z"/>
                                                </svg>
                                            </div>
                                            <div class="col-sm-5 sansserif text-gray-700">
                                                Información
                                            </div>
                                        </div>
                                    </div> 
                                    <div class="row">
                                        <div class="col-sm-6 mb-2">
                                            <label class="control-label text-gray-600 sansserif" for="datepicker-start">&nbsp; Inicia * </label>
                                            <input id="dtinicioupd" class="form-control"  value=""  type="date" name="dateinicio">
                                        </div>
                                        <div class="col-sm-6 mb-2">
                                            <label class="control-label text-gray-600 sansserif" for="datepicker-start">&nbsp; Entrega *</label>
                                            <input id="dtfinupd" class="form-control" value="" type="date" name="datefinal">
                                        </div>
                                    </div>
                                    <div class="card mb-2">
                                        <div class="mb-1 text-gray-600 sansserif">
                                            <span class="m-0">&nbsp;  Nombre  *</span> 
                                        </div>
                                        <textarea id="txtnameupd" class="form-control " name="txtnombre"></textarea>
                                    </div>
                                    <div class="card mb-2">
                                        <div class="mb-1 text-gray-600 sansserif">
                                            <span class="m-0">&nbsp;  Anotación *</span>
                                        </div>
                                        <textarea id="txtanotacionupd"  class="form-control" name="txtcoment"></textarea>
                                    </div>
                                    <div class="card mb-2">
                                        <div class="mb-1 text-gray-600 sansserif">
                                            <span class="m-0">&nbsp;  Características *</span>
                                        </div>
                                        <span class="border border-0"></span>
                                        <textarea id="txtcaractupd"  class="form-control" name="txtcaract" ></textarea>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-8">
                                            <label id="upd"></label>
                                        </div>
                                        <div class="col-sm-4 ">
                                            <button  id="btnupdatetarea"type="button" class="btn bg-aqua text-white float-right sansserif" onclick="updateTr();">Actualizar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 

                    <!-- Contenedor para confirmacion de eliminar una tarea  -->
                    <div class="modal fade" id="deleteTarea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header bg-danger">
                                    <h5 class="modal-title text-gray-100 sansserif" id="exampleModalLabel" style="font-size: 18px">Eliminar tarea</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body sansserif" style="font-size: 15px">
                                    <p>¿ Confirma eliminar la tarea ? </p>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn bg-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar</button>
                                    <button class="btn bg-danger text-gray-100" type="button"   onclick="deleteTarea();">Eliminar</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Contenedor para confirmacion sobre aprobacion de la tarea  -->
                    <div class="modal fade" id="mdaprTarea" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header bg-success">
                                    <h5 class="modal-title text-gray-100 sansserif" id="exampleModalLabel"  style="font-size: 18px">Aprobación de tarea</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body sansserif" style="font-size: 15px">
                                    <p>Al aprobar se enviara al historial de tareas.</p>
                                </div>
                                <div class="modal-footer">
                                    <button class="btn bg-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar</button>
                                    <button class="btn bg-success text-gray-100" type="button"   onclick="aprobarTarea();">Aprobar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- end container de confirmacion de eliminacion de una tarea  -->

                </div>
            </div>
        </div>
  
    <!-- MODALES -->
    <div id="modaltarea" class="modal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header bg-sidevar sansserif">
                    <h6 class="modal-title text-gray-200 sansserif" style="font-size: 18px">Asignación de tarea</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body bg-gradient-fond">
                    <div class="col-lg-12">
                        <div class="row">
                            <div  class="col-lg-12 mb-1">
                                <select  class="browser-default custom-select mb-1" required name="idus" id="idus">
                                    <option value="0">Grupo</option>
                                    <c:forEach var="grupo" items="${migrupo}">
                                        <option  value="${grupo.getID()}"> ${grupo.getNombres()} </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="row sansserif">
                            <div class="col-lg-6 mb-1">
                                <label class="control-label text-gray-700" for="datepicker-start">&nbsp; Inicia<span class="text-danger">*</span> </label>
                                <input id="addtimeinicio" class="form-control"  type="date" name="addtimeinicio">
                            </div>
                            <div class="col-lg-6 mb-2">
                                <label class="control-label text-gray-700" for="datepicker-end">&nbsp; Entrega<span class="text-danger">*</span> </label>
                                <input id="addtimefinal" class="form-control"  type="date" name="addtimefinal">
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-12 mb-2"> 
                        <div class="card">
                            <div class="card bg-gradient-fond p-2 ">
                                <h6 class="m-0 text-gray-700 sansserif">&nbsp;   Nombre <span class="text-danger">*</span> </h6>
                            </div>
                            <input id="txtnombretr" class="card border border p-1" name="txtnombretr" >
                        </div>
                    </div>
                    <div class="col-lg-12 mb-2">
                        <div class="card">
                            <div class="card p-2 bg-gradient-fond ">
                                <h6 class="m-0  text-gray-700 sansserif">&nbsp;   Anotación <span class="text-danger">*</span> </h6>
                            </div>
                            <textarea  class="card border  p-1" name="txtanotacion" id="txtanotacion" ></textarea>
                        </div>
                    </div>
                    <div class="col-lg-12 mb-2">
                        <div class="card">
                            <div class="card p-2 bg-gradient-fond ">
                                <h6 class="m-0  text-gray-700 sansserif">&nbsp;   Características <span class="text-danger">*</span> </h6>
                            </div>
                            <textarea  class="card border  p-1" name="txtcaractr" id="txtcaractr" ></textarea>
                        </div>
                    </div>
                    <div class="col-lg-12 mb-1">
                        <div class="card">
                            <div class="card p-2 bg-gradient-fond">
                                <h6 class="m-0 sansserif text-gray-700">&nbsp; Comentarios <span class="text-danger">*</span> </h6>
                            </div>
                            <textarea class="card border  p-1" id="txtcomentarionewtr" ></textarea>
                        </div>
                    </div>
                    <div id="alertcampos">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn bg-secondary text-gray-100" data-dismiss="modal">Cancelar</button>
                    <input id="btnadd" type="submit" name="accion" value="Asignar" class="btn  bg-sidevar text-gray-100" onclick="return addTarea();" >
                </div>
            </div>
        </div>
    </div>
    <div id="mdalert" class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header bg-warning">
                    <h5 class="modal-title text-gray-100">Información</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <p class="fas text-gray-900 sansserif" id="alermd"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn bg-warning text-gray-100" data-dismiss="modal">Aceptar</button>
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
        request.getRequestDispatcher("error404.jsp").forward(request, response);
    }%>
<% } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>
