package com.example.backend.core.core_repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.model.Country;

public interface ICountryCoreRepository {

    List<Country> findAll();

    Page<Country> findAll(Pageable pageable);

    Country findById(Long id);

    Country save(Country country);

    Country update(Long id, Country country);

    void deleteById(Long id);

}