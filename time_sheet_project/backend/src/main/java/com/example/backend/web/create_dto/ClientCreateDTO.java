package com.example.backend.web.create_dto;

import lombok.Data;

@Data
public class ClientCreateDTO {
    private String name;
    private String address;
    private String city;
    private String postalCode;

    private String countryName;

}
