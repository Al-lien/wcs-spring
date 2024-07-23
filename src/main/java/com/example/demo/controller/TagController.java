package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TagRequestDto;
import com.example.demo.dto.TagResponseDto;
import com.example.demo.service.TagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        List<TagResponseDto> tagDtos = tagService.getAllTags();

        if (tagDtos == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tagDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> getTagById(@PathVariable("id") UUID id) {
        TagResponseDto tagDto = tagService.getTagById(id);

        if (tagDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tagDto);
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagRequestDto tagDto) {
        TagResponseDto savedTagDto = tagService.createTag(tagDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTagDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDto> updatedTag(@PathVariable("id") UUID id,
            @Valid @RequestBody TagRequestDto tagDetailsDto) {
        TagResponseDto updatedTag = tagService.updateTag(id, tagDetailsDto);

        if (updatedTag == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTag);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") UUID id) {
        tagService.deleteTag(id);

        return ResponseEntity.noContent().build();
    }
}
