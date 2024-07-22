package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.AuthorEntity;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

}
