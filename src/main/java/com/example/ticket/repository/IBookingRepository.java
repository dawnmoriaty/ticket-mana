package com.example.ticket.repository;

import com.example.ticket.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
    // Thêm các method custom nếu cần
} 