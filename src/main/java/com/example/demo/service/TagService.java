package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.ArticleEntity;
import com.example.demo.domain.TagEntity;
import com.example.demo.dto.TagRequestDto;
import com.example.demo.dto.TagResponseDto;
import com.example.demo.dto.converter.TagConverter;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.TagRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final ArticleRepository articleRepository;
    private final TagConverter tagConverter;

    public List<TagEntity> getAllTagEntities(List<UUID> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    public List<TagResponseDto> getAllTags() {
        List<TagEntity> tags = tagRepository.findAll();

        if (tags.isEmpty()) {
            return null;
        }

        List<TagResponseDto> tagDtos = tags
                .stream()
                .map(tagConverter::convertToResponseDto)
                .collect(Collectors.toList());

        return tagDtos;
    }

    public TagResponseDto getTagById(UUID id) {
        TagEntity tag = tagRepository.findById(id).orElse(null);

        return tagConverter.convertToResponseDto(tag);
    }

    @Transactional
    public TagResponseDto createTag(TagRequestDto tagDto) {
        TagEntity tag = tagConverter.convertToDomain(tagDto);

        return tagConverter.convertToResponseDto(tagRepository.save(tag));
    }

    @Transactional
    public TagResponseDto updateTag(UUID id, TagRequestDto tagDetails) {
        TagEntity tag = tagRepository.findById(id).orElse(null);

        if (tag == null) {
            return null;
        }

        TagEntity updatedTag = tagConverter.convertToDomain(tagDetails);

        tagRepository.save(updatedTag);

        return tagConverter.convertToResponseDto(updatedTag);

    }

    public void deleteTag(UUID id) {
        TagEntity tag = tagRepository.findById(id).orElse(null);
        List<ArticleEntity> articles = articleRepository.findAllByTagsId(tag.getId());

        for (ArticleEntity article : articles) {
            article.getTags().remove(tag);
        }

        tagRepository.delete(tag);
    }
}
