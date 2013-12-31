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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amedeo
 */
public class Home extends HttpServlet {
    
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
        
        try {            
            HttpSession session = request.getSession();
            int id;
            synchronized(session){id = (Integer) session.getAttribute("idUser");} 
            /* TODO output your page here. You may use following sample code. */            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(out,ip);
            db.DBClose();
            request.getRequestDispatcher("webPages/Home/home1.html").include(request, response);
            Notifiche(id,out);
            Name(id,out);
            request.getRequestDispatcher("webPages/Home/home2.html").include(request, response);
            StampaCookie(id, request, out);
//request.getRequestDispatcher("webPages/Home/cookie.html").include(request, response);
            request.getRequestDispatcher("webPages/Home/home3.html").include(request, response);
            User(id,out);
            request.getRequestDispatcher("webPages/Home/home4.html").include(request, response);
            EditAvatar(id,out);
            request.getRequestDispatcher("webPages/Home/home5.html").include(request, response);
             out.close();        
        } catch(Exception e) {
           
        }
    }

    private void Notifiche(int id, PrintWriter out){
        
        DBConnect db = new DBConnect(out,ip); 
        try{           
            int numero = 0;
            
            PreparedStatement ps = db.conn.prepareStatement("select id,news,page from news where see = 0 and id_users = ?");
            ps.setInt(1, id);
            ResultSet rs = db.Query(ps,out);
            
            rs.last();
            numero = rs.getRow();
            rs.beforeFirst();
                                    
            out.println("<li class=\"dropdown\">");
                        
            if(numero == 0){
                out.println("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" onclick=\"Notify()\">Notifications <span id=\"notify\" class=\"badge notnotify\">0</span></a>\n");
                out.println("<ul class=\"dropdown-menu\">");
                out.println("</ul> \n </li>");
            }else{  
                
                out.println("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" onclick=\"Notify()\">Notifications <span id=\"notify\" class=\"badge notify\">"+numero+"</span></a>\n");
                out.println("<ul class=\"dropdown-menu\">");
                
                while(rs.next()){
                    out.println("<li><a href='DeleteNews?val="+rs.getString("page")+"&id="+rs.getString("id")+"'>"+rs.getString("news")+"</a></li>\n <li class='divider'></li>");
                }
                out.println("</ul> \n </li>");              
            }
            rs.close();
            
        }
        catch(SQLException e){
            out.println(e.getMessage());
        }
        
        db.DBClose();
    }

    private void User(int id, PrintWriter out){
        
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,surname,avatar from users where id = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps,out);
            
             while(rs.next()){
                 out.println("<h1>"+rs.getString("surname")+" "+rs.getString("name")+"</h1>");
             
                 out.println("<img class=\"img-circle\" data-src=\"holder.js/200x200\"\" style=\"width: 200px; height: 200px;\" src='"+rs.getString("avatar")+"'/>");
             }
            rs.close();
        }
        catch(SQLException e){}
        
        db.DBClose();
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
            rs.close();
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
            rs.close();
        }
        catch(SQLException e){}
        
        db.DBClose();
    }
    
    private void StampaCookie(int id, HttpServletRequest request, PrintWriter out) {
        Cookie cookies [] = request.getCookies();
        for(int i= 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("date"+id)){
                if(cookies[i].getValue().equals("no")){
                    out.println("<h4 class=\"title\" id=\"ultimoAccesso\">Last access: First visit</h4>");
                }
                else{
                    out.println("<h4 class=\"title\" id=\"ultimoAccesso\">Last access: "+cookies[i].getValue()+"</h4>");
                }                    
            }
        }
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
