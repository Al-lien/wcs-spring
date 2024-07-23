package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.AuthorEntity;
import com.example.demo.dto.AuthorRequestDto;
import com.example.demo.dto.AuthorResponseDto;
import com.example.demo.dto.converter.AuthorConverter;
import com.example.demo.repository.AuthorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;

    public List<AuthorResponseDto> getAllAuthors() {
        List<AuthorEntity> authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            return null;
        }

        List<AuthorResponseDto> authorDtos = authors.stream()
                .map(authorConverter::convertToResponseDto)
                .collect(Collectors.toList());

        return authorDtos;

    }

    public AuthorResponseDto getAuthorById(UUID id) {
        AuthorEntity author = authorRepository.findById(id).orElse(null);

        return authorConverter.convertToResponseDto(author);
    }

    @Transactional
    public AuthorResponseDto createAuthor(AuthorRequestDto newAuthor) {
        AuthorEntity author = authorConverter.convertToDomain(newAuthor);

        return authorConverter.convertToResponseDto(author);
    }

    @Transactional
    public AuthorResponseDto updateAuthor(UUID id, AuthorRequestDto authorDetails) {
        AuthorEntity author = authorRepository.findById(id).orElse(null);

        if (author == null) {
            return null;
        }

        AuthorEntity updatedAuthor = authorConverter.convertToDomain(authorDetails);

        authorRepository.save(updatedAuthor);

        return authorConverter.convertToResponseDto(updatedAuthor);

    }

    public void deleteAuthor(UUID id) {

    }

}
