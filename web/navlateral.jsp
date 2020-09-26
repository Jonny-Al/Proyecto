<%-- 
    Document   : navlateral
    Created on : 3/03/2020, 9:01:05 p. m.
    Author     : ALEJANDRO
--%>
<ul class="navbar-nav bg-gradient-sidevar sidebar sidebar-dark accordion toggled" id="accordionSidebar">
    <input type="hidden" value="<%=session.getAttribute("NOMBRE")%>" id="nm">
    <input  type="hidden" value="<%=session.getAttribute("ID")%>" id="id">
    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center mb-4" href="https://mailaccountsenterkey.000webhostapp.com/" target="_blank" >
        <!--Logo muñeco-->
        <div class="sidebar-brand-icon rotate-n-20">
            <div class="mb-2"></div>
            <img src="dashboard/Imagenes/LogoWC.png" class="fas fa-laugh-wink" />
        </div>
    </a>
    <div class="mb-4"></div>
    <div class="mb-4"></div>
    <!-- <hr class="sidebar-divider"> -->
    <li class="nav-item active">
        <a class="nav-link hover-aqua"  href="profile.jsp">
            <svg width="22" height="22" viewBox="0 0 16 16" class="bi bi-emoji-smile" fill="currentColor">
            <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
            <path fill-rule="evenodd" d="M4.285 9.567a.5.5 0 0 1 .683.183A3.498 3.498 0 0 0 8 11.5a3.498 3.498 0 0 0 3.032-1.75.5.5 0 1 1 .866.5A4.498 4.498 0 0 1 8 12.5a4.498 4.498 0 0 1-3.898-2.25.5.5 0 0 1 .183-.683z"/>
            <path d="M7 6.5C7 7.328 6.552 8 6 8s-1-.672-1-1.5S5.448 5 6 5s1 .672 1 1.5zm4 0c0 .828-.448 1.5-1 1.5s-1-.672-1-1.5S9.448 5 10 5s1 .672 1 1.5z"/>
            </svg>
            <span class="text-gray-400">${miname}</span>
        </a>
    </li>
    <li class="nav-item active">
        <a class="nav-link"  href="allindex.jsp">
            <svg width="22" height="22" viewBox="0 0 16 16" class="bi bi-house hover-aqua" fill="currentColor">
            <path fill-rule="evenodd" d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
            <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
            </svg>
            <span class="text-gray-400">Inicio</span>
        </a>
    </li>
    <div class="mb-5"></div>
    <!-- <hr class="sidebar-divider mb-4"> -->
    <!-- NAV ITEM TAREAS -->
    <li class="nav-item mb-1">
        <a class="nav-link collapsed hover-aqua" href="#"  data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-card-checklist hover-aqua" fill="currentColor" >
            <path fill-rule="evenodd" d="M14.5 3h-13a.5.5 0 0 0-.5.5v9a.5.5 0 0 0 .5.5h13a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5zm-13-1A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h13a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-13z"/>
            <path fill-rule="evenodd" d="M7 5.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 1 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0zM7 9.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm-1.496-.854a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0l-.5-.5a.5.5 0 0 1 .708-.708l.146.147 1.146-1.147a.5.5 0 0 1 .708 0z"/>
            </svg>
            <span class="text-gray-400">Tareas</span> 
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-gradient-sidevar py-2 collapse-inner rounded">
                <a class="collapse-item" href="mytasks.jsp" >Mis tareas</a>
                <%if ((int) session.getAttribute("IDROL") < 3) { %> 
                <a class="collapse-item " href="tasksofgroup.jsp">Grupales </a> 
                <% } %>
                <a class="collapse-item " href="taskhistory.jsp">Historial</a>
            </div>
        </div>
    </li>
    <!-- NAV ITEM CALENDARIOS -->
    <li class="nav-item mb-1">
        <a class="nav-link collapsed hover-aqua" href="#" data-toggle="collapse" data-target="#collapseUtilities"
           aria-expanded="true" aria-controls="collapseUtilities">
            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-calendar3" fill="currentColor">
            <path fill-rule="evenodd" d="M14 0H2a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM1 3.857C1 3.384 1.448 3 2 3h12c.552 0 1 .384 1 .857v10.286c0 .473-.448.857-1 .857H2c-.552 0-1-.384-1-.857V3.857z"/>
            <path fill-rule="evenodd" d="M6.5 7a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm-9 3a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2zm3 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
            </svg>
            <span class="text-gray-400">Sala</span> 
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
            <div class="bg-gradient-sidevar py-2 collapse-inner rounded">
                <!-- menu=CalendarioSalas&accion=ListarCalendario -->
                <a class="collapse-item" href="meetings.jsp">Reuniones</a>
                <!--  <a class="collapse-item" href="meetings?menu=Micalendario">Mi calendario</a> -->
            </div>
        </div>
    </li>
    <!-- Divider -->
    <!-- Heading -->
    <!-- NAV ITEM OBJETOS -->
    <li class="nav-item mb-1">
        <a class="nav-link collapsed hover-aqua" href="#" data-toggle="collapse" data-target="#collapsePages" aria-expanded="true" aria-controls="collapsePages">
            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-box-seam" fill="currentColor">
            <path fill-rule="evenodd" d="M8.186 1.113a.5.5 0 0 0-.372 0L1.846 3.5l2.404.961L10.404 2l-2.218-.887zm3.564 1.426L5.596 5 8 5.961 14.154 3.5l-2.404-.961zm3.25 1.7l-6.5 2.6v7.922l6.5-2.6V4.24zM7.5 14.762V6.838L1 4.239v7.923l6.5 2.6zM7.443.184a1.5 1.5 0 0 1 1.114 0l7.129 2.852A.5.5 0 0 1 16 3.5v8.662a1 1 0 0 1-.629.928l-7.185 2.874a.5.5 0 0 1-.372 0L.63 13.09a1 1 0 0 1-.63-.928V3.5a.5.5 0 0 1 .314-.464L7.443.184z"/>
            </svg>
            <span class="text-gray-400">Objetos</span> 
        </a>
        <div id="collapsePages" class="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
            <div class="bg-gradient-sidevar py-2 collapse-inner rounded">
                <a class="collapse-item" href="implementsevents.jsp">Eventos</a>
                <%  if ((int) session.getAttribute("IDROL") < 3) { %>
                <a class="collapse-item" href="allobjects.jsp">Registros</a>
                <% } %>
                <div class="collapse-divider"></div>
            </div>
        </div>
    </li>
    <!-- Nav Item - Usuarios -->
    <li class="nav-item mb-1">
        <a class="nav-link collapsed hover-aqua" href="#" data-toggle="collapse" data-target="#collapseUsers" aria-expanded="true" aria-controls="collapsePages">
            <svg width="18" height="18" viewBox="0 0 16 16" class="bi bi-people " fill="currentColor">
            <path fill-rule="evenodd" d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1h7.956a.274.274 0 0 0 .014-.002l.008-.002c-.002-.264-.167-1.03-.76-1.72C13.688 10.629 12.718 10 11 10c-1.717 0-2.687.63-3.24 1.276-.593.69-.759 1.457-.76 1.72a1.05 1.05 0 0 0 .022.004zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10c-1.668.02-2.615.64-3.16 1.276C1.163 11.97 1 12.739 1 13h3c0-1.045.323-2.086.92-3zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"/>
            </svg>
            <span class="text-gray-400">Grupos</span> 
        </a>
        <div id="collapseUsers" class="collapse" aria-labelledby="headingUsers" data-parent="#accordionSidebar">
            <div class="bg-gradient-sidevar py-2 collapse-inner rounded">
                <%if ((int) session.getAttribute("IDROL") == 1) {%> 
                <a class="collapse-item"  href="areaandrole.jsp">Areas y roles</a>
                <% }%>
                <a class="collapse-item" href="allusers.jsp">Usuarios</a> 
                <div class="collapse-divider"></div>
            </div>
        </div>
    </li>
    <!-- Divider -->
    <!--  <hr class="sidebar-divider d-none d-md-block mb-4"> -->
    <!-- Sidebar Toggler (Sidebar) -->
    <div class="mb-5"></div>
    <div class="text-center d-none d-md-inline ">
        <!--   <button class="rounded-circle border-0" id="sidebarToggle"></button> -->
    </div> 
</ul>
