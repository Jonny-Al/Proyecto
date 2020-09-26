<%-- 
    Document   : activateaccount
    Created on : 3/03/2020, 8:00:04 p. m.
    Author     : ALEJANDRO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% if (session.getAttribute("ROL") != null) { %>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Activar cuenta</title>
        <!--===============================================================================================-->
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css"  href="login/fonts/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="login/css/util.css">
        <link rel="stylesheet" type="text/css" href="login/css/main.css">
        <%  response.setHeader("Programa", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store,must-revalidate");
            response.setDateHeader("Expires", 0); %>
        <script type="text/javascript">
            history.forward();
        </script>
        <!--===============================================================================================-->
    </head>
    <body onload="cambios()">
        <div>
            <div class="login100-form validate-form">
                <div  class="limiter">
                    <div class="container-login100" style="background-image: url('login/images/fondo.jpg');">
                        <!-------------------CONFIRMACION DE CODIGO ENVIADO AL CORREO-------------------->
                        <div id="activate">
                            <div  class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-49" >
                                <span class="login100-form-title  text-gray-700">
                                    <h6 class="fas ">ACTIVACIÓN DE CUENTA</h6>
                                </span>
                                <br>
                                <!----- Contenedor para confirmar correo -->
                                <span class="login100-form-title p-b-49  text-gray-600 fas fa-1x">
                                    <h6>${nombres} </h6>
                                </span>
                                <span class="login100-form-title p-b-49  text-gray-600">
                                    <h6>${apellidos} </h6>
                                </span>
                                <span class="login100-form-title p-b-49  text-gray-600">
                                    <h6>${telefono}</h6>
                                </span>
                                <span class="login100-form-title p-b-1  text-gray-600">
                                    <h6>${correo}</h6>
                                </span>
                                <div class="p-t-8  text-center" >
                                    <a href="https://mailaccountsenterkey.000webhostapp.com/terminosycondiciones.html"  target="_blank"> Al confirmar aceptas los términos del servicio</a>
                                </div>
                                <div id="alert" class="p-t-8 p-b-10 text-center"></div>
                                <div class="container-login100-form-btn">
                                    <div class="wrap-login100-form-btn">
                                        <div class="login100-form-bgbtn"></div>
                                        <button type="button"  class="login100-form-btn" onclick="confirmarDatos()">Confirmar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--===============================================================================================-->
        <script src="login/js/validate.js"></script>
        <script src="login/js/jquery-3.2.1.min.js"></script>
        <script src="login/js/main.js"></script>
    </body>
</html>
<%} else { request.getRequestDispatcher("index.jsp").forward(request, response); }%>
