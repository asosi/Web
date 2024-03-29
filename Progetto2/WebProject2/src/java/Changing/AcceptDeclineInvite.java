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
public class AcceptDeclineInvite extends HttpServlet {
    
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
            int idT;
            synchronized(session){idT = (Integer) session.getAttribute("idUser");} 
            String id;
            id = Integer.toString(idT);
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(ip);
            db.DBClose();
            /* TODO output your page here. You may use following sample code. */
            String a = request.getParameter("accettati");
            String d = request.getParameter("declinati");
            
            
            String[] ac = a.split(" ");
            String[] de = d.split(" ");           
            
            Accept(ac, id);
            Decline(de, id);
            
            response.sendRedirect("Home.jsp");
            
        } catch(Exception e) {}
    }
    
    private void Decline(String [] de, String id){
        DBConnect db = new DBConnect(ip);                
        for(int i = 0; i < de.length; i++){
            Update(2, de[i], id);
        }                    
    }
    
    private void Accept(String [] ac, String id){
        DBConnect db = new DBConnect(ip);                
        for(int i = 0; i < ac.length; i++){
            Update(1, ac[i], id);
            if(!ValidateMembers(ac[i], id))
                InsertMember(ac[i], id);
            else
                UpdateMember(ac[i], id);
        }                    
    }
    
    private void Update(int val, String idG, String id){
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
    
    private void UpdateMember(String idG, String id){
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("update users_groups set active = 1 where id_users = ? and id_groups = ?");
            ps.setString(1, id);
            ps.setString(2, idG);
            db.QueryInsert(ps);   
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private boolean ValidateMembers(String gruppo, String utente){
        DBConnect db = new DBConnect(ip);
        boolean st = false;
        
        try{
            db = new DBConnect(ip);
            PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM users_groups where id_groups = ? and id_users = ? and active=0");
            ps.setString(1, gruppo);
            ps.setString(2, utente);
            
            ResultSet rs = db.Query(ps);
            
            st = rs.next();
        
        }catch(Exception e)
        {}
        
        db.DBClose();
        return st;
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
