package com.example.demo.dto.converter;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.CategoryDto;
import com.example.demo.service.CategoryService;

@Component
public class CategoryConverter {

    @Autowired
    private CategoryService categoryService;

    public CategoryDto convertToDto(CategoryEntity category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setArticles(category.getArticles().stream().map(article -> {
            ArticleDto articleDto = new ArticleDto();
            articleDto.setId(article.getId());
            articleDto.setTitle(article.getTitle());
            articleDto.setContent(article.getContent());
            articleDto.setCreatedAt(article.getCreatedAt());
            articleDto.setUpdatedAt(article.getUpdatedAt());
            articleDto.setCategoryId(article.getCategory().getId());
            return articleDto;
        }).collect(Collectors.toList()));

        return categoryDto;
    }

    public CategoryEntity convertToDomain(CategoryDto categoryDto) {
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        category.setArticles(categoryDto.getArticles().stream().map(articleDto -> {
            ArticleEntity article = new ArticleEntity();
            article.setId(articleDto.getId());
            article.setTitle(articleDto.getTitle());
            article.setContent(articleDto.getContent());
            article.setCreatedAt(articleDto.getCreatedAt());
            article.setUpdatedAt(articleDto.getUpdatedAt());
            if (articleDto.getCategoryId() != null) {
                CategoryEntity categoryRef = categoryService.getCategoryById(articleDto.getCategoryId());
                article.setCategory(categoryRef);
            }
            return article;
        }).collect(Collectors.toList()));

        return category;
    }

}
