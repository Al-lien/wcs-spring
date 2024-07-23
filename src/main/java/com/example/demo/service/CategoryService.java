package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.dto.CategoryRequestDto;
import com.example.demo.dto.CategoryResponseDto;
import com.example.demo.dto.converter.CategoryConverter;
import com.example.demo.exception.IdMismatchException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final CategoryConverter categoryConverter;

    public CategoryEntity getCategoryEntity(UUID id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category Entity with id: " + id + " wasn't found..."));

    }

    public List<CategoryResponseDto> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            return null;
        }

        List<CategoryResponseDto> categoryDtos = categories
                .stream()
                .map(categoryConverter::convertToResponseDto)
                .collect(Collectors.toList());

        return categoryDtos;
    }

    public CategoryResponseDto getCategoryById(UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category with id: " + id + " wasn't found..."));

        return categoryConverter.convertToResponseDto(category);
    }

    public CategoryResponseDto getCategoryByName(String name) {
        CategoryEntity category = categoryRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Category " + name + " wasn't found..."));

        return categoryConverter.convertToResponseDto(category);
    }

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto newCategory) {
        CategoryEntity category = categoryConverter.convertToDomain(newCategory);

        CategoryEntity savedCategory = categoryRepository.save(category);

        return categoryConverter.convertToResponseDto(savedCategory);
    }

    @Transactional
    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto categoryDetails) {
        if (!id.equals(categoryDetails.getId())) {
            throw new IdMismatchException("Category id does not match path provided id...");
        }

        categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category with id: " + id + " wasn't found..."));

        CategoryEntity categoryToUpdate = categoryConverter.convertToDomain(categoryDetails);

        CategoryEntity updatedCategory = categoryRepository.save(categoryToUpdate);

        return categoryConverter.convertToResponseDto(updatedCategory);
    }

    public void deleteCategory(UUID id) {
        CategoryEntity category = categoryRepository.findById(id).orElse(null);
        List<ArticleEntity> articles = articleRepository.findAllByCategoryName(category.getName());

        for (ArticleEntity article : articles) {
            article.setCategory(null);
        }

        categoryRepository.delete(category);
    }
}
