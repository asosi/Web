<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>

    <!-- Bootstrap-->
    <link href="dist/css/bootstrap.css" rel="stylesheet" />
    <!-- Bootstrap theme -->
    <link href="dist/css/bootstrap-theme.min.css" rel="stylesheet" />

    <!-- My css -->
    <link href="css/mycss.css" rel="stylesheet" />

    <!-- Custom styles for this template -->
    <link href="theme.css" rel="stylesheet" />

	<!-- Codice per la tabella ordinabile -->
	<script type="text/javascript" src="TableOrder/jquery.js"></script>
	<script type="text/javascript" src="TableOrder/jquery.tablesorter.js"></script>

	<!-- Codice per la tabella filtrabile -->
	<script type="text/javascript" src="TableFilter/tablefilter.js"></script>

    <!-- My css -->
    <link href="css/mycss.css" rel="stylesheet" />

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

        function Block(id) {
            document.getElementsByName("btnBlock" + id)[0].disabled = true;

            if ($(document.getElementsByName("btnSBlock" + id)).is(':disabled')) {
                document.getElementsByName("btnSBlock" + id)[0].disabled = false;
            }
        }

        function SBlock(id) {
            document.getElementsByName("btnSBlock" + id)[0].disabled = true;

            if ($(document.getElementsByName("btnBlock" + id)).is(':disabled')) {
                document.getElementsByName("btnBlock" + id)[0].disabled = false;
            }
        }

        function Salva() {
            var num = countRowsTable();
            Bloccati(num);
        }

        function Bloccati(num) {
            var bl = "";

            for (i = 1; i < num + 1; i++) {
                var y = GetButtonStatus("btnBlock" + i);
                if (y) {
                    bl += GetValue("tr" + i) + " ";
                }

            }
            alert("Bloccati: " + bl);
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
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="Home" class="navbar-brand">Back to Home</a>
            </div>
            <div class="navbar-collapse navbar-right">
                <ul class="nav navbar-nav">
                    <li>
                        <div class="navbar-collapse collapse">
                            <form class="navbar-form navbar-right">
                                <div class="form-group">
                                    <button class="btn btn-success" type="button" onclick="Salva()">Save</button>
                                </div>
                            </form>
                        </div>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Thomas <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Change User Data</a></li>
                            <li class="divider"></li>
                            <li><a onclick="CallServlet('Logout')" href="#">Logout</a></li>
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

    <div class="container">
        <h3>Here is a list of all the groups in our Forum:</h3>
        <br />
        <table class="table tablesorter" id="tabella">
            <thead>
                <tr>
                    <th style="width: 5%;">ID</th>
                    <th style="width: 20%">Group Name</th>
                    <th style="width: 20%">Admin</th>
                    <th style="width: 15%">Number of Post</th>
                    <th style="width: 10%">Flag</th>
                    <th style="width: 10%">Link</th>
                    <th style="width: 20%">Block</th>
                </tr>
            </thead>
            <tbody>
                <tr id="tr1">
                    <td style="">1</td>
                    <td>
                        <img class="table" src="img/group/icon.png" />Cucina</td>
                    <td>
                        <img class="table" src="img/users/avatar.jpg" />Alfredo Mengiazzo</td>
                    <td>10</td>
                    <td>Private</td>
                    <td>
                        <button class="btn btn-primary btn-sm" type="button" onclick="SelectGroup('tr1')">Page of Group</button></td>
                    <td>
                        <button class="btn btn-danger btn-sm" type="button" name="btnBlock1" onclick="Block('1')">Block this Group</button>
                        <button class="btn btn-default btn-sm" disabled="disabled" type="button" name="btnSBlock1" onclick="SBlock('1')">Cancel</button>
                    </td>
                </tr>
                <tr id="tr2">
                    <td style="">2</td>
                    <td>
                        <img class="table" src="img/group/icon2.png" />Teatro</td>
                    <td>
                        <img class="table" src="img/users/avatar.jpg" />Turbato Thomas</td>
                    <td>25</td>
                    <td>Public</td>
                    <td>
                        <button class="btn btn-primary btn-sm" type="button" onclick="SelectGroup('tr2')">Page of Group</button></td>
                    <td>
                        <button class="btn btn-danger btn-sm" type="button" name="btnBlock2" onclick="Block('2')">Block this Group</button>
                        <button class="btn btn-default btn-sm" disabled="disabled" type="button" name="btnSBlock2" onclick="SBlock('2')">Cancel</button>
                    </td>
                </tr>
            </tbody>
        </table>

    </div>

    <!--order-->
    <script type="text/javascript">
        $(document).ready(function () {
            $("#tabella").tablesorter({
                // si definisce l'oggetto passato 
                headers: {
                    // disabilitiamo il sorting della seconda colonna; 
                    5: { sorter: false },

                    // disabilitiamo il sorting della terza colonna 
                    6: { sorter: false }
                }
            });
        });


    </script>

    <!--filters-->
    <script type="text/javascript">
        var tableFilters = {
            col_4: "select",
            col_5: "none",
            col_6: "none"
        }
        var tf = setFilterGrid("tabella", 1, tableFilters);
    </script>

     <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
	
</body>
</html>
