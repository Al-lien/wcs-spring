package com.example.demo.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAuthorResponseDto {

    UUID id;
    String articleTitle;
    String authorFullname;
    String contribution;
}
