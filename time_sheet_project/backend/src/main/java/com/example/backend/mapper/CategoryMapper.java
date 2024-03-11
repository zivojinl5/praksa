package com.example.backend.mapper;

import com.example.backend.core.model.Category;
import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.web.create_dto.CategoryCreateDTO;
import com.example.backend.web.dto.CategoryDTO;

import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CategoryMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CategoryDTO mapModelDTOCategory(Category Category) {
        return modelMapper.map(Category, CategoryDTO.class);

    }

    public Category mapDTOModelCategory(CategoryDTO dto) {
        return modelMapper.map(dto, Category.class);
    }

    public CategoryEntity mapModelEntityCategory(Category Category) {
        return modelMapper.map(Category, CategoryEntity.class);
    }

    public Category mapEntityModelCategory(CategoryEntity entity) {
        return modelMapper.map(entity, Category.class);
    }

    public Category mapCreateDTOModelCategory(CategoryCreateDTO createDTO) {
        return modelMapper.map(createDTO, Category.class);
    }

}
