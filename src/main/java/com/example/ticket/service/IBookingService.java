package com.example.ticket.service;

import com.example.ticket.model.Booking;
import com.example.ticket.model.Ticket;
import java.util.List;

public interface IBookingService {
    Booking findById(Long id);
    List<Booking> findAll();
    Booking save(Booking booking);
    void deleteById(Long id);

    /**
     * Đặt vé cho 1 user tại 1 showtime, tối đa 4 vé liên tiếp/lần, không mua lẻ.
     * @param userId id người dùng
     * @param showtimeId id suất chiếu
     * @param seatIds danh sách id ghế muốn đặt (tối đa 4, phải liên tiếp)
     * @return Booking và danh sách Ticket đã đặt
     */
    Booking bookTickets(Long userId, Long showtimeId, List<Long> seatIds);
} 