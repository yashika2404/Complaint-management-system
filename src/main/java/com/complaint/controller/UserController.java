package com.complaint.controller;

import com.complaint.entity.User;
import com.complaint.service.ComplaintService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/user/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Login check - agar session mein userId nahi hai to login page pe bhej do
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        var complaints = complaintService.getComplaintsByUser(userId);

        long resolvedCount = complaints.stream()
                .filter(c -> c.getStatus().equals("RESOLVED") || c.getStatus().equals("CLOSED"))
                .count();
        long pendingCount = complaints.size() - resolvedCount;

        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("complaints", complaints);
        model.addAttribute("totalCount", complaints.size());
        model.addAttribute("resolvedCount", resolvedCount);
        model.addAttribute("pendingCount", pendingCount);
        return "user-dashboard"; // templates/user-dashboard.html
    }

    @PostMapping("/user/complaint/new")
    public String createComplaint(@RequestParam String title,
                                   @RequestParam String description,
                                   @RequestParam String category,
                                   @RequestParam String location,
                                   HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = new User();
        user.setId(userId);

        complaintService.createComplaint(title, description, category, location, user);
        return "redirect:/user/dashboard";
    }
}