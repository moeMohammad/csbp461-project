package servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Post;

/**
 *
 * @author Aziz
 */
@WebServlet(urlPatterns = {"/PostCreateServlet"})
public class PostCreateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private final String databaseURL = "jdbc:mysql://localhost:3306/blogDB";
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private final String user = "root";
    private final String password = "root";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // get params
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        // make sure we're logged in
        HttpSession session = request.getSession();
        Integer authorId = (Integer) session.getAttribute("authorId");
        if (authorId == null) {
            // user is not logged in, redirect to login page
            response.sendRedirect("login.jsp");
            return;
        }
        
        // create the post
        Post post = new Post(authorId, title, content);
        
        Connection conn;
        PreparedStatement stmt;
        
        try {
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection(databaseURL, user, password);

            String sql = "INSERT INTO posts (author_id, title, content, created_at) VALUES (?, ?, ?, NOW())";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, authorId);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.executeUpdate();

            conn.close();

            // redirect
            response.sendRedirect("index.jsp");

        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            response.sendRedirect("create_post.jsp?error=1"); //replace with error page
        }
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
