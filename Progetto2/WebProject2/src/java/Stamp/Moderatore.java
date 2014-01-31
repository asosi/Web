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
public class Moderatore extends Stamp{

    public Moderatore(HttpServletRequest request) {
        super(request);
    }
    
     public ArrayList<String> Table(){
        ArrayList<String> result = new ArrayList<String>();
        DBConnect db = new DBConnect(ip);
        try{
            PreparedStatement ps = db.conn.prepareStatement("SELECT T2.id, T2.name, T2.avatar, T2.flag, T2.blocked, \n" +
                                                            "T1.npost, T2.admin_name, T2.admin_surname, T2.admin_avatar \n" +
                                                            "FROM \n" +
                                                            "(SELECT ID_groups, COUNT(id) as npost \n" +
                                                            "FROM post \n" +
                                                            "group by ID_groups) as T1 \n" +
                                                            "RIGHT JOIN \n" +
                                                            "(SELECT groups.id, groups.name, groups.avatar, flag, blocked, \n" +
                                                            "users.name as admin_name, users.surname as admin_surname, \n" +
                                                            "users.avatar as admin_avatar \n" +
                                                            "FROM groups, users \n" +
                                                            "WHERE groups.ID_owner = users.ID) as T2 \n" +
                                                            "ON T1.id_groups = T2.id order by T2.id");
            ResultSet rs =  db.Query(ps);
            int contatore = 0;
            while(rs.next()){
                contatore++;
                result.add("<tr id='tr"+contatore+"'>");
                result.add("<td>"+rs.getString("T2.id")+"</td>");
                result.add("<td><img class='table' src='img/group/"+rs.getString("T2.avatar")+"' />"+rs.getString("T2.name")+"</td>");
                result.add("<td><img class='table' src='"+rs.getString("T2.admin_avatar")+"' />"+rs.getString("T2.admin_surname")+" "+rs.getString("T2.admin_name")+"</td>");
                
                if(rs.getString("T1.npost")!=null){
                    result.add("<td>"+rs.getString("T1.npost")+"</td>");
                }
                else{
                    result.add("<td>0</td>");
                }
                
                if(rs.getInt("T2.flag")==0){
                    result.add("<td>Public</td>");
                }
                else{
                    result.add("<td>Private</td>");
                }
                
                result.add("<td><button class='btn btn-primary btn-sm' type='button' onclick='SelectGroup('tr"+contatore+"')'>Page of Group</button></td></td>");
                result.add("<td>");
                
                if(rs.getInt("T2.blocked")==0){
                    result.add("<button class='btn btn-danger btn-sm' disabled='disabled' type='button' name='btnBlock"+contatore+"' onclick=\"Block('"+contatore+"')\">Block this Group</button>");
                    result.add("<button class='btn btn-default btn-sm' type='button' name='btnSBlock"+contatore+"' onclick=\"SBlock('"+contatore+"')\">Cancel</button>");
                }
                else{
                    result.add("<button class='btn btn-danger btn-sm' type='button' name='btnBlock"+contatore+"' onclick=\"Block('"+contatore+"')\">Block this Group</button>");
                    result.add("<button class='btn btn-default btn-sm' disabled='disabled' type='button' name='btnSBlock"+contatore+"' onclick=\"SBlock('"+contatore+"')\">Cancel</button>");
                }
                result.add("</td>");
                result.add("</tr>");
                result.add("");
            }
        }catch(SQLException e){}
        db.DBClose();
        return result;
     }
}
