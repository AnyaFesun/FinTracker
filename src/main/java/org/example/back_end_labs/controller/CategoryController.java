package org.example.back_end_labs.controller;

import org.example.back_end_labs.model.Category;
import org.example.back_end_labs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@RequestParam String name) {
        return categoryService.addCategory(name);
    }

    @GetMapping("/{categoryId}")
    public  Optional<Category> getCategoryById(@PathVariable Long categoryId) {
        return Optional.ofNullable(categoryService.getCategoryById(categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategoryById(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}