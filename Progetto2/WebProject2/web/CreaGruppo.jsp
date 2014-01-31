<%@page import="Stamp.CreaGruppo"%>
<%
    session = request.getSession();
    int idUser = (Integer)session.getAttribute("idUser");
    
    CreaGruppo creaG = new CreaGruppo(request);
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
    <title>Create Groups</title>

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
        function Send(id) {
            document.getElementsByName("btnCancel" + id)[0].disabled = false;
            document.getElementsByName("btnSend" + id)[0].disabled = true;
        }
        function Cancel(id) {
            document.getElementsByName("btnCancel" + id)[0].disabled = true;
            document.getElementsByName("btnSend" + id)[0].disabled = false;
        }


        var membriG = "";

        function CreaGruppo() {
            document.getElementById("creaG").disabled = "disabled";
            var flag = document.getElementById("flag").value;

            var name = document.getElementById("GroupName").value;

            if (name == "") {
            document.getElementById("creaG").disabled = "";
                document.getElementById("testo").className = "form-group has-error input-group-lg";
                document.getElementById('GroupName').focus();
            }
            else {
                document.getElementById("testo").className = "form-group  input-group-lg";
	
                var image = document.getElementById("imageGroup").src;
	
                var num = countRowsTable();
                Membri(num);
				
                document.location.href = "AddGroup?group_name="+name+"&group_avatar="+image+"&group_member="+membriG+"&group_flag="+flag;
		
            }
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
            membriG = x;
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

        function ChangeColorImage(id) {
            document.getElementById(id).className = "table1";

            for(i = 1; i < 8; i++){
                var x = "EditImg0" + i;
                if (x != id) {
                    if (document.getElementById(x).className == "table1") {
                        document.getElementById(x).className = "table1 desaturate";
                    }
                }
                var x = "EditImg1" + i;
                if (x != id) {
                    if (document.getElementById(x).className == "table1")
                        document.getElementById(x).className = "table1 desaturate";
                }
            }
        }

        function EditImageGroup() {
            var valore;

            for (i = 1; i < 8; i++) {
                var x = "EditImg0" + i;
                if (document.getElementById(x).className == "table1") {
                    valore = document.getElementById(x).src;
                }
                x = "EditImg1" + i;
                if (document.getElementById(x).className == "table1")
                    valore = document.getElementById(x).src;
            }
            document.getElementById("imageGroup").src = valore;

            $('#EditImageGroupModal').modal('hide');
        }

        function CloseEditGroupModal() {
            for (i = 1; i < 8; i++) {
                var x = "EditImg0" + i;
                document.getElementById(x).className = "table1 desaturate";
                x = "EditImg1" + i;
                document.getElementById(x).className = "table1 desaturate";
            }
            
            document.getElementById("EditImg01").className = "table1";
        }

        function SaveEditModal() {
            var tes = document.getElementById('InputImage').value;
            var img = document.getElementById("InputImage").files[0];
                
                var x = 0;
                
                switch(img.type){
                    case "image/jpeg":
                    case "image/png": x = 1;
                }
            
            
            if(img.size > 10485760 || x == 0){                
                document.getElementById("ImageEditFile").className = "form-group has-error";
            }
            else{
                document.EditAvatar.submit();	
            }
        }


        function CloseEditModal() {
            document.getElementById('InputImage').value = "";

            //richiamo funzione che resetta l'immagine in base a quella salvata nel DB
        }

        //funzione che cambia l'immagine di anteprima nella modal: "Modifica Dati utente"
        function readURL(input) {
            
                
                var type = document.getElementById("InputImage").files[0].type;
                
                var x = 0;
                
                switch(type){
                    case "image/jpeg":
                    case "image/png": x = 1;
                }

                if (input.files && input.files[0] && x == 1) {

                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('#avatar')
                        .attr('src', e.target.result)
                        .width(200)
                        .height(200);
                    };
                    reader.readAsDataURL(input.files[0]);

                }
                else
                    document.getElementById("ImageEditFile").className = "form-group has-error";
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
                                    <button class="btn btn-success" id="creaG" type="button" onclick="CreaGruppo()">Create Group</button>
                                </div>
                            </form>
                        </div>
                    </li>
                    <%
                        creaG.Stampa(creaG.Name(idUser), out);
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
            
            <h1><img class="createGroup" id="imageGroup" src="img/group/icon.png" />Create Group / Discussion</h1>
            <div class="divOrizzontale"> 
                <br>
                <div id="testo" class="form-group input-group-lg">   
                    <input type="text" class="form-control createGroup" style="width:60%" id="GroupName" placeholder="Name" required autofocus />
                </div>
                <select id="flag" class="form-control" style="width:20%;float:left; margin-right: 2%">
                    <option value="0">Public</option>
                    <option value="1">Private</option>
                </select>
                <button class="btn btn-primary createGroup" type="button" id="btnImage" data-toggle="modal" data-target="#EditImageGroupModal"  >Change Image</button>
                    
                    
                </div>
                <br />
                
            </div>

            <br />
            <div class="bs-example">
                <table class="table" style="text-align: left" id="tabella">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Invites</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            creaG.Stampa(creaG.Table(idUser), out);
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal Edit Image Group -->
    <div class="modal fade" id="EditImageGroupModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="CloseEditGroupModal()">&times;</button>
                    <h3 class="modal-title" id="H1">Edit Image Group</h3>
                </div>
                <div class="modal-body" style="text-align: center">
                   <table style="width:100%">
                       <tbody>
                           <tr>
                               <td id="01"><img id ="EditImg01" onclick="ChangeColorImage(id)" class="table1" src="img/group/icon.png"" /></td>
                               <td id="02"><img id ="EditImg02" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon13.png" /></td>
                               <td id="03"><img id ="EditImg03" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon1.png" /></td>
                               <td id="04"><img id ="EditImg04" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon2.png" /></td>
                               <td id="05"><img id ="EditImg05" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon3.png" /></td>
                               <td id="06"><img id ="EditImg06" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon4.png" /></td>
                               <td id="07"><img id ="EditImg07" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon5.png" /></td>
                           </tr>
                           <tr>
                               <td id="11"><img id ="EditImg11" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon6.png" /></td>
                               <td id="12"><img id ="EditImg12" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon7.png" /></td>
                               <td id="13"><img id ="EditImg13" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon8.png" /></td>
                               <td id="14"><img id ="EditImg14" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon9.png" /></td>
                               <td id="15"><img id ="EditImg15" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon10.png" /></td>
                               <td id="16"><img id ="EditImg16" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon11.png" /></td>
                               <td id="17"><img id ="EditImg17" onclick="ChangeColorImage(id)"  class="table1 desaturate" src="img/group/icon12.png" /></td>
                           </tr>
                       </tbody>
                   </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="CloseEditGroupModal()">Close</button>
                    <button type="button" class="btn btn-success" onclick="EditImageGroup()">Edit</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->


    <%@ include file="WebPages/ModalEditAvatar.jsp" %>
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
</body>
</html>
