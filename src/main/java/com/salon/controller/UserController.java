package com.salon.controller;

import com.salon.model.AdminUser;
import com.salon.model.RegularUser;
import com.salon.model.User;
import com.salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // =====================
    // SHOW REGISTER PAGE
    // =====================
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // loads register.html
    }

    // =====================
    // CREATE - Register User
    // =====================
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String userId,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String role,
            @RequestParam(required = false) String membershipType,
            @RequestParam(required = false) String designation,
            Model model) {

        // Check if username already exists
        if (userService.usernameExists(username)) {
            model.addAttribute("error",
                    "Username already exists! Please choose another.");
            return "register";
        }

        // Create correct user type based on role
        User user;
        if (role.equals("ADMIN")) {
            user = new AdminUser(userId, username, email,
                    password, phone,
                    designation != null ? designation : "Staff");
        } else {
            user = new RegularUser(userId, username, email,
                    password, phone,
                    membershipType != null ? membershipType : "SILVER");
        }

        boolean success = userService.registerUser(user);
        if (success) {
            model.addAttribute("success",
                    "User registered successfully!");
        } else {
            model.addAttribute("error",
                    "Registration failed! Please try again.");
        }
        return "register";
    }

    // =====================
    // SHOW LOGIN PAGE
    // =====================
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // loads login.html
    }

    // =====================
    // READ - Login User
    // =====================
    @PostMapping("/login")
    public String loginUser(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                // Redirect based on role
                if (user.getRole().equals("ADMIN")) {
                    model.addAttribute("user", user);
                    return "redirect:/users";
                } else {
                    model.addAttribute("user", user);
                    return "redirect:/profile/" + user.getUserId();
                }
            }
        }
        model.addAttribute("error",
                "Invalid username or password!");
        return "login";
    }

    // =====================
    // READ - Show All Users (Admin only)
    // =====================
    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList"; // loads userList.html
    }

    // =====================
    // READ - Show User Profile
    // =====================
    @GetMapping("/profile/{userId}")
    public String showProfile(
            @PathVariable String userId,
            Model model) {
        User user = userService.getUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "profile"; // loads profile.html
        }
        return "redirect:/login";
    }

    // =====================
    // SHOW UPDATE PAGE
    // =====================
    @GetMapping("/update/{userId}")
    public String showUpdatePage(
            @PathVariable String userId,
            Model model) {
        User user = userService.getUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "updateUser"; // loads updateUser.html
        }
        return "redirect:/users";
    }

    // =====================
    // UPDATE - Update User
    // =====================
    @PostMapping("/update")
    public String updateUser(
            @RequestParam String userId,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password,
            Model model) {

        boolean success = userService.updateUser(
                userId, email, phone, password);

        if (success) {
            model.addAttribute("success",
                    "User updated successfully!");
        } else {
            model.addAttribute("error",
                    "User not found!");
        }
        return "redirect:/users";
    }

    // =====================
    // DELETE - Delete User
    // =====================
    @GetMapping("/delete/{userId}")
    public String deleteUser(
            @PathVariable String userId,
            Model model) {

        boolean success = userService.deleteUser(userId);
        if (success) {
            model.addAttribute("success",
                    "User deleted successfully!");
        }
        return "redirect:/users";
    }
}
