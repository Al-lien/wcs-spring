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

import com.example.demo.dto.ArticleAuthorRequestDto;
import com.example.demo.dto.ArticleAuthorResponseDto;
import com.example.demo.service.ArticleAuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contributions")
@RequiredArgsConstructor
public class ArticleAuthorController {

    private final ArticleAuthorService articleAuthorService;

    @GetMapping
    public ResponseEntity<List<ArticleAuthorResponseDto>> getAllContributions() {
        List<ArticleAuthorResponseDto> contributions = articleAuthorService.getAllContributions();

        if (contributions == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(contributions);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleAuthorResponseDto> getContributionById(@PathVariable("id") UUID id) {
        ArticleAuthorResponseDto contribution = articleAuthorService.getContributionById(id);

        if (contribution == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(contribution);
    }

    @PostMapping
    public ResponseEntity<ArticleAuthorResponseDto> createContribution(
            @Valid @RequestBody ArticleAuthorRequestDto articleAuthorDto) {
        ArticleAuthorResponseDto savedContribution = articleAuthorService.createContribution(articleAuthorDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedContribution);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleAuthorResponseDto> updateContribution(@PathVariable("id") UUID id,
            @Valid @RequestBody ArticleAuthorRequestDto articleAuthorDto) {
        ArticleAuthorResponseDto updatedContribution = articleAuthorService.updateContribution(id, articleAuthorDto);

        if (updatedContribution == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(updatedContribution);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContribution(@PathVariable("id") UUID id) {
        articleAuthorService.deleteContribution(id);
        return ResponseEntity.noContent().build();

    }
}
