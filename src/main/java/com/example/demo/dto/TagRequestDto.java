package com.example.demo.dto;

import java.util.List;
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
public class TagRequestDto {

    UUID id;

    @NotBlank(message = "Tag name is mandatory")
    String name;
    List<UUID> articleIds;

}
