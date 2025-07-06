package com.example.ticket.service;

import com.example.ticket.model.Category;
import jakarta.validation.Valid;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();

    Category save( Category category);

    Category findById(Long id);
    Category update(Long id ,Category category);

    void delete(Long id);

    List<Category> findByName(String keyword);
}
