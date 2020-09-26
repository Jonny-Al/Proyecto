<%-- 
    Document   : roomcalendar
    Created on : 3/03/2020, 9:03:52 p. m.
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
            <% request.getRequestDispatcher("meetings?menu=Salajuntas").forward(request, response);%>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Reuniones</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico">
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
        <!-- RUTAS DE ESTILOS DEL CALENDARIO -->
        <link href='dashboard/calendar/core/main.css' rel='stylesheet' >
        <link href='dashboard/calendar/daygrid/main.css' rel='stylesheet' >
        <link href='dashboard/calendar/timegrid/main.css' rel='stylesheet'>
        <link href='dashboard/calendar/list/main.css' rel='stylesheet' >
        <style>
            tr:hover{
                background-image: linear-gradient(180deg, #fff 20%, #fff 100%);
                background-size: cover;}
            </style>
        </head>
        <body>
            <div id="wrapper">
            <!-- Sidebar -->
            <jsp:include page="navlateral.jsp" flush="false"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content" class="bg-gray-100">
                    <jsp:include page="navsuperficie.jsp" flush="false"></jsp:include>
                        <!--Contenedor de las demas paginas--> 
                        <div class="container-fluid">
                            <!-- Content Wrapper. Contains page content -->
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-lg-3">
                                        <div class="card bg-fond-transparent">
                                            <div class="card-body rounded bg-blanco">
                                                <button class="card py-2 mb-2 bg-gradient-aqua text-white border-0 col-12"  data-toggle="modal"  data-target="#mdUpdate" title="Mis reuniones">
                                                    <i class="fas fa-map-marked-alt">&nbsp;<span class="sansserif">Mis reuniones</span> </i> 
                                                </button>
                                                <hr>
                                                <div class="card p-2 mb-4 bg-gradient-primary text-white text-center">
                                                    <i class="fas fa-calendar-alt  fa-1x text-gray-200">&nbsp; <span class="sansserif">Crear reunión</span></i>
                                                </div> 
                                                <div class="card mb-3 bg-fond-transparent sansserif text-gray-700">
                                                    <div class="form-group row">
                                                        <div class="col-sm-12 mb-2 mb-sm-0">
                                                            Nombre de reunión <span class="text-danger">*</span> 
                                                            <input id="txtevento" type="text" class="form-control"   placeholder="Escribe aqui." >
                                                        </div>
                                                    </div>
                                                    <!------------------------------------>
                                                    <div class="form-group row">
                                                        <div class="col-sm-12 mb-2 mb-sm-0">
                                                            Fecha de inicio <span class="text-danger">*</span> 
                                                            <input class="form-control" type="date" id="dateinicio" >
                                                        </div>
                                                    </div>
                                                    <!------------------------------------>
                                                    <div class="form-group row">
                                                        <div class="col-sm-12 mb-2 mb-sm-0">
                                                            Fecha final <span class="text-danger">*</span> 
                                                            <input class="form-control" type="date" id="datefin" >
                                                        </div>
                                                    </div>
                                                    <!------------------------------------>
                                                    <div class="form-group row">
                                                        <div class="col-sm-12 mb-2 mb-sm-0">
                                                            Hora de inicio <span class="text-danger">*</span> 
                                                            <input class="form-control" type="time" id="timeinicio"  >
                                                        </div>
                                                        <div class="col-sm-12 mb-2 mb-sm-0">
                                                            Hora final <span class="text-danger">*</span> 
                                                            <input class="form-control" type="time" id="timefin">
                                                        </div>
                                                    </div>
                                                    <div class="mb-1 p-1 col-lg-12">
                                                        <textarea class="card col-lg-12" id="comentreunion"></textarea>
                                                    </div>
                                                    <div id="dtinvalid" class=""> <label id="lbl"></label></div>
                                                    <div class="mb-2">
                                                        <button class="btn bg-gradient-primary text-gray-200 float-right" id="btnaddre" onclick="return addReunion();">Agregar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Calendario -->
                                    <div class="col-lg-6">
                                        <div class="card">
                                            <div class="card-body rounded bg-blanco">
                                                <div id="calendar" class="bg-fond-transparent shadow-lg"></div>
                                            </div>
                                        </div>
                                    </div>

                                    <!--- LISTA CON COMENTARIOS SOBRE UNA REUNION -->

                                    <div class="col-lg-3 card" >
                                        <div class="card">
                                            <div class="card-body rounded bg-blanco">
                                            <%! int numid = 1; %>
                                            <c:forEach var="lista" items="${espacios}"> 
                                                <%!String[] colors = {"amarillo", "cafe", "lilaclaro", "bg-info", "verde"};%><%!int contador = 0;%>
                                                <a  href="#" class="list-group mb-1 shadow-lg " onclick="verComentarios('<%=numid%>')" title="Ver comentarios">
                                                    <div href="#" class="list-group-item list-group-item-action flex-column align-items-start <%=colors[contador]%>">
                                                        <div class="d-flex w-100 justify-content-between">
                                                            <span class="mb-1 ">${lista.getNombrereunion()}</span>
                                                            <small>${lista.getNombres()}</small>
                                                        </div>
                                                        <p id="pcomentarios<%=numid%>" class="mb-0 eyereuniones y sansserif"> ${lista.getComentarios()}</p>
                                                    </div>
                                                </a>
                                                <% contador++;
                                                    numid++;
                                                    if (contador == 5) {
                                                        contador = 0;
                                                    }%>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div id="mdUpdate" class="modal" tabindex="-1" role="dialog">
                            <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header bg-gradient-primary">
                                        <h6 class="fas fa-map-marked-alt  modal-title text-gray-200"> &nbsp; <span class="sansserif" style="font-size:14px">Mis reuniones</span></h6>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- REUNIONES DEL USUARIO QUE ESTA EN EL SISTEMA -->
                                        <table class="table table-striped bg-fond-transparent sansserif">
                                            <thead class=" bg-fond-transparent">
                                                <tr>
                                                    <th>Reunión</th>
                                                    <th>Fecha Inicial</th>
                                                    <th>Fecha final</th>
                                                    <th>Inicia</th>
                                                    <th>Finaliza</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="misreu" items="${reuniones}">
                                                    <tr class=" bg-fond-transparent">
                                                        <td> ${misreu.getNombrereunion()}</td>
                                                        <td> ${misreu.getFechainicio()}</td>
                                                        <td> ${misreu.getFechafinal()}</td>
                                                        <td> ${misreu.getHorainicio()}</td>
                                                        <td> ${misreu.getHorafin()}</td>
                                                        <td scope="row" >
                                                            <button id="btnmeeting" type="button" class="btn text-primary" title="Editar" onclick="return traerReunion('${misreu.getIdReunion()}', '${misreu.getNombrereunion()}', '${misreu.getFechainicio()}', '${misreu.getFechafinal()}', '${misreu.getHorainicio()}', '${misreu.getHorafin()}', '${misreu.getComentarios()}');"><i class="fas fa-edit"></i></button>
                                                            <button id="btnmeeting" type="button" class="btn text-danger" title="Eliminar" onclick="return confimEliminar('${misreu.getIdReunion()}')"><i class="gg-trash"></i></button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                        <div class="eye">
                                            <hr>
                                            <div class="card-body mb-1">
                                                <div id="external-events" class="sansserif" >
                                                    <div class="form-group row">
                                                        <div class="col-sm-12 mb-1 mb-sm-0">
                                                            Nombre <span class="text-danger">*</span> 
                                                            <input id="nmreunion" type="text" class="form-control"  name="txteventoupdate" placeholder="Escribe aqui.">
                                                        </div>
                                                    </div>
                                                    <!------------------------------------>
                                                    <div class="form-group row">
                                                        <div class="col-sm-6 mb-1 mb-sm-0">
                                                            Fecha de inicio <span class="text-danger">*</span> 
                                                            <input class="form-control" type="date" name="dateinicioupdate"  id="dtinicio">
                                                        </div>
                                                        <div class="col-sm-6 mb-1 mb-sm-0">
                                                            Fecha final <span class="text-danger">*</span> 
                                                            <input class="form-control" type="date" name="datefinupdate"  id="dtfin" >
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <div class="col-sm-6 mb-1 mb-sm-0">
                                                            Hora de inicio <span class="text-danger">*</span> 
                                                            <input class="form-control" type="time" name="timeinicioupdate"  id="hrinicio" >
                                                        </div>
                                                        <div class="col-sm-6 mb-1 mb-sm-0">
                                                            Hora final <span class="text-danger">*</span> 
                                                            <input class="form-control" type="time" name="timefinupdate"  id="hrfin">
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-12">
                                                        <div class="card mb-1">
                                                            <textarea id="txtcomentarios" class="card col-lg-12"></textarea>
                                                        </div>
                                                    </div>
                                                    <div id="alertupdd" class="mb-2 p-2"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer eye">
                                            <button id="btnupd" type="button" class="btn bg-gradient-primary text-gray-100" onclick="return updateReunion();">Actualizar</button>
                                            <button type="button" class="btn bg-gradient-secondary text-gray-100" data-dismiss="modal">Cerrar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--MODAL -> MEETING OF THE USER -->

                        <div class="modal fade" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header bg-gradient-danger">
                                        <h5 class="fas fa-exclamation-triangle modal-title text-gray-200" id="exampleModalLongTitle">&nbsp; <span class="sansserif" style="font-size:14px">Eliminar</span></h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body sansserif">
                                        ¿ Confirma eliminar la reunión ? <br>
                                        No podra deshacer los cambios.
                                    </div>
                                    <label id="eliminareunion"></label>
                                    <div class="modal-footer">
                                        <button type="button" class="btn bg-gradient-secondary text-gray-100" data-dismiss="modal">Cerrar</button>
                                        <button id="btndelete" class="btn bg-gradient-danger text-gray-100" onclick="return deleteReunion();" >Eliminar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <script>
                        document.addEventListener('DOMContentLoaded', function () {
                            var calendarEl = document.getElementById('calendar');
                            var fechactual = new Date();
                            var calendar = new FullCalendar.Calendar(calendarEl, {
                                plugins: ['dayGrid', 'timeGrid', 'list', 'interaction'],
                                header: {
                                    left: 'prev,next',
                                    center: 'title',
                                    right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
                                },
                                defaultDate: fechactual, // Fecha por defectto
                                navLinks: true, // can click day/week names to navigate views
                                editable: true,
                                eventLimit: true, // allow "more" link when too many events
                                events: [<c:forEach var="espacios" items="${espacios}">{
                                        title: '${espacios.getNombrereunion()}', // No eliminar esta 
                                        start: '${espacios.getFechainicio()}T${espacios.getHorainicio()}', // Todo debe estar pegado entre las ''
                                                            end: '${espacios.getFechafinal()}T${espacios.getHorafin()}'  // Todo debe estar pegado entre las ''
                                                                            }, </c:forEach>]
                                                                    });

                                                                    calendar.render();
                                                                });
                        </script>

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
            <!-- RUTAS DE SCRIPTS DEL REUNIONES -->

            <script src="dashboard/js/meetings.js"></script>
            <!-- RUTAS DE SCRIPTS DEL CALENDARIO -->

            <script src='dashboard/calendar/core/main.js'></script>
            <script src='dashboard/calendar/daygrid/main.js'></script>
            <script src='dashboard/calendar/timegrid/main.js'></script>
            <script src=dashboard/calendar/list/main.js '></script>
            <!-- RUTAS DE SCRIPT DEL SIDEBAR -->
            <script src="dashboard/vendor/jquery/jquery.min.js"></script>
            <script src="dashboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="dashboard/js/sb-admin-2.min.js"></script>
        </body>
    </html>
<% } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>