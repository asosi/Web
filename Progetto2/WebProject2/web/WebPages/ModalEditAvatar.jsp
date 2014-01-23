<%@page import="Stamp.Stamp"%>
<%
    int idUser1 = (Integer)request.getSession().getAttribute("idUser");
    
    Stamp stamp = new Stamp(request);
%> 

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