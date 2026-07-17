package com.complaint.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data // ye annotation Lombok ka hai - khud getters/setters/toString bana deta hai, likhne ki zaroorat nahi
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password; // abhi plain text hai, seekhne ke baad BCrypt add karna

    // ROLE ek simple String hai: "USER", "STAFF", "ADMIN"
    private String role;
}
