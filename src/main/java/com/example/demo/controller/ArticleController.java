package com.example.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.demo.domain.ArticleEntity;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.converter.ArticleConverter;
import com.example.demo.service.ArticleService;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleConverter articleConverter;

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleEntity> articles = articleService.getAllArticles();

        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDto> articleDtos = articles
                .stream()
                .map(articleConverter::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(articleDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable UUID id) {
        ArticleEntity article = articleService.getArticleById(id);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articleConverter.convertToDto(article));
    }

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto) {
        ArticleEntity savedArticle = articleService.createArticle(articleConverter.convertToDomain(articleDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(articleConverter.convertToDto(savedArticle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable UUID id, @RequestBody ArticleDto articleDetailsDto) {
        ArticleEntity updatedArticle = articleService.updateArticle(id,
                articleConverter.convertToDomain(articleDetailsDto));

        return ResponseEntity.ok(articleConverter.convertToDto(updatedArticle));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable UUID id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * @GetMapping("/title/{title}")
     * public ResponseEntity<List<ArticleDto>> getArticleByTitle(@PathVariable
     * String title) {
     * List<ArticleDto> articlesDto = articleService.getArticleByTitle(title);
     * if (articlesDto.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * return ResponseEntity.ok(articlesDto);
     * }
     * 
     * @GetMapping("/article/{article}")
     * public ResponseEntity<List<ArticleDto>> getArticleByContent(@PathVariable
     * String content) {
     * List<ArticleDto> articlesDto = articleService.getArticleByContent(content);
     * if (articlesDto.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * return ResponseEntity.ok(articlesDto);
     * }
     * 
     * @GetMapping("/date/{date}")
     * public ResponseEntity<List<ArticleDto>> getArticleByDate(@PathVariable
     * LocalDate date) {
     * List<ArticleDto> articlesDto =
     * articleService.getArticleByCreationDateAfterTime(date);
     * if (articlesDto.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * return ResponseEntity.ok(articlesDto);
     * }
     * 
     * @GetMapping("/lastcreated")
     * public ResponseEntity<List<ArticleDto>> getLastFiveArticles() {
     * List<ArticleDto> articlesDto =
     * articleService.getFirstFiveByCreatedAtOrderByCreatedAtDesc();
     * if (articlesDto.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * return ResponseEntity
     * .status(HttpStatus.OK)
     * .body(articlesDto);
     * }
     */
}
