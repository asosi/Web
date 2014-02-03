/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import java.sql.*;


/**
 *
 * @author Amedeo
 */
public class DBConnect {

    public Connection conn;
    
    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {}
     
     public DBConnect(String IP){
        conn = null;
        String url = "jdbc:mysql://"+IP+":3306/";
        //String url = "jdbc:mysql://192.168.1.123:3306/";
        
        String dbName = "new_schema2";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "user";
        String password = "erpice";
        //String password = "user";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url+dbName,userName,password);            
        }
        catch (Exception e){}
     }
     
     public void DBClose(){
         try{
            conn.close();
         }
         catch(SQLException err){}
     }
     
     public ResultSet Query(PreparedStatement ps){
         try{
             
            ResultSet rs = ps.executeQuery();            
            return rs;
            
         }catch(SQLException err){             
             return null;
         }
     }
     
     public void QueryInsert(PreparedStatement ps){
        try{
             
            ps.executeUpdate(); 
            
         }catch(SQLException err){             
         }
     }
     
}