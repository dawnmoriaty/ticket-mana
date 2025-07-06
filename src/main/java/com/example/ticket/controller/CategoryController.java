package com.example.ticket.controller;

import com.example.ticket.dto.rep.ApiResponse;
import com.example.ticket.model.Category;
import com.example.ticket.service.impl.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(
                ApiResponse.<List<Category>>builder()
                        .success(true)
                        .message("GET ALL CATEGORIES")
                        .data(categoryService.findAll())
                        .build()
        );
    }
    @PostMapping("/")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody @Valid Category category) {
        // Assuming you have a method to save the category in the service
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.ok(
                ApiResponse.<Category>builder()
                        .success(true)
                        .message("CATEGORY CREATED")
                        .data(savedCategory)
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
            return ResponseEntity.ok(
                    ApiResponse.<Category>builder()
                            .success(true)
                            .message("CATEGORY WITH ID "+ id)
                            .data(categoryService.findById(id))
                            .build()
            );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody @Valid Category category) {
        // Assuming you have a method to update the category in the service
        Category updatedCategory = categoryService.update(id,category);
        return ResponseEntity.ok(
                ApiResponse.<Category>builder()
                        .success(true)
                        .message("CATEGORY UPDATED WITH ID " + id)
                        .data(updatedCategory)
                        .build()
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("CATEGORY DELETED WITH ID " + id)
                        .data("Category deleted successfully")
                        .build()
        );
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<ApiResponse<List<Category>>> findCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(
                ApiResponse.<List<Category>>builder()
                        .success(true)
                        .message("Categories found with name " + name)
                        .data(categoryService.findByName(name))
                        .build()
        );
    }
}
