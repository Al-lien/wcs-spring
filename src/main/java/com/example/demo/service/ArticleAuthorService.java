package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleAuthorEntity;
import com.example.demo.dto.ArticleAuthorRequestDto;
import com.example.demo.dto.ArticleAuthorResponseDto;
import com.example.demo.dto.converter.ArticleAuthorConverter;
import com.example.demo.exception.IdMismatchException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ArticleAuthorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleAuthorService {

    private final ArticleAuthorRepository articleAuthorRepository;
    private final ArticleAuthorConverter articleAuthorConverter;

    public List<ArticleAuthorResponseDto> getAllContributions() {
        List<ArticleAuthorEntity> contributions = articleAuthorRepository.findAll();
        List<ArticleAuthorResponseDto> contributionDtos = contributions.stream()
                .map(articleAuthorConverter::convertToResponseDto)
                .collect(Collectors.toList());

        return contributionDtos;
    }

    @Transactional
    public ArticleAuthorResponseDto createContribution(ArticleAuthorRequestDto articleAuthorDto) {
        ArticleAuthorEntity articleAuthor = articleAuthorConverter.convertToDomain(articleAuthorDto);

        return articleAuthorConverter.convertToResponseDto(articleAuthorRepository.save(articleAuthor));

    }

    @Transactional
    public ArticleAuthorResponseDto updateContribution(UUID id, ArticleAuthorRequestDto articleAuthorDetails) {

        if (!id.equals(articleAuthorDetails.getId())) {
            throw new IdMismatchException("Contribution id does not match path provided id...");
        }

        articleAuthorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Contribution with id: " + id + " wasn't found..."));

        ArticleAuthorEntity updatedArticle = articleAuthorConverter.convertToDomain(articleAuthorDetails);

        articleAuthorRepository.save(updatedArticle);

        return articleAuthorConverter.convertToResponseDto(updatedArticle);

    }

    public void deleteContribution(UUID id) {
        ArticleAuthorEntity contribution = articleAuthorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Contribution with id: " + id + " wasn't found..."));

        articleAuthorRepository.delete(contribution);
    }

}
