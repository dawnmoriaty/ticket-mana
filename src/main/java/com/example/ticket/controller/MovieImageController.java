package com.example.ticket.controller;

import com.example.ticket.dto.rep.ApiResponse;
import com.example.ticket.dto.rep.MovieImageResponse;
import com.example.ticket.dto.req.MovieImageRequestDTO;
import com.example.ticket.service.IMovieImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie-images")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Movie Image Management", description = "APIs for managing movie images")
public class MovieImageController {

    private final IMovieImageService movieImageService;

    @PostMapping
    @Operation(summary = "Create movie image", description = "Create a new image for a movie with URL")
    public ResponseEntity<ApiResponse<MovieImageResponse>> createMovieImage(
            @RequestBody MovieImageRequestDTO request) {
        try {
            MovieImageResponse response = movieImageService.createMovieImage(request);
            return ResponseEntity.ok(ApiResponse.success(response, "Movie image created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.conflict(e.getMessage()));
        }
    }

    @PostMapping(value = "/upload/{movieId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload movie image", description = "Upload an image file for a movie")
    public ResponseEntity<ApiResponse<MovieImageResponse>> uploadMovieImage(
            @Parameter(description = "Movie ID", required = true)
            @PathVariable Long movieId,
            @Parameter(description = "Image file to upload", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            MovieImageResponse response = movieImageService.uploadMovieImage(movieId, file);
            return ResponseEntity.ok(ApiResponse.success(response, "Movie image uploaded successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.internalServerError("Failed to upload image: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie image by ID", description = "Retrieve movie image information by ID")
    public ResponseEntity<ApiResponse<MovieImageResponse>> getMovieImageById(
            @Parameter(description = "Movie image ID", required = true)
            @PathVariable Long id) {
        return movieImageService.findById(id)
            .map(response -> ResponseEntity.ok(ApiResponse.success(response, "Movie image found")))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("Movie image not found with id: " + id)));
    }

    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Get movie image by movie ID", description = "Retrieve movie image by movie ID")
    public ResponseEntity<ApiResponse<MovieImageResponse>> getMovieImageByMovieId(
            @Parameter(description = "Movie ID", required = true)
            @PathVariable Long movieId) {
        return movieImageService.findByMovieId(movieId)
            .map(response -> ResponseEntity.ok(ApiResponse.success(response, "Movie image found")))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.notFound("No image found for movie with id: " + movieId)));
    }

    @GetMapping
    @Operation(summary = "Get all movie images", description = "Retrieve all movie images")
    public ResponseEntity<ApiResponse<List<MovieImageResponse>>> getAllMovieImages() {
        List<MovieImageResponse> responses = movieImageService.findAll();
        return ResponseEntity.ok(ApiResponse.success(responses, "Movie images retrieved successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update movie image", description = "Update movie image URL")
    public ResponseEntity<ApiResponse<MovieImageResponse>> updateMovieImage(
            @Parameter(description = "Movie image ID", required = true)
            @PathVariable Long id,
            @RequestBody MovieImageRequestDTO request) {
        try {
            MovieImageResponse response = movieImageService.updateMovieImage(id, request);
            return ResponseEntity.ok(ApiResponse.success(response, "Movie image updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie image by ID", description = "Delete movie image by ID")
    public ResponseEntity<ApiResponse<Void>> deleteMovieImageById(
            @Parameter(description = "Movie image ID", required = true)
            @PathVariable Long id) {
        try {
            movieImageService.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success(null, "Movie image deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }

    @DeleteMapping("/movie/{movieId}")
    @Operation(summary = "Delete movie image by movie ID", description = "Delete movie image by movie ID")
    public ResponseEntity<ApiResponse<Void>> deleteMovieImageByMovieId(
            @Parameter(description = "Movie ID", required = true)
            @PathVariable Long movieId) {
        try {
            movieImageService.deleteByMovieId(movieId);
            return ResponseEntity.ok(ApiResponse.success(null, "Movie image deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }
}
