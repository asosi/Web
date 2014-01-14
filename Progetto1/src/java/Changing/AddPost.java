/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Changing;

import DB.DBConnect;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amedeo
 */
public class AddPost extends HttpServlet {
    
    private String dirName;
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
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dirName = config.getInitParameter("uploadDir");
        if (dirName == null) {
            throw new ServletException("Please supply uploadDir parameter");
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
                                    
            HttpSession session = request.getSession();
            int id;
            synchronized(session){ id = (Integer) session.getAttribute("idUser");}
            PrintWriter out = response.getWriter();
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(out,ip);
            db.DBClose();
            String text = request.getParameter("testopost");
            //idG = request.getParameter("idG");
            String idG;
            idG = (String) session.getAttribute("idG");
            
            Add(text, idG, id, out);            
            String name = ReturnGroupName(idG,out);
             
            List<String> membri = SearchMembers(idG, id, out, name);
            for(int i = 0; i < membri.size(); i++)
               AddNews(membri.get(i), idG, out, name);
            
            //***************************************************   
            String idP;
            idP = ReturnIDPost(id, out);
            
            try {
                
               /* String path = " /home/davide/Scaricati/apache-tomcat-7.0.47/webapps/boobs3/files/";
                File dir=new File(path+idG);
                if(dir.exists()){
                    out.println("A folder with name '"+idG+"' is already exist in the path "+path+"<br>");
                }else{
                    dir.mkdirs();
                    out.println("Cartella creata<br>");
                }*/
                
                MultipartRequest multi;
                multi = new MultipartRequest(request, dirName+idG+"/", 1024*1024*1024,"ISO-8859-1",
                        new DefaultFileRenamePolicy());
               
                String nomeFile = request.getParameter("numeroElementi");
                String arrayFile[] = nomeFile.split("_-_-_");
                
                out.println("arrayfile.lenght: "+arrayFile.length+"<br>");
                
                for(int i = 1; i < arrayFile.length; i++){
                    out.println("File"+i+": "+arrayFile[i]+"<br>");                    
                    AddFile(arrayFile[i], idG, idP, out);
                }
            }
            catch (Exception lEx) {
                out.println("crasho nell'ultimo try: " +lEx.getMessage()+"<br>");                
                //this.getServletContext().log(lEx, "Impossibile caricare il file");
            }
            
            response.sendRedirect("GroupPage?numero="+idG);
            
            
            
            
        } catch(Exception e) {
        }
    }
        
    private String ReturnIDPost(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        String idP = "-1";
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select max(id) from post where id_users = ?");
            ps.setInt(1, id);
            
            ResultSet rs = db.Query(ps,out);          
                        
            rs.next();
            idP = rs.getString("max(id)");
        }
        catch(SQLException e){out.println(e.getMessage());}
        db.DBClose();
        
        return idP;
    }
    
    private void Add(String text, String idG, int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("insert into post (id_groups, id_users, text) values (?,?,?)");
            ps.setString(1, idG);
            ps.setInt(2, id);
            ps.setString(3, text);

            db.QueryInsert(ps,out);            
        }
        catch(SQLException e){}
        db.DBClose();
    }
    
    private List<String> SearchMembers(String idG, int id, PrintWriter out, String name){
        List<String> membri = new ArrayList<String>();
        
        DBConnect db = new DBConnect(out,ip);        
        try{
            PreparedStatement ps = db.conn.prepareStatement("(select users_groups.ID_users, groups.name\n" +
                    " from users_groups, groups\n" +
                    " where users_groups.ID_groups = ? and users_groups.ID_users <> ?\n" +
                    " and groups.ID = users_groups.ID_groups and users_groups.active = 1)\n" +
                    " UNION\n" +
                    " (select groups.ID_owner, groups.name\n" +
                    " from groups\n" +
                    " where groups.ID = ? and groups.ID_owner <> ?)");
            ps.setString(1, idG);
            ps.setInt(2, id);
            ps.setString(3, idG);
            ps.setInt(4, id);
            
            ResultSet rs = db.Query(ps,out);
            
             while(rs.next()){
                 name = rs.getString("name");
                 membri.add(rs.getString("id_users"));
             }
        }
        catch(SQLException e){}
        db.DBClose();
        
        return membri;
    }
    
    private void AddNews(String membro, String idG, PrintWriter out, String name){
        DBConnect db = new DBConnect(out,ip);
        String news = "Nuovi post nel gruppo "+name;
        String page = "GroupPage?numero="+idG;
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
    
    private String ReturnGroupName(String idG, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        String name = "";
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select name from groups where id = ?");
            ps.setString(1, idG);
            
            ResultSet rs = db.Query(ps,out);          
                        
            rs.next();
            name = rs.getString("name");
        }
        catch(SQLException e){out.println(e.getMessage());}
        db.DBClose();
        
        return name;
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
    
    private void AddFile(String nome, String idG, String idP, PrintWriter out){
        
        DBConnect db = new DBConnect(out,ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("insert post_file (id_post,post_file) values (?,?)");
            ps.setString(1, idP );
            ps.setString(2, "files/" + idG+ "/" + nome);
            db.QueryInsert(ps,out);            
        }
        catch(SQLException e){
            out.println(e.getMessage());
        }
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
