package com.example.demo.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Article;
import com.example.demo.domain.Category;
import com.example.demo.service.CategoryService;

@Component
public class ArticleConverter {

    @Autowired
    private CategoryService categoryService;

    public ArticleDto convertToDto(Article article) {
        ArticleDto articleDTO = new ArticleDto();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        if (article.getCategory() != null) {
            articleDTO.setCategoryId(article.getCategory().getId());
        }
        return articleDTO;
    }

    public Article convertToDomain(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setCreatedAt(articleDto.getCreatedAt());
        article.setUpdatedAt(articleDto.getUpdatedAt());
        if (articleDto.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(articleDto.getCategoryId()).orElse(null);
            article.setCategory(category);
        }
        return article;

    }
}
