package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    List<ArticleEntity> findByTitle(String title);

    List<ArticleEntity> findByContent(String content);

    /**
     * A method that fetch all the articles created after a specified date
     * 
     * @param date (format YYYY-MM-DD)
     * @return a List<Article> organized in descending order by creation date;
     */
    @Query(value = "SELECT * FROM article WHERE created_at >=:date ORDER BY created_at DESC", nativeQuery = true)
    List<ArticleEntity> findByCreatedAtAfter(LocalDate date);

    /**
     * A method that fetch the last five articles created
     * 
     * @return a List<Article> (5 max) organized in descending order by creation
     *         date;
     */
    @Query(value = "SELECT * FROM article ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<ArticleEntity> findFirstFiveByCreatedAtOrderByCreatedAtDesc();

}
