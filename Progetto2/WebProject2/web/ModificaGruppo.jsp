<%@page import="Stamp.ModificaGruppo"%>
<%
    session = request.getSession();
    int idUser = (Integer)session.getAttribute("idUser");
    String idG = request.getParameter("numero");
    
    ModificaGruppo modificaG = new ModificaGruppo(request);
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
    <title>Modifica gruppo</title>

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
        window.onload = function () {
            document.onkeyup = key_event;
        }

        function key_event(e) {
            if (e.keyCode == 27) {
                CloseModal();
            }
        }

        function Send(id) {
            document.getElementsByName("btnCancel" + id)[0].disabled = false;
            document.getElementsByName("btnSend" + id)[0].disabled = true;
        }
        function Cancel(id) {
            document.getElementsByName("btnCancel" + id)[0].disabled = true;
            document.getElementsByName("btnSend" + id)[0].disabled = false;
        }

        var invitati = "";
        var noinvitati = "";

        function Salva() {
            document.getElementById("editG").disabled = "disabled";
            var num = countRowsTable();
            var name = document.getElementById("GroupName").innerText;
            var flag = document.getElementById("flag").value; 
            var idG = <%=idG%>;
            Membri(num);
            NoMembri(num);

            location.href="EditGroup?group_id="+idG+"&group_name="+name+"&group_member="+invitati+"&group_nomember="+noinvitati+"&group_flag="+flag;

			
        }

        function EditName() {
            var tes = document.getElementById("postName").value;

            if (tes == "" || /^\s*$/.test(tes)) {
                document.getElementById("postNameDiv").className = "form-group has-error";
                document.getElementById("postNameLabel").innerText = "Text: Incorrect Field";
                document.getElementById("postName").focus();
            }
            else {
                document.getElementById("GroupName").innerText = tes;
                $('#PostEditTitle').modal('hide');
                CloseModal();
            }            
        }

        function CloseModal() {
            document.getElementById("postName").value = "";
        }

        //Funzione che restituisce i nomi dei membri selezionati
        function Membri(num) {
            var x = "";
            for (i = 1; i < num + 1; i++) {
                var y = GetButtonStatus("btnSend" + i);
                if (y) {
                    x += GetValue("tr" + i) + " ";
                }
            }
            invitati = x;
        }
		
        function NoMembri(num) {
            var x = "";
            for (i = 1; i < num + 1; i++) {
                var y = GetButtonStatus("btnCancel" + i);
                if (y) {
                    x += GetValue("tr" + i) + " ";
                }
            }
            
            noinvitati = x;
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
                                    <button class="btn btn-success" id="editG" type="button" onclick="Salva()">Save</button>
                                </div>
                            </form>
                        </div>
                    </li>
                    <%
                        modificaG.Stampa(modificaG.Name(idUser), out);
                    %>
                        <ul class="dropdown-menu">
                            <li><a href="#" data-toggle="modal" data-target="#EditModal">Change Avatar</a></li>
                            <li><a href="#" data-toggle="modal" data-target="#EditPasswordModal">Change Password</a></li>
                            <li class="divider"></li>
                            <li><a onclick="Logout" href="Logout">Logout</a></li>
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
            <h1>Edit Group / Discussion</h1>
            <h1 id='GroupName'>
                <%
                    modificaG.Stampa(modificaG.GroupTitle(idG), out);
                %>
            </h1>
            <br>
            
            <div class="divOrizzontale">
                <button class="btn btn-primary" style="margin-top: -12px" data-toggle="modal" data-target="#PostEditTitle" type="button" onclick="SetTextBox()">Edit Name</button>

                <select id="flag" class="form-control" style="width:80%;float:left; margin-right: 2%">
                    <option value="0">Public</option>
                    <option value="1">Private</option>
                </select>            
            </div>
            <br /><br />
            <div class="bs-example">
                <table class="table" style="text-align: left" id="tabella">
                    <thead>
                        <tr>
                            <th>#</td>
                            <th>Name</th>
                            <th>Invites</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            modificaG.Stampa(modificaG.Table(idG), out);
                        %>
                        <%
                            modificaG.Stampa(modificaG.Table1(idUser, idG), out);
                        %>
                                    </div>
                                </form>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal Name -->
    <div class="modal fade" id="PostEditTitle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="CloseModal()">&times;</button>
                    <h3 class="modal-title" id="myModalLabel">Edit Name</h3>
                </div>
                <div class="modal-body">
                    <div id="postNameDiv" class="form-group">
                        <label class="control-label" id="postNameLabel" for="inputError">Text</label>
                        <input type="text" id="postName" class="form-control" />
                    </div>

                    <!--<label>File</label>
                    <input type="file" class="form-control" />
                    -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="CloseModal()">Close</button>
                    <button type="button" class="btn btn-primary" id="btnEditName" onclick="EditName()">Edit</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
	
    
    <%@ include file="WebPages/ModalEditAvatar.jsp" %>
    <%@ include file="WebPages/ModalEditPassword.jsp" %>
    
    
	
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>

</body>
</html>

