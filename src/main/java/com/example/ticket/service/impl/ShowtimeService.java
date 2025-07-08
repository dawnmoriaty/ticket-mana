package com.example.ticket.service.impl;

import com.example.ticket.model.Showtime;
import com.example.ticket.repository.IShowtimeRepository;
import com.example.ticket.service.IShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeService implements IShowtimeService {
    private final IShowtimeRepository showtimeRepository;

    @Override
    public Showtime findById(Long id) {
        return showtimeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Showtime> findAll() {
        return showtimeRepository.findAll();
    }

    @Override
    public Showtime save(Showtime showtime) {
        return showtimeRepository.save(showtime);
    }

    @Override
    public void deleteById(Long id) {
        showtimeRepository.deleteById(id);
    }
} 