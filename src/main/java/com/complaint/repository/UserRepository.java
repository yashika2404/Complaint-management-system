package com.complaint.repository;

import com.complaint.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// JpaRepository extend karne se save(), findAll(), findById() jaise methods free milte hain
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);
}
