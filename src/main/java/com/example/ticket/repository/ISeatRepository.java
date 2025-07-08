package com.example.ticket.repository;

import com.example.ticket.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeatRepository extends JpaRepository<Seat, Long> {
    // Thêm các method custom nếu cần
} 