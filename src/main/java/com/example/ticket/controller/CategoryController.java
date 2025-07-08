package com.example.ticket.controller;

import com.example.ticket.dto.rep.ApiResponse;
import com.example.ticket.model.Category;
import com.example.ticket.service.impl.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Category Management", description = "APIs for managing movie categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all movie categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(ApiResponse.success(categories, "Categories retrieved successfully"));
    }

    @PostMapping
    @Operation(summary = "Create category", description = "Create a new movie category")
    public ResponseEntity<ApiResponse<Category>> createCategory(
            @RequestBody @Valid Category category) {
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.ok(ApiResponse.success(savedCategory, "Category created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a category by its ID")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(category, "Category found"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update an existing category")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id,
            @RequestBody @Valid Category category) {
        Category updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(ApiResponse.success(updatedCategory, "Category updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Delete a category by ID")
    public ResponseEntity<ApiResponse<String>> deleteCategory(
            @Parameter(description = "Category ID", required = true)
            @PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", "Category deleted"));
    }

    @GetMapping("/search/{name}")
    @Operation(summary = "Search categories by name", description = "Find categories by name")
    public ResponseEntity<ApiResponse<List<Category>>> findCategoryByName(
            @Parameter(description = "Category name to search", required = true)
            @PathVariable String name) {
        List<Category> categories = categoryService.findByName(name);
        return ResponseEntity.ok(ApiResponse.success(categories, "Categories found with name: " + name));
    }
}
