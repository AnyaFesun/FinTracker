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
import java.util.Optional;

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
    public ResponseEntity<?> createCategory(@RequestParam @NotBlank(message = "Category name cannot be empty")
                                       String name)  {
        Category createdCategory = categoryService.addCategory(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> category =  categoryService.getCategoryById(categoryId);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + categoryId + " not found.");
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId) {
        Optional<Category> category =  categoryService.getCategoryById(categoryId);
        if (category.isPresent()) {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok("Category with ID " + categoryId + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + categoryId + " not found.\nCategory cannot be deleted.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No categories found.");
        }
        return ResponseEntity.ok(categories);
    }
}