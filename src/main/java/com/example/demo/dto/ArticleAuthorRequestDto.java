package com.example.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAuthorRequestDto {

    UUID id;

    @NotNull(message = "Article id is mandatory")
    UUID articleId;

    @NotNull(message = "Author id is mandatory")
    UUID authorId;

    @NotBlank(message = "Contribution is mandatory")
    String contribution;

}
