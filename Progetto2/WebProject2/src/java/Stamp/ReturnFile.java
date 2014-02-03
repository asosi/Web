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
 * @author davide
 */
public class ReturnFile extends Print{

    public ReturnFile(HttpServletRequest request) {
        super(request);
    }
    
    public ArrayList<String> Email(String idG){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT post_file.post_file FROM post_file, post \n" +
                                                            "WHERE post.ID = post_file.ID_post and post.ID_groups = ?");
            ps.setString(1, idG);
            ResultSet rs = db.Query(ps);
            
            while(rs.next()){
                result.add(rs.getString("email"));
            }
            
        }
        catch(SQLException ex){
        }
        db.DBClose();
        return result;
        
    }
    
}
