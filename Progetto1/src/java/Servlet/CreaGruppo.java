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
public class CreaGruppo extends HttpServlet {
    
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
            request.getRequestDispatcher("webPages/CreaGruppo/CreaGruppo1.html").include(request, response);
            Name(id, out);
            request.getRequestDispatcher("webPages/CreaGruppo/CreaGruppo2.html").include(request, response);
            Table(id, out);
            request.getRequestDispatcher("webPages/CreaGruppo/CreaGruppo3.html").include(request, response);
            EditAvatar(id, out);
            request.getRequestDispatcher("webPages/CreaGruppo/CreaGruppo4.html").include(request, response);
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
            PreparedStatement ps = db.conn.prepareStatement("SELECT id,name,surname,avatar from users where id <> ? order by surname,name");            
            ps.setInt(1, id);
            
            ResultSet rs = db.Query(ps,out);
            
            int contatore = 0;
            
             while(rs.next()){
                 contatore++;
                 
                 out.println("<tr id='tr"+contatore+"'>");
                 out.println("<td>"+rs.getString("id")+"</td>");
                 out.println("<td>\n" +
"                                <img class='table' src='"+rs.getString("avatar")+"' />"+rs.getString("surname")+" "+rs.getString("name")+"\n" +
"                            </td>");
                 out.println("<td>\n" +
"                                <form>\n" +
"                                    <div class='form-group'>\n" +
"                                        <button class='btn btn-primary' name='btnSend"+contatore+"' id='"+contatore+"' onclick='Send(id)' type='button'>Send Invitation</button>\n" +
"                                        <button disabled='disabled' class='btn btn-danger' id='"+contatore+"' name='btnCancel"+contatore+"' onclick='Cancel(id)' type='button'>Cancel</button>\n" +
"                                    </div>\n" +
"                                </form>\n" +
"                            </td>");
                 out.println("");
                 out.println("");
                 out.println("");
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
