/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Changing;

import Class.Email;
import DB.DBConnect;
import java.io.IOException;
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
    String server;
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
                        
            ip = request.getLocalAddr();
            server = request.getServerName();
            
            DBConnect db = new DBConnect(ip);
            db.DBClose();
            
            String name = request.getParameter("group_name");
            String membri = request.getParameter("group_member");
            String nomembri = request.getParameter("group_nomember");            
            String flag = request.getParameter("group_flag");
            
            idG = request.getParameter("group_id");
            
            String[] members = membri.split(" ");                         
            
            String[] nomembers = nomembri.split(" ");             
            
            EditGroup(name,flag);
            if(members[0]!= "")
                AddMembers(members);
            if(nomembers[0]!= "")
                EditMembers(nomembers);             
            
            response.sendRedirect("Gruppi.jsp");
            
        } catch(Exception e) {
        }
    }
    
    private void EditGroup(String nome, String flag){
         DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("update groups set name = ?, flag = ? where id = ?");
            ps.setString(1, nome);
            ps.setString(2, flag);
            ps.setString(3, idG);

            db.QueryInsert(ps);            
        }
        catch(SQLException e){}
        db.DBClose();
    }

   private void AddMembers(String [] membri){
         DBConnect db = new DBConnect(ip);
        
        try{
            for(int i = 0; i < membri.length; i++){                 
                if(!ValidateMembers(idG,membri[i])){
                    if(!ValidateAsk(idG, membri[i])){                       
                        PreparedStatement ps = db.conn.prepareStatement("insert into ask (id_groups, id_users) values (?,?)");
                        ps.setString(1, idG);
                        ps.setString(2, membri[i]);
                        db.QueryInsert(ps); 
                        AddNews(membri[i]);
                        SendEmail(Integer.parseInt(idG),membri[i]);
                    }
                    else{
                        PreparedStatement ps = db.conn.prepareStatement("update ask set state = 0 where id_groups = ? and id_users = ?");
                        ps.setString(1, idG);
                        ps.setString(2, membri[i]);
                        db.QueryInsert(ps); 
                        AddNews(membri[i]);
                    }
                    
                }        
            }     
        }
        catch(SQLException e){}
        db.DBClose();
    }
   
   private void EditMembers(String [] nomembri){
         DBConnect db = new DBConnect(ip);
        
        try{
            for(int i = 0; i < nomembri.length; i++){                 
                if(ValidateMembers(idG,nomembri[i])){
                    PreparedStatement ps = db.conn.prepareStatement("update users_groups set active = 0 where id_groups = ? and id_users = ?");
                    ps.setString(1, idG);
                    ps.setString(2, nomembri[i]);
                    db.QueryInsert(ps);                     
                }        
            }     
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private boolean ValidateMembers(String gruppo, String utente){
        boolean st = false;
        DBConnect db = new DBConnect(ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM users_groups where id_groups = ? and id_users = ? and active=1");
            ps.setString(1, gruppo);
            ps.setString(2, utente);
            
            ResultSet rs = db.Query(ps);
            
            st = rs.next();
        
        }catch(Exception e)
        {}
        
        db.DBClose();
        return st;
    } 
    
    private boolean ValidateAsk(String gruppo, String utente){
        boolean st = false;
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM ask where id_groups = ? and id_users = ? and state <> 0");
            ps.setString(1, gruppo);
            ps.setString(2, utente);
            
            ResultSet rs = db.Query(ps);
            
            st = rs.next();
        
        }catch(Exception e)
        {}
        
        db.DBClose();
        return st;
    } 

    private void AddNews(String membro){
        DBConnect db = new DBConnect(ip);
        String news = "Hai nuovi inviti";
        String page = "Inviti.jsp";
        try{
            if(!ValidateNews(membro, page))
            {
                PreparedStatement ps = db.conn.prepareStatement("insert into news (id_users,news,page) values (?,?,?)");
                ps.setString(1, membro);
                ps.setString(2, news);            
                ps.setString(3, page);
                db.QueryInsert(ps);
            }
            
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private boolean ValidateNews(String idU, String page){
        boolean st = false;
        DBConnect db = new DBConnect(ip);
        try{
            
            PreparedStatement ps = db.conn.prepareStatement("SELECT * FROM news where id_users = ? and page = ? and see = 0");
            ps.setString(1, idU);
            ps.setString(2, page);
            
            ResultSet rs = db.Query(ps);
            
            st = rs.next();
        
        }catch(Exception e)
        {}
        
        db.DBClose();
        return st;
    }
    
    private void SendEmail(int idG,String membro){
        DBConnect db = new DBConnect(ip);
        Email emailClass = new Email();
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT email FROM users where id = ?");
            ps.setString(1, membro);            
            ResultSet rs = db.Query(ps);   
            rs.next();
            String email = rs.getString("email");
            rs.close();
            
            PreparedStatement ps1 = db.conn.prepareStatement("SELECT groups.name, users.name, users.surname FROM groups "
                    + "JOIN users on (groups.id_owner = users.id) where groups.id = ?");
            ps1.setInt(1, idG);
            ResultSet rs1 = db.Query(ps1);   
            rs1.next();
            String nameG = rs1.getString("groups.name");
            String nameU = rs1.getString("users.surname")+" "+rs1.getString("users.name");
            rs1.close();
            
            PreparedStatement ps2 = db.conn.prepareStatement("SELECT id FROM ask where id_groups = ? and id_users = ?");
            ps2.setInt(1, idG);
            ps2.setString(2, membro);                        
            ResultSet rs2 = db.Query(ps2);   
            rs2.next();
            String idI = rs2.getString("id");
            rs2.close();
                        
            String oggetto = "New Invitation";
            String testo = "\nYou've received a new invitation from the group '"+nameG+"', created by "+nameU+
                    "\n\nClick the following link to accept the invitation"+
                    "\n\n\n http://"+server+":8080/Forum2/AcceptInvite?email="+email+"&n="+idI+"&g="+idG;
            
            
            emailClass.Send(email, oggetto, testo);
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
