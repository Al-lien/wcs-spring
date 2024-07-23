package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contributions")
@RequiredArgsConstructor
public class ArticleAuthorController {

    private final ArticleAuthorService articleAuthorService;

    @GetMapping
    public ResponseEntity<List<ArticleAuthorResponseDto>> getAllContributions() {
        List<ArticleAuthorResponseDto> contributions = articleAuthorService.getAllContributions();

        return ResponseEntity.ok().body(contributions);

    }

    @PostMapping
    public ResponseEntity<ArticleAuthorResponseDto> createContribution(
            @RequestBody ArticleAuthorRequestDto articleAuthorDto) {
        ArticleAuthorResponseDto savedContribution = articleAuthorService.createContribution(articleAuthorDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedContribution);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleAuthorResponseDto> updateContribution(@PathVariable("id") UUID id,
            @RequestBody ArticleAuthorRequestDto articleAuthorDto) {
        ArticleAuthorResponseDto updatedContribution = articleAuthorService.updateContribution(id, articleAuthorDto);

        if (updatedContribution == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(updatedContribution);
    }

}
