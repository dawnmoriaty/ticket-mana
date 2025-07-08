package com.example.ticket.service;

import com.example.ticket.model.Seat;
import java.util.List;

public interface ISeatService {
    Seat findById(Long id);
    List<Seat> findAll();
    Seat save(Seat seat);
    void deleteById(Long id);
} 