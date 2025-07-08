package com.example.ticket.service;

import com.example.ticket.dto.rep.MovieImageResponse;
import com.example.ticket.dto.req.MovieImageRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IMovieImageService {
    MovieImageResponse createMovieImage(MovieImageRequestDTO request);
    MovieImageResponse uploadMovieImage(Long movieId, MultipartFile file);
    Optional<MovieImageResponse> findById(Long id);
    Optional<MovieImageResponse> findByMovieId(Long movieId);
    List<MovieImageResponse> findAll();
    MovieImageResponse updateMovieImage(Long id, MovieImageRequestDTO request); // Sử dụng DTO chung
    void deleteById(Long id);
    void deleteByMovieId(Long movieId);
}
