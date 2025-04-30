/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.User;

/**
 *
 * @author mohammad
 */
@WebServlet(name = "ProfileUpdateServlet", urlPatterns = {"/ProfileUpdateServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class ProfileUpdateServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads" + File.separator + "profile_pics";

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
        final String databaseURL = "jdbc:mysql://localhost:3306/projectDB?useSSL=false&allowPublicKeyRetrieval=true";
        final String driverName = "com.mysql.cj.jdbc.Driver";
        final String dbUser = "root";
        final String dbPassword = "root";
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        String successMessage = null;
        String destination = "/edit-profile.jsp"; // Forward back to edit page

        // --- Security Check: User must be logged in ---
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        User currentUser = (User) session.getAttribute("user");
        int userId = currentUser.getId();
        String oldProfilePicFilename = currentUser.getProfilePictureFilename(); // Get old filename for potential deletion
        // --- End Security Check ---

        // --- Get Form Data ---
        String bio = request.getParameter("bio");
        Part filePart = request.getPart("profile_picture");
        String newUploadedFileName = null; // Holds the *new* unique filename if upload succeeds
        boolean fileUploadedSuccessfully = false;
        boolean attemptUpload = false; // Flag to know if user selected a file
        // --- End Get Form Data ---

        // --- File Upload Handling ---
        if (filePart != null && filePart.getSize() > 0) {
            attemptUpload = true; // User tried to upload
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            if (originalFileName != null && !originalFileName.isEmpty()) {
                String fileExtension = "";
                int i = originalFileName.lastIndexOf('.');
                if (i > 0) {
                    fileExtension = originalFileName.substring(i).toLowerCase(); // Use lowercase extension
                }

                // Basic validation for image types
                if (!fileExtension.matches("\\.(jpg|jpeg|png|gif)$")) {
                    errorMessage = "Invalid file type. Only JPG, PNG, GIF allowed.";
                } else {
                    // Generate unique name and attempt save
                    newUploadedFileName = UUID.randomUUID().toString() + fileExtension;
                    String applicationPath = request.getServletContext().getRealPath("");
                    String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
                    File uploadDir = new File(uploadFilePath);
                    if (!uploadDir.exists()) {
                        if (!uploadDir.mkdirs()) {
                            System.err.println("ProfileUpdateServlet: Could not create upload directory: " + uploadFilePath);
                            errorMessage = "Server error: Could not prepare for file upload.";
                            newUploadedFileName = null; // Ensure filename isn't used
                        }
                    }

                    // Proceed with saving only if directory exists/was created and no previous error
                    if (errorMessage == null) {
                        try (InputStream fileContent = filePart.getInputStream()) {
                            File fileToSave = new File(uploadFilePath + File.separator + newUploadedFileName);
                            Files.copy(fileContent, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            fileUploadedSuccessfully = true; // Mark as successful upload
                            System.out.println("ProfileUpdateServlet: New profile pic saved: " + newUploadedFileName + " for user ID: " + userId);
                        } catch (IOException e) {
                            errorMessage = "Error saving uploaded profile picture. Please try again.";
                            System.err.println("ProfileUpdateServlet: Error writing file: " + e.getMessage());
                            e.printStackTrace(System.err);
                            newUploadedFileName = null; // Ensure filename isn't used if save failed
                            fileUploadedSuccessfully = false;
                        }
                    }
                }
            } else {
                // User selected the file input but didn't choose a file, or filename was weird
                attemptUpload = false; // Treat as no upload attempt
            }
        }
        // --- End File Upload Handling ---

        // --- Database Update ---
        // Proceed if there wasn't a critical file error (like invalid type)
        if (errorMessage == null || !attemptUpload || fileUploadedSuccessfully) {
            Connection con = null;
            PreparedStatement pstmt = null;
            boolean dbUpdateSuccessful = false;

            try {
                Class.forName(driverName);
                con = DriverManager.getConnection(databaseURL, dbUser, dbPassword);

                // Update bio and conditionally update filename
                String sql = "UPDATE users SET bio = ?, profile_picture_filename = ? WHERE id = ?";
                pstmt = con.prepareStatement(sql);

                pstmt.setString(1, bio != null ? bio.trim() : null); // Set bio

                // Determine which filename to save
                String filenameToSave = oldProfilePicFilename; // Default to old one
                if (fileUploadedSuccessfully) {
                    filenameToSave = newUploadedFileName; // Use new one if upload worked
                }
                // If user didn't attempt upload, filenameToSave remains the old one

                if (filenameToSave != null) {
                    pstmt.setString(2, filenameToSave);
                } else {
                    pstmt.setNull(2, java.sql.Types.VARCHAR); // Set to NULL if no old/new pic
                }

                pstmt.setInt(3, userId); // Set user ID for WHERE clause

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    dbUpdateSuccessful = true;
                    successMessage = "Profile updated successfully!";
                    System.out.println("ProfileUpdateServlet: Profile updated for user ID: " + userId + ". New Pic: " + filenameToSave);

                    // --- Update User object in Session ---
                    currentUser.setBio(bio != null ? bio.trim() : null);
                    currentUser.setProfilePictureFilename(filenameToSave); // Update with the filename actually saved
                    session.setAttribute("user", currentUser); // Put updated object back
                    // --- End Session Update ---

                    // --- Delete Old Picture (Optional but Recommended) ---
                    if (fileUploadedSuccessfully && oldProfilePicFilename != null && !oldProfilePicFilename.equals(newUploadedFileName)) {
                        deleteOldProfilePicture(request, oldProfilePicFilename);
                    }
                    // --- End Delete Old Picture ---

                } else {
                    // This might happen if the user ID somehow doesn't exist, though unlikely if they are logged in
                    errorMessage = "Failed to update profile in database (user not found?).";
                    System.err.println("ProfileUpdateServlet: DB update affected 0 rows for user ID: " + userId);
                }

            } catch (SQLException e) {
                errorMessage = "Database error occurred while updating profile.";
                System.err.println("ProfileUpdateServlet SQL Error: " + e.getMessage());
                e.printStackTrace(System.err);
            } catch (Exception e) { // Catch ClassNotFound etc.
                errorMessage = "Server configuration error during profile update.";
                System.err.println("ProfileUpdateServlet Error: " + e.getMessage());
                e.printStackTrace(System.err);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
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
        }
        // --- End Database Update ---

        // --- Forward back to Edit Page with message ---
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }
        // Overwrite success message if a later error occurred (e.g., DB error after successful upload)
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            request.removeAttribute("successMessage"); // Remove success if error happened
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }

    // Helper method to delete the old picture file
    private void deleteOldProfilePicture(HttpServletRequest request, String oldFilename) {
        if (oldFilename == null || oldFilename.isEmpty() || oldFilename.equals("default.png")) {
            return; // Don't delete null, empty, or the default
        }
        try {
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
            File oldFile = new File(uploadFilePath + File.separator + oldFilename);
            if (oldFile.exists()) {
                if (oldFile.delete()) {
                    System.out.println("ProfileUpdateServlet: Deleted old profile pic: " + oldFilename);
                } else {
                    System.err.println("ProfileUpdateServlet: Failed to delete old profile pic: " + oldFilename);
                }
            }
        } catch (Exception e) {
            System.err.println("ProfileUpdateServlet: Error deleting old profile pic '" + oldFilename + "': " + e.getMessage());
            // Log error, but don't necessarily fail the whole update process
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
