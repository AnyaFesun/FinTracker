package org.example.back_end_2.service;

import org.example.back_end_2.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private List<Category> categories = new ArrayList<>();

    public List<Category> getAllCategories() {
        return categories;
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categories.stream().filter(category -> category.getId().equals(categoryId)).findFirst();
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void deleteCategory(Long categoryId) {
        categories.removeIf(category -> category.getId().equals(categoryId));
    }

}
