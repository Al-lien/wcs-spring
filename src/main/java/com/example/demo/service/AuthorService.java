package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleAuthorEntity;
import com.example.demo.domain.AuthorEntity;
import com.example.demo.dto.AuthorRequestDto;
import com.example.demo.dto.AuthorResponseDto;
import com.example.demo.dto.converter.AuthorConverter;
import com.example.demo.exception.IdMismatchException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ArticleAuthorRepository;
import com.example.demo.repository.AuthorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorConverter authorConverter;
    private final ArticleAuthorRepository articleAuthorRepository;

    public List<AuthorResponseDto> getAllAuthors() {
        List<AuthorEntity> authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            return null;
        }

        List<AuthorResponseDto> authorDtos = authors
                .stream()
                .map(authorConverter::convertToResponseDto)
                .collect(Collectors.toList());

        return authorDtos;

    }

    public AuthorResponseDto getAuthorById(UUID id) {
        AuthorEntity author = authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Author with id: " + id + " wasn't found..."));

        return authorConverter.convertToResponseDto(author);
    }

    @Transactional
    public AuthorResponseDto createAuthor(AuthorRequestDto newAuthor) {
        AuthorEntity author = authorConverter.convertToDomain(newAuthor);

        AuthorEntity savedAuthor = authorRepository.save(author);

        return authorConverter.convertToResponseDto(savedAuthor);
    }

    @Transactional
    public AuthorResponseDto updateAuthor(UUID id, AuthorRequestDto authorDetails) {
        if (!id.equals(authorDetails.getId())) {
            throw new IdMismatchException("Author id does not match path provided id...");
        }

        authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Author with id: " + id + " wasn't found..."));

        AuthorEntity authorToUpdate = authorConverter.convertToDomain(authorDetails);

        AuthorEntity updatedAuthor = authorRepository.save(authorToUpdate);

        return authorConverter.convertToResponseDto(updatedAuthor);

    }

    public void deleteAuthor(UUID id) {
        AuthorEntity author = authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Author with id: " + id + " wasn't found..."));
        List<ArticleAuthorEntity> contributions = articleAuthorRepository.findAllByAuthorId(author.getId());

        for (ArticleAuthorEntity contribution : contributions) {
            articleAuthorRepository.delete(contribution);
        }

        authorRepository.delete(author);
    }

}
