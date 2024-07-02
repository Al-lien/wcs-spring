package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Article;
import com.example.demo.dto.ArticleConverter;
import com.example.demo.dto.ArticleDto;
import com.example.demo.repository.ArticleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Autowired
    private final ArticleRepository articleRepository;

    @Autowired
    private ArticleConverter articleConverter;

    public List<ArticleDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        List<ArticleDto> articleDtos = articles
                .stream()
                .map(articleConverter::convertToDto)
                .collect(Collectors.toList());

        return articleDtos;
    }

    public ArticleDto getArticleById(UUID id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found..."));

        return articleConverter.convertToDto(article);
    }

    @Transactional
    public ArticleDto createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(article);
        return articleConverter.convertToDto(article);
    }

    @Transactional
    public ArticleDto updateArticle(UUID id, ArticleDto articleDetails) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return null;
        }

        articleRepository.save(articleConverter.convertToDomain(articleDetails));

        return articleConverter.convertToDto(article);

    }

    public void deleteArticle(UUID id) {
        Article article = articleRepository.findById(id).orElse(null);
        articleRepository.delete(article);
    }

    public List<ArticleDto> getArticleByTitle(String title) {
        List<ArticleDto> articlesDto = articleRepository.findByTitle(title);
        return articlesDto;
    }

    public List<ArticleDto> getArticleByContent(String content) {
        List<ArticleDto> articlesDto = articleRepository.findByContent(content);
        return articlesDto;
    }

    public List<ArticleDto> getArticleByCreationDateAfterTime(LocalDate date) {
        List<ArticleDto> articlesDto = articleRepository.findByCreatedAtAfter(date);
        return articlesDto;
    }

    public List<ArticleDto> getFirstFiveByCreatedAtOrderByCreatedAtDesc() {
        List<ArticleDto> articlesDto = articleRepository.findFirstFiveByCreatedAtOrderByCreatedAtDesc();
        return articlesDto;
    }

}
