package com.example.ticket.service;

import com.example.ticket.model.Showtime;
import java.util.List;

public interface IShowtimeService {
    Showtime findById(Long id);
    List<Showtime> findAll();
    Showtime save(Showtime showtime);
    void deleteById(Long id);
} 