package Filters;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

public class FilterA implements Filter{
    
  public void init(FilterConfig arg0) throws ServletException {  }

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
      
      //faccio qualcosa prima di chiamare la servlet Home 
      //tipo posso controllare che l'utente sia loggato e controllo la sessione
      try{
          
        HttpSession session = request.getSession();           
        int id;
        synchronized(session){id = (Integer) session.getAttribute("idUser");}
      }catch (Exception e){
          
      }
      
      //se c'è sessione lascio vedere la pagina, sennò reindirizzo al login
      
      
      try
      {
        chain.doFilter(request, response);
      }
      catch(Exception ex)
      {
       ex.printStackTrace();
      }
      
  }
  
  @Override
  public void destroy() {  }
  
}