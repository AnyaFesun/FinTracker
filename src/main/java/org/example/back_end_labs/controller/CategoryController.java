package org.example.back_end_labs.controller;

import jakarta.validation.constraints.NotBlank;
import org.example.back_end_labs.model.Category;
import org.example.back_end_labs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestParam @NotBlank(message = "Category name cannot be empty")
                                       String name)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(name));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok("Category with ID " + categoryId + " deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}