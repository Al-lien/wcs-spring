package com.example.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Author firstname is mandatory")
    String firstname;

    @NotBlank(message = "Author lastname is mandatory")
    String lastname;

}
