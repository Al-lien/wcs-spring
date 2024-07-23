package com.example.demo.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequestDto {

    UUID id;
    String title;
    String content;
    UUID categoryId;
    List<UUID> tagIds;

}
