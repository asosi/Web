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
        
        int contatore = 1;
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT \n" +
                    "T2.id, T2.name, T2.avatar, T2.adminName, T2.adminSurname,\n" +
                    "T1.n_post, T1.date, T1.name, T1.surname\n" +
                    "FROM\n" +
                    "(SELECT COUNT(post.ID) as n_post, max(date) as date, groups.id ,users.name, users.surname\n" +
                    "FROM post, users, groups\n" +
                    "WHERE users.ID = post.ID_users\n" +
                    "AND groups.id = post.ID_groups\n" +
                    "group by ID_groups) as T1\n" +
                    "RIGHT JOIN\n" +
                    "(SELECT groups.id,groups.name,groups.avatar,users.name as adminName, users.surname as adminSurname \n" +
                    "FROM groups, users\n" +
                    "Where users.id = groups.ID_owner\n" +
                    "AND groups.flag=0 ORDER By groups.name) as T2\n" +
                    "ON T1.id = T2.id");
            //
            ResultSet rs = db.Query(ps);
            
            while(rs.next()){
                 result.add(" <tr id='tr'"+contatore+">");
                 result.add(" <td style='display:none'>"+rs.getString("T2.id")+"</td>");
                 result.add("<td><img class='table' src='img/group/"+rs.getString("T2.avatar")+"' />"+rs.getString("T2.name")+"</td>");
                 result.add("<td>"+rs.getString("T2.adminSurname")+" "+rs.getString("T2.adminName")+"</td>");
                 
                 if(rs.getString("T1.n_post") != null){                 
                    result.add("<td>"+rs.getString("T1.n_post")+"</td>");
                    
                    String date = rs.getString("T1.date");
                    result.add("<td>From: <strong>"+rs.getString("T1.surname")+" "+rs.getString("T1.name")+"</strong> <br />Date: "+date.substring(0,date.length()-2)+"</td>");
                 }
                 else{
                     result.add("<td>0</td>");
                    result.add("<td></td>");
                 }
                 
                 result.add("<td><button class='btn btn-primary btn-sm' type='button' onclick='SelectGroup('tr"+contatore+"')'>Page of Group</button></td>");
                 result.add("</tr>");
                 contatore++;
            }
            rs.close();
        }
        catch(SQLException e){}
        
        db.DBClose();
        return result;
    }
}
