package com.example.backend.core.service_Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.CategoryICoreRepository;
import com.example.backend.core.model.Category;
import com.example.backend.core.service.ICategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private final CategoryICoreRepository coreCategoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return coreCategoryRepository.findAll();

    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return coreCategoryRepository.findAll(pageable);

    }

    @Override
    public Category getCategoryById(Long id) {
        return coreCategoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category Category) {
        // Validate Category
        // Save the Category
        return coreCategoryRepository.save(Category);
    }

    @Override
    public Category updateCategory(Long id, Category Category) {
        // Validate Category
        // Update the Category
        return coreCategoryRepository.update(id, Category);
    }

    @Override
    public void deleteCategory(Long id) {
        coreCategoryRepository.deleteById(id);
    }

}
