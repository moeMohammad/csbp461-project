package model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String bio;
    private LocalDateTime createdAt;

    public User() {
    }

    public User(int id, String fname, String lname, String email,String bio, LocalDateTime createdAt) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.bio = bio;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt;
    }

    // Optional: toString() method for debugging
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", fname='" + fname + '\'' +
               ", lname='" + lname + '\'' +
               ", email='" + email + '\'' +
               ", bio='" + bio + '\'' +
               ", createdAt=" + createdAt + 
               '}';
    }
}