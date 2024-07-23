package com.example.demo.dto.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.AuthorEntity;
import com.example.demo.dto.AuthorRequestDto;
import com.example.demo.dto.AuthorResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorConverter {

    private final ArticleAuthorConverter articleAuthorConverter;

    public AuthorResponseDto convertToResponseDto(AuthorEntity author) {

        AuthorResponseDto authorDto = AuthorResponseDto.builder()
                .id(author.getId())
                .firstname(author.getFirstname())
                .lastname(author.getLastname())
                .articles(
                        author.getArticleAuthors()
                                .stream()
                                .map(articleAuthorConverter::convertToAuthorContributionResponseDto)
                                .collect(Collectors.toList()))
                .build();

        return authorDto;
    }

    public AuthorEntity convertToDomain(AuthorRequestDto authorDto) {

        AuthorEntity author = AuthorEntity.builder()
                .id(authorDto.getId())
                .firstname(authorDto.getFirstname())
                .lastname(authorDto.getLastname())
                .build();

        if (authorDto.getArticleAuthors() != null) {
            author.setArticleAuthors(authorDto.getArticleAuthors());
        }

        return author;
    }

}
