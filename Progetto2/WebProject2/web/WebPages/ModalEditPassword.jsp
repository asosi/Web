<%@page import="Stamp.Stamp"%>
<%
    int idUser2 = (Integer)request.getSession().getAttribute("idUser");
    
    Stamp stamp1 = new Stamp(request);
%> 

<script>
    function SaveEditPasswordModal(){
        var oldP = document.getElementById("OLDpassword").value;
        var newP = document.getElementById("NEWpassword").value;
        var newP1 = document.getElementById("NEWpassword1").value;
        
        var o,n,n1 = false;
        
        if(oldP==""){
            document.getElementById("formOLDpassword").className = "form-group has-error";
            o = false;
        }
        else{
            document.getElementById("formOLDpassword").className = "form-group";
            o = true;            
        }
        
        if(newP==""){
            document.getElementById("formNEWpassword").className = "form-group has-error";
            n = false;
        }
        else{
            document.getElementById("formNEWpassword").className = "form-group";
            n = true;            
        }
        
        if(newP1==""){
            document.getElementById("formNEWpassword1").className = "form-group has-error";
            n1 = false;
        }
        else{
            document.getElementById("formNEWpassword1").className = "form-group";
            n1 = true;            
        }
        
        if(newP != newP1){
            document.getElementById("formNEWpassword1").className = "form-group has-error";
            n1 = false;            
        }
        
        if(!isValid(newP) || /^\s*$/.test(newP)){
            document.getElementById("formNEWpassword").className = "form-group has-error";
            n = false;            
        }
        
        if(o && n && n1){
            location.href="ChangePassword?old="+oldP+"&new="+newP;
        }
        
    }
    
    function isValid(str){
        return !/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/g.test(str);
    }
</script>

<!-- Modal Edit Avatar -->
    <div class="modal fade" id="EditPasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="CloseEditModal()">&times;</button>
                    <h3 class="modal-title" id="H1">Change Password</h3>
                </div>
                <div class="modal-body" style="text-align: center">
		
                    <div id="formOLDpassword" class="form-group">                                  
                        <input type="password" class="form-control" id="OLDpassword" placeholder="Old Password"/>
                    </div>
                    <div id="formNEWpassword" class="form-group">                                  
                        <input type="password" class="form-control" id="NEWpassword" placeholder="New Password"/>
                    </div>
                    <div id="formNEWpassword1" class="form-group">                                  
                        <input type="password" class="form-control" id="NEWpassword1" placeholder="Confirm New Password"/>
                    </div>
                    
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="CloseEditModal()">Close</button>
                    <button type="button" class="btn btn-success" onclick="SaveEditPasswordModal()">Save</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->