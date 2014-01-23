package Upload_Download;

import DB.DBConnect;
import com.itextpdf.text.Chunk;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreatePDF extends HttpServlet {
    
    String ip;            
                
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","inline; filename=\"file.pdf\"");
        
        String idGroup = request.getParameter("numero");
        
        try {
            // step 1
            
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document, response.getOutputStream());
            // step 3
            document.open();
            
            try{
                        
                //nome e cognone / numero partecipanti al gruppo da prendere dal DB               
                // SELECT COUNT(id) FROM test.users_group, test.users WHERE ID_groups = #idGroup# AND ID_users = ID
                 //connessione al DB
                ip = request.getLocalAddr();
                DBConnect db = new DBConnect(null,ip);
                PreparedStatement ps1 = db.conn.prepareStatement("SELECT COUNT(id) FROM users_groups, users WHERE ID_groups = ? AND ID_users = ID AND active = 1");
                ps1.setString(1, idGroup);
                ResultSet rs1 = db.Query(ps1,null);
                rs1.next();
                Integer numPar = rs1.getInt("COUNT(id)");
                
                
                //numero post presenti nel gruppo da prendere dal DB
                // SELECT COUNT(id) FROM test.post WHERE ID_group = #idGroup#
                
                PreparedStatement ps3 = db.conn.prepareStatement("SELECT COUNT(id) FROM post WHERE ID_groups = ?");
                ps3.setString(1, idGroup);
                ResultSet rs3 = db.Query(ps3,null);
                rs3.next();
                Integer numPost = rs3.getInt("COUNT(id)");

                
                //data ultimo post nel gruppo/discussione da prendere dal DB
                // SELECT max(date) FROM test.post WHERE ID_groups = #idGroup#
                
                PreparedStatement ps4 = db.conn.prepareStatement("SELECT max(date) FROM post WHERE ID_groups = ?");
                ps4.setString(1, idGroup);
                ResultSet rs4 = db.Query(ps4,null);
                rs4.next();
                String dateLast = rs4.getString("max(date)");
                 
                
		//create a writer that listens to the document
                //creazione file pdf con il nome dell' amministratore (nameAmm) e nome gruppo (nameGroup)
                // SELECT test.users.name, test.users.surname, test.groups.name FROM test.users, test.groups WHERE test.groups.ID_owner = test.users.ID AND test.groups.ID = #idGroup#
                
                PreparedStatement ps5 = db.conn.prepareStatement("SELECT users.name, users.surname, groups.name AS gname FROM users, groups WHERE groups.ID_owner = users.ID AND groups.ID = ?");
                ps5.setString(1, idGroup);
                ResultSet rs5 = db.Query(ps5,null);
                rs5.next();
                String nameAmm = rs5.getString("name");
                String surnameAmm = rs5.getString("surname");
                String nameGroup = rs5.getString("gname");
         
                
                //String path = new String("pdf/" + nameAmm + "_" + nameGroup + ".pdf");
		//PdfWriter.getInstance(document, new FileOutputStream(path));

		//open the document
		//document.open();
                
                Paragraph p1 = new Paragraph();
                //titolo documento
                p1.add(new Chunk("Report gruppo/discussione",FontFactory.getFont(FontFactory.HELVETICA, 16)));
                document.add( Chunk.NEWLINE );   
                document.add(p1);
                
                //aggiunta avatar gruppo (avatarGroup)
                // SELECT avatar FROM test.groups WHERE ID = #idGroup#
                
                PreparedStatement ps6 = db.conn.prepareStatement("SELECT avatar FROM groups WHERE ID = ?");
                ps6.setString(1, idGroup);
                ResultSet rs6 = db.Query(ps6,null);
                rs6.next();
                
                document.add( Chunk.NEWLINE );
                
                //String pathImg = "apache-tomcat-7.0.47/webapps/ciao/img/group/"+rs6.getString("avatar");//sosi
                String pathImg = "/home/davide/Scaricati/apache-tomcat-7.0.47/webapps/Forum/img/group/"+rs6.getString("avatar");//campi
 
                Image avatarGroup = Image.getInstance(pathImg);
               document.add(avatarGroup);
                
                document.add( Chunk.NEWLINE );
                document.add( Chunk.NEWLINE );
                
                //aggiunta del nome gruppo (nameGroup)
                Paragraph p2 = new Paragraph();
                p2.add("Nome gruppo: " + nameGroup);
                document.add(p2);
                
                document.add( Chunk.NEWLINE );
                
                //numero dei partecipanti alla discussione
                Paragraph p3 = new Paragraph();
                p3.add("Numero di partecipanti al gruppo: " + numPar);
                document.add(p3);
                
                document.add( Chunk.NEWLINE );
                
                //elenco partecipanti alla discussione
                Paragraph p4 = new Paragraph();
                p4.add("Elenco dei partecipanti alla discussione:");
                document.add(p4);
                

                //lista dei partecipanti da prendere dal DB
                List list = new List(true, 40);
                list.setListSymbol(new Chunk("\u2022", FontFactory.getFont(FontFactory.HELVETICA)));
                
                
                // SELECT name, surname FROM test.users_group, test.users WHERE ID_groups = #idGroup# AND ID_users = ID
                //ATTENZIONE DA SISTEMARE CREAZIONE LISTA USERS #######
                PreparedStatement ps2 = db.conn.prepareStatement("SELECT name, surname FROM users_groups, users WHERE ID_groups = ? AND ID_users = ID");
                ps2.setString(1, idGroup);
                ResultSet rs2 = db.Query(ps2,null);
                
                //stampo tutti i partecipanti al gruppo
                for(int i=0;i<numPar;i++){     
                    rs2.next();
                    String nameUser = rs2.getString("name");
                    String surnameUser = rs2.getString("surname");
                    ListItem listItem = new ListItem(nameUser+" "+surnameUser);
                    list.add(listItem);
                }
                
                document.add(list);
                
                document.add( Chunk.NEWLINE );
                
                //aggiunta data ultimo post
                Paragraph p5 = new Paragraph();
                if(dateLast == null)
                    p5.add("Data ultimo post: Non sono ancora presenti post in questo gruppo");
                else
                    p5.add("Data ultimo post: " + dateLast);
                document.add(p5);
                
                document.add( Chunk.NEWLINE );

                //aggiunta numero dei post presenti
                Paragraph p6 = new Paragraph();
                p6.add("Numero post presenti nella discussione: " + numPost);
                document.add(p6);
                //step5
                PdfWriter.getInstance(document, response.getOutputStream()).close();
                //step6
                document.close();
                
                db.DBClose();
                }catch(SQLException se){
                    System.out.println(se.getMessage());
                }
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        }
    }
}