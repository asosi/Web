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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author davide
 */
public class Home extends Stamp{

    public Home(HttpServletRequest request) {
        super(request);
    }
    
    public ArrayList<String> Notifiche(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip); 
        
        try{           
            int numero = 0;
            
            PreparedStatement ps = db.conn.prepareStatement("select id,news,page from news where see = 0 and id_users = ?");
            ps.setInt(1, id);
            ResultSet rs = db.Query(ps);
            
            rs.last();
            numero = rs.getRow();
            rs.beforeFirst();
                                    
            result.add("<li class=\"dropdown\">");
                        
            if(numero == 0){
                result.add("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" onclick=\"Notify()\">Notifications <span id=\"notify\" class=\"badge notnotify\">0</span></a>\n");
                result.add("<ul class=\"dropdown-menu\">");
                result.add("</ul> \n </li>");
            }else{  
                
                result.add("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" onclick=\"Notify()\">Notifications <span id=\"notify\" class=\"badge notify\">"+numero+"</span></a>\n");
                result.add("<ul class=\"dropdown-menu\">");
                
                while(rs.next()){
                    result.add("<li><a href='DeleteNews?val="+rs.getString("page")+"&id="+rs.getString("id")+"'>"+rs.getString("news")+"</a></li>\n <li class='divider'></li>");
                }
                result.add("</ul> \n </li>");              
            }
            rs.close();
            
        }
        catch(SQLException e){
        }
        
        db.DBClose();
        return result;
    }

    public ArrayList<String> User(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,surname,avatar from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps);
            
             while(rs.next()){
                 result.add("<h1>"+rs.getString("surname")+" "+rs.getString("name")+"</h1>");
             
                 result.add("<img class=\"img-circle\" data-src=\"holder.js/200x200\"\" style=\"width: 200px; height: 200px;\" src='"+rs.getString("avatar")+"'/>");
             }
            rs.close();
        }
        catch(SQLException e){}
        
        db.DBClose();
        return result;
    }
    
    public ArrayList<String> StampaCookie(int id, HttpServletRequest request) {
        ArrayList<String> result = new ArrayList<String>();
        Cookie cookies [] = request.getCookies();
        for(int i= 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("date"+id)){
                if(cookies[i].getValue().equals("no")){
                    result.add("<h4 class=\"title\" id=\"ultimoAccesso\">Last access: First visit</h4>");
                }
                else{
                    result.add("<h4 class=\"title\" id=\"ultimoAccesso\">Last access: "+cookies[i].getValue()+"</h4>");
                }                    
            }
        }
        return result;
    }
}
