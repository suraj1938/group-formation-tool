package com.app.group15.UserManagement;

public enum UserRole {
    STUDENT("STUDENT"), INSTRUCTOR("INSTRUCTOR"), TA("TA"), GUEST("GUEST"), ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
