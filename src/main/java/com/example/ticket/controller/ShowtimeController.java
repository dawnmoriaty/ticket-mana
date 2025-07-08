package com.example.ticket.controller;

import com.example.ticket.dto.rep.ApiResponse;
import com.example.ticket.model.Showtime;
import com.example.ticket.service.IShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {
    private final IShowtimeService showtimeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Showtime>>> getAllShowtimes() {
        List<Showtime> showtimes = showtimeService.findAll();
        return ResponseEntity.ok(ApiResponse.success(showtimes, "Lấy danh sách showtime thành công"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Showtime>> getShowtimeById(@PathVariable Long id) {
        Showtime showtime = showtimeService.findById(id);
        if (showtime == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Không tìm thấy showtime"));
        }
        return ResponseEntity.ok(ApiResponse.success(showtime, "Lấy showtime thành công"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Showtime>> createShowtime(@RequestBody Showtime showtime) {
        Showtime saved = showtimeService.save(showtime);
        return ResponseEntity.ok(ApiResponse.success(saved, "Tạo showtime thành công"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Xóa showtime thành công"));
    }
} 