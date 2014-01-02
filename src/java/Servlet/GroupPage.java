/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import DB.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amedeo
 */
public class GroupPage extends HttpServlet {
    
    String ip;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            
            HttpSession session = request.getSession();
            int id;
            synchronized(session){id = (Integer) session.getAttribute("idUser");}
            String idG;
            idG = request.getParameter("numero");
            session.setAttribute("idG", idG);
            PrintWriter out = response.getWriter();
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(out,ip);
            db.DBClose();
            request.getRequestDispatcher("webPages/GroupPage/GroupPage1.html").include(request, response);
            Name(id, out);
            request.getRequestDispatcher("webPages/GroupPage/GroupPage2.html").include(request, response);
            GroupTitle(idG, out);
            request.getRequestDispatcher("webPages/GroupPage/GroupPage3.html").include(request, response);
            //request.getRequestDispatcher("webPages/GroupPage/post.html").include(request, response);
            Post(idG, out);
            request.getRequestDispatcher("webPages/GroupPage/GroupPage4.html").include(request, response);
            SetGroupTextBox(id, idG, out);
            request.getRequestDispatcher("webPages/GroupPage/GroupPage5.html").include(request, response);
            EditAvatar(id, out);
            request.getRequestDispatcher("webPages/GroupPage/GroupPage6.html").include(request, response);
            out.close();
        } catch(Exception e) {
        }
    }
    
    private void Name(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps,out);
            
             while(rs.next()){
                 out.println("<li class=\"dropdown\">");
                 out.println("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">"+rs.getString("name")+"<b class=\"caret\"></b></a>");
             }
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private void Post(String idG, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select post.id,users.name,users.surname,users.avatar,\n" +
                                                            " post.date,post.text\n" +
                                                            "from post,users\n" +
                                                            " where post.id_groups = ? and users.ID = post.ID_users "+
                                                            " order by (post.date) desc");
            ps.setString(1, idG);
            ResultSet rs = db.Query(ps,out);
            
            
            int contatore=0;
            while(rs.next()){
                 
                 PreparedStatement ps1 = db.conn.prepareStatement("select post_file, ID from post_file where ID_post = ?");
                 ps1.setString(1, rs.getString("post.id"));
                 ResultSet rs1 = db.Query(ps1,out);
                 
                 
                 contatore++;
                 out.println("<tr id='post"+contatore+"'><td>\n" +
"                    <div class='well'>\n" +
"                        <div class=\"postLeft\">\n" +
"                            <br />");
                 out.println("<img class='img-circle post' data-src='holder.js/80x80' src='"+rs.getString("avatar")+"' />");
                 out.println("</div>\n" +
    "                        <div class='postRight'>\n" +
    "                            <p class='postDate'>"+rs.getString("date").substring(0,rs.getString("date").length()-2)+"</p>");
                 out.println("<h4>"+rs.getString("surname")+" "+rs.getString("name")+"</h4>");
                 
                 String testo = ConvertiLink(rs.getString("text"));
                 
                 out.println("<p>"+testo+"</p>");
                 out.println("<div class=\"postLink\">\n");
                    while(rs1.next()){
                        out.println("<a href='Download?idF="+rs1.getString("ID_post")+"'>"+rs1.getString("post_file")+" Download</a><br>");
                    }
                 out.println("</div>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                </td></tr>");
                 
             }
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private void GroupTitle(String idG, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,avatar from groups where id = ?");
            ps.setString(1, idG);

            ResultSet rs = db.Query(ps,out);
            
             while(rs.next()){
                 out.println("<h1><img class='createGroup' id='imageGroup' src='img/group/"+rs.getString("avatar")+"' />"+rs.getString("name")+"</h1>");
             }
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private void SetGroupTextBox(int id, String idG, PrintWriter out){        
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps,out);
            
             while(rs.next()){
                 out.println("<input type='text' name='idG' id='idG' hidden='hidden' value='"+idG+"'/>");
             }
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private void EditAvatar(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,surname,avatar from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps,out);
            
             while(rs.next()){
                 out.println("<h1>"+rs.getString("surname")+" "+rs.getString("name")+"</h1>");
                 out.println("<img id='avatar' class=\"img-circle\" data-src=\"holder.js/200x200\"\" style=\"width: 200px; height: 200px;\" src='"+rs.getString("avatar")+"'/>");
             }
        }
        catch(SQLException e){}
        
        db.DBClose();
    }
    
    private String ConvertiLink(String testo){
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
                testofinale += "<a  target='_blank' href='http://";
                testofinale += testo.substring(inizio,fine);
                testofinale += "'>";
                testofinale += testo.substring(inizio,fine);
                testofinale += "</a>";
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
