/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Stamp;

import DB.DBConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author davide
 */
public class CreaGruppo extends Stamp{

    public CreaGruppo(HttpServletRequest request) {
        super(request);
    }
    
    public ArrayList<String> Table(int id){
         ArrayList<String> result = new ArrayList<String>();
         DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT id,name,surname,avatar from users where id <> ? order by surname,name");            
            ps.setInt(1, id);
            
            ResultSet rs = db.Query(ps);
            
            int contatore = 0;
            
             while(rs.next()){
                 contatore++;
                 
                 result.add("<tr id='tr"+contatore+"'>");
                 result.add("<td>"+rs.getString("id")+"</td>");
                 result.add("<td>\n" +
"                                <img class='table' src='"+rs.getString("avatar")+"' />"+rs.getString("surname")+" "+rs.getString("name")+"\n" +
"                            </td>");
                 result.add("<td>\n" +
"                                <form>\n" +
"                                    <div class='form-group'>\n" +
"                                        <button class='btn btn-primary' name='btnSend"+contatore+"' id='"+contatore+"' onclick='Send(id)' type='button'>Send Invitation</button>\n" +
"                                        <button disabled='disabled' class='btn btn-danger' id='"+contatore+"' name='btnCancel"+contatore+"' onclick='Cancel(id)' type='button'>Cancel</button>\n" +
"                                    </div>\n" +
"                                </form>\n" +
"                            </td>");
                 result.add("");
                 result.add("");
                 result.add("");
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
}
