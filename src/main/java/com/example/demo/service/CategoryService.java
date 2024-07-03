package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CategoryEntity;
import com.example.demo.dto.converter.CategoryConverter;
import com.example.demo.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        return categories;
    }

    public CategoryEntity getCategoryById(UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElse(null);

        return category;
    }

    public CategoryEntity getCategoryByName(String name) {
        CategoryEntity category = categoryRepository.findByName(name).orElse(null);
        return category;
    }

    @Transactional
    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public CategoryEntity updateCategory(UUID id, CategoryEntity categoryDetails) {
        CategoryEntity category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            return null;
        }

        category = CategoryEntity.builder()
                .id(categoryDetails.getId())
                .name(categoryDetails.getName())
                .articles(categoryDetails.getArticles())
                .build();

        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
