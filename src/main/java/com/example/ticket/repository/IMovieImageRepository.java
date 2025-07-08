package com.example.ticket.repository;

import com.example.ticket.model.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IMovieImageRepository extends JpaRepository<MovieImage, Long> {
    Optional<MovieImage> findByMovieId(Long movieId);

    boolean existsByMovieId(Long movieId);

    void deleteByMovieId(Long movieId);

    @Query("SELECT mi FROM MovieImage mi JOIN FETCH mi.movie WHERE mi.movie.id = :movieId")
    Optional<MovieImage> findByMovieIdWithMovie(@Param("movieId") Long movieId);
}
