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

/**
 *
 * @author Amedeo
 */
public class AcceptInvite extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            
            ip = request.getLocalAddr();
            String id = request.getParameter("n");
            String idG = request.getParameter("g");
            String email = request.getParameter("email");
            String idU = GetIDUser(email);
            
            UpdateAsk(1, idG, idU);
            DeleteNews(id);
            InsertMember(idG, idU);
            
            response.sendRedirect("AcceptInvitation.html");
            
        } finally {
            out.close();
        }
    }
    
    private String GetIDUser(String email){
        String id = "";
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select id from users where email = ?");
            ps.setString(1, email);
            
            ResultSet rs = db.Query(ps);
            rs.next();
            id = rs.getString("id");
            rs.close();
        }
        catch(SQLException e){}
        db.DBClose();
        return id;
    }
    
    private void InsertMember(String idG, String id){
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("insert into users_groups (id_groups,id_users) values(?,?)");
            ps.setString(1, idG);
            ps.setString(2, id);
            db.QueryInsert(ps);   
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
     private void UpdateAsk(int val, String idG, String id){
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("update ask set state = ? where id_groups = ? and id_users = ?");
            ps.setInt(1, val);
            ps.setString(2, idG);
            ps.setString(3, id);
            db.QueryInsert(ps);   
        }
        catch(SQLException e){}
        db.DBClose();
    }
     
      private void DeleteNews(String id){
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("update news set see = 1 where id = ?");
            ps.setString(1, id);

            db.QueryInsert(ps);            
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
