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

import com.example.demo.dto.AuthorRequestDto;
import com.example.demo.dto.AuthorResponseDto;
import com.example.demo.service.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        List<AuthorResponseDto> authors = authorService.getAllAuthors();

        if (authors == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable("id") UUID id) {
        AuthorResponseDto author = authorService.getAuthorById(id);

        if (author == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(author);

    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorRequestDto authorDto) {
        AuthorResponseDto savedAuthor = authorService.createAuthor(authorDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@PathVariable("id") UUID id,
            @RequestBody AuthorRequestDto authorDetailsDto) {
        AuthorResponseDto updatedAuthor = authorService.updateAuthor(id, authorDetailsDto);

        if (updatedAuthor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedAuthor);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") UUID id) {
        authorService.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }

}
