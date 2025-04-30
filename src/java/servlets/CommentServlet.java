package servlets;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import java.io.PrintWriter;
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
import model.Comment;
import model.Post;
import model.User;

/**
 *
 * @author husse
 */
@WebServlet(urlPatterns = {"/CommentServlet"})
public class CommentServlet extends HttpServlet {

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
        String databaseURL = "jdbc:mysql://localhost:3306/projectDB?useSSL=false&allowPublicKeyRetrieval=true";
        String driverName = "com.mysql.cj.jdbc.Driver";
        String dbUser = "root";
        String password = "root";
        // get params
        String content = request.getParameter("content");
int postId = Integer.parseInt(request.getParameter("post_id"));

        // make sure we're logged in
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // user is not logged in, redirect to login page
            response.sendRedirect("login.jsp");
            return;
        }
        System.out.println("user" + user.toString());

        int authorId = user.getId();

        System.out.println("content: " + content);
        System.out.println("postId: " + postId);
        System.out.println("authorId: " + authorId);

//        // create the post
//        Comment comment = new Comment(authorId, postId, content);

        Connection conn;
        PreparedStatement stmt;

        try {
            Class.forName(driverName).newInstance();
            conn = DriverManager.getConnection(databaseURL, dbUser, password);

            String sql = "INSERT INTO comment (post_id, author_id, content) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, postId);
            stmt.setInt(2, authorId);
            stmt.setString(3, content);
            stmt.executeUpdate();

            conn.close();

            // redirect
            response.sendRedirect("ViewPost?id=" + postId);

        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            e.printStackTrace();
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
