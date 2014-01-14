<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
    <title>Groups</title>

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

        var riga;

		function GroupPage(IDriga){
			
			document.Scelta.action = "GroupPage";
			document.Scelta.target = "";
			document.getElementById("txtScelta").value = GetValue(IDriga);
			document.Scelta.submit();	
		}
		
        function PDFGroup(IDriga) {
			document.Scelta.action = "CreatePDF";
			document.Scelta.target = "_blank";
			document.getElementById("txtScelta").value = GetValue(IDriga);
			document.Scelta.submit();
        }

        function EditGroup(IDriga) {
            riga = IDriga;
			
			document.Scelta.action = "ModificaGruppo";
			document.Scelta.target = "";
			document.getElementById("txtScelta").value = GetValue(IDriga);
			document.Scelta.submit();			
			
        }

        function Send(id) {
            document.getElementsByName("btnCancel" + id)[0].disabled = false;
            document.getElementsByName("btnSend" + id)[0].disabled = true;
        }
        function Cancel(id) {
            document.getElementsByName("btnCancel" + id)[0].disabled = true;
            document.getElementsByName("btnSend" + id)[0].disabled = false;
        }

        
        //Funzione che restituisce i nomi dei membri selezionati
        function Membri(num) {
            var x = "";
            for (i = 1; i < num + 1; i++) {
                var y = GetButtonStatus("btnSend" + i);
                if (y) {
                    x += GetValue("tre" + i) + " ";
                }
            }
            //alert(x);
        }

        //funzione che restituisce il valore della cella (riga,0) della tabella
        function GetValue(IDriga) {
            //Codice che restituisce il valore della cella [IDriga,0]
            var Row = document.getElementById(IDriga);
            var Cells = Row.getElementsByTagName("td");
            return Cells[0].innerText;
        }
		
		function GetValue1(IDriga) {
            //Codice che restituisce il valore della cella [IDriga,1]
            var Row = document.getElementById(IDriga);
            var Cells = Row.getElementsByTagName("td");
            return Cells[1].innerText;
        }

        //funzione che restituisce il valore src dell'immagine nella cella (riga,0) della tabella
        function GetImageSrc(IDriga) {
            //Codice che restituisce il valore della cella [IDriga,0]
            var Row = document.getElementById(IDriga);
            var content = Row.getElementsByTagName('img')
            var Cells = Row.getElementsByTagName("td");
            return (content[0]['src']);
        }


        //funzione che modifica il valore della cella (riga,0) della tabella
        function SetValue(IDriga,src,nome) {
            //Codice che restituisce il valore della cella [IDriga,0]
            var Row = document.getElementById(IDriga);
            Row.getElementsByTagName("td")[1].innerHTML = "<img class='table' src='"+src+"' />"+nome;
        }

        //funzione che verifica se un bottono è disabilitato
        function GetButtonStatus(target) {
            if ($(document.getElementsByName(target)).is(':disabled')) {
                return true;
            }
            else {
                return false;
            }

        }

        //funzione che restituisce il numero di righe della tabella
        function countRowsTable() {
            var rows = document.getElementById('tabellaEditGroup').getElementsByTagName('tbody')[0].rows.length;
            return rows;
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
		

    </script>

</head>

<body>
    <div class="navbar navbar-inverse barra">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"><span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
                <a class="navbar-brand" href="Home">Back to Home</a>
            </div>
            <div class="navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
				
					<%
						//Name
					%>
					
					                        <ul class="dropdown-menu">
                             <li><a href="#" data-toggle="modal" data-target="#EditModal">Change User Data</a></li>
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
            <h1>Groups</h1>
        </div>
        <div class="bs-example">
            <table class="table">
                <thead>
                    <tr>
						<th>#</th>
                        <th>Group Name</th>
                        <th>Date</th>
                        <th>Link Group</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
				
					<%
						//Table
					%>
					
					<%
						//Table1
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

	<%
		//Caricare pagina ModalEditAvatar
	%>
		
	<form name="Scelta">
		<input type="text" id="txtScelta" name="numero" hidden="hidden" />
	</form>
	
	
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
</body>
</html>

				
							