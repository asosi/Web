<%@page import="Stamp.Stamp"%>
<%
    int idUser1 = (Integer)request.getSession().getAttribute("idUser");
    
    Stamp stamp = new Stamp(request);
%> 

<script>
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

<!-- Modal Edit Avatar -->
    <div class="modal fade" id="EditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="CloseEditModal()">&times;</button>
                    <h3 class="modal-title" id="H1">Edit Avatar</h3>
                </div>
                <div class="modal-body" style="text-align: center">
				
                    <%
                            stamp.Stampa(stamp.EditAvatar(idUser1), out);
                    %>

                    <br />
                    <br />

                    <FORM name="EditAvatar" ENCTYPE='multipart/form-data'  method='POST' action='SaveAvatar'>
                        <input type='file' name="ImageFile" accept="image/*" id="InputImage" class="form-control" onchange="readURL(this);" />
                    </FORM>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="CloseEditModal()">Close</button>
                    <button type="button" class="btn btn-success" onclick="SaveEditModal()">Save</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->