package com.example.ticket.service.impl;

import com.example.ticket.model.Category;
import com.example.ticket.repository.ICategoryRepository;
import com.example.ticket.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        log.info("Find all Categories");
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        if(category.getName()==null || category.getName().isEmpty()) {
            log.error("Category name cannot be null or empty");
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        if(categoryRepository.existsCategoryByName(category.getName())) {
            log.error("Category with name {} already exists", category.getName());
            throw new IllegalArgumentException("Category with this name already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        log.info("Find Category with id {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
    }

    @Override
    public Category update(Long id, Category category) {
        Category existingCategory = findById(id);
        if(categoryRepository.existsCategoryByName(category.getName())) {
            log.error("Category with name {} already exists can not update ", category.getName());
            throw new IllegalArgumentException("Category with this name already exists");
        }
        log.info("Category with id {} updated", id);
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Long id) {
        Category existingCategory = findById(id);
        log.info("Delete Category with id {}", id);
        if (existingCategory == null) {
            log.error("Category with id {} not found", id);
            throw new IllegalArgumentException("Category not found with id: " + id);
        }
        categoryRepository.delete(existingCategory);
    }

    @Override
    public List<Category> findByName(String keyword) {
        List<Category> categories = categoryRepository.findByNameContaining(keyword);
        if (categories.isEmpty()) {
            log.warn("No categories found with name: {}", keyword);
            throw new IllegalArgumentException("No categories found with name: " + keyword);
        }
        return categories;
    }
}
