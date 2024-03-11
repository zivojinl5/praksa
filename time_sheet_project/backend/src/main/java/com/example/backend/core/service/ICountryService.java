package com.example.backend.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.model.Country;

public interface ICountryService {
    Page<Country> getAllCountries(Pageable pageable);

    List<Country> getAllCountries();

    Country getCountryById(Long id);

    Country createCountry(Country country);

    Country updateCountry(Long id, Country country);

    void deleteCountry(Long id);

}