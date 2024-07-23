package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleAuthorEntity;
import com.example.demo.domain.ArticleEntity;
import com.example.demo.dto.ArticleRequestDto;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.dto.converter.ArticleConverter;
import com.example.demo.exception.IdMismatchException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ArticleAuthorRepository;
import com.example.demo.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;
    private final ArticleAuthorRepository articleAuthorRepository;

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
        ArticleEntity article = articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Article with id: " + id + " wasn't found..."));

        return articleConverter.convertToResponseDto(article);
    }

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto newArticle) {
        ArticleEntity article = articleConverter.convertToDomain(newArticle);

        ArticleEntity savedArticle = articleRepository.save(article);

        return articleConverter.convertToResponseDto(savedArticle);
    }

    @Transactional
    public ArticleResponseDto updateArticle(UUID id, ArticleRequestDto articleDetails) {
        if (!id.equals(articleDetails.getId())) {
            throw new IdMismatchException("Article id does not match path provided id...");
        }

        articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Article with id: " + id + " wasn't found..."));

        ArticleEntity articleToUpdate = articleConverter.convertToDomain(articleDetails);

        ArticleEntity updatedArticle = articleRepository.save(articleToUpdate);

        return articleConverter.convertToResponseDto(updatedArticle);

    }

    public void deleteArticle(UUID id) {
        ArticleEntity article = articleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Article with id: " + id + " wasn't found..."));

        List<ArticleAuthorEntity> contributions = articleAuthorRepository.findAllByArticleId(article.getId());

        for (ArticleAuthorEntity contribution : contributions) {
            articleAuthorRepository.delete(contribution);
        }

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
