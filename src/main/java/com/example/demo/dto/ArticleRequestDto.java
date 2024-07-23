package com.example.demo.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Title is mandatory")
    String title;

    @NotBlank(message = "Title is mandatory")
    @Size(min = 10, message = "Content must be at least 10 characters")
    String content;

    UUID categoryId;
    List<UUID> tagIds;

}
