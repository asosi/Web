/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import DB.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amedeo
 */
public class Login extends HttpServlet {

    int id;
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
        PrintWriter out = response.getWriter();
        try {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");
            if(Validate(user,pass,out)==true){
                
                HttpSession session = request.getSession(true);                
                synchronized(session){ session.setAttribute("idUser", id);}
                
                if(request.getCookies() == null){                    
                    Cookie date = new Cookie("date"+id,"no");
                    response.addCookie(date);
                }
                else{
                    Cookie cookies [] = request.getCookies();
                        for(int i= 0; i < cookies.length+1; i++){
                            if(i==cookies.length){
                                Cookie date = new Cookie("date"+id,"no");
                                response.addCookie(date);
                            }
                            else if(cookies[i].getName().equals("date"+id)){
                                break;
                            }
                        }
                }
                
                response.sendRedirect("Home");
            }
            else{
                response.sendRedirect("loginError.html");
            }
            out.close();
            
            
        } catch(Exception e) {
        }
    }
    
    boolean Validate(String user, String pass, PrintWriter out){
        
        boolean st = false;
        try{
            
            DBConnect db = new DBConnect(out);
            PreparedStatement ps = db.conn.prepareStatement("SELECT id FROM users where username = ? and password = ?");
            ps.setString(1, user);
            ps.setString(2, pass);
            
            ResultSet rs = db.Query(ps,out);
            
            st = rs.next();
            id= rs.getInt("id");
            rs.close();
            db.DBClose();
        }catch(Exception e){
        }
        
        return st;
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
