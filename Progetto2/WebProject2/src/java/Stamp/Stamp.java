package Stamp;


import DB.DBConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davide
 */
public class Stamp extends Print{

    public Stamp(HttpServletRequest request) {
        super(request);
    }
    
    
    public ArrayList<String> Name(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name from users where id = ?");            
            ps.setInt(1, id);
            
            ResultSet rs = db.Query(ps);
            
             while(rs.next()){
                 result.add("<li class=\"dropdown\">");
                 result.add("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">"+rs.getString("name")+"<b class=\"caret\"></b></a>");
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
    
    public ArrayList<String> EditAvatar(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,surname,avatar from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps);
            
             while(rs.next()){
                 result.add("<h1>"+rs.getString("surname")+" "+rs.getString("name")+"</h1>");
                 result.add("<img id='avatar' class=\"img-circle\" data-src=\"holder.js/200x200\"\" style=\"width: 200px; height: 200px;\" src='"+rs.getString("avatar")+"'/>");
             }
        }
        catch(SQLException e){}
        
        db.DBClose();
        return result;
    }
    
}
