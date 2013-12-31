package Upload_Download;

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
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;
import DB.DBConnect;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author davide
 */
public class UploadFile extends HttpServlet {
    
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
        int id;
        synchronized(session){id = (Integer) session.getAttribute("idUser");}
        PrintWriter out = response.getWriter();
        ip = request.getLocalAddr();
        String idG;
        idG="28";
        String idP = "";
        //idP = ReturnIDPost();
        
        
        try {    
            MultipartRequest multi;
            multi = new MultipartRequest(request, dirName + idG, 1024*1024*1024,"ISO-8859-1",
                    new DefaultFileRenamePolicy());
            AddFile(out, idG, idP);
                        
        }
        catch (IOException lEx) {
            this.getServletContext().log(lEx, "Impossibile caricare il file");
        }
    }
    
    private void AddFile(PrintWriter out, String idG, String idP){
        DBConnect db = new DBConnect(out,ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("insert post_file (id_post,post_file) values (?,?)");
            ps.setString(1, idP );
            ps.setString(2, "files/" + idG+ "/" + idP);
            db.QueryInsert(ps,out);            
        }
        catch(SQLException e){}
        db.DBClose();
    }
}