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
public class Login extends HttpServlet {
    
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
            
            String email = request.getParameter("email");
            String pass = request.getParameter("password");
            
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(ip);
            db.DBClose();
            if(Validate(email,pass)==true){
                int id = RetrunID(email, pass);
                
                HttpSession session = request.getSession(true);                
                synchronized(session){ session.setAttribute("idUser", id);}
            
                response.sendRedirect("Home.jsp");
            }
            else{
                response.sendRedirect("LoginError.html");
            }
            
        } catch(Exception e) {
        }
    }
    
    boolean Validate(String email, String pass){
        
        boolean st = false;
        try{
            
            DBConnect db = new DBConnect(ip);
            PreparedStatement ps = db.conn.prepareStatement("SELECT id FROM users where email = ? and password = ?");
            ps.setString(1, email);
            ps.setString(2, pass);
            
            ResultSet rs = db.Query(ps);
            
            st = rs.next();
            
            rs.close();
            db.DBClose();
        }catch(Exception e){
        }
        
        return st;
    }
    
    int RetrunID(String email, String pass){
        int  id = -1;
        try{
            
            DBConnect db = new DBConnect(ip);
            PreparedStatement ps = db.conn.prepareStatement("SELECT id FROM users where email = ? and password = ?");
            ps.setString(1, email);
            ps.setString(2, pass);
            
            ResultSet rs = db.Query(ps);
            
            rs.next();
            id= rs.getInt("id");
            
            rs.close();
            db.DBClose();
        }catch(Exception e){
        }
        
        return id;
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

//da rivedere e testare