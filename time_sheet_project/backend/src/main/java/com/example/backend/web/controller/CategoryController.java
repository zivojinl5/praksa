package com.example.backend.web.controller;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.backend.core.model.Category;
import com.example.backend.core.service.ICategoryService;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.web.create_dto.CategoryCreateDTO;
import com.example.backend.web.dto.CategoryDTO;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {
    private final ICategoryService categoryService;
    private final CategoryMapper mapper;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> CategorysPage = categoryService.getAllCategories(pageable);
        Page<CategoryDTO> CategoryDTOsPage = mapModelsPageDTOsPage(CategorysPage);

        return ResponseEntity.ok(CategoryDTOsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {
        Category foundCategory = categoryService.getCategoryById(id);
        CategoryDTO foundCategoryDTO = (CategoryDTO) mapper.mapModelDTOCategory(foundCategory);
        return ResponseEntity.ok(foundCategoryDTO);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryCreateDTO categoryCreateDTO) {
        Category Category = (com.example.backend.core.model.Category) mapper
                .mapCreateDTOModelCategory(categoryCreateDTO);
        Category createdCategory = categoryService.createCategory(Category);
        CategoryDTO createdCategoryDTO = (CategoryDTO) mapper.mapModelDTOCategory(createdCategory);
        return new ResponseEntity<>(createdCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id,
            @RequestBody CategoryDTO details) {
        Category Category = (com.example.backend.core.model.Category) mapper.mapDTOModelCategory(details);
        Category.setId(id);
        Category updatedCategory = categoryService.updateCategory(id, Category);

        CategoryDTO updatedCategoryDTO = (CategoryDTO) mapper.mapModelDTOCategory(updatedCategory);
        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted");
    }

    private Page<CategoryDTO> mapModelsPageDTOsPage(Page<Category> models) {
        List<CategoryDTO> CategoryDTOList = models.getContent().stream()
                .map(model -> (CategoryDTO) mapper.mapModelDTOCategory(model))
                .collect(Collectors.toList());

        return new PageImpl<>(CategoryDTOList, models.getPageable(), models.getTotalElements());
    }

}
