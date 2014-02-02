package Filters;

import DB.DBConnect;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilterPDF implements Filter{
    
  String ip;
    
  public void init(FilterConfig arg0) throws ServletException {  }

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
      
      
      boolean trovato = false;
      try{
          
        HttpSession session = ((HttpServletRequest) request).getSession();       
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        
        if(session != null && session.getAttribute("idUser")!= null){
            
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(ip);
            int id;
            synchronized(session){id = (Integer) session.getAttribute("idUser");}
            
            // seleziono tutti i gruppi come table
            PreparedStatement ps = db.conn.prepareStatement("SELECT * from groups where id_owner = ?");
            ps.setInt(1, id);
            ResultSet rs = db.Query(ps);
            while(rs.next()){
                String idgruppo = rs.getString("id");
                
                if(request.getParameter("numero").equals(idgruppo)){               
                    trovato = true;
                }
            }
            // chiudo resultset
            rs.close();
            
            // chiudo la connessione con il DB
            db.DBClose();
            if(trovato == true){
                chain.doFilter(req, res);
            }else{
                res.sendRedirect("Gruppi.jsp");
            }
        } else {
            res.sendRedirect("index.jsp");
        }
        
      }catch (Exception e){
          
      }
      
  }
  
  @Override
  public void destroy() {  }
  
}