package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ArticleRequestDto;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<ArticleResponseDto>> getAllArticlesByTagsId(
            @PathVariable("tag") UUID tag) {

        List<ArticleResponseDto> articles = articleService.getAllArticlesByTagsId(tag);

        if (articles == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(articles);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponseDto>> getAllArticles() {

        List<ArticleResponseDto> articles = articleService.getAllArticles();

        if (articles == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable("id") UUID id) {
        ArticleResponseDto articleDto = articleService.getArticleById(id);

        if (articleDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(articleDto);
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto articleDto) {
        ArticleResponseDto savedArticle = articleService.createArticle(articleDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable("id") UUID id,
            @RequestBody ArticleRequestDto articleDetailsDto) {

        ArticleResponseDto updatedArticle = articleService.updateArticle(id,
                articleDetailsDto);

        if (updatedArticle == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedArticle);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") UUID id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

}
