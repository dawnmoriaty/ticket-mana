package com.example.ticket.controller;

import com.example.ticket.dto.rep.ApiResponse;
import com.example.ticket.model.Seat;
import com.example.ticket.service.ISeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {
    private final ISeatService seatService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Seat>>> getAllSeats() {
        List<Seat> seats = seatService.findAll();
        return ResponseEntity.ok(ApiResponse.success(seats, "Lấy danh sách seat thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Seat>> getSeatById(@PathVariable Long id) {
        Seat seat = seatService.findById(id);
        if (seat == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Không tìm thấy seat"));
        }
        return ResponseEntity.ok(ApiResponse.success(seat, "Lấy seat thành công"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Seat>> createSeat(@RequestBody Seat seat) {
        Seat saved = seatService.save(seat);
        return ResponseEntity.ok(ApiResponse.success(saved, "Tạo seat thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSeat(@PathVariable Long id) {
        seatService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa seat thành công"));
    }
} 