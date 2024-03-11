package com.example.backend.data.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.ICountryCoreRepository;
import com.example.backend.core.model.Country;
import com.example.backend.data.entity.CountryEntity;
import com.example.backend.data.repository.ICountryJPARepository;
import com.example.backend.mapper.CountryMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class CountryAdapter implements ICountryCoreRepository {

    private final ICountryJPARepository countryJPARepository;
    private final CountryMapper mapper;

    @Override
    public List<Country> findAll() {
        List<CountryEntity> entityList = countryJPARepository
                .findAll();
        return mapEntitiesToModels(entityList);
    }

    @Override
    public Page<Country> findAll(Pageable pageable) {
        Page<CountryEntity> page = countryJPARepository
                .findAll(pageable);
        return mapEntitiesPageToModelsPage(page);

    }

    @Override
    public Country findById(Long id) {
        CountryEntity entity = countryJPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));
        return (Country) mapper.mapEntityModelCountry(entity);

    }

    @Override
    public Country save(Country model) {
        CountryEntity entity = (CountryEntity) mapper.mapModelEntityCountry(model);
        CountryEntity createdEntity = countryJPARepository.save(entity);
        return (Country) mapper.mapEntityModelCountry(createdEntity);
    }

    @Override
    public Country update(Long id, Country model) {
        if (!countryJPARepository.existsById(id)) {
            return null;
        }
        CountryEntity entity = (CountryEntity) mapper.mapModelEntityCountry(model);
        entity.setId(id);
        CountryEntity updatedEntity = countryJPARepository.save(entity);
        return (Country) mapper.mapEntityModelCountry(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        countryJPARepository.deleteById(id);
    }

    private List<Country> mapEntitiesToModels(List<CountryEntity> entities) {
        return entities.stream()
                .map(entity -> (Country) mapper.mapEntityModelCountry(entity))
                .collect(Collectors.toList());
    }

    private Page<Country> mapEntitiesPageToModelsPage(Page<CountryEntity> entities) {
        List<Country> countries = entities.getContent().stream()
                .map(entity -> (Country) mapper.mapEntityModelCountry(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(countries, entities.getPageable(), entities.getTotalElements());
    }
}
