package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Article;

import com.example.demo.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Autowired
    private final ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

    public Optional<Article> getArticleById(UUID id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle;
    }

    @Transactional
    public Article createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(article);
        return article;
    }

    @Transactional
    public Article updateArticle(UUID id, Article articleDetails) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }

        article = Article.builder()
                .id(articleDetails.getId())
                .title(articleDetails.getTitle())
                .content(articleDetails.getContent())
                .createdAt(articleDetails.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .category(articleDetails.getCategory())
                .build();

        return articleRepository.save(article);

    }

    public void deleteArticle(UUID id) {
        Article article = articleRepository.findById(id).orElse(null);
        articleRepository.delete(article);
    }

    public List<Article> getArticleByTitle(String title) {
        List<Article> articles = articleRepository.findByTitle(title);
        return articles;
    }

    public List<Article> getArticleByContent(String content) {
        List<Article> articles = articleRepository.findByContent(content);
        return articles;
    }

    public List<Article> getArticleByCreationDateAfterTime(LocalDate date) {
        List<Article> articles = articleRepository.findByCreatedAtAfter(date);
        return articles;
    }

    public List<Article> getFirstFiveByCreatedAtOrderByCreatedAtDesc() {
        List<Article> articles = articleRepository.findFirstFiveByCreatedAtOrderByCreatedAtDesc();
        return articles;
    }

}
