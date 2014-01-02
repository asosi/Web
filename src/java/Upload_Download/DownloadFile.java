package Upload_Download;

import DB.DBConnect;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class DownloadFile extends javax.servlet.http.HttpServlet implements
        javax.servlet.Servlet {
    static final long serialVersionUID = 1L;
    private static final int BUFSIZE = 4096;
    private String filePath;
    String idF;
    String ip;
    
    public void init() {
        // the file data.xls is under web application folder
        filePath = "/home/davide/Scaricati/apache-tomcat-7.0.47/webapps/boobs3/";
    }
    
    private String File(){
        String name = "";       
        DBConnect db = new DBConnect(null,ip);        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT post_file from post_file where id = ?");
            ps.setString(1, idF);

            ResultSet rs = db.Query(ps,null);
            
             while(rs.next()){
                 name = rs.getString("post_file");
              }
            rs.close();
        }
        catch(SQLException e){}
        
        db.DBClose();
        return name;
    }
    
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
            ip = request.getLocalAddr();
            idF = request.getParameter("idF");
            
            HttpSession session = request.getSession();
            int id = (Integer) session.getAttribute("idUser");
            
            String name = File();
            ServletOutputStream outStream;
            DataInputStream in;
            
            
            String path = filePath + name;              

            File file = new File(path);
            int length   = 0;
            outStream = response.getOutputStream();
            ServletContext context  = getServletConfig().getServletContext();
            String mimetype = context.getMimeType(path);

            // sets response content type
            if (mimetype == null) {
                mimetype = "text/plain";
            }
            response.setContentType(mimetype);
            response.setContentLength((int)file.length());
            String fileName = (new File(path)).getName();

            // sets HTTP header
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            byte[] byteBuffer = new byte[BUFSIZE];
            in = new DataInputStream(new FileInputStream(file));

            // reads the file's bytes and writes them to the response stream
            while ((in != null) && ((length = in.read(byteBuffer)) != -1))
            {
                outStream.write(byteBuffer,0,length);
            }

            in.close();
            outStream.close();
    }
}