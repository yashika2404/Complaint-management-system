package com.complaint.service;

import com.complaint.entity.Complaint;
import com.complaint.entity.User;
import com.complaint.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint createComplaint(String title, String description, String category, String location, User user) {
        Complaint complaint = new Complaint();
        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setCategory(category);
        complaint.setLocation(location);
        complaint.setUser(user);
        complaint.setStatus("CREATED");
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setPriority(autoAssignPriority(category)); // category dekh kar priority khud set ho jayegi
        return complaintRepository.save(complaint);
    }

    // Simple rule-based priority - category ke hisaab se
    private String autoAssignPriority(String category) {
        if (category.equals("PLUMBING") || category.equals("SECURITY")) {
            return "HIGH";
        } else if (category.equals("CLEANING") || category.equals("FURNITURE")) {
            return "LOW";
        } else {
            return "MEDIUM"; // ELECTRICAL, INTERNET, OTHER
        }
    }

    public List<Complaint> getComplaintsByUser(Long userId) {
        return complaintRepository.findByUserId(userId);
    }

    public List<Complaint> getComplaintsByStaff(Long staffId) {
        return complaintRepository.findByStaffId(staffId);
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public void assignStaff(Long complaintId, Long staffId, User staff) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow();
        complaint.setStaff(staff);
        complaint.setStatus("ASSIGNED");
        complaintRepository.save(complaint);
    }

    public void updateStatus(Long complaintId, String status) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow();
        complaint.setStatus(status);
        complaintRepository.save(complaint);
    }
}
