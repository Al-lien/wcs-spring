package com.example.demo.dto;

import java.util.List;
import java.util.UUID;

import com.example.demo.domain.ArticleAuthorEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDto {

    UUID id;
    String firstname;
    String lastname;
    List<ArticleAuthorEntity> articleAuthors;

}
