package Filters;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FilterA implements Filter{
    
  public void init(FilterConfig arg0) throws ServletException {  }

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
      
      //faccio qualcosa prima di chiamare la servlet Home 
      //tipo posso controllare che l'utente sia loggato e controllo la sessione
      try{
         HttpSession session = ((HttpServletRequest) request).getSession();
        //HttpSession session = request.getSession();           
        int id;
        synchronized(session){id = (Integer) session.getAttribute("idUser");}
        
        HttpServletResponse res = (HttpServletResponse) response;
        
        if(session == null)
            res.sendRedirect("index.html");
        else{
            try
            {
                chain.doFilter(request, response);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        
      }catch (Exception e){
          
      }
      
      
      
      
      
  }
  
  @Override
  public void destroy() {  }
  
}