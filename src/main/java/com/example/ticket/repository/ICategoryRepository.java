package com.example.ticket.repository;

import com.example.ticket.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category , Long> {
    boolean existsCategoryByName(String name);
    List<Category> findByNameContaining(String name);
}
