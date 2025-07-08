package com.example.ticket.service.impl;

import com.example.ticket.model.Seat;
import com.example.ticket.repository.ISeatRepository;
import com.example.ticket.service.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService implements ISeatService {
    private final ISeatRepository seatRepository;

    @Override
    public Seat findById(Long id) {
        return seatRepository.findById(id).orElse(null);
    }

    @Override
    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public void deleteById(Long id) {
        seatRepository.deleteById(id);
    }
} 