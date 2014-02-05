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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
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
    
    public ArrayList<String> Post(String idG, HttpServletRequest request) throws IOException{
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
                 
                 String testo = ConvertiLinkQR(rs.getString("text"),idG);
                 String testo1 = ConvertiLink(testo,idG);
                 
                 HttpSession session = ((HttpServletRequest) request).getSession();
                 
                 String testo3 = "";
                 if(session != null && session.getAttribute("idUser")!= null){                     
                     testo3 = testo1;
                 }
                 else{                     
                    String[] testo2 = testo1.split(" ");
                    
                    for(int i = 0; i < testo2.length; i++)
                        testo3 += MascheraEmail(testo2[i])+" ";
                    
                 }
                     
                 
                 result.add("<p>"+testo3+"</p>");
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
                    if(testo.substring(inizio,fine).length() != 0){
                        testofinale += "<a  target='_blank' href='http://";
                        testofinale += testo.substring(inizio,fine);
                        testofinale += "'>";
                        testofinale += testo.substring(inizio,fine);
                        testofinale += "</a>";
                    }
                    else
                        testofinale += "$$$$";
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
    
    private String ConvertiLinkQR(String testo, String idG) throws IOException{
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
                    if(i < testo.length()-3){
                        String s = testo.substring(i,i+4);            
                        if(s.compareTo("$QR$") == 0){
                            azione = true;
                            i++;
                            inizio = i+1;
                        }
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
            if(inizio+2<=fine)
            {
                if(inizio-2 >inizio1)
                    testofinale += testo.substring(inizio1,inizio-2);
                if(testo.substring(inizio+2,fine).length() != 0){
                    testofinale += "<table><tr><td>";
                    testofinale += GeneraQR(testo.substring(inizio+2,fine), idG);     
                    testofinale += "</td><td>";          
                    testofinale += "<a  target='_blank' href='http://";
                    testofinale += testo.substring(inizio+2,fine);
                    testofinale += "'>";
                    testofinale += testo.substring(inizio+2,fine);
                    testofinale += "</a>";
                    testofinale += "</td></tr></table><br>";
                }
                else
                    testofinale += "$QR$$$";
                inizio1 = fine+2;
                inizio = fine+2;
                azione = false;
            }
            else{
                inizio = inizio1;
                testofinale += testo.substring(inizio,testo.length());
                finito = true;
            }
        }
        
        return testofinale;
    }
    
    private String GeneraQR(String qrtext, String idG) throws IOException{
        ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).stream();

        String image = "";
        
        try {
            
            String path = "/home/davide/Scaricati/apache-tomcat-7.0.47/webapps/Forum2/files/"+idG+"/QR/"+qrtext+".jpg";
            
            FileOutputStream fout = new FileOutputStream(new File(path));

            image = "<a href='files/"+idG+"/QR/"+qrtext+".jpg'><img style='width:60px; height:60px;' src='files/"+idG+"/QR/"+qrtext+".jpg'/></a>";
            
            fout.write(out.toByteArray());

            fout.flush();
            fout.close();

        } catch (FileNotFoundException e) {
            // Do Logging
        } catch (IOException e) {
            // Do Logging
        } 
        
        return image;
    }
    
    public boolean isBlocked(String idG){
        DBConnect db = new DBConnect(ip);
        boolean stato = false;
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT blocked from groups where id = ?");
            ps.setString(1, idG);

            ResultSet rs = db.Query(ps);
            rs.next();
            int bloc = rs.getInt("blocked");
            
            switch(bloc){
                case 0: stato = false;break;
                case 1: stato = true;break;
            }
            
        }
        catch(SQLException e){}
        db.DBClose();
        
        return stato;
    }
    
    private boolean controlloLinkFile(String idG, String nomeFile){
        boolean exist = false;
        if(nomeFile.length() != 0){
            File f = new File("/home/davide/Scaricati/apache-tomcat-7.0.47/webapps/Forum2/files/"+ idG +"/"+nomeFile);
            exist = f.exists();
        }
        return exist;
    }
    
     private static String MascheraEmail(String email){
        String[] array = email.split("@");
        if(array.length==2){
            if(array[1].contains (".") && array[1].length()>3){
                array[1] = "xxxxx";
                return array[0]+"@"+array[1];
            }
            else
                return array[0]+"@"+array[1];
        }
        else
            return email;
    }
  
}
