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

public class FilterFiles implements Filter{
    
  String ip;
    
  public void init(FilterConfig arg0) throws ServletException {  }

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
      
      
      boolean trovato = false;

      try{
          
        HttpSession session = ((HttpServletRequest) request).getSession();       
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        //prendo il numero del gruppo dall'URI
        String requestURI = req.getRequestURI();
        String[] splitta = requestURI.split("/");
        
        if(splitta.length == 3){
            res.sendRedirect("index.html");
        }else  if(splitta.length == 4){
            res.sendRedirect("../index.html");
        }
        
        String idGruppo = splitta[3];
        
        if(session != null && session.getAttribute("idUser")!= null){
            
            ip = request.getLocalAddr();
            DBConnect db = new DBConnect(null,ip);
            int id;
            synchronized(session){id = (Integer) session.getAttribute("idUser");}
            
            
            //se il gruppo è mio
            if(!trovato){
                // seleziono tutti i gruppi come table
                PreparedStatement ps = db.conn.prepareStatement("SELECT * from groups where id_owner = ? and id = ?");
                ps.setInt(1, id);
                ps.setString(2, idGruppo);
                ResultSet rs = db.Query(ps,null);

                trovato = rs.next();
                // chiudo resultset
                rs.close();
            }
            
            //se sono invitato nel gruppo
            if(!trovato){
            // seleziono tutti i gruppi come table 1
            PreparedStatement ps2 = db.conn.prepareStatement("SELECT * from  groups join users_groups on(groups.id = users_groups.id_groups) where users_groups.id_users = ? and active = 1 and groups.id = ?");
            ps2.setInt(1, id);
            ps2.setString(2, idGruppo);
            ResultSet rs2 = db.Query(ps2,null);
            
            trovato = rs2.next();
            // chiudo resultset2
            rs2.close();
            }
            
            // se il gruppo è pubblico(0) o supremo(2) e non sono invitato
            if(!trovato){
            PreparedStatement ps3 = db.conn.prepareStatement("SELECT * from groups where flag <> 1 and id = ?");
            ps3.setString(1, idGruppo);
            ResultSet rs3 = db.Query(ps3,null);
            
            trovato = rs3.next();
            // chiudo resultset
            rs3.close();
            }           
            
            // chiudo la connessione con il DB
            db.DBClose();
            if(trovato == true){
                chain.doFilter(request, response);
            }else{
                res.sendRedirect("../../Home");
            }
            
        } else {
            res.sendRedirect("../../index.html");
        }
        
      }catch (Exception e){
          
      }   
  }
  
  @Override
  public void destroy() {  }
  
}