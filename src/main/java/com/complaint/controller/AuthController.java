package com.complaint.controller;

import com.complaint.entity.User;
import com.complaint.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Root URL -> login page pe bhej do
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // templates/register.html dikhega
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String role,
                            Model model) {

        if (userService.emailExists(email)) {
            model.addAttribute("error", "Ye email already registered hai. Login karo.");
            return "register";
        }

        userService.register(name, email, password, role);
        return "redirect:/login?registered=true";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // templates/login.html dikhega
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                         @RequestParam String password,
                         HttpSession session,
                         Model model) {

        User user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("error", "Email ya password galat hai.");
            return "login";
        }

        // Session mein user data daal do - jab tak session hai, login maana jayega
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userRole", user.getRole());

        // Role ke hisaab se sahi dashboard pe bhej do
        if (user.getRole().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        } else if (user.getRole().equals("STAFF")) {
            return "redirect:/staff/dashboard";
        } else {
            return "redirect:/user/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // session khatam -> user logged out
        return "redirect:/login";
    }
}
