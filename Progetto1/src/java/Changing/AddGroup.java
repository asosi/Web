/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Changing;

import DB.DBConnect;
import java.io.File;
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
public class AddGroup extends HttpServlet {

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
            String avatar = request.getParameter("group_avatar");
            String membri = request.getParameter("group_member");
        
            String[] members = membri.split(" "); 

            String[] images = avatar.split("/");
            String image = images[images.length-1];


            AddGroup(name,image,id,out);
            int idG = returnID(id, out);

            while(idG==-1)
                idG = returnID(id, out);

            AddMembers(idG, members, out);
            
            //File dir = new File("/home/pi/apache-tomcat-7.0.47/webapps/ciao/files/"+idG);//sosi
            File dir = new File("/home/davide/Scaricati/apache-tomcat-7.0.47/webapps/boobs3/files/"+idG);//campi
            boolean a = dir.mkdirs();
            
            out.println(a);
            
            response.sendRedirect("GroupPage?numero="+idG);
            out.close();
              
        } catch(Exception e) {}
    }    
    
    private void AddGroup(String nome, String image, int id, PrintWriter out){
         DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("insert into groups (name,id_owner, avatar) values "
                    + "(?,?,?)");
            ps.setString(1, nome);
            ps.setInt(2, id);
            ps.setString(3, image);

            db.QueryInsert(ps,out);            
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private int returnID(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        int idG = -1;
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select max(id) from groups where id_owner = ?");
            ps.setInt(1, id);
            
            ResultSet rs = db.Query(ps,out);          
                        
            rs.next();
            idG = rs.getInt("max(id)");
        }
        catch(SQLException e){out.println(e.getMessage());}
        db.DBClose();
        
        return idG;
    }

    private void AddMembers(int idG,String [] membri, PrintWriter out){
         DBConnect db = new DBConnect(out,ip);
               
            for(int i = 0; i < membri.length; i++){
                AddInvito(idG, membri[i], out);
                AddNews(membri[i], out);
            }      
        db.DBClose();                
        
    }
    
    private void AddInvito(int idG,String membro, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("insert into ask (id_groups, id_users) values (?,?)");
            ps.setInt(1, idG);
            ps.setString(2, membro);
            db.QueryInsert(ps,out); 
        }
        catch(SQLException e){}
        db.DBClose();
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
        
        DBConnect db = new DBConnect(out,ip);
        boolean st = false;
        
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
