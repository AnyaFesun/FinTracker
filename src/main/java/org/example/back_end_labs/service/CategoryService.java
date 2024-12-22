package org.example.back_end_labs.service;

import org.example.back_end_labs.model.Category;
import org.example.back_end_labs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String name) {
        categoryRepository.findByName(name)
            .ifPresent(existingAccount -> {
                throw new IllegalArgumentException("This category already exists, please choose another name to create!");
            });

        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category with ID " + categoryId + " not found."));
    }

    public void deleteCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category with ID " + categoryId + " not found."));
        categoryRepository.delete(category);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new NoSuchElementException("No categories found.");
        }
        return categories;
    }
}
