<%-- 
    Document   : profile
    Created on : 3/03/2020, 9:03:08 p. m.
    Author     : ALEJANDRO
--%>
<% if (session.getAttribute("ID") != null) { %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%  response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0); %>
        <c:set var="nombre" value="${miname}"></c:set>
        <c:if test="${nombre == null}">
            <% request.getRequestDispatcher("users?menu=Perfil&accion=Miperfil").forward(request, response); %>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Mi perfil</title>
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <link href="dashboard/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="dashboard/css/sb-admin-2.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navlateral.jsp" flush="true"></jsp:include>
                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content" class="bg-gray-200">
                    <jsp:include  page="navsuperficie.jsp" flush="true"></jsp:include>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-lg-12 d-inline-block">
                                    <div class="card rounded">
                                        <div class="card-body p-0 rounded bg-blanco">
                                            <!-- Nested Row within Card Body -->
                                            <div class="row ">
                                                <div class="col-lg-6 d-none d-lg-block  border-0">
                                                    <div class="card">
                                                        <div class="card-body">
                                                            <div class="text-center border-0">
                                                                <i class=" fa-1x text-gray-300 text-center border-0">
                                                                    <a class="dropdown-toggle text-gray-500" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>
                                                                    <div class="dropdown-menu text-center" aria-labelledby="btnGroupDrop1">
                                                                        <button class="fas fa-camera text-gray-400 bg-fond-transparent btn-circle border-0" title="Cambiar foto"  data-toggle="modal" data-target="#modalfoto"></button>
                                                                        <button id="btneliminafoto" class="fas fa-trash-alt text-gray-400 bg-fond-transparent btn-circle border-0" title="Eliminar foto" ></button>
                                                                    </div>
                                                                </i>
                                                            </div>
                                                            <img class="card w-100 p-3 h-75 d-inline-block" src="/${misdatos.getFotoperfil()}">
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="card">
                                                    <div class="card-body">
                                                        <div class="mb-5">
                                                            <div class="user">
                                                                <div class="form-group mb-5">
                                                                    <div class="col-sm-12 mb-3 fas  fa-2x mb-sm-0 text-gray-200">
                                                                        <div class="py-5 mb-5 text-center sansserif text-gray-700" style="font-size:22px">
                                                                            INFORMACIÓN DEL PERFIL
                                                                        </div>
                                                                        <div class="text-gray-500 text-center sansserif" style="font-size:25px">
                                                                            <label id="lblnombres" > ${misdatos.getNombres()} </label>&nbsp;<label id="lblapellidos">${misdatos.getApellidos()} </label> 
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group mb-5">
                                                                    <div class="col-sm-11 mb-2 fas  fa-2x mb-sm-0 text-gray-200 sansserif" style="font-size:25px">
                                                                        <div  class="text-gray-500 text-center mb-4">
                                                                            <label id="lbltelefono">  ${misdatos.getTelefono()}</label>
                                                                        </div>
                                                                        <br>
                                                                        <div class="text-center">
                                                                            <div class="text-gray-500 mb-4">
                                                                                <label id="lblcorreo"> ${misdatos.getCorreo()} </label>
                                                                            </div>
                                                                            <div class="fas text-gray-500 sansserif">
                                                                                <label id="lblcorreoalterno" style="font-size:25px"> &nbsp;  ${misdatos.getCorreoalterno()} </label>
                                                                            </div> 
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="form-group mb-4">
                                                                    <div class="col-sm-11 mb-3 fas  fa-2x mb-sm-0 text-gray-200">
                                                                        <div class="text-gray-500 text-center sansserif mb-3" style="font-size:25px">
                                                                            ${misdatos.getRolNombre()}
                                                                        </div>
                                                                        <br>
                                                                        <div class="text-gray-500 text-center sansserif" style="font-size:25px">
                                                                            ${misdatos.getAreaNombre()} 
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div id="alercmp">
                                                                <div id="alertdatos" class="mb-b-23 ${clase}" role="alert">${alert}</div>
                                                            </div>
                                                        </div>
                                                        <div  class="mx-auto form-group row float-right" >
                                                            <div class="p-1">
                                                                <button type="button" class="btn bg-sidevar btn-user text-gray-200 "  data-toggle="modal"  data-target="#updateDatos">Actualizar información</button>  
                                                            </div>
                                                            <div class="p-1">
                                                                <button type="button" class="btn bg-encabezados  text-gray-200tn bg-aqua text-gray-200 "style="width: 200px;" data-toggle="modal"  data-target="#updatePassword">Actualizar clave</button>  
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!------------- contenedor para actualziar los datos------------->
                            <div class="modal fade" id="updateDatos" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-primary">
                                            <h5 class="modal-title text-gray-200" id="exampleModalLongTitle">Actualizar datos</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group row sansserif">
                                                <div class="col-sm-9 mb-2 ">
                                                    Nombres <span class="text-danger">*</span>
                                                    <div class=" validate-input" >
                                                        <input id="txtnombres" type="text" title="Nombres" class="form-control form-control-user input100 " value="${misdatos.getNombres()}"  placeholder="Nombres" >
                                                    </div>
                                                </div>
                                                <div class="col-sm-9">
                                                    Apellidos <span class="text-danger">*</span>
                                                    <div class=" validate-input mb-1" >
                                                        <input id="txtapellidos" type="text" title="Apellidos" class="form-control form-control-user input100 " value="${misdatos.getApellidos()}"  placeholder="Apellidos">
                                                    </div>
                                                    Telefono <span class="text-danger">*</span>
                                                    <div class=" validate-input m-b-23 mb-1" >
                                                        <input id="txttelefono" type="text" title="Telefono " class="form-control form-control-user  input100 " value="${misdatos.getTelefono()}"  placeholder="Telefono">
                                                    </div>
                                                </div>
                                                <div class="col-sm-9">
                                                    Correo alternativo <span class="text-danger">*</span>
                                                    <div class=" validate-input m-b-23" >
                                                        <input id="txtcorreoalterno" type="text" title="Correo alternativo" class="form-control form-control-user input100" value="${misdatos.getCorreoalterno()}"  placeholder="Correo alternativo">
                                                    </div>
                                                </div>

                                            </div>
                                            <div id="class">
                                                <label id="lblinf"></label>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button class="btn btn-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar</button>
                                            <button  type="button" class="btn btn-primary text-gray-100" onclick="updInfo()">Actualizar datos</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="updatePassword" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-success">
                                            <h5 class="modal-title text-gray-200" id="exampleModalLongTitle">Actualizar contraseña</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body sansserif">
                                            <div class="form-group row">
                                                <div class="col-sm-6  mb-sm-0">
                                                    <span class="text-danger float-right">*</span>
                                                    <input id="txtpsactual" type="password" class="form-control form-control-user input100"  name="txtpassactual" placeholder="Clave actual" >
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <div class="col-sm-6  mb-sm-0">
                                                    <span class="text-danger float-right">*</span>
                                                    <input id="txtpssone" type="password" class="form-control form-control-user input100"  name="txtpass" placeholder="Clave">
                                                </div>
                                                <hr>
                                                <div class="col-sm-6">
                                                    <span class="text-danger float-right">*</span>
                                                    <input id="txtpsstwo" type="password" class="form-control form-control-user input100"  name="txtpassconfirma" placeholder="Confirme su clave">
                                                </div>
                                            </div>
                                            <div class="text-center">
                                                <div id="p" class="progress col-lg-4 eye"  style="height: 1px; margin-left:30%">
                                                    <div id="progress" class="progress-bar progress-bar-striped" role="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100"></div>
                                                </div>
                                            </div>
                                            <div id="altert">
                                                <label id="lblps"></label>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button class="btn btn-secondary text-gray-100" type="button"  data-dismiss="modal">Cancelar</button>
                                            <button id="btnupdatepass"  type="button" class="btn btn-success text-gray-100" >Actualizar</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div id="modalfoto" class="modal" tabindex="-1" role="dialog">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-gradient-blue">
                                            <h5 class="modal-title text-gray-200">Cambiar foto de perfil</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <input  name="file" id="file" type="file" class="form-control-file">
                                            </div> 
                                        </div>
                                        <label id="lblcarga"></label>
                                        <div class="modal-footer">
                                            <button id="btncargafoto" type="button" class="btn bg-gradient-blue text-gray-100" >Cargar foto</button> 
                                            <button type="button" class="btn bg-gradient-secondary text-gray-100" data-dismiss="modal">Cancelar</button>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="modalcodigoalterno" class="modal" tabindex="-1" role="dialog">
                                <div class="modal-dialog modal-dialog-centered" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header bg-gradient-blue">
                                            <h5 class="modal-title text-gray-200">Confirmacion de codigo</h5>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <div id="divcodigo" class="col-sm-12">
                                                    <div id="divmensajecodigo" class="text-gray-600 mb-2">
                                                        Comprobamos que agregaste un nuevo correo alternativo, por favor ingresa el código enviado al correo nuevo y revisa tu bandeja de entrada o spam.
                                                    </div>
                                                    <div class=" validate-input m-b-23" >
                                                        <input id="txtcodalterno" type="text" title="Correo alternativo" class="form-control form-control-user input100"  placeholder="Codigo de confirmacion">
                                                    </div>
                                                </div>
                                            </div> 
                                        </div>
                                        <label id="lblcodigo" class=""></label>
                                        <div class="modal-footer">
                                            <button type="button" class="btn bg-gradient-blue text-gray-100" onclick="updInfo()">Comprobar</button> 
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
        <script src="dashboard/js/jquery-3.5.1.js"></script>
        <script src="dashboard/js/users.js"></script>
        <script src="dashboard/vendor/jquery/jquery.min.js"></script>
        <script src="dashboard/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="dashboard/js/sb-admin-2.min.js"></script>
    </body>
</html>
<%  } else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>
