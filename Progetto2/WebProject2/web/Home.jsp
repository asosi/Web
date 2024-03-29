<%@page import="Stamp.Home"%>
<%
    session = request.getSession();
    int idUser = (Integer)session.getAttribute("idUser");
    
    Home home = new Home(request);
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

    <title>Home</title>

    <!-- Bootstrap core CSS -->
    <link href="dist/css/bootstrap.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="dist/css/bootstrap-theme.min.css" rel="stylesheet">

    <!-- My css -->    
    <link href="css/mycss.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="theme.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../docs-assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <script type="text/javascript">

        function Notify() {
            document.getElementById("notify").className = "badge notnotify";
        }

        function CallServlet(servlet) {
            document.location.href = servlet;
        }   
        
        function PageLink(page){
            location.href = page;
        }

    </script>

</head>

<body>
    <div class="navbar navbar-inverse barra">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand">Forum2</a>
            </div>
            <div class="navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
                    <%
                        home.Stampa(home.Notifiche(idUser), out);
                    %>
                    <%
                        home.Stampa(home.Name(idUser), out);
                    %>
                        <ul class="dropdown-menu">
                            <li><a href="#" data-toggle="modal" data-target="#EditModal">Change Avatar</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#EditPasswordModal">Change Password</a></li>
                            <li class="divider"></li>
                            <li><a onclick="CallServlet('Logout')" href="Logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>

    <br /><br /><br />
    
    <div class="container">
        <%
            home.Stampa(home.StampaData(idUser, request), out);
        %>
                <br>               
                
                
        <div class="row marketing">
            <div class="col-lg-6">
                <center>
                    <%
                        if(home.Moderatore(idUser))
                            out.println("<br><br>");
            
                        home.Stampa(home.User(idUser), out);
                    %>
                </center>
            </div>
            <div class="col-lg-6">
                <br>
                <br>
                <br>
                <br>
                <div style="width: 70%">
                    <center>
                        <%if(home.NumberInvitation(idUser)>0){ 
                            out.println("<button class='btn btn-lg btn-primary btn-block' onclick=\"PageLink('Inviti.jsp')\" type='button'> Invitations </button>");                           
                        }
                        else{
                            out.println("<button disabled='disabled' class='btn btn-lg btn-primary btn-block' onclick=\"PageLink('Inviti.jsp')\" type='button'> Invitations </button>");                           
                        }
                        %>
                        <br>
                        
                        <%if(home.NumberGroup(idUser)>0){ 
                            out.println("<button class='btn btn-lg btn-primary btn-block' onclick=\"PageLink('Gruppi.jsp')\" type='button'>Groups</button>");                           
                        }
                        else{
                            out.println("<button disabled='disabled' class='btn btn-lg btn-primary btn-block' onclick=\"PageLink('Gruppi.jsp')\" type='button'>Groups</button>");                           
                        }%>
                        
                        <br>
                        <button class="btn btn-lg btn-primary btn-block" onclick="PageLink('CreaGruppo.jsp')" type="button">Create Groups</button>
                        <br>
                        
                        <%
                        
                        if(home.Moderatore(idUser)){
                            out.println("<button class='btn btn-lg btn-warning btn-block' onclick=\"PageLink(\'Moderatore.jsp\')\" type='button'>Moderatore</button>");
                        }
                        
                        %>
                        
                    </center>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="WebPages/ModalEditAvatar.jsp" %>
    <%@ include file="WebPages/ModalEditPassword.jsp" %>
    
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
	
</body>
</html>
