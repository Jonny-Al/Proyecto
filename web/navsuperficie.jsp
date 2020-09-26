<%-- 
    Document   : navsuperficie
    Created on : 3/03/2020, 9:01:31 p. m.
    Author     : ALEJANDRO
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<nav class="navbar navbar-expand navbar-light bg-fond-transparent topbar  static-top shadow">
    <!-- Sidebar Toggle (Topbar) -->
    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
        <i class="fa fa-bars"></i>
    </button>
    <!-- Topbar Navbar -->
    <ul class="navbar-nav ml-auto">
        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
        <!-- Nav Item - Alerts -->
        <!--  <li class="nav-item dropdown no-arrow mx-1">
              <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown"
                 aria-haspopup="true" aria-expanded="false" style="border-radius: 30px;">
                  <i class="fas fa-bell fa-fw text-gray-500"></i>
                
                  <span class="badge badge-danger badge-counter">3</span>
              </a>
              <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
                   aria-labelledby="alertsDropdown">
                  <h6 class="dropdown-header">Alerts Center
                  </h6>
                  <a class="dropdown-item d-flex align-items-center" href="#">
                      <div class="mr-3">
                          <div class="icon-circle bg-primary">
                              <i class="fas fa-file-alt text-white"></i>
                          </div>
                      </div>
                      <div>
                          <div class="small text-gray-500">December 12, 2019</div>
                          <span class="font-weight-bold">A new monthly report is ready to download!</span>
                      </div>
                  </a>
                  <a class="dropdown-item d-flex align-items-center" href="#">
                      <div class="mr-3">
                          <div class="icon-circle bg-success">
                              <i class="fas fa-donate text-white"></i>
                          </div>
                      </div>
                      <div>
                          <div class="small text-gray-500">December 7, 2019</div>
                          $290.29 has been deposited into your account!
                      </div>
                  </a>
                  <a class="dropdown-item d-flex align-items-center" href="#">
                      <div class="mr-3">
                          <div class="icon-circle bg-warning">
                              <i class="fas fa-exclamation-triangle text-white"></i>
                          </div>
                      </div>
                      <div>
                          <div class="small text-gray-500">December 2, 2019</div>
                          Spending Alert: We've noticed unusually high spending for your account.
                      </div>
                  </a>
                  <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
              </div>
          </li> -->
        <!-- Nav Item - Messages -->
        <li class="nav-item dropdown no-arrow mx-0">
            <a class="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown"
               aria-haspopup="true" aria-expanded="false" style="border-radius: 30px;">
                <div class="card-body-icon text-gray-500">
                    <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-chat-text hover-aqua" fill="currentColor">
                        <path fill-rule="evenodd" d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
                        <path fill-rule="evenodd" d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
                    </svg>
                </div>

                <!-- Counter - Messages -->
                <span class="badge badge-danger badge-counter ch"></span> <!-- 4 -->
            </a>
            <!-- Dropdown - Messages -->
            <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
                 aria-labelledby="messagesDropdown">
                <h6 class="dropdown-header">Mensajeria.
                </h6>
                <a class="dropdown-item d-flex align-items-center" href="#" onclick="openChatTodos()">
                    <div class="mr-3">
                        <div class="icon-circle bg-success">
                            <i class="fas fa-users text-white"></i>
                        </div>
                    </div>
                    <div>
                        <div class="small text-gray-500">Todos</div>
                        <span class="font-weight-bold">Mensaje de toda la empresa</span>
                    </div>
                </a>
                <!-- <a class="dropdown-item d-flex align-items-center" href="#" >
                     <div class="mr-3">
                         <div class="icon-circle bg-primary">
                             <i class="fas fa-user-friends text-white"></i>
                         </div>
                     </div>
                     <div>
                         <div class="small text-gray-500">Grupo</div>
                         <span class="font-weight-bold">Mensajes del grupo!</span>
                     </div>
                 </a>
                 <a class="dropdown-item d-flex align-items-center" href="#">
                     <div class="mr-3">
                         <div class="icon-circle bg-info">
                             <i class="far fa-comment-dots text-white"></i>
                         </div>
                     </div>
                     <div>
                         <div class="small text-gray-500">Mis mensajes</div>
                         <span class="font-weight-bold">Mensajes enviados a mi!</span>
                     </div>
                 </a> 
                 <a class="dropdown-item text-center small text-gray-500" href="#">Ver todos</a> -->
            </div> 
        </li>
        <!-- Nav Item - User Information -->
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle hover-aqua" href="#" id="userDropdown"
               role="button" data-toggle="dropdown" aria-haspopup="true"
               aria-expanded="false" style="border-radius: 50px;">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"></span>
                <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-person-circle text-gray-500 hover-aqua" fill="currentColor">
                    <path d="M13.468 12.37C12.758 11.226 11.195 10 8 10s-4.757 1.225-5.468 2.37A6.987 6.987 0 0 0 8 15a6.987 6.987 0 0 0 5.468-2.63z"/>
                    <path fill-rule="evenodd" d="M8 9a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                    <path fill-rule="evenodd" d="M8 1a7 7 0 1 0 0 14A7 7 0 0 0 8 1zM0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8z"/>
                </svg>
            </a>
            <!-- Dropdown - User Information -->
            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="modification.jsp"  >
                    <i class="fas fa-tools fa-sm fa-fw mr-2 text-gray-400"></i>
                    Modificaciones
                </a> 
                <hr class="sidebar-divider col-lg-8 mb-1">
                    <a class="dropdown-item" href="#" data-toggle="modal"
                       data-target="#logoutModal" >
                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                        Cerrar sesion
                    </a>
            </div>
        </li>
    </ul>
</nav>
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-gradient-primary">
                <h5 class="modal-title text-gray-200" id="exampleModalLabel">Desea cerrar sesion ?</h5>
                <button class="close" type="button" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Seleccione "Cerrar"  para finalizar su sesión actual de lo contrario cancelar.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button"
                        data-dismiss="modal">Cancelar</button>
                <form action="validate" method="POST">
                    <input  type="submit" class="btn btn-primary bg-primary"  name="accion"  value="Cerrar">
                </form>
            </div>
        </div>
    </div>
</div>

<!-- MODAL DE RECARGA -->
<div class="modal fade bd-example-modal-sm" id="modalcarga" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="">
            <div class="modal-body ">
                <div class=" text-center">
                    <div class="spinner-grow text-rosado p-8" role="status"> </div>
                </div>
                <div class="spinner-grow text-info p-8" role="status"> </div>
                <div class="spinner-grow text-primary p-8" role="status"> </div>
            </div>
            <div class="mb-2 text-center">
                <label id="labelmodalcarga" class="fas fa-2x text-purple"></label>
            </div>
        </div>
    </div>
</div>

<!--- chats -->
<div id="mySidenav" class="sidenav bg-gray-100 border-left h-100 d-inline-block imgchat">
    <a href="javascript:void(0)" class="closebtn mb-1 fas fa-1x" onclick="closeChatTodos();">&times;</a>
    <div id="chatsgrup"  class="scr panel-body w-100 p-3 h-83 d-inline-block "> 
        <c:set var = "minombre"  value = "${miname}"/>
        <c:forEach var="ch" items="${listchat}">
            <div class="d-flex justify-content-start mb-1">
                <c:set var = "nombreusuario"  value = "${ch.getUsuario()}"/>
                <c:if test = "${minombre == nombreusuario}">
                    <c:set var = "colorchat"  value = "alert-primary"/>
                </c:if>
                <c:if  test = "${minombre != nombreusuario}">
                    <c:set var = "colorchat" value = "amarillo"/>
                </c:if>
                <div class="fas msg_cotainer  ${colorchat} text-gray-700">
                    <strong class=" text-purple">${ch.getUsuario()}:<br> </strong> ${ch.getMensaje()}
                    <div class="msg">
                        <time><i class="fas fa-clock"></i> ${ch.getHoramensaje()}  </time>
                    </div>
                </div>
            </div>
        </c:forEach>
        <ul class="chat"></ul>
    </div>
    <div style="display: none" id="plantilla">
        <div class="d-flex justify-content-start mb-1">
            <div class="fas msg_cotainer alert-primary text-gray-700">
                <strong class="text-purple"> </strong> 
                <label id="nombreusuario" class="text-purple"></label><br>
                    <label class="msj"></label> <!-- llegan los msjs escritos de los websockets -->
                    <div class="msg">
                        <time id="tiempo"></i></time>
                    </div>
            </div>
        </div>
    </div>
    <div class="p-2">
        <div class="input-group">
            <textarea id="txtchat" class="form-control p-0 col-lg-12" aria-label="With textarea"></textarea>
        </div>
        <div class="p-2 mb-2">
            <input id="btnchat"  type="button" name="accion" value="Enviar" class="btn bg-gradient-blue text-gray-200"  onclick="chatAll();">
        </div>
    </div>
</div>
<script src="dashboard/js/chat.js"></script>





