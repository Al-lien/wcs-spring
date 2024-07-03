package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Autowired
    private final ArticleRepository articleRepository;

    @Autowired
    private CategoryService categoryService;

    public List<ArticleEntity> getAllArticles() {
        List<ArticleEntity> articles = articleRepository.findAll();
        return articles;
    }

    public ArticleEntity getArticleById(UUID id) {
        ArticleEntity article = articleRepository.findById(id).orElse(null);

        return article;
    }

    @Transactional
    public ArticleEntity createArticle(ArticleEntity article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        return articleRepository.save(article);
    }

    @Transactional
    public ArticleEntity updateArticle(UUID id, ArticleEntity articleDetails) {
        ArticleEntity article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            return null;
        }

        article = ArticleEntity.builder()
                .id(articleDetails.getId())
                .title(articleDetails.getTitle())
                .content(articleDetails.getContent())
                .createdAt(articleDetails.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        if (articleDetails.getCategory() != null) {
            CategoryEntity category = categoryService.getCategoryById(articleDetails.getCategory().getId());
            if (category == null) {
                return null;
            }
            article.setCategory(category);
        }

        return articleRepository.save(article);

    }

    public void deleteArticle(UUID id) {
        ArticleEntity article = articleRepository.findById(id).orElse(null);
        articleRepository.delete(article);
    }

    /*
     * public List<ArticleDto> getArticleByTitle(String title) {
     * List<ArticleEntity> articles = articleRepository.findByTitle(title);
     * List<ArticleDto> articleDtos = articles
     * .stream()
     * .map(articleConverter::convertToDto)
     * .collect(Collectors.toList());
     * return articleDtos;
     * }
     * 
     * public List<ArticleDto> getArticleByContent(String content) {
     * List<ArticleEntity> articles = articleRepository.findByContent(content);
     * List<ArticleDto> articleDtos = articles
     * .stream()
     * .map(articleConverter::convertToDto)
     * .collect(Collectors.toList());
     * return articleDtos;
     * }
     * 
     * public List<ArticleDto> getArticleByCreationDateAfterTime(LocalDate date) {
     * List<ArticleEntity> articles = articleRepository.findByCreatedAtAfter(date);
     * List<ArticleDto> articleDtos = articles
     * .stream()
     * .map(articleConverter::convertToDto)
     * .collect(Collectors.toList());
     * return articleDtos;
     * }
     * 
     * public List<ArticleDto> getFirstFiveByCreatedAtOrderByCreatedAtDesc() {
     * List<ArticleEntity> articles =
     * articleRepository.findFirstFiveByCreatedAtOrderByCreatedAtDesc();
     * List<ArticleDto> articleDtos = articles
     * .stream()
     * .map(articleConverter::convertToDto)
     * .collect(Collectors.toList());
     * return articleDtos;
     * }
     */

}
