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
            
                result.add("<li class='divider'></li>");
                result.add("<li><a href='DeleteAllNews'>Delete all news</a></li>");
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
    
    public ArrayList<String> StampaData(int id, HttpServletRequest request) {
        
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT lastvisit from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps);
            rs.next();
            
            String date;
            
            if(rs.getString("lastvisit")==null)
                date = "<h4 class='title' id='ultimoAccesso'>Welcome! this is the first time you connect to the Forum</h4>";
            else
                date = "<h4 class='title' id='ultimoAccesso'>Last access: "+rs.getString("lastvisit").substring(0,rs.getString("lastvisit").length()-2)+"</h4>";
            
            result.add(date);
            rs.close();
        }
        catch(SQLException e){}
        
        db.DBClose();
        return result;
    }
    
    public boolean Moderatore(int id){
        boolean res = false;
        try{
            DBConnect db = new DBConnect(ip);
            PreparedStatement ps = db.conn.prepareStatement("SELECT id FROM users where id = ? AND moderatore = 1");
            ps.setInt(1, id);
            ResultSet rs = db.Query(ps);
            res = rs.next();
            rs.close();
            db.DBClose();
        }catch(Exception e){
        }
        return res;
    }
    
    public int NumberInvitation(int id){
        int res = 0;
        try{
            DBConnect db = new DBConnect(ip);
            PreparedStatement ps = db.conn.prepareStatement("SELECT count(id) as num FROM ask where id_users = ? and state = 0");
            ps.setInt(1, id);
            ResultSet rs = db.Query(ps);
            rs.next();
            res = rs.getInt("num");
            rs.close();
            db.DBClose();
        }catch(Exception e){
        }
        return res;
    }
    
    public int NumberGroup(int id){
        int res = 0;
        try{
            DBConnect db = new DBConnect(ip);
            PreparedStatement ps = db.conn.prepareStatement("SELECT count(id) as num FROM groups where id_owner = ?");
            ps.setInt(1, id);
            ResultSet rs = db.Query(ps);
            rs.next();
            res = rs.getInt("num");
            rs.close();
            
            PreparedStatement ps1 = db.conn.prepareStatement("SELECT count(id_users) as num FROM users_groups where id_users = ?");
            ps1.setInt(1, id);
            ResultSet rs1 = db.Query(ps1);
            rs1.next();
            res += rs1.getInt("num");
            rs1.close();
            
            PreparedStatement ps2 = db.conn.prepareStatement("SELECT count(id) as num FROM groups where flag = 0");
            ResultSet rs2 = db.Query(ps2);
            rs2.next();
            res += rs2.getInt("num");
            rs2.close();
            
            db.DBClose();
        }catch(Exception e){
        }
        return res;
    }
}
