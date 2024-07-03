package com.example.demo.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.dto.ArticleDto;
import com.example.demo.service.CategoryService;

@Component
public class ArticleConverter {

    @Autowired
    private CategoryService categoryService;

    public ArticleDto convertToDto(ArticleEntity article) {
        ArticleDto articleDTO = new ArticleDto();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setUpdatedAt(article.getCreatedAt());

        if (article.getCategory() != null) {
            articleDTO.setCategoryId(article.getCategory().getId());
        }

        return articleDTO;
    }

    public ArticleEntity convertToDomain(ArticleDto articleDto) {
        ArticleEntity article = new ArticleEntity();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setCreatedAt(articleDto.getCreatedAt());
        article.setUpdatedAt(articleDto.getUpdatedAt());

        if (articleDto.getCategoryId() != null) {
            CategoryEntity category = categoryService.getCategoryById(articleDto.getCategoryId());
            article.setCategory(category);
        }

        return article;

    }
}
