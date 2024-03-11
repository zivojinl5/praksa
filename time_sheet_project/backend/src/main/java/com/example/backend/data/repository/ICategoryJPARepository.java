package com.example.backend.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.data.entity.CategoryEntity;

public interface ICategoryJPARepository extends JpaRepository<CategoryEntity, Long> {

}