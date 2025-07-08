package com.example.ticket.service;

import com.example.ticket.model.Ticket;
import java.util.List;

public interface ITicketService {
    Ticket findById(Long id);
    List<Ticket> findAll();
    Ticket save(Ticket ticket);
    void deleteById(Long id);
} 