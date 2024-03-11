package com.example.backend.web.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;

    private String name;
    private String address;
    private String city;
    private String postalCode;

    private String countryName;

}
