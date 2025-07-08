package com.example.ticket.model;

import com.example.ticket.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    private String room;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

} 