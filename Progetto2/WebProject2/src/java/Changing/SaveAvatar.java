package Changing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import DB.DBConnect;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author davide
 */
public class SaveAvatar extends HttpServlet {
    
    private String dirName;
    String ip;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dirName = config.getInitParameter("uploadDir");
        if (dirName == null) {
            throw new ServletException("Please supply uploadDir parameter");
        }
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        final int id;
        synchronized(session){id = (Integer)session.getAttribute("idUser");}
        PrintWriter out = response.getWriter();
        try {
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(out,ip);
            db.DBClose();
            MultipartRequest multi;
            multi = new MultipartRequest(request, dirName, 10*1024*1024,"ISO-8859-1",
                    new FileRenamePolicy(){
                            @Override
                            public File rename(File file) {
                                return new File(file.getParentFile(),Integer.toString(id));
                            }
                    });
            AddImg(id, out);
            request.getAttribute("javax.servlet.forward.request_uri");
            
            String referer = request.getHeader("Referer"); 
            response.sendRedirect(referer);
            
        }
        
        catch (Exception lEx) {
            response.sendRedirect("ErrorFile.html");
        }
        out.close();
    }
    
    private void AddImg(int id, PrintWriter out){
        DBConnect db = new DBConnect(out,ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("update users set avatar = ? where id = ?");
            ps.setString(1, "img/users/" +Integer.toString(id));
            ps.setInt(2, id);
            db.QueryInsert(ps,out);            
        }
        catch(SQLException e){}
        db.DBClose();
    }
}