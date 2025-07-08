package com.example.ticket.service.impl;

import com.example.ticket.dto.rep.MovieImageResponse;
import com.example.ticket.dto.req.MovieImageRequestDTO;
import com.example.ticket.dto.req.MovieImageUpdateRequest;
import com.example.ticket.service.IMovieImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MovieImageServiceImpl implements IMovieImageService {
    @Override
    public MovieImageResponse createMovieImage(MovieImageRequestDTO request) {
        return null;
    }

    @Override
    public MovieImageResponse uploadMovieImage(Long movieId, MultipartFile file) {
        return null;
    }

    @Override
    public Optional<MovieImageResponse> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<MovieImageResponse> findByMovieId(Long movieId) {
        return Optional.empty();
    }

    @Override
    public List<MovieImageResponse> findAll() {
        return List.of();
    }

    @Override
    public MovieImageResponse updateMovieImage(Long id, MovieImageUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByMovieId(Long movieId) {

    }
}
