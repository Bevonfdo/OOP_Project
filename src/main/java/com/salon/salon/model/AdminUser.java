package com.salon.salon.model;

public class AdminUser extends User {

    private String designation; // e.g. "Manager", "Receptionist"

    // Constructor
    public AdminUser(String userId, String username, String email,
                     String password, String phone, String designation) {
        super(userId, username, email, password, phone, "ADMIN");
        this.designation = designation;
    }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Polymorphism - overrides parent getDisplayInfo()
    @Override
    public String getDisplayInfo() {
        return "Admin: " + getUsername() + " | Email: " + getEmail()
                + " | Designation: " + designation;
    }

    // Override toFileString to include designation
    @Override
    public String toFileString() {
        return super.toFileString() + "," + designation;
    }
}