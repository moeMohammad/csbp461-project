package servlets;

import model.User;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String databaseURL = "jdbc:mysql://localhost:3306/projectDB?useSSL=false&allowPublicKeyRetrieval=true";
        String driverName = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String dbpassword = "root";
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");
        String destination = "register.jsp";
        String errorMessage = null;
        if (fname == null || fname.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()) {
            errorMessage = "Please fill in all required fields (*).";
        } else if (!password.equals(confirmPassword)) {
            errorMessage = "Passwords do not match.";
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
            dispatcher.forward(request, response);
            return;
        }
        Connection con;
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;
        User newUser = null;
        int generatedId = -1;
Timestamp creationTime = new Timestamp(System.currentTimeMillis());        try {
            String hashedPassword = hashPassword(password);
            Class.forName(driverName).newInstance();
            con = DriverManager.getConnection(databaseURL, user, dbpassword);
            String sql = "INSERT INTO users (fname, lname, email, password, profile_picture_filename, created_at) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, fname.trim());
            if (lname != null && !lname.trim().isEmpty()) {
                pstmt.setString(2, lname.trim());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }
            pstmt.setString(3, email.trim().toLowerCase());
            pstmt.setString(4, hashedPassword);
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setTimestamp(6, creationTime);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
                if (generatedId != -1) {
                    newUser = new User();
                    newUser.setId(generatedId);
                    newUser.setFname(fname.trim());
                    if (lname != null && !lname.trim().isEmpty()) {
                        newUser.setLname(lname.trim());
                    }
                    newUser.setEmail(email.trim().toLowerCase());
                    newUser.setCreatedAt(creationTime);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", newUser);
                    destination = "index.jsp";
                    System.out.println("User registered successfully: " + newUser.getEmail() + " with ID: " + newUser.getId());

                } else {
                    errorMessage = "Registration successful, but failed to retrieve user ID.";
                    System.err.println("Registration successful but failed to retrieve generated ID for email: " + email);
                }

            } else {
                errorMessage = "Registration failed. Please try again.";
                System.err.println("executeUpdate returned 0 rows affected for email: " + email);
            }
            con.close();
        } catch (Exception e) {
            errorMessage = "An unexpected error occurred during registration.";
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        if (errorMessage != null && newUser == null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

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
