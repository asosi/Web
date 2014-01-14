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
public class Gruppi extends HttpServlet {
    
    int contatoreTabPrinc = 0;
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
            PrintWriter out = response.getWriter();
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(out,ip);
            db.DBClose();
            request.getRequestDispatcher("webPages/Gruppi/gruppi1.html").include(request, response);
            Name(id, out);
            request.getRequestDispatcher("webPages/Gruppi/gruppi2.html").include(request, response);
            Table(id, out);
            Table1(id, out);
            request.getRequestDispatcher("webPages/Gruppi/gruppi3.html").include(request, response);
            request.getRequestDispatcher("webPages/Gruppi/edit.html").include(request, response); //creare altra pagina
            request.getRequestDispatcher("webPages/Gruppi/gruppi4.html").include(request, response);
            EditAvatar(id, out);
            request.getRequestDispatcher("webPages/Gruppi/gruppi5.html").include(request, response);
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
    
    private void Table(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * from groups where id_owner = ?");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps,out);
                       
            while(rs.next()){
                contatoreTabPrinc++;
                out.println("<tr id='tr"+contatoreTabPrinc+"'>");                
                out.println("<td>"+rs.getString("id")+"</td>");
                out.println("<td>\n <img class='table' src='img/group/"+rs.getString("avatar")+"' />"+rs.getString("name")+"\n</td>");
                out.println("<td>"+rs.getString("born_date").substring(0,rs.getString("born_date").length()-2)+"</td>");
                out.println("<td>\n<form>\n<div class=\"form-group\">\n" +
"                                    <button class=\"btn btn-primary \" name='tr"+contatoreTabPrinc+"' onclick=\"GroupPage(name)\" type=\"button\">Page of Group</button>\n" +
"                                </div>\n</form>\n</td>");
                out.println("<td>\n" +
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
    }
    
    private void Table1(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * from  groups join users_groups on(groups.id = users_groups.id_groups) where users_groups.id_users = ? and active = 1");
            ps.setInt(1, id);

            ResultSet rs = db.Query(ps,out);
                        
            while(rs.next()){
                contatoreTabPrinc++;
                out.println("<tr id='tr"+contatoreTabPrinc+"'>");                
                out.println("<td>"+rs.getString("id")+"</td>");
                out.println("<td>\n <img class='table' src='img/group/"+rs.getString("groups.avatar")+"' />"+rs.getString("groups.name")+"\n</td>");
                out.println("<td>"+rs.getString("born_date").substring(0,rs.getString("born_date").length()-2)+"</td>");
                out.println("<td>\n<form>\n<div class=\"form-group\">\n" +
"                                    <button class=\"btn btn-primary \" name='tr"+contatoreTabPrinc+"' onclick=\"GroupPage(name)\" type=\"button\">Page of Group</button>\n" +
"                                </div>\n</form>\n</td>");
                out.println("<td></td></tr>");
            }
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private void EditGroup(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT name,avatar from users join users_group on (users.id = users_groups.id_users where users_groups.id_groups = ?");
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

