/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


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
     
     public DBConnect(PrintWriter out, String IP){
        conn = null;
        //String url = "jdbc:mysql://82.56.40.103:3306/";   //sosi 
        //String url = "jdbc:mysql://192.168.0.7:3306/";   //for me
        //inizializza ip
        //String url = "jdbc:mysql://"+IP+":3306/";
        String url = "jdbc:mysql://80.116.139.211:3306/";
        
        String dbName = "new_schema";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "user";
        String password = "erpice";
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url+dbName,userName,password);            
        }
        catch (Exception e){
            out.println("<!DOCTYPE html>\n" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
"<head>\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
"    <title>500 - Internal Server Error</title>\n" +
"\n" +
"    <link rel=\"shortcut icon\" href=\"img/error/icon.ico\">\n" +
"\n" +
"    <script type=\"text/javascript\">\n" +
"        function Invia() {\n" +
"            document.getElementById(\"text\").value = \"\";\n" +
"        }\n" +
"\n" +
"    </script>\n" +
"\n" +
"</head>\n" +
"<body>\n" +
"    <h1>500 - Internal Server Error</h1>\n" +
"    <p style=\"font-size:20px\">Si è verificato un errore del server <br />\n" +
"        (nonostante i nostri criceti infaticabili)<br /><br /><br />\n" +
"        Ebbene si: alcuni dei criceti che rendono possibile il nostro Forum si sono addormentati sul lavoro e questo ha causato un errore.<br />\n" +
"        Una segnalazione dell'errore è stata mandata automaticamente al capo dei criceti e sappi che i pigroni saranno adeguatamente rimproverati!</p>\n" +
"    <br />\n" +
"    <img style=\"width:400px\" src=\"img/error/criceto.jpg\" />\n" +
"    <br />\n" +
"    <p>\n" +
"        Se ti andasse di darci qualche dettaglio in più, sappi che la tua segnalazione ci sarebbe di grandissima utilità. In caso, puoi completare il form qui sotto.<br />\n" +
"        I dati sono raccolti in forma totalmente anonima.<br />\n" +
"    </p>\n" +
"    <input id=\"text\" style=\"width:70%; float:left; margin-right:20px\" type=\"text\" />\n" +
"    <button type=\"submit\" onclick=\"Invia()\" >Invia Segnalazione</button>\n" +
"</body>\n" +
"</html>");
        }
    }
     
     public void DBClose(){
         try{
            conn.close();
         }
         catch(SQLException err){}
     }
     
     public ResultSet Query(PreparedStatement ps,PrintWriter out){
         try{
             
            ResultSet rs = ps.executeQuery();            
            return rs;
            
         }catch(SQLException err){             
             out.println(err.getMessage());
             return null;
         }
     }
     
     public void QueryInsert(PreparedStatement ps, PrintWriter out){
        try{
             
            ps.executeUpdate(); 
            
         }catch(SQLException err){             
             out.println(err.getMessage());
         }
     }
     
}