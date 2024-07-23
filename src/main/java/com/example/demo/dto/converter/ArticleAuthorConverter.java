package com.example.demo.dto.converter;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleAuthorEntity;
import com.example.demo.dto.ArticleAuthorRequestDto;
import com.example.demo.dto.ArticleAuthorResponseDto;
import com.example.demo.dto.ArticleContributionResponseDto;
import com.example.demo.dto.AuthorContributionResponseDto;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleAuthorConverter {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    public ArticleAuthorResponseDto convertToResponseDto(ArticleAuthorEntity articleAuthor) {

        ArticleAuthorResponseDto articleAuthorDto = ArticleAuthorResponseDto.builder()
                .id(articleAuthor.getId())
                .authorFullname(articleAuthor.getAuthor().getFullname())
                .articleTitle(articleAuthor.getArticle().getTitle())
                .contribution(articleAuthor.getContribution())
                .build();

        return articleAuthorDto;
    }

    public ArticleContributionResponseDto convertToContributionResponseDto(ArticleAuthorEntity articleAuthor) {

        ArticleContributionResponseDto articleAuthorDto = ArticleContributionResponseDto.builder()
                .id(articleAuthor.getId())
                .author(articleAuthor.getFullname())
                .contribution(articleAuthor.getContribution())
                .build();

        return articleAuthorDto;
    }

    public AuthorContributionResponseDto convertToAuthorContributionResponseDto(ArticleAuthorEntity articleAuthor) {

        AuthorContributionResponseDto articleAuthorDto = AuthorContributionResponseDto.builder()
                .id(articleAuthor.getId())
                .title(articleAuthor.getArticle().getTitle())
                .contribution(articleAuthor.getContribution())
                .build();

        return articleAuthorDto;
    }

    public ArticleAuthorEntity convertToDomain(ArticleAuthorRequestDto articleAuthorDto) {
        ArticleAuthorEntity articleAuthor = ArticleAuthorEntity.builder()
                .id(articleAuthorDto.getId())
                .article(articleRepository.findById(articleAuthorDto.getArticleId()).orElse(null))
                .author(authorRepository.findById(articleAuthorDto.getAuthorId()).orElse(null))
                .contribution(articleAuthorDto.getContribution())
                .build();

        return articleAuthor;
    }

}
