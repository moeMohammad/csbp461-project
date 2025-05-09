/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Comment;
import model.Post;

/**
 *
 * @author Aziz
 */
@WebServlet(name = "ViewPost", urlPatterns = {"/ViewPost"})
public class ViewPost extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads" + File.separator + "profile_pics"; // <-- ADDED    
    private final String databaseURL = "jdbc:mysql://localhost:3306/projectDB?useSSL=false&allowPublicKeyRetrieval=true";
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private final String user = "root";
    private final String password = "root";

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

        int postId = Integer.parseInt(request.getParameter("id"));
        Post post = new Post();
        List<Comment> comments = new ArrayList<Comment>();

        try {
            Class.forName(driverName).newInstance();
            Connection conn = DriverManager.getConnection(databaseURL, user, password);
            PreparedStatement stmt;

            String sql = "SELECT post.*, users.fname as fname, users.lname as lname, users.profile_picture_filename as pfp FROM post INNER JOIN users ON post.author_id = users.id WHERE post.id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int authorId = rs.getInt("author_id");
                post.setId(postId);
                post.setAuthorId(authorId);
                if (rs.getString("lname") == null) {
                    post.setAuthor(rs.getString("fname"));
                } else {
                    post.setAuthor(rs.getString("fname") + " " + rs.getString("lname"));
                }
                String postAuthorPfpFilename = rs.getString("pfp");
                String verifiedPostAuthorPfp = verifyProfilePictureExists(request, postAuthorPfpFilename);
                post.setPfp(verifiedPostAuthorPfp);
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
            }

            String sqlComment = "SELECT comment.*, users.fname as fname, users.lname as lname, users.profile_picture_filename as pfp FROM comment INNER JOIN users ON comment.author_id = users.id WHERE comment.post_id = ?";
            PreparedStatement stmtComments = conn.prepareStatement(sqlComment);
            stmtComments.setInt(1, postId);
            ResultSet rsComments = stmtComments.executeQuery();

            while (rsComments.next()) {

                System.out.println("author pfp: " + rsComments.getString("pfp"));
                Comment comment = new Comment();
                comment.setId(rsComments.getInt("id"));
                comment.setAuthorId(rsComments.getInt("author_id"));
                if (rsComments.getString("lname") == null) {
                    comment.setAuthor(rsComments.getString("fname"));
                } else {
                    comment.setAuthor(rsComments.getString("fname") + " " + rsComments.getString("lname"));
                }
                String commentAuthorPfpFilename = rsComments.getString("pfp");
                String verifiedCommentAuthorPfp = verifyProfilePictureExists(request, commentAuthorPfpFilename);
                comment.setPfp(verifiedCommentAuthorPfp);
                comment.setContent(rsComments.getString("content"));
                comment.setCreatedAt(rsComments.getTimestamp("created_at"));
                comments.add(comment);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("post", post);
        request.setAttribute("comments", comments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("post.jsp");
        dispatcher.forward(request, response);
    }

    private String verifyProfilePictureExists(HttpServletRequest request, String filenameFromDb) {
        if (filenameFromDb == null || filenameFromDb.trim().isEmpty()) {
            return "default.png"; // No filename to check
        }

        String applicationPath = request.getServletContext().getRealPath("");
        String fullFilePath = applicationPath + File.separator + UPLOAD_DIR + File.separator + filenameFromDb;
        File profilePicFile = new File(fullFilePath);

        if (!profilePicFile.exists() || !profilePicFile.isFile()) {
            System.out.println(getClass().getSimpleName() + ": Profile pic file not found on disk: " + fullFilePath + ". Using default.");
            return "default.png"; // File doesn't exist, return null
        } else {
            // System.out.println(getClass().getSimpleName() + ": Profile pic file found: " + fullFilePath); // Optional: Log success
            return filenameFromDb; // File exists, return original filename
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
