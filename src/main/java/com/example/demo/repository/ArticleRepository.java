package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    List<ArticleEntity> findByTitle(String title);

    List<ArticleEntity> findByContent(String content);

    List<ArticleEntity> findAllByTagsId(UUID tagId);

    List<ArticleEntity> findAllByCategoryName(String category);

}
