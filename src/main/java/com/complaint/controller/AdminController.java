package com.complaint.controller;

import com.complaint.entity.User;
import com.complaint.service.ComplaintService;
import com.complaint.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");

        if (session.getAttribute("userId") == null || !"ADMIN".equals(role)) {
            return "redirect:/login";
        }

        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("complaints", complaintService.getAllComplaints());
        model.addAttribute("staffList", userService.getStaffList());
        return "admin-dashboard"; // templates/admin-dashboard.html
    }

    @PostMapping("/admin/complaint/{id}/assign")
    public String assignStaff(@PathVariable Long id,
                               @RequestParam Long staffId,
                               HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        User staff = new User();
        staff.setId(staffId);

        complaintService.assignStaff(id, staffId, staff);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/users")
    public String viewUsers(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("users", userService.getAllUsers());
        return "admin-users"; // templates/admin-users.html
    }
}
