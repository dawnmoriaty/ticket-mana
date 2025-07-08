package com.example.ticket.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    String uploadImage(MultipartFile file);
    void deleteImage(String fileName);
    String getImageUrl(String fileName);
}
