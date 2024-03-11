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

import com.example.backend.core.model.Country;
import com.example.backend.core.service.ICountryService;
import com.example.backend.mapper.CountryMapper;
import com.example.backend.web.create_dto.CountryCreateDTO;
import com.example.backend.web.dto.CountryDTO;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/countries")
@PreAuthorize("hasRole('ADMIN')")
public class CountryController {
    private final ICountryService countryService;
    private final CountryMapper mapper;

    @GetMapping
    public ResponseEntity<Page<CountryDTO>> getAllCountries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Country> CountrysPage = countryService.getAllCountries(pageable);
        Page<CountryDTO> CountryDTOsPage = mapModelsPageDTOsPage(CountrysPage);

        return ResponseEntity.ok(CountryDTOsPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable("id") Long id) {
        Country foundCountry = countryService.getCountryById(id);
        CountryDTO foundCountryDTO = mapper.mapModelDTOCountry(foundCountry);
        return ResponseEntity.ok(foundCountryDTO);
    }

    @PostMapping
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryCreateDTO CountryCreateDTO) {
        Country Country = mapper
                .mapCreateDTOModelCountry(CountryCreateDTO);
        Country createdCountry = countryService.createCountry(Country);
        CountryDTO createdCountryDTO = mapper.mapModelDTOCountry(createdCountry);
        return new ResponseEntity<>(createdCountryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable("id") Long id,
            @RequestBody CountryDTO details) {
        Country Country = (com.example.backend.core.model.Country) mapper.mapDTOModelCountry(details);
        Country.setId(id);
        Country updatedCountry = countryService.updateCountry(id, Country);

        CountryDTO updatedCountryDTO = (CountryDTO) mapper.mapModelDTOCountry(updatedCountry);
        return ResponseEntity.ok(updatedCountryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable("id") Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok("Country deleted");
    }

    private Page<CountryDTO> mapModelsPageDTOsPage(Page<Country> models) {
        List<CountryDTO> CountryDTOList = models.getContent().stream()
                .map(model -> (CountryDTO) mapper.mapModelDTOCountry(model))
                .collect(Collectors.toList());

        return new PageImpl<>(CountryDTOList, models.getPageable(), models.getTotalElements());
    }

}
