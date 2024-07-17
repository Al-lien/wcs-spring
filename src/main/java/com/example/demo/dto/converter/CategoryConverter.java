package com.example.demo.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.CategoryEntity;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.dto.CategoryRequestDto;
import com.example.demo.dto.CategoryResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryConverter {

    private final ArticleConverter articleConverter;

    public CategoryResponseDto convertToResponseDto(CategoryEntity category) {

        CategoryResponseDto categoryDto = CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();

        if (category.getArticles() != null) {

            List<ArticleResponseDto> articles = category.getArticles()
                    .stream()
                    .map(articleConverter::convertToResponseDto)
                    .collect(Collectors.toList());

            categoryDto.setArticles(articles);
        }

        return categoryDto;

    }

    public CategoryEntity convertToDomain(CategoryRequestDto category) {

        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
