package com.example.backend.core.core_repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.model.Category;

public interface CategoryICoreRepository {

    Page<Category> findAll(Pageable pageable);

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    Category update(Long id, Category category);

    void deleteById(Long id);

}