package model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String bio;
    private String profilePictureFilename; // <-- ADDED FIELD
    private LocalDateTime createdAt;

    public User() {
    }

    // Optional: Update constructor if needed
    public User(int id, String fname, String lname, String email, String bio, String profilePictureFilename, LocalDateTime createdAt) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.bio = bio;
        this.profilePictureFilename = profilePictureFilename; // <-- ADDED
        this.createdAt = createdAt;
    }

    // --- Getters and Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFname() { return fname; }
    public void setFname(String fname) { this.fname = fname; }

    public String getLname() { return lname; }
    public void setLname(String lname) { this.lname = lname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    // --- ADDED GETTER/SETTER ---
    public String getProfilePictureFilename() { return profilePictureFilename; }
    public void setProfilePictureFilename(String profilePictureFilename) { this.profilePictureFilename = profilePictureFilename; }
    // --- END ADDED ---

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", fname='" + fname + '\'' +
               ", lname='" + lname + '\'' +
               ", email='" + email + '\'' +
               ", bio='" + bio + '\'' +
               ", profilePictureFilename='" + profilePictureFilename + '\'' + // <-- ADDED
               ", createdAt=" + createdAt +
               '}';
    }
}
