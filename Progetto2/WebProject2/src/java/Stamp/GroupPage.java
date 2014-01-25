/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Stamp;

import DB.DBConnect;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author davide
 */
public class GroupPage extends Stamp{

    int contatoreTabPrinc;
    public GroupPage(HttpServletRequest request) {
        super(request);
        contatoreTabPrinc = 0;
    }
    
    public ArrayList<String> Post(String idG){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select post.id,users.name,users.surname,users.avatar,\n" +
                                                            " post.date,post.text\n" +
                                                            "from post,users\n" +
                                                            " where post.id_groups = ? and users.ID = post.ID_users "+
                                                            " order by (post.date) desc");
            ps.setString(1, idG);
            ResultSet rs = db.Query(ps);
            
            
            int contatore=0;
            while(rs.next()){
                 
                 PreparedStatement ps1 = db.conn.prepareStatement("select post_file, ID from post_file where ID_post = ?");
                 ps1.setString(1, rs.getString("post.id"));
                 ResultSet rs1 = db.Query(ps1);
                 
                 
                 contatore++;
                 result.add("<tr id='post"+contatore+"'><td>\n" +
"                    <div class='well'>\n" +
"                        <div class=\"postLeft\">\n" +
"                            <br />");
                 result.add("<img class='img-circle post' data-src='holder.js/80x80' src='"+rs.getString("avatar")+"' />");
                 result.add("</div>\n" +
    "                        <div class='postRight'>\n" +
    "                            <p class='postDate'>"+rs.getString("date").substring(0,rs.getString("date").length()-2)+"</p>");
                 result.add("<h4>"+rs.getString("surname")+" "+rs.getString("name")+"</h4>");
                 
                 String testo = ConvertiLink(rs.getString("text"),idG);
                 
                 result.add("<p>"+testo+"</p>");
                 //out.println("<div class=\"postLink\">\n");
                 boolean canDownload = true;
                 while(rs1.next()){
                     if(canDownload)
                        result.add("<br><br><strong>Download:</strong><br>");
                     String[] n = rs1.getString("post_file").split("/");
                     result.add("<a href='Download?idF="+rs1.getString("ID")+"'>"+n[2]+"</a> - ");
                     canDownload = false;
                 }
                 result.add("<br>");
                 result.add(/*"</div>\n" +*/
"                        </div>\n" +
"                    </div>\n" +
"                </td></tr>");
                 
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
    
    public ArrayList<String> GroupTitle(String idG){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,avatar from groups where id = ?");
            ps.setString(1, idG);

            ResultSet rs = db.Query(ps);
            
             while(rs.next()){
                 result.add("<h1><img class='createGroup' id='imageGroup' src='img/group/"+rs.getString("avatar")+"' />"+rs.getString("name")+"</h1>");
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
    
    public ArrayList<String> SetGroupTextBox(int id, String idG){        
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps);
            
             while(rs.next()){
                 result.add("<input type='text' name='idG' id='idG' hidden='hidden' value='"+idG+"'/>");
             }
        }
        catch(SQLException e){}
        db.DBClose();
        return result;
    }
    
    private String ConvertiLink(String testo, String idG){
        String testofinale="";
        boolean azione = false;
        boolean finito = false;
        //che porcata
        int inizio = 0;
        int fine = -1;
        int inizio1 = 0;
        
        while(!finito)
        {    
            for(int i = inizio; i < testo.length()-1; i++){
                if(!azione) {
                    String s = testo.substring(i,i+2);            
                    if(s.compareTo("$$") == 0){
                        azione = true;
                        i++;
                        inizio = i+1;
                    }
                }
                else{
                    String s = testo.substring(i,i+2);           
                    if(s.compareTo("$$") == 0){
                        i++;
                        fine = i-1;
                        break;                        
                    }
                }
            }
            //non toccare
            if(inizio<=fine)
            {
                System.out.println(testo.substring(inizio, fine));
                if(inizio-2 >inizio1)
                    testofinale += testo.substring(inizio1,inizio-2);
                if(controlloLinkFile(idG, testo.substring(inizio,fine))){
                    DBConnect db= new DBConnect(ip);
                    try{
                        String name = "files/"+idG+"/";
                        name += testo.substring(inizio, fine);
                        PreparedStatement ps = db.conn.prepareStatement("SELECT ID from post_file where post_file = ?");
                        ps.setString(1, name);
                        ResultSet rs = db.Query(ps);
                        rs.next();
                        testofinale += "<a href='Download?idF="+rs.getString("ID")+"'>";
                    }
                    catch(SQLException e){}
                    db.DBClose();
                    testofinale += testo.substring(inizio,fine);
                    testofinale += "</a>";
                    inizio1 = fine+2;
                    inizio = fine+2;
                    azione = false;
                }
                else{
                    testofinale += "<a  target='_blank' href='http://";
                    testofinale += testo.substring(inizio,fine);
                    testofinale += "'>";
                    testofinale += testo.substring(inizio,fine);
                    testofinale += "</a>";
                    inizio1 = fine+2;
                    inizio = fine+2;
                    azione = false;
                }
            }
            else{
                inizio = inizio1;
                testofinale += testo.substring(inizio,testo.length());
                finito = true;
            }
        }
        
        return testofinale;
    }
    
    //lasciate che lo faccio io :-)
    private boolean controlloLinkFile(String idG, String nomeFile){
        File f = new File("/home/davide/Scaricati/apache-tomcat-7.0.47/webapps/boobs3/files/"+ idG +"/"+nomeFile);
        boolean exist = f.exists();
        return exist;
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
    
    public ArrayList<String> EditGroup(int id){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,avatar from users join users_group on (users.id = users_groups.id_users where users_groups.id_groups = ?");
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
    
}
