package com.example.demo.dto.converter;

import org.springframework.stereotype.Component;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.dto.ArticleResponseDto;

@Component
public class ArticleResponseConverter {

    public ArticleResponseDto convertToDto(ArticleEntity article) {
        ArticleResponseDto articleDto = new ArticleResponseDto();
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());
        articleDto.setCategory(article.getCategory().getName());

        return articleDto;
    }

}
