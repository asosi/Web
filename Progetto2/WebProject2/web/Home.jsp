<%
    session = request.getSession();
    int id;
    synchronized(session){id = (Integer) session.getAttribute("idUser");} 
    
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

        function SaveEditModal() {
            var tes = document.getElementById('InputImage').value;
            $('#EditModal').modal('hide');						
			document.EditAvatar.submit();	
        }

        function CloseEditModal() {
            document.getElementById('InputImage').value = "";

            //richiamo funzione che resetta l'immagine in base a quella salvata nel DB
        }

        //funzione che cambia l'immagine di anteprima nella modal: "Modifica Dati utente"
        function readURL(input) {

            if (input.files && input.files[0]) {

                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#avatar')
                    .attr('src', e.target.result)
                    .width(200)
                    .height(200);
                };
                reader.readAsDataURL(input.files[0]);
            }
        }
		
		function Inviti(){
			document.Scelta.action = "Inviti";
			document.Scelta.submit();	
		}
		
		function Gruppi(){
			document.Scelta.action = "Gruppi";
			document.Scelta.submit();	
		}
		
		function CreaGruppo(){
			document.Scelta.action = "CreaGruppo";
			document.Scelta.submit();	
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
                <a class="navbar-brand">Forum 80085</a>
            </div>
            <div class="navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
				
                        <%
                                //Notifiche
                        %>

                        <%
                                //Name
                        %>
				
                        <ul class="dropdown-menu">
                            <li><a href="#" data-toggle="modal" data-target="#EditModal">Change User Data</a></li>
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
			//Ex cookie, stampa dal Db data e ora ultimo accesso
		%>
				
				<br>
        <div class="row marketing">
            <div class="col-lg-6">
                <center>
					
                    <%
                        //User
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
                        <button class="btn btn-lg btn-primary btn-block" onclick="Inviti()" type="button"> Invitations </button>
                        <br>
                        <button class="btn btn-lg btn-primary btn-block" onclick="Gruppi()" type="button">Groups</button>
                        <br>
                        <button class="btn btn-lg btn-primary btn-block" onclick="CreaGruppo()" type="button">Create Groups</button>
                    </center>
                </div>
            </div>
        </div>
    </div>
    
    <%
		//Caricare pagina ModalEditAvatar
	%>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
	
	<form name="Scelta">
	</form>
	
</body>
</html>

				