<%

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
        function SelectGroup(IDriga) {
            alert(GetValue(IDriga));
        }

        function GetValue(IDriga) {
            //Codice che restituisce il valore della cella [IDriga,0]
            var Row = document.getElementById(IDriga);
            var Cells = Row.getElementsByTagName("td");
            return Cells[0].innerText;
        }

        function Registered() {
            location.href = "Registrazione.html";
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
                <a class="navbar-brand">Forum</a>
            </div>
            <div class="navbar-collapse navbar-right mynavbar-right">
                <form class="form-signin" action="Login">
                    <input type="text" name="email" class="form-control input-sm newHome" placeholder="Email">
                    <input name="password" type="password" class="form-control input-sm newHome1" placeholder="Password" />
                    <button class="btn btn-primary btn-sm newHome" name="login" type="submit">Sign in</button>
                </form>
                    <button class="btn btn-success btn-sm newHome1" name="login" type="button" onclick="Registered()">Registered</button>
            </div>            
            <!--/.nav-collapse -->
        </div>
    </div>
    
    <br />    
    <br />
    <br />
    <div class="ForgotPassword">
            <a class="newHome" href="ForgotPassword.html">Forgot your password?</a>
    </div>

    <div class="container">
            <h3>Here is a list of public groups in our Forum:</h3>
            <br />
            <table class="table">
                <thead>
                    <tr>
                        <th style="width:5%; display:none">ID</th>
                        <th style="width:25%">Group Name</th>
                        <th style="width:20%">Admin</th>
                        <th style="width:15%">Post</th>
                        <th style="width:25%">Last Post</th>
                        <th style="width:10%">Page</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <%
                        
                    %>
                    
                    <tr id="tr1">
                        <td style="display:none">1</td>
                        <td><img class="table" src="img/group/icon.png" />Cucina</td>
                        <td>Alfredo Mengiazzo</td>
                        <td>Post: 10</td>
                        <td>From: <strong>Turbato Thomas</strong> <br />19-12-2013, 11:00</td>
                        <td><button class="btn btn-primary btn-sm" type="button" onclick="SelectGroup('tr1')">Page of Group</button></td>
                    </tr>
                    <tr id="tr2">
                        <td style="display:none">2</td>
                        <td><img class="table" src="img/group/icon.png" />Cucina</td>
                        <td>Alfredo Mengiazzo</td>
                        <td>Post: 10</td>
                        <td>From: <strong>Turbato Thomas</strong> <br />19-12-2013, 11:00</td>
                        <td><button class="btn btn-primary btn-sm" type="button" onclick="SelectGroup('tr2')">Page of Group</button></td>
                    </tr>
                    <tr id="tr3">
                        <td style="display:none">3</td>
                        <td><img class="table" src="img/group/icon.png" />Cucina</td>
                        <td>Alfredo Mengiazzo</td>
                        <td>Post: 10</td>
                        <td>From: <strong>Turbato Thomas</strong> <br />19-12-2013, 11:00</td>
                        <td><button class="btn btn-primary btn-sm" type="button" onclick="SelectGroup('tr3')">Page of Group</button></td>
                    </tr>
                    <tr id="tr4">
                        <td style="display:none">14</td>
                        <td><img class="table" src="img/group/icon.png" />Cucina</td>
                        <td>Alfredo Mengiazzo</td>
                        <td>Post: 10</td>
                        <td>From: <strong>Turbato Thomas</strong> <br />19-12-2013, 11:00</td>
                        <td><button class="btn btn-primary btn-sm" type="button" onclick="SelectGroup('tr4')">Page of Group</button></td>
                    </tr>
                </tbody>
            </table>

        </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
	
	<form name="Scelta">
	</form>

</body>
</html>