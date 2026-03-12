package com.salon.service;

import com.salon.model.User;
import com.salon.model.RegularUser;
import com.salon.model.AdminUser;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService{

    // Path to users.txt file
    private static final String FILE_PATH =
            "src/main/resources/data/users.txt";

    // =====================
    // CREATE - Register User
    // =====================
    public boolean registerUser(User user) {
        try {
            FileWriter fw = new FileWriter(FILE_PATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(user.toFileString());
            bw.newLine();
            bw.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    // =====================
    // READ - Get All Users
    // =====================
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FILE_PATH);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    User user = parseUser(line);
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    // =====================
    // READ - Search User by ID
    // =====================
    public User getUserById(String userId) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    // =====================
    // UPDATE - Update User
    // =====================
    public boolean updateUser(String userId, String newEmail,
                              String newPhone, String newPassword) {
        List<User> users = getAllUsers();
        boolean found = false;

        try {
            FileWriter fw = new FileWriter(FILE_PATH, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (User user : users) {
                if (user.getUserId().equals(userId)) {
                    user.setEmail(newEmail);
                    user.setPhone(newPhone);
                    user.setPassword(newPassword);
                    found = true;
                }
                bw.write(user.toFileString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
        return found;
    }

    // =====================
    // DELETE - Delete User
    // =====================
    public boolean deleteUser(String userId) {
        List<User> users = getAllUsers();
        boolean found = false;

        try {
            FileWriter fw = new FileWriter(FILE_PATH, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (User user : users) {
                if (!user.getUserId().equals(userId)) {
                    bw.write(user.toFileString());
                    bw.newLine();
                } else {
                    found = true;
                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
        return found;
    }

    // =====================
    // HELPER - Parse line from users.txt to User object
    // =====================
    private User parseUser(String line) {
        String[] parts = line.split(",");
        if (parts.length < 6) return null;

        String userId = parts[0];
        String username = parts[1];
        String email = parts[2];
        String password = parts[3];
        String phone = parts[4];
        String role = parts[5];

        if (role.equals("ADMIN")) {
            String designation = parts.length > 6 ? parts[6] : "Staff";
            return new AdminUser(userId, username, email,
                    password, phone, designation);
        } else {
            String membership = parts.length > 6 ? parts[6] : "SILVER";
            return new RegularUser(userId, username, email,
                    password, phone, membership);
        }
    }

    // =====================
    // HELPER - Check if username already exists
    // =====================
    public boolean usernameExists(String username) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
