<%@page import="Stamp.Inviti"%>
<%
    session = request.getSession();
    int idUser = (Integer)session.getAttribute("idUser");
    
    Inviti inviti = new Inviti(request);
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
    <title>Inviti</title>

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

        function Accept(id) {
            document.getElementsByName("btnAccept" + id)[0].disabled = true;

            if ($(document.getElementsByName("btnDecline" + id)).is(':disabled')) {
                document.getElementsByName("btnDecline" + id)[0].disabled = false;
            }
        }

        function Decline(id) {
            document.getElementsByName("btnDecline" + id)[0].disabled = true;

            if ($(document.getElementsByName("btnAccept" + id)).is(':disabled')) {
                document.getElementsByName("btnAccept" + id)[0].disabled = false;
            }
        }

        function Save() {
            document.getElementById("invita").disabled = "disabled";
            var num = countRowsTable();
            Membri(num);
			
			document.Salvataggio.submit();
        }

        //Funzione che restituisce i nomi dei membri selezionati
        function Membri(num) {
            var ac = "";
            var de = "";

            for (i = 1; i < num + 1; i++) {
                var y = GetButtonStatus("btnAccept" + i);
                if (y) {
                    ac += GetValue("tr" + i) + " ";
                }

                var x = GetButtonStatus("btnDecline" + i);
                if (x) {
                    de += GetValue("tr" + i) + " ";
                }

            }
			document.getElementById("accettati").value = ac;
			document.getElementById("declinati").value = de;
			
        }

        //funzione che restituisce il valore della cella (riga,0) della tabella
        function GetValue(IDriga) {
            //Codice che restituisce il valore della cella [IDriga,0]
            var Row = document.getElementById(IDriga);
            var Cells = Row.getElementsByTagName("td");
            return Cells[0].innerText;
        }

        //funzione che verifica se un bottono è disabilitato
        function GetButtonStatus(target) {
            //Controllo se il bottone con name=target è disabilitato
            if ($(document.getElementsByName(target)).is(':disabled')) {
                return true;
            }
            else {
                return false;
            }
        }

        //funzione che restituisce il numero di righe della tabella
        function countRowsTable() {
            var rows = document.getElementById('tabella').getElementsByTagName('tbody')[0].rows.length;
            return rows;
        }
		
	
    </script>

</head>

<body>
    <div class="navbar navbar-inverse barra">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
                <a class="navbar-brand" href="Home.jsp">Back to Home</a>
            </div>
            <div class="navbar-collapse navbar-right">
                <ul class="nav navbar-nav">

                    <li>
                        <div class="navbar-collapse collapse">
                            <form class="navbar-form navbar-right">
                                <div class="form-group">
                                    <button class="btn btn-success " id="invita" type="button" onclick="Save()">Save</button>
                                </div>
                            </form>
                        </div>
                    </li>
                    <%
                        inviti.Stampa(inviti.Name(idUser), out);
                    %>
                       <ul class="dropdown-menu">
                            <li><a href="#" data-toggle="modal" data-target="#EditModal">Change Avatar</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#EditPasswordModal">Change Password</a></li>
                            <li class="divider"></li>
                            <li><a href="Logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
    <div class="container">

        <br />
        <br />

        <!-- Jumbotron -->
        <div class="jumbotron myjumbotron">
            <h1>Invitations</h1>
        </div>
        <div class="bs-example">
            <table class="table" id="tabella">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Group Name</th>
                        <th>From</th>
                        <th>Accept / Decline</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        inviti.Stampa(inviti.Table(idUser), out);
                    %>
                    <!--ultima riga (vuota)-->
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <%@ include file="WebPages/ModalEditAvatar.jsp" %>
    <%@ include file="WebPages/ModalEditPassword.jsp" %>
    
    
    <form name="Salvataggio" action="AcceptDeclineInvite">
            <input type="text" id="accettati" name="accettati" hidden="hidden" />
            <input type="text" id="declinati" name="declinati" hidden="hidden" />
    </form>
	
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
</body>
</html>
