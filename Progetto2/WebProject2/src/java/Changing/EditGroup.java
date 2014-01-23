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
public class EditGroup extends HttpServlet {

    String idG;
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
            
            String name = request.getParameter("group_name");
            String membri = request.getParameter("group_member");
            String nomembri = request.getParameter("group_nomember");
            
            idG = request.getParameter("group_id");
            
            String[] members = membri.split(" ");             
            out.println(name);
            out.println("<br>Membri: ");
            for(int i = 0; i < members.length; i++)
                out.println(members[i]);
            
            
            String[] nomembers = nomembri.split(" ");             
            out.println("<br>No Membri: ");
            for(int i = 0; i < nomembers.length; i++)
                out.println(nomembers[i]);
            
            EditGroupName(name, out);
            AddMembers(members,out);
            EditMembers(nomembers,out);            
            
            response.sendRedirect("Gruppi");
            out.close();
            
        } catch(Exception e) {
        }
    }
    
    private void EditGroupName(String nome, PrintWriter out){
         DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("update groups set name = ? where id = ?");
            ps.setString(1, nome);
            ps.setString(2, idG);

            db.QueryInsert(ps,out);            
        }
        catch(SQLException e){}
        db.DBClose();
    }

   private void AddMembers(String [] membri, PrintWriter out){
         DBConnect db = new DBConnect(out,ip);
        
        try{
            for(int i = 0; i < membri.length; i++){                 
                if(!ValidateMembers(idG,membri[i],out)){
                    if(!ValidateAsk(idG, membri[i],out)){                       
                        PreparedStatement ps = db.conn.prepareStatement("insert into ask (id_groups, id_users) values (?,?)");
                        ps.setString(1, idG);
                        ps.setString(2, membri[i]);
                        db.QueryInsert(ps,out); 
                        AddNews(membri[i],out);
                    }
                    else{
                        PreparedStatement ps = db.conn.prepareStatement("update ask set state = 0 where id_groups = ? and id_users = ?");
                        ps.setString(1, idG);
                        ps.setString(2, membri[i]);
                        db.QueryInsert(ps,out); 
                        AddNews(membri[i], out);
                    }
                    
                }        
            }     
        }
        catch(SQLException e){}
        db.DBClose();
    }
   
   private void EditMembers(String [] nomembri, PrintWriter out){
         DBConnect db = new DBConnect(out,ip);
        
        try{
            for(int i = 0; i < nomembri.length; i++){                 
                if(ValidateMembers(idG,nomembri[i], out)){
                    PreparedStatement ps = db.conn.prepareStatement("update users_groups set active = 0 where id_groups = ? and id_users = ?");
                    ps.setString(1, idG);
                    ps.setString(2, nomembri[i]);
                    db.QueryInsert(ps,out);                     
                }        
            }     
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private boolean ValidateMembers(String gruppo, String utente, PrintWriter out){
        boolean st = false;
        DBConnect db = new DBConnect(out,ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM users_groups where id_groups = ? and id_users = ? and active=1");
            ps.setString(1, gruppo);
            ps.setString(2, utente);
            
            ResultSet rs = db.Query(ps,out);
            
            st = rs.next();
        
        }catch(Exception e)
        {}
        
        db.DBClose();
        return st;
    } 
    
    private boolean ValidateAsk(String gruppo, String utente, PrintWriter out){
        boolean st = false;
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM ask where id_groups = ? and id_users = ? and state <> 0");
            ps.setString(1, gruppo);
            ps.setString(2, utente);
            
            ResultSet rs = db.Query(ps,out);
            
            st = rs.next();
        
        }catch(Exception e)
        {}
        
        db.DBClose();
        return st;
    } 

    private void AddNews(String membro, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        String news = "Hai nuovi inviti";
        String page = "Inviti";
        try{
            if(!ValidateNews(membro, page, out))
            {
                PreparedStatement ps = db.conn.prepareStatement("insert into news (id_users,news,page) values (?,?,?)");
                ps.setString(1, membro);
                ps.setString(2, news);            
                ps.setString(3, page);
                db.QueryInsert(ps,out);
            }
            
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private boolean ValidateNews(String idU, String page, PrintWriter out){
        boolean st = false;
        DBConnect db = new DBConnect(out,ip);
        try{
            
            PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM news where id_users = ? and page = ? and see = 0");
            ps.setString(1, idU);
            ps.setString(2, page);
            
            ResultSet rs = db.Query(ps,out);
            
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
