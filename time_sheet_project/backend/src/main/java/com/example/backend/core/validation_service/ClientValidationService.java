package com.example.backend.core.validation_service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.backend.core.model.Client;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ClientValidationService {

    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\d{5}-\\d{4}");

    public void validateClientForCreation(Client client) {
        validatePostalCodeFormat(client.getPostalCode());
    }

    public void validateClientForUpdate(Client client) {
        validatePostalCodeFormat(client.getPostalCode());

    }

    private void validatePostalCodeFormat(String postalCode) {
        if (!POSTAL_CODE_PATTERN.matcher(postalCode).matches()) {
            throw new IllegalArgumentException("Invalid postal code format");
        }
    }
}
