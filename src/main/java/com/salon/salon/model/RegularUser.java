package com.salon.salon.model;

public class RegularUser extends User {

    private String membershipType; // "SILVER" or "GOLD"

    // Constructor
    public RegularUser(String userId, String username, String email,
                       String password, String phone, String membershipType) {
        super(userId, username, email, password, phone, "REGULAR");
        this.membershipType = membershipType;
    }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    // Polymorphism - overrides parent getDisplayInfo()
    @Override
    public String getDisplayInfo() {
        return "Customer: " + getUsername() + " | Email: " + getEmail()
                + " | Membership: " + membershipType;
    }

    // Override toFileString to include membership
    @Override
    public String toFileString() {
        return super.toFileString() + "," + membershipType;
    }
}