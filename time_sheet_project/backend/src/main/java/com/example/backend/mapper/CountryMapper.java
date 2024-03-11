package com.example.backend.mapper;

import com.example.backend.core.model.Country;
import com.example.backend.data.entity.CountryEntity;
import com.example.backend.web.create_dto.CountryCreateDTO;
import com.example.backend.web.dto.CountryDTO;

import lombok.NoArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CountryMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CountryDTO mapModelDTOCountry(Country country) {
        return modelMapper.map(country, CountryDTO.class);

    }

    public Country mapDTOModelCountry(CountryDTO dto) {
        return modelMapper.map(dto, Country.class);
    }

    public CountryEntity mapModelEntityCountry(Country country) {
        return modelMapper.map(country, CountryEntity.class);
    }

    public Country mapEntityModelCountry(CountryEntity entity) {
        return modelMapper.map(entity, Country.class);
    }

    public Country mapCreateDTOModelCountry(CountryCreateDTO createDTO) {
        return modelMapper.map(createDTO, Country.class);
    }

}
