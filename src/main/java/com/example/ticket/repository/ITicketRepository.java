package com.example.ticket.repository;

import com.example.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
    // Thêm các method custom nếu cần
} 