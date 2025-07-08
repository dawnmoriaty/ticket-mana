package com.example.ticket.dto.rep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieImageResponse {
    private Long id;
    private String url;
    private Long movieId;
    private String movieTitle;
}
