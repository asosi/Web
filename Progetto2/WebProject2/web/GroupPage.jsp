<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
    <title>Group Page</title>

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
                CloseModalPost();
                CloseEditModal();
            }
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

        var indice = 1;

        function AddFile(input) {
            var file = input.value;
            var trovato = false;


            var refTab = document.getElementById("tabellaFile");
            for (var i = 1; row = refTab.rows[i]; i++) {
                row = refTab.rows[i];
                var x = row.cells[0].firstChild.nodeValue;

                if (x == input.value)
                    trovato = true;

            }

            if (!trovato) {
                var x = document.getElementById('tabellaFile').insertRow(1);
                var c1 = x.insertCell(0);
                var c2 = x.insertCell(1);
                c1.innerHTML = input.value;
                c2.innerHTML = "<input type='button' class='btn btn-danger' name='btntabFile' id=" + indice + " onclick='CancellaFile(this)' value = 'Cancel'></input>";
                indice++;
            }
            document.getElementById('postFile').value = "";
        }

        function CancellaFile(input) {
            $(input).parent().parent().remove();
        }
        
        function PublicPost() {
           
            //Controllo textArea
            var test = document.getElementById("postTesto").value;
            var tes = test.replace(/[|&;%@"<>()+,]/g,"");
            
            var dim = 0;
            var file = document.getElementById('postFile').files;
                 
            for (var i = 0; i < file.length; i++)
                dim += file[i].size;
            
            
            var t, f = 0;
            
            
            if (tes == "") {
                t = 1;
                document.getElementById("postTestoDiv").className = "form-group has-error";
                document.getElementById("postTestoLabel").innerText = "Text: Incorrect Field";
            }
            else{
                t = 0;
                document.getElementById("postTestoDiv").className = "form-group";
                document.getElementById("postTestoLabel").innerText = "Text:";                
            }
            
            if(dim > 104857600){
                f = 1;
                document.getElementById("postFileDiv").className = "form-group has-error";
            }
            else{
                t = 0;
                document.getElementById("postFileDiv").className = "form-group";
            }
            
            
            if(t==0&&f==0) {
                document.getElementById("postTestoDiv").className = "form-group";
                document.getElementById("postTestoLabel").innerText = "Text";
                $('#PostModal').modal('hide');
				
				
                var finale = "_-_-_";

                var files = document.getElementById("postFile").files;
                for (var i = 0; i < files.length; i++)
                        finale += files[i].name+"_-_-_";
				
                //CloseModalPost();
                document.addpost.action = "AddPost?testopost="+tes+"&numeroElementi="+finale;
                document.addpost.submit();
            }
        }

        function CloseModalPost() {
            document.getElementById("postTestoDiv").className = "form-group";
            document.getElementById("postTestoLabel").innerText = "Text"; 
            document.getElementById("postTesto").value = "";
            document.getElementById('postFile').value = ""; 
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
                    <li>
                        <div class="navbar-collapse collapse">
                            <form class="navbar-form navbar-right">
                                <div class="form-group">
                                    <button class="btn btn-success " data-toggle="modal" data-target="#PostModal" type="button">Add Post</button>
                                </div>
                            </form>
                        </div>
                    </li>
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
    <br />
    <br />
    <br />

    <!-- POST -->
    <div class="container postContainer">
        <div>
            <center
                <%
                    //GroupTitle
                %>
            </center>
        </div>

        <div style="width:100%">
                <table style="width:100%">
                    <!-- SINGLE POST -->
                    <%
                        //Post
                    %>
                </table>
        </div>
    </div>


    <!-- Modal Post -->
    <div class="modal fade" id="PostModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="CloseModalPost()">&times;</button>
                    <h3 class="modal-title" id="myModalLabel">Add Post</h3>
                </div>
                <div class="modal-body">
                    <div id="postTestoDiv" class="form-group">
                        <label class="control-label" id="postTestoLabel" for="inputError">Text</label>
                        <textarea style="height: 100px;" class="form-control" id="postTesto"></textarea>
                    </div>

                    <div id="postFileDiv" class="form-group">
                        <label class="control-label" id="postFileLabel" for="inputError">File: (Max Size: 100 Mb)</label>
                        <div id="postFileDiv" class="form-group">		
                            <form ENCTYPE="multipart/form-data" method="POST" name="addpost" action="AddPost">
                                    <input name="upload" type="file" class="form-control" id="postFile"  multiple="" />	
                                    <%
                                        //set Group Text Box
                                    %>

                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="CloseModalPost()">Close</button>
                    <button type="button" class="btn btn-primary" id="btnPublicpost" onclick="PublicPost()">Public</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    
    <%
        //Edit Avatar
    %>
    
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
</body>
</html>