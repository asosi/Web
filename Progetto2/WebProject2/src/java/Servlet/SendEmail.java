/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Class.Email;
import DB.DBConnect;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amedeo
 */
public class SendEmail extends HttpServlet {

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
        ip = request.getLocalAddr();
        try {
            
            String email = request.getParameter("email");
            
            String ogget = "Change password";
            
            String testo = "Dear " + email
                        + "\n\n To change your email address click (within 90 seconds) on the following link!"
                        + "\n \n  http://"+request.getServerName()+":8080/Forum2/TimeLink?email="+email;
            
            Email send = new Email();
            send.Send(email,ogget,testo);
            
            SetDate(email);
            
            response.sendRedirect("index.jsp");
            
            
        } catch(Exception e) {
        }
    }
    
    private void SetDate(String email){
        DBConnect db = new DBConnect(ip);
                
        try{
            PreparedStatement ps = db.conn.prepareStatement("UPDATE users SET countdown = NOW() WHERE email = ?");
            ps.setString(1, email);
            
            db.QueryInsert(ps);
        }catch(SQLException e){}
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
