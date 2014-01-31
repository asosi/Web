/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Changing;

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
public class BlockGroup extends HttpServlet {

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
            
            HttpSession session = request.getSession();
            int id;
            synchronized(session){id = (Integer) session.getAttribute("idUser");}
                        
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(ip);
            db.DBClose();
            
            String bloccati = request.getParameter("bloccati");
            String nobloccati = request.getParameter("nobloccati");
            
            
            String[] bloc = bloccati.split(" ");
            String[] nobloc = nobloccati.split(" ");             
            
            
            if(bloc[0]!= "")
                AddBloc(bloc);
            if(nobloc[0]!= "")
                AddsBloc(nobloc);             
            
            response.sendRedirect("Home.jsp");
            
        } catch(Exception e) {
        }
    }
    
   private void AddBloc(String [] bloccati){
        DBConnect db = new DBConnect(ip);
        
        try{
            for(int i = 0; i < bloccati.length; i++){  
                PreparedStatement ps = db.conn.prepareStatement("update groups set blocked = 1 where id = ?");
                ps.setString(1, bloccati[i]);
                db.QueryInsert(ps); 
            }     
        }
        catch(SQLException e){}
        db.DBClose();
    }
   
   private void AddsBloc(String [] nobloccati){
         DBConnect db = new DBConnect(ip);
        
        try{
            for(int i = 0; i < nobloccati.length; i++){  
                PreparedStatement ps = db.conn.prepareStatement("update groups set blocked = 0 where id = ?");
                ps.setString(1, nobloccati[i]);
                db.QueryInsert(ps);                     
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
