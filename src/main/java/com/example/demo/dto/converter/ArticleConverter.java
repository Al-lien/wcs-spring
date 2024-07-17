package com.example.demo.dto.converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.CategoryEntity;
import com.example.demo.domain.TagEntity;
import com.example.demo.dto.ArticleRequestDto;
import com.example.demo.dto.ArticleResponseDto;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleConverter {

    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public ArticleResponseDto convertToResponseDto(ArticleEntity article) {

        ArticleResponseDto articleDto = ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .build();

        if (article.getCategory() != null) {
            articleDto.setCategory(article.getCategory().getName());
        }

        if (article.getTags() != null) {
            List<String> tags = article.getTags()
                    .stream()
                    .map(TagEntity::getName)
                    .collect(Collectors.toList());

            articleDto.setTags(tags);
        }

        return articleDto;

    }

    public ArticleEntity convertToDomain(ArticleRequestDto articleDto) {

        ArticleEntity article = ArticleEntity.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .build();

        if (articleDto.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findById(articleDto.getCategoryId()).orElse(null);
            article.setCategory(category);
        }

        if (articleDto.getTagIds() != null) {
            List<TagEntity> tags = tagRepository.findAllById(articleDto.getTagIds());
            article.setTags(tags);
        }

        return article;

    }
}
