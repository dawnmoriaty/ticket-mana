package com.example.ticket.controller;

import com.example.ticket.dto.rep.ApiResponse;
import com.example.ticket.model.Booking;
import com.example.ticket.service.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final IBookingService bookingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Booking>>> getAllBookings() {
        List<Booking> bookings = bookingService.findAll();
        return ResponseEntity.ok(ApiResponse.success(bookings, "Lấy danh sách booking thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Booking>> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.findById(id);
        if (booking == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Không tìm thấy booking"));
        }
        return ResponseEntity.ok(ApiResponse.success(booking, "Lấy booking thành công"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Booking>> createBooking(@RequestBody Booking booking) {
        Booking saved = bookingService.save(booking);
        return ResponseEntity.ok(ApiResponse.success(saved, "Tạo booking thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(@PathVariable Long id) {
        bookingService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa booking thành công"));
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<Booking>> bookTickets(@RequestParam Long userId,
                                                           @RequestParam Long showtimeId,
                                                           @RequestBody List<Long> seatIds) {
        Booking booking = bookingService.bookTickets(userId, showtimeId, seatIds);
        return ResponseEntity.ok(ApiResponse.success(booking, "Đặt vé thành công"));
    }
} 