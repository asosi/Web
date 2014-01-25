<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Stamp.ReturnEmail"%>
?<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

    <title>Forgot Password</title>

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

        function Send() {
            document.getElementById("sendEmail").disabled = "disabled";
            var email = document.getElementById("email").value;
            
            if(!validEmail(email)){
                document.getElementById("formEmail").className = "form-group has-error";
            document.getElementById("sendEmail").disabled = "";
            }
            else{
                
            <%
                ReturnEmail ret = new ReturnEmail(request);
                ArrayList<String> email = ret.Email(); 
                Iterator ITemail = email.iterator();
            %>
                
                var Arrayemail = new Array(<%                
                    int contaEmail = 0;
                    while(ITemail.hasNext()){
                       String a = (String)ITemail.next();
                       if(contaEmail == 0){
                            out.print("'"+a+"'"); 
                            contaEmail++;
                       }
                       else
                           out.print(",'"+a+"'");
                    }
                %>);
                
                    
                var trovato = false;    
                for(var i= 0; i < Arrayemail.length; i++)
                    if(email == Arrayemail[i])
                        trovato = true;
                
                if(trovato)
                    location.href = "SendEmail?email="+email;
                else{
                    document.getElementById("formEmail").className = "form-group has-error";
                    document.getElementById("sendEmail").disabled = "";
                }
            }
        }
        
        function validEmail(email){
            return  /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+.([a-zA-Z])+([a-zA-Z])+/.test(email);
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
                <a href="index.jsp" class="navbar-brand">Back to Home</a>
            </div>  
            <!--/.nav-collapse -->
        </div>
    </div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <div class="container">

        <center>
            <div style="width:55%" id="errore" class="alert alert-info">
                <strong>Forgot your password?</strong><br />
               Enter your email address to get a new one!
            </div>

            <div id="formEmail" class="form-group">
                <input id="email" name="email" style="width:40%" type="text" class="form-control" placeholder="Email" />
            </div>
            <br />
            <button id="sendEmail" type="button" class="btn btn-primary" onclick="Send()">Send Email</button>
        </center>

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