package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Article;
import com.example.demo.domain.Category;
import com.example.demo.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public Optional<Category> getCategoryById(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory;
    }

    public Optional<Category> getArticleByName(String categoryName) {
        Optional<Category> category = categoryRepository.findByName(categoryName);
        return category;
    }

    public Optional<Category> getCategoryByName(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        return optionalCategory;
    }

    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(UUID id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        category = Category.builder()
                .id(categoryDetails.getId())
                .name(categoryDetails.getName())
                .build();

        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
