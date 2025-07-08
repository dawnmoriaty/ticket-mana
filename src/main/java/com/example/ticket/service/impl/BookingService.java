package com.example.ticket.service.impl;

import com.example.ticket.enums.BookingStatus;
import com.example.ticket.model.*;
import com.example.ticket.repository.*;
import com.example.ticket.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final IBookingRepository bookingRepository;
    private final IUserRepository userRepository;
    private final IShowtimeRepository showtimeRepository;
    private final ISeatRepository seatRepository;
    private final ITicketRepository ticketRepository;

    @Override
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Booking bookTickets(Long userId, Long showtimeId, List<Long> seatIds) {
        if (seatIds == null || seatIds.isEmpty() || seatIds.size() > 4) {
            throw new IllegalArgumentException("Số lượng vé mỗi lần đặt phải từ 1 đến 4");
        }
        // Lấy thông tin ghế
        List<Seat> seats = seatRepository.findAllById(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new IllegalArgumentException("Một hoặc nhiều ghế không tồn tại");
        }
        // Kiểm tra cùng phòng, cùng suất chiếu
        String room = seats.getFirst().getRoom();
        for (Seat seat : seats) {
            if (!seat.getRoom().equals(room)) {
                throw new IllegalArgumentException("Các ghế phải cùng phòng");
            }
        }
        // Kiểm tra liên tiếp
        List<Integer> seatNumbers = seats.stream()
                .map(s -> Integer.parseInt(s.getSeatNumber()))
                .sorted()
                .toList();
        for (int i = 1; i < seatNumbers.size(); i++) {
            if (seatNumbers.get(i) - seatNumbers.get(i - 1) != 1) {
                throw new IllegalArgumentException("Các ghế phải liên tiếp nhau");
            }
        }
        // Kiểm tra ghế đã được đặt ở suất chiếu này chưa
        List<Ticket> existingTickets = ticketRepository.findAll(); // Nên custom query theo showtime và seat
        for (Ticket ticket : existingTickets) {
            if (seatIds.contains(ticket.getSeat().getId()) && ticket.getBooking().getShowtime().getId().equals(showtimeId)) {
                throw new IllegalArgumentException("Một hoặc nhiều ghế đã được đặt ở suất chiếu này");
            }
        }
        // Tạo booking
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User không tồn tại"));
        Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(() -> new IllegalArgumentException("Showtime không tồn tại"));
        Booking booking = Booking.builder()
                .user(user)
                .showtime(showtime)
                .status(BookingStatus.CONFIRMED)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        booking = bookingRepository.save(booking);
        // Tạo ticket
        for (Seat seat : seats) {
            Ticket ticket = Ticket.builder()
                    .booking(booking)
                    .seat(seat)
                    .price(50.0) // TODO: tính giá động
                    .build();
            ticketRepository.save(ticket);
        }
        return booking;
    }
} 