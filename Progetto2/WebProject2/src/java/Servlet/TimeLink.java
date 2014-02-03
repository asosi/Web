/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Class.Email;
import DB.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amedeo
 */
public class TimeLink extends HttpServlet {

    private final String ALPHA_CAPS  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String ALPHA   = "abcdefghijklmnopqrstuvwxyz";
    private final String NUM     = "0123456789";
    
    String ip;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ip = request.getLocalAddr();
        try {
            Timestamp dataNow = GetNow();
            Timestamp dataPrec = GetDate(request.getParameter("email"),response.getWriter());
            long differenza = (dataNow.getTime()-dataPrec.getTime())/1000;
                        
            response.getWriter().println(differenza+"<br>");
            
            
            if(differenza>90){
                response.sendRedirect("LinkScaduto.html");
            }
            else{
                
                char[] p = generatePswd(8, 12, 1, 1, 1);  
                String password = String.valueOf(p, 0, p.length) ;
                
                String email = request.getParameter("email");
                String ip = request.getLocalAddr();
                
                UpdatePassword(email,password);                
            

                String ogget = "Confirm change password";

                String testo = "Dear " + email
                            + "\n This is your new password:"
                            + "\n\n "+password;

                Email send = new Email();
                send.Send(email,ogget,testo);
                
                response.sendRedirect("LinkValido.html");
            }
            
        } catch(IOException e) {}
    }
    //manca id o email
    private Timestamp GetDate(String email, PrintWriter out){
        Timestamp date = null;
        //select che ritorna data creazione link
        DBConnect db = new DBConnect(ip);
        try {
            PreparedStatement ps = db.conn.prepareStatement("SELECT countdown FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = db.Query(ps);
            rs.next();
            
            String datevalue = rs.getString("countdown");
            date = Timestamp.valueOf(datevalue);
        } catch (SQLException e) {
        }
        db.DBClose();
        return date;
    }
    
    private Timestamp GetNow(){
        Timestamp date = null;
        //select che ritorna data del server
        DBConnect db = new DBConnect(ip);
        try {
            PreparedStatement ps = db.conn.prepareStatement("SELECT NOW() as now");
            ResultSet rs = db.Query(ps);
            rs.next();
            
            String datevalue = rs.getString("now");
            date = Timestamp.valueOf(datevalue);
        } catch (SQLException e) {
        }
        db.DBClose();
        return date;
    }
    
    public char[] generatePswd(int minLen, int maxLen, int noOfCAPSAlpha,
            int noOfDigits, int noOfSplChars) {
        if(minLen > maxLen)
            throw new IllegalArgumentException("Min. Length > Max. Length!");
        if( (noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen )
            throw new IllegalArgumentException
            ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
        Random rnd = new Random();
        int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < noOfCAPSAlpha; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < noOfDigits; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for(int i = 0; i < len; i++) {
            if(pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return pswd;
    }
 
    private int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while(pswd[index = rnd.nextInt(len)] != 0);
        return index;
    }
    
    private void UpdatePassword(String email, String password){
        DBConnect db = new DBConnect(ip);
        
        try{
            PreparedStatement ps = db.conn.prepareStatement("update users set password = ? where email = ?");
            ps.setString(1, password);
            ps.setString(2, email);

            db.QueryInsert(ps);            
        }
        catch(SQLException e){}
        db.DBClose();
                
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
