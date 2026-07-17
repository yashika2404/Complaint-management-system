package com.complaint.controller;

import com.complaint.service.ComplaintService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StaffController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/staff/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long staffId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole");

        if (staffId == null || !"STAFF".equals(role)) {
            return "redirect:/login";
        }

        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("complaints", complaintService.getComplaintsByStaff(staffId));
        return "staff-dashboard"; // templates/staff-dashboard.html
    }

    @PostMapping("/staff/complaint/{id}/status")
    public String updateStatus(@PathVariable Long id,
                                @RequestParam String status,
                                HttpSession session) {

        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }

        complaintService.updateStatus(id, status);
        return "redirect:/staff/dashboard";
    }
}
