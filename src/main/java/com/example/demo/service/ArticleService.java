package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Article;
import com.example.demo.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles;
    }

    public Optional<Article> getArticleById(UUID id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle;
    }

    public Article createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        articleRepository.save(article);
        return article;
    }

    public Article updateArticle(UUID id, Article articleDetails) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());
        Article updatedArticle = articleRepository.save(article);
        return updatedArticle;
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
