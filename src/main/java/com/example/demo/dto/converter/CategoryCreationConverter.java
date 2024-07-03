package com.example.demo.dto.converter;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.example.demo.domain.CategoryEntity;
import com.example.demo.dto.CategoryCreationRequestDto;

@Component
public class CategoryCreationConverter {

    public CategoryEntity convertToDomain(CategoryCreationRequestDto newCategory) {
        CategoryEntity category = new CategoryEntity();
        category.setId(newCategory.getId());
        category.setName(newCategory.getName());
        category.setArticles(new ArrayList<>());

        return category;
    }

}
