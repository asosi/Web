/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Stamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 *
 * @author davide
 */
public class Print {
    String ip;
    
    public Print(HttpServletRequest request){
        ip = request.getLocalAddr();
    }
    
    public void Stampa(ArrayList<String> result, JspWriter out) throws IOException{
        Iterator iter = result.iterator();
        while(iter.hasNext()){
            out.println((String)iter.next());
        }
    }
}
