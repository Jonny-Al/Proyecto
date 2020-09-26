<%-- 
    Document   : confirmcode
    Created on : 3/03/2020, 8:50:35 p. m.
    Author     : ALEJANDRO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% if (session.getAttribute("Idusuario") != null && session.getAttribute("codigoemail") != null) { %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmacion de código</title>
        <!--===============================================================================================-->
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico" />
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css"  href="login/fonts/iconic/css/material-design-iconic-font.min.css">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="login/css/util.css">
        <link rel="stylesheet" type="text/css" href="login/css/main.css">
        <!--===============================================================================================-->
    </head>
    <body>
        <div class="container-fluid">
            <div class="limiter">
                <div class="container-login100" style="background-image: url('login/images/fondo.jpg');">
                    <!-------------------CONFIRMACION DE CODIGO ENVIADO AL CORREO-------------------->
                    <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-40" >
                        <span class="login100-form-title p-b-31  text-white" >
                            <img src="login/images/WC.png" class="fas fa-laugh-wink"/>
                        </span>
                        <span class="login100-form-title p-b-49  text-gray-500">
                            <h6>Ingresa el código enviado a tu correo alternativo.</h6>
                        </span>
                        <!----- Contenedor para confirmar correo -->
                        <div class="wrap-input100 validate-input m-b-23" data-validate="Ingresa el codigo.">
                            <input id="txtcodigo" type="text" name="txtcodigo" class="input100"  placeholder="Código confirmación">
                            <span class="focus-input100" data-symbol="&#xf190;"></span>
                        </div>
                        <div id="alertcod" class="p-t-8 p-b-31">  </div>
                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <button class="login100-form-btn" onclick="confirmarCodigo()">Confirmar código</button>
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
<%} else {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }%>
