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
public class Gruppi extends Stamp{
    
    int contatoreTabPrinc;
    public Gruppi(HttpServletRequest request) {
        super(request);
        contatoreTabPrinc = 0;
    }
    
    public ArrayList<String> Table(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * from groups where id_owner = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps);
                       
            while(rs.next()){
                contatoreTabPrinc++;
                result.add("<tr id='tr"+contatoreTabPrinc+"'>");                
                result.add("<td>"+rs.getString("id")+"</td>");
                result.add("<td>\n <img class='table' src='img/group/"+rs.getString("avatar")+"' />"+rs.getString("name")+"\n</td>");
                result.add("<td>"+rs.getString("born_date").substring(0,rs.getString("born_date").length()-2)+"</td>");
                result.add("<td>\n<form>\n<div class=\"form-group\">\n" +
"                                    <button class=\"btn btn-primary \" name='tr"+contatoreTabPrinc+"' onclick=\"GroupPage(name)\" type=\"button\">Page of Group</button>\n" +
"                                </div>\n</form>\n</td>");
                result.add("<td>\n" +
"                            <form>\n" +
"                                <div class=\"form-group\">\n" +
"                                    <button class=\"btn btn-primary \" name='tr"+contatoreTabPrinc+"' onclick=\"EditGroup(name)\" type=\"button\">Edit</button>\n" +
"                                    <button class=\"btn btn-danger \" name='tr"+contatoreTabPrinc+"' onclick=\"PDFGroup(name)\" type=\"button\">Report PDF</button>\n" +
"                                </div>\n" +
"                            </form>\n" +
"                        </td></tr>");
            }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
    
    public ArrayList<String> Table1(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * from  groups join users_groups on(groups.id = users_groups.id_groups) where users_groups.id_users = ? and active = 1");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps);
                        
            while(rs.next()){
                contatoreTabPrinc++;
                result.add("<tr id='tr"+contatoreTabPrinc+"'>");                
                result.add("<td>"+rs.getString("id")+"</td>");
                result.add("<td>\n <img class='table' src='img/group/"+rs.getString("groups.avatar")+"' />"+rs.getString("groups.name")+"\n</td>");
                result.add("<td>"+rs.getString("born_date").substring(0,rs.getString("born_date").length()-2)+"</td>");
                result.add("<td>\n<form>\n<div class=\"form-group\">\n" +
"                                    <button class=\"btn btn-primary \" name='tr"+contatoreTabPrinc+"' onclick=\"GroupPage(name)\" type=\"button\">Page of Group</button>\n" +
"                                </div>\n</form>\n</td>");
                result.add("<td></td></tr>");
            }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
}
