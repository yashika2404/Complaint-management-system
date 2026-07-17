package com.complaint.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Data
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String category; // ELECTRICAL, PLUMBING, CLEANING, INTERNET, FURNITURE, SECURITY, OTHER

    private String priority; // LOW, MEDIUM, HIGH -> category ke hisaab se khud set hoga

    private String status; // CREATED, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED

    private String location;

    private LocalDateTime createdAt;

    // Complaint kisne banayi
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Complaint kis staff ko assign hui (shuru mein null rahega)
    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User staff;
}
