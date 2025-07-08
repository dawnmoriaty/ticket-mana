package com.example.ticket.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieImageRequestDTO {
    private Long movieId;
    private String url;

    // Constructor cho update (không cần movieId)
    public MovieImageRequestDTO(String url) {
        this.url = url;
    }
}
