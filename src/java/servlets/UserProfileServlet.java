/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Post;
import model.User;

/**
 *
 * @author mohammad
 */
@WebServlet(name = "UserProfileServlet", urlPatterns = {"/UserProfileServlet"})
public class UserProfileServlet extends HttpServlet {

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
        String databaseURL = "jdbc:mysql://localhost:3306/projectDB?useSSL=false&allowPublicKeyRetrieval=true";
        String driverName = "com.mysql.cj.jdbc.Driver";
        String dbUser = "root";
        String dbPassword = "root";
        String userIdParam = request.getParameter("userId");
        int userId;
        List<Post> userPosts = new ArrayList<>();
        User profileUser = null;
        // List<Post> userPosts = new ArrayList<>(); // Remove posts for now
        String errorMessage = null;

        if (userIdParam == null || userIdParam.trim().isEmpty()) {
            errorMessage = "User ID is required to view a profile.";
        } else {
            try {
                userId = Integer.parseInt(userIdParam);
                Connection con = null;
                PreparedStatement pstmtUser = null;
                ResultSet rsUser = null;
                PreparedStatement pstmtPosts = null; // Need this
                ResultSet rsPosts = null;
                // PreparedStatement pstmtPosts = null; // Remove posts
                // ResultSet rsPosts = null; // Remove posts

                try {
                    Class.forName(driverName);
                    con = DriverManager.getConnection(databaseURL, dbUser, dbPassword);

                    // Fetch User Details
                    String sqlUser = "SELECT id, fname, lname, email, bio, profile_picture_filename, created_at FROM users WHERE id = ?";
                    pstmtUser = con.prepareStatement(sqlUser);
                    pstmtUser.setInt(1, userId);
                    rsUser = pstmtUser.executeQuery();

                    if (rsUser.next()) {
                        profileUser = new User();
                        profileUser.setId(rsUser.getInt("id"));
                        profileUser.setFname(rsUser.getString("fname"));
                        profileUser.setLname(rsUser.getString("lname"));
                        profileUser.setEmail(rsUser.getString("email"));
                        profileUser.setBio(rsUser.getString("bio"));
                        profileUser.setProfilePictureFilename(rsUser.getString("profile_picture_filename"));
                        Timestamp createdAtTimestamp = rsUser.getTimestamp("created_at");
                        profileUser.setCreatedAt(createdAtTimestamp != null ? createdAtTimestamp : null);

                        // 2. Fetch User's Posts (ID and Title needed)
                        // Fetching full post object is okay, JSP will only use title/id
                        String sqlPosts = "SELECT id, author_id, title, content, created_at, updated_at FROM post WHERE author_id = ? ORDER BY created_at DESC";
                        pstmtPosts = con.prepareStatement(sqlPosts);
                        pstmtPosts.setInt(1, userId);
                        rsPosts = pstmtPosts.executeQuery();

                        while (rsPosts.next()) {
                            Post post = new Post();
                            post.setId(rsPosts.getInt("id"));
                            // post.setAuthorId(rsPosts.getInt("author_id")); // Not strictly needed for display
                            post.setTitle(rsPosts.getString("title"));
                            // post.setContent(rsPosts.getString("content")); // Not needed for display
                            // post.setCreatedAt(rsPosts.getTimestamp("created_at")); // Not needed for display
                            // post.setUpdatedAt(rsPosts.getTimestamp("updated_at")); // Not needed for display
                            userPosts.add(post);
                        }
                    } else {
                        errorMessage = "User profile not found.";
                    }

                } catch (SQLException e) {
                    errorMessage = "Database error occurred while fetching profile data.";
                    System.err.println("UserProfileServlet SQL Error: " + e.getMessage());
                    e.printStackTrace(System.err);
                } catch (Exception e) { // Catch ClassNotFound etc.
                    errorMessage = "Server configuration error.";
                    System.err.println("UserProfileServlet Error: " + e.getMessage());
                    e.printStackTrace(System.err);
                } finally {
                    // Close resources
                    // try { if (rsPosts != null) rsPosts.close(); } catch (SQLException e) { e.printStackTrace(System.err); } // Remove
                    // try { if (pstmtPosts != null) pstmtPosts.close(); } catch (SQLException e) { e.printStackTrace(System.err); } // Remove
                    try {
                        if (rsUser != null) {
                            rsUser.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace(System.err);
                    }
                    try {
                        if (pstmtUser != null) {
                            pstmtUser.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace(System.err);
                    }
                    try {
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace(System.err);
                    }
                }

            } catch (NumberFormatException e) {
                errorMessage = "Invalid User ID format.";
            }
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        request.setAttribute("profileUser", profileUser);
        request.setAttribute("userPosts", userPosts);
        // request.setAttribute("userPosts", userPosts); // Remove posts

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user-profile.jsp");
        dispatcher.forward(request, response);
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
