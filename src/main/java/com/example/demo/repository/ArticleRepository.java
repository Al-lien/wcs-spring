package com.example.demo.repository;

import com.example.demo.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    List<Article> findByTitle(String title);

    List<Article> findByContent(String content);

    /**
     * A method that fetch all the articles created after a specified date
     * 
     * @param date (format YYYY-MM-DD)
     * @return a List<Article> organized in descending order by creation date;
     */
    @Query(value = "SELECT * FROM article WHERE created_at >=:date ORDER BY created_at DESC", nativeQuery = true)
    List<Article> findByCreatedAtAfter(LocalDate date);

    /**
     * A method that fetch the last five articles created
     * 
     * @return a List<Article> (5 max) organized in descending order by creation
     *         date;
     */
    @Query(value = "SELECT * FROM article ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<Article> findFirstFiveByCreatedAtOrderByCreatedAtDesc();

}
