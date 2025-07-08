package com.example.ticket.service.impl;

import com.example.ticket.dto.rep.MovieImageResponse;
import com.example.ticket.dto.req.MovieImageRequestDTO;
import com.example.ticket.model.MovieImage;
import com.example.ticket.repository.IMovieImageRepository;
import com.example.ticket.repository.IMovieRepository;
import com.example.ticket.service.IImageService;
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
public class MovieImageService implements IMovieImageService {
    private final IMovieImageRepository movieImageRepository;
    private final IMovieRepository movieRepository;
    private final IImageService imageService;

    @Override
    public MovieImageResponse createMovieImage(MovieImageRequestDTO request) {
        // Kiểm tra movie có tồn tại không
        var movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + request.getMovieId()));

        // Kiểm tra movie đã có ảnh chưa
        if (movieImageRepository.existsByMovieId(request.getMovieId())) {
            throw new IllegalStateException("Movie already has an image. Use update instead.");
        }

        // Tạo movieImage mới
        var movieImage = MovieImage.builder()
                .movie(movie)
                .url(request.getUrl())
                .build();

        var saved = movieImageRepository.save(movieImage);
        log.info("Created movie image with id: {} for movie: {}", saved.getId(), movie.getTitle());

        return mapToResponse(saved);
    }

    @Override
    public MovieImageResponse uploadMovieImage(Long movieId, MultipartFile file) {
        // Kiểm tra movie có tồn tại không
        var movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movieId));

        // Upload file và lấy URL
        String url = imageService.uploadImage(file);

        // Tìm ảnh hiện tại hoặc tạo mới
        var movieImage = movieImageRepository.findByMovieId(movieId)
                .orElse(MovieImage.builder().movie(movie).build());

        movieImage.setUrl(url);
        var saved = movieImageRepository.save(movieImage);

        log.info("Uploaded image for movie: {} with URL: {}", movie.getTitle(), url);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovieImageResponse> findById(Long id) {
        return movieImageRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MovieImageResponse> findByMovieId(Long movieId) {
        return movieImageRepository.findByMovieIdWithMovie(movieId)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieImageResponse> findAll() {
        return movieImageRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public MovieImageResponse updateMovieImage(Long id, MovieImageRequestDTO request) {
        var movieImage = movieImageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MovieImage not found with id: " + id));

        // Cập nhật URL
        movieImage.setUrl(request.getUrl());
        var saved = movieImageRepository.save(movieImage);

        log.info("Updated movie image with id: {} for movie: {}", saved.getId(), saved.getMovie().getTitle());

        return mapToResponse(saved);
    }

    @Override
    public void deleteById(Long id) {
        if (!movieImageRepository.existsById(id)) {
            throw new IllegalArgumentException("MovieImage not found with id: " + id);
        }
        movieImageRepository.deleteById(id);
        log.info("Deleted movie image with id: {}", id);
    }

    @Override
    public void deleteByMovieId(Long movieId) {
        if (!movieImageRepository.existsByMovieId(movieId)) {
            throw new IllegalArgumentException("No image found for movie with id: " + movieId);
        }
        movieImageRepository.deleteByMovieId(movieId);
        log.info("Deleted movie image for movie id: {}", movieId);
    }

    // Helper method để mapping MovieImage sang MovieImageResponse
    private MovieImageResponse mapToResponse(MovieImage movieImage) {
        return MovieImageResponse.builder()
                .id(movieImage.getId())
                .url(movieImage.getUrl())
                .movieId(movieImage.getMovie().getId())
                .movieTitle(movieImage.getMovie().getTitle())
                .build();
    }
}
