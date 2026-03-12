package com.salon.model;

public class User {

    // Encapsulation - private fields
    private String userId;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String role; // "ADMIN" or "REGULAR"

    // Constructor
    public User(String userId, String username, String email,
                String password, String phone, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    // Default constructor
    public User() {}

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Convert user to text line for saving in users.txt
    public String toFileString() {
        return userId + "," + username + "," + email + ","
                + password + "," + phone + "," + role;
    }

    // Display user info - will be overridden (Polymorphism)
    public String getDisplayInfo() {
        return "User: " + username + " | Email: " + email + " | Phone: " + phone;
    }
}
