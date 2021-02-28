<%-- 
    Document   : index
    Created on : 3/03/2020, 7:58:49 p. m.
    Author     : ALEJANDRO
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
        <META HTTP-EQUIV="Cache-Control" CONTENT="no-store">
        <title>WooClic</title>
        <!--===============================================================================================-->
        <link rel="icon" type="login/image/png" href="login/images/icons/wc.ico">
        <!--===============================================================================================-->
        <link rel="stylesheet" type="text/css" href="login/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="login/fonts/iconic/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" type="text/css" href="login/css/util.css">                                                                                                                                                                                                                  
        <link rel="stylesheet" type="text/css" href="login/css/main.css">
        <!--===============================================================================================-->
    </head>
    <body>
<!-- login  html hoja-->
        <div class="limiter">
            <div class="container-login100" style="background-image: url('login/images/fondo.jpg');">
                <div class="wrap-login100 p-l-55 p-r-55 p-t-45 p-b-30">
                    <form class="login100-form validate-form" action="validate" method="POST">
                        <span class="login100-form-title p-b-15  text-white">
                            <img src="login/images/WC.png" class="fas fa-laugh-wink">
                        </span>
                        <span class="login100-form-title text-gray-500 p-b-30">
                            <h6 class="fas">Iniciar Sesión</h6>
                        </span>
                        <div class="wrap-input100 validate-input m-b-10" data-validate="El correo es obligatorio">
                            <input type="text" name="correo" class="input100"  value="${correo}" placeholder="Correo electrónico">
                            <span class="focus-input100" data-symbol="&#xf206;"></span>
                        </div>
                        <div class="wrap-input100 validate-input" data-validate="La contraseña es obligatoria">
                            <input id="pass" type="password"  name="pass" class="input100" placeholder="Contraseña">
                            <span class="focus-input100" data-symbol="&#xf190"></span>
                        </div><br>
                        <div class="text-center p-t-5 p-b-20">
                            <a id="eye" href="#" onclick="ps()">
                                Ver contraseña
                            </a>&nbsp;
                            <a href="confirmemail.jsp">
                                Olvide mi contraseña
                            </a>
                        </div>
                        <div id="alert" class="alertmsj" >${alert}</div>
                        <div class="container-login100-form-btn">
                            <div class="wrap-login100-form-btn">
                                <div class="login100-form-bgbtn"></div>
                                <input type="submit" name="accion" value="Ingresar" class="login100-form-btn"> 
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script src="login/js/validate.js"></script>
        <script src="login/js/jquery-3.2.1.min.js"></script>
        <script src="login/js/main.js"></script>
    </body>
</html>
