package com.example.ticket.dto.req;

import org.springframework.web.multipart.MultipartFile;

public class MovieImageUploadRequest {
    private Long movieId;
    private MultipartFile file;
}
