package servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String databaseURL = "jdbc:mysql://localhost:3306/projectDB?useSSL=false&allowPublicKeyRetrieval=true";
        String driverName = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String dbpassword = "root";
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String destination = "login.jsp";
        String errorMessage = null;
        User loggedInUser = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            errorMessage = "Email and password are required.";
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
            dispatcher.forward(request, response);
            return;
        }

        try {
            String submittedHashedPassword = hashPassword(password);
            Class.forName(driverName).newInstance();
            con = DriverManager.getConnection(databaseURL, user, dbpassword);

            String sql = "SELECT id, fname, lname, email, password, created_at FROM users WHERE email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email.trim().toLowerCase());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");
                if (submittedHashedPassword.equals(storedHashedPassword)) {
                    int id = rs.getInt("id");
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    String storedEmail = rs.getString("email");
                    Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
                    LocalDateTime createdAt = createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null;
                    loggedInUser = new User();
                    loggedInUser.setId(id);
                    loggedInUser.setFname(fname);
                    loggedInUser.setLname(lname);
                    loggedInUser.setEmail(storedEmail);
                    loggedInUser.setCreatedAt(createdAt);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", loggedInUser);
                    destination = "index.jsp";
                    System.out.println("User logged in successfully: " + loggedInUser.getEmail());

                } else {
                    errorMessage = "Invalid email or password.";
                    System.out.println("Login failed: Incorrect password for email - " + email);
                }
            } else {
                errorMessage = "Invalid email or password.";
                System.out.println("Login failed: Email not found - " + email);
            }
        } catch (Exception e) {
            errorMessage = "An unexpected error occurred during login.";
            System.err.println("Unexpected error during login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    /**
     * Hashes a password using SHA-256. Should be identical to the one in
     * RegisterServlet. NOTE: For production, use a stronger, salted hashing
     * algorithm like BCrypt or Argon2.
     *
     * @param password The plain text password.
     * @return The hashed password as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not
     * available.
     */
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    /**
     * Helper method to convert byte array to hexadecimal string. Should be
     * identical to the one in RegisterServlet.
     *
     * @param hash The byte array hash.
     * @return The hexadecimal string representation.
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
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
