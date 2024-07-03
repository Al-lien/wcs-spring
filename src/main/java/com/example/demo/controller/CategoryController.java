package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.CategoryEntity;
import com.example.demo.dto.CategoryCreationRequest;
import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.converter.CategoryConverter;
import com.example.demo.dto.converter.CategoryCreationConverter;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private CategoryCreationConverter creationConverter;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CategoryDto> categoryDtos = categories
                .stream()
                .map(categoryConverter::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable UUID id) {
        CategoryEntity category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        CategoryDto categoryDto = categoryConverter.convertToDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryCreationRequest categoryDto) {

        CategoryEntity savedCategory = categoryService.createCategory(creationConverter.convertToDomain(categoryDto));

        CategoryDto savedCategoryDto = categoryConverter.convertToDto(savedCategory);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCategoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable UUID id,
            @RequestBody CategoryDto categoryDetailsDto) {

        CategoryEntity updatedCategory = categoryService.updateCategory(id,
                categoryConverter.convertToDomain(categoryDetailsDto));

        if (updatedCategory == null) {
            return ResponseEntity.notFound().build();
        }

        CategoryDto updatedCategoryDto = categoryConverter.convertToDto(updatedCategory);

        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
