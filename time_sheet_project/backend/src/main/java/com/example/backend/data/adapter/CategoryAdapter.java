package com.example.backend.data.adapter;

import com.example.backend.core.core_repository.CategoryICoreRepository;
import com.example.backend.core.model.Category;
import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.repository.ICategoryJPARepository;
import com.example.backend.mapper.CategoryMapper;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class CategoryAdapter implements CategoryICoreRepository {

    private final ICategoryJPARepository categoryJPARepository;
    private final CategoryMapper mapper;

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> entityList = categoryJPARepository.findAll();
        return mapEntitiesToModels(entityList);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        Page<CategoryEntity> page = categoryJPARepository
                .findAll(pageable);
        return mapEntitiesPageToModelsPage(page);

    }

    @Override
    public Category findById(Long id) {
        CategoryEntity entity = categoryJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return mapper.mapEntityModelCategory(entity);

    }

    @Override
    public Category save(Category model) {
        CategoryEntity entity = (CategoryEntity) mapper.mapModelEntityCategory(model);
        CategoryEntity createdEntity = categoryJPARepository.save(entity);
        return (Category) mapper.mapEntityModelCategory(createdEntity);
    }

    @Override
    public Category update(Long id, Category model) {
        if (!categoryJPARepository.existsById(id)) {
            return null;
        }
        CategoryEntity entity = (CategoryEntity) mapper.mapModelEntityCategory(model);
        entity.setId(id);
        CategoryEntity updatedEntity = categoryJPARepository.save(entity);
        return (Category) mapper.mapEntityModelCategory(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        categoryJPARepository.deleteById(id);

    }

    private List<Category> mapEntitiesToModels(List<CategoryEntity> entities) {
        return entities.stream()
                .map(entity -> (Category) mapper.mapEntityModelCategory(entity))
                .collect(Collectors.toList());
    }

    private Page<Category> mapEntitiesPageToModelsPage(Page<CategoryEntity> entities) {
        List<Category> categories = entities.getContent().stream()
                .map(entity -> (Category) mapper.mapEntityModelCategory(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(categories, entities.getPageable(), entities.getTotalElements());
    }
}
