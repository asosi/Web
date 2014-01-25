package Stamp;

import DB.DBConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author davide
 */
public class Index extends Print{

    public Index(HttpServletRequest request) {
        super(request);
    }
    
    public ArrayList<String> Table(){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT \n" +
                    "T2.id, T2.name, T2.avatar, T2.admin,\n" +
                    "T1.n_post, T1.date, T1.name\n" +
                    "FROM\n" +
                    "(SELECT COUNT(post.ID) as n_post, max(date) as date, groups.id ,users.name\n" +
                    "FROM post, users, groups\n" +
                    "WHERE users.ID = post.ID_users\n" +
                    "AND groups.id = post.ID_groups\n" +
                    "group by ID_groups) as T1\n" +
                    "RIGHT JOIN\n" +
                    "(SELECT groups.id,groups.name,groups.avatar,users.name as admin \n" +
                    "FROM groups, users\n" +
                    "Where users.id = groups.ID_owner\n" +
                    "AND groups.flag=0 ORDER By groups.name) as T2\n" +
                    "ON T1.id = T2.id");
            //
            ResultSet rs = db.Query(ps);
            
            while(rs.next()){
                 //html?
            }
            rs.close();
        }
        catch(SQLException e){}
        
        db.DBClose();
        return result;
    }
}
