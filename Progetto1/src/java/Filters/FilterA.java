package Filters;

import java.io.IOException;
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
               
        HttpServletResponse res = (HttpServletResponse) response;
        
        if(session != null && session.getAttribute("idUser")!= null){
            chain.doFilter(request, response);
        } else {
            res.sendRedirect("index.html");
        }
        
      }catch (Exception e){
          
      }   
  }
  
  @Override
  public void destroy() {  }
  
}