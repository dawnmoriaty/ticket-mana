package com.example.ticket.repository;

import com.example.ticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    // Thêm các method custom nếu cần
} 