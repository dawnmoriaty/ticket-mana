package com.example.ticket.repository;

import com.example.ticket.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShowtimeRepository extends JpaRepository<Showtime, Long> {
    // Thêm các method custom nếu cần
} 