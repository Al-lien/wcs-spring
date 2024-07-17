package com.example.demo.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.TagEntity;
import com.example.demo.dto.TagRequestDto;
import com.example.demo.dto.TagResponseDto;
import com.example.demo.service.ArticleService;

@Service
public class TagConverter {

    private ArticleService articleService;

    public TagResponseDto convertToResponseDto(TagEntity tag) {
        TagResponseDto tagDto = new TagResponseDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());

        if (tag.getArticles() != null) {
            tagDto.setArticleIds(tag.getArticles()
                    .stream()
                    .map(ArticleEntity::getId)
                    .collect(Collectors.toList()));
        }

        return tagDto;
    }

    public TagEntity convertToDomain(TagRequestDto tagDto) {
        TagEntity tag = TagEntity.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();

        if (tagDto.getArticleIds() != null) {
            List<ArticleEntity> articles = articleService.getAllArticleEntities(tagDto.getArticleIds());
            tag.setArticles(articles);
        }

        return tag;
    }

}
