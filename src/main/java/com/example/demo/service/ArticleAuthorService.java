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

        if (contributions.isEmpty()) {
            return null;
        }

        List<ArticleAuthorResponseDto> contributionDtos = contributions.stream()
                .map(articleAuthorConverter::convertToResponseDto)
                .collect(Collectors.toList());

        return contributionDtos;
    }

    public ArticleAuthorResponseDto getContributionById(UUID id) {
        ArticleAuthorEntity contribution = articleAuthorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Contribution with id: " + id + " wasn't found..."));

        return articleAuthorConverter.convertToResponseDto(contribution);
    }

    @Transactional
    public ArticleAuthorResponseDto createContribution(ArticleAuthorRequestDto newContributionDto) {
        ArticleAuthorEntity contribution = articleAuthorConverter.convertToDomain(newContributionDto);

        ArticleAuthorEntity savedContribution = articleAuthorRepository.save(contribution);

        return articleAuthorConverter.convertToResponseDto(savedContribution);

    }

    @Transactional
    public ArticleAuthorResponseDto updateContribution(UUID id, ArticleAuthorRequestDto contributionDetails) {

        if (!id.equals(contributionDetails.getId())) {
            throw new IdMismatchException("Contribution id does not match path provided id...");
        }

        articleAuthorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Contribution with id: " + id + " wasn't found..."));

        ArticleAuthorEntity contributionToUpdate = articleAuthorConverter.convertToDomain(contributionDetails);

        ArticleAuthorEntity updatedContribution = articleAuthorRepository.save(contributionToUpdate);

        return articleAuthorConverter.convertToResponseDto(updatedContribution);

    }

    public void deleteContribution(UUID id) {
        ArticleAuthorEntity contribution = articleAuthorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Contribution with id: " + id + " wasn't found..."));

        articleAuthorRepository.delete(contribution);
    }

}
