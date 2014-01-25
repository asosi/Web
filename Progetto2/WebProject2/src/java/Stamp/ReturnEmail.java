/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Stamp;

import DB.DBConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Amedeo
 */
public class ReturnEmail extends Print{
    
    public ReturnEmail(HttpServletRequest request) {
        super(request);
    }
    
    public ArrayList<String> Email(){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("select email from users");
            ResultSet rs = db.Query(ps);
            
            while(rs.next()){
                result.add(rs.getString("email"));
            }
            
        }
        catch(SQLException ex){
        }
        return result;
        
    }
}
