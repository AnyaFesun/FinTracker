package org.example.back_end_labs.service;

import org.example.back_end_labs.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryService {
    private final Map<Long, Category> categories = new ConcurrentHashMap<>();
    private final AtomicLong categoryIdCounter = new AtomicLong();

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories.values());
    }

    public Category addCategory(String name) {
        Long id = categoryIdCounter.incrementAndGet();
        Category category = new Category(id, name);
        categories.put(id, category);
        return category;
    }

    public Category getCategoryById(Long categoryId) {
        return categories.get(categoryId);
    }

    public boolean deleteCategoryById(Long categoryId) {
        return categories.remove(categoryId) != null;
    }
}
