package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.dto.ArticleRequestDto;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.dto.converter.ArticleConverter;
import com.example.demo.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    public List<ArticleEntity> getAllArticleEntities(List<UUID> articleId) {
        return articleRepository.findAllById(articleId);
    }

    public List<ArticleResponseDto> getAllArticles() {
        List<ArticleEntity> articles = articleRepository.findAll();

        if (articles.isEmpty()) {
            return null;
        }

        List<ArticleResponseDto> articleDtos = articles
                .stream()
                .map(articleConverter::convertToResponseDto)
                .collect(Collectors.toList());

        return articleDtos;
    }

    public ArticleResponseDto getArticleById(UUID id) {
        ArticleEntity article = articleRepository.findById(id).orElse(null);

        return articleConverter.convertToResponseDto(article);
    }

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto newArticle) {
        ArticleEntity article = articleConverter.convertToDomain(newArticle);

        articleRepository.save(article);

        return articleConverter.convertToResponseDto(article);
    }

    @Transactional
    public ArticleResponseDto updateArticle(UUID id, ArticleRequestDto articleDetails) {
        ArticleEntity article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            return null;
        }

        ArticleEntity updatedArticle = articleConverter.convertToDomain(articleDetails);

        articleRepository.save(updatedArticle);

        return articleConverter.convertToResponseDto(updatedArticle);

    }

    public void deleteArticle(UUID id) {
        ArticleEntity article = articleRepository.findById(id).orElse(null);
        articleRepository.delete(article);
    }

    public List<ArticleResponseDto> getAllArticlesByTagsId(UUID tagId) {
        List<ArticleEntity> articles = articleRepository.findAllByTagsId(tagId);
        return articles
                .stream()
                .map(articleConverter::convertToResponseDto)
                .toList();
    }

}
