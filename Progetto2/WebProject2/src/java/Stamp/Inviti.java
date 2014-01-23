/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Stamp;

import DB.DBConnect;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author davide
 */
public class Inviti extends Stamp{

    public Inviti(HttpServletRequest request) {
        super(request);
    }
    
    public ArrayList<String> Table(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
                
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT groups.id, groups.avatar, groups.name, users.name, users.surname from"
                    + " users, groups, ask where ask.state = 0 and ask.id_users = ? and groups.id_owner = users.id and "
                    + " ask.id_groups = groups.id ");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps);
            
            int contatore = 0;
             while(rs.next()){
                 contatore++;
                 result.add("<tr id='tr"+contatore+"'>");
                 result.add("<td>"+rs.getString("groups.id")+"</td>");
                 result.add("<td>\n" +
"                                <img class=\"table\" src='img/group/"+rs.getString("groups.avatar")+"' />"+rs.getString("groups.name")+"\n" +
"                            </td>");
                 result.add("<td>"+rs.getString("users.surname")+" "+rs.getString("users.name")+"</td>");
                 result.add("<td>\n" +
"                            <form>\n" +
"                                <div class='form-group'>\n" +
"                                    <button class='btn btn-primary' name='btnAccept"+contatore+"' id="+contatore+" type='button' onclick='Accept(id)'>Accept</button>\n" +
"                                    <button class='btn btn-danger' name='btnDecline"+contatore+"' id="+contatore+" type='button' onclick='Decline(id)'>Decline</button>\n" +
"                                </div>\n" +
"                            </form>\n" +
"                        </td>\n" +
"                    </tr>");
              }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
}
