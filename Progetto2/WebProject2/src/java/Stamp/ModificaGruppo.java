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
public class ModificaGruppo extends Stamp{

    int contatore;
    
    public ModificaGruppo(HttpServletRequest request) {
        super(request);
        contatore = 0;
    }
    
    public ArrayList<String> Table(String idG, PrintWriter out){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(out,ip);        
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select id, name, surname, avatar from users, users_groups where users.ID = users_groups.Id_users and users_groups.ID_groups =? and active = 1");
            ps.setString(1, idG);

            ResultSet rs = db.Query(ps,out);
             while(rs.next()){
                 contatore++;
                 result.add("<tr id='tr"+contatore+"'>"
                         + "<td>"+rs.getString("id")+"</td>");
                 result.add("<td>\n" +
"                                <img class='table' src='"+rs.getString("avatar")+"'/>"+rs.getString("surname")+" "+rs.getString("name")+
                            "</td>");   
                 result.add("<td>\n" +
"                                <form>\n" +
"                                    <div class='form-group'>\n" +
"                                        <button disabled='disabled' class='btn btn-primary' name='btnSend"+contatore+"' id='"+contatore+"' onclick='Send(id)' type='button'>Send Invitation</button>\n" +
"                                        <button class='btn btn-danger' id='"+contatore+"' name='btnCancel"+contatore+"' onclick='Cancel(id)' type='button'>Cancel</button>\n" +
"                                    </div>\n" +
"                                </form>\n" +
"                            </td>\n" +
                           "</tr>");                 
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
    
    public ArrayList<String> Table1(int id, String idG, PrintWriter out){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(out,ip);        
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select users.id, users.name, users.surname, users.avatar" +
                                                            " from users where users.id NOT IN " +
                                                            " (select users.id" +
                                                            " from users, users_groups" +
                                                            " where users.ID = users_groups.Id_users" +
                                                            " and users_groups.ID_groups =? and active = 1)" +
                                                            " and users.ID <> ?");
            ps.setString(1, idG);
            ps.setInt(2, id);

            ResultSet rs = db.Query(ps,out);
            while(rs.next()){
                 contatore++;
                 result.add("<tr id='tr"+contatore+"'><td>"+rs.getString("id")+"</td>");
                 result.add("<td>\n" +
"                                <img class='table' src='"+rs.getString("avatar")+"'/>"+rs.getString("surname")+" "+rs.getString("name")+
                            "</td>");   
                 result.add("<td>\n" +
"                                <form>\n" +
"                                    <div class='form-group'>\n" +
"                                        <button class='btn btn-primary' name='btnSend"+contatore+"' id='"+contatore+"' onclick='Send(id)' type='button'>Send Invitation</button>\n" +
"                                        <button disabled='disabled' class='btn btn-danger' id='"+contatore+"' name='btnCancel"+contatore+"' onclick='Cancel(id)' type='button'>Cancel</button>\n" +
"                                    </div>\n" +
"                                </form>\n" +
"                            </td>\n" +
                           "</tr>");                 
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
    
    public ArrayList<String> GroupTitle(String idG, PrintWriter out){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name from groups where id = ?");
            ps.setString(1, idG);

            ResultSet rs = db.Query(ps,out);
            
             while(rs.next()){
                 result.add("<h1 id='GroupName'>"+rs.getString("name")+"</h1>");
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
}
