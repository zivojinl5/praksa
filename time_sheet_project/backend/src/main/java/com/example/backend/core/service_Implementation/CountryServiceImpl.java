package com.example.backend.core.service_Implementation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.backend.core.core_repository.ICountryCoreRepository;
import com.example.backend.core.model.Country;
import com.example.backend.core.service.ICountryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CountryServiceImpl implements ICountryService {

    private final ICountryCoreRepository coreCountryRepository;

    @Override
    public List<Country> getAllCountries() {
        return coreCountryRepository.findAll();

    }

    @Override
    public Page<Country> getAllCountries(Pageable pageable) {
        return coreCountryRepository.findAll(pageable);

    }

    @Override
    public Country getCountryById(Long id) {
        return coreCountryRepository.findById(id);
    }

    @Override
    public Country createCountry(Country Country) {
        // Validate Country
        // Save the Country
        return coreCountryRepository.save(Country);
    }

    @Override
    public Country updateCountry(Long id, Country Country) {
        // Validate Country
        // Update the Country
        return coreCountryRepository.update(id, Country);
    }

    @Override
    public void deleteCountry(Long id) {
        coreCountryRepository.deleteById(id);
    }

}
