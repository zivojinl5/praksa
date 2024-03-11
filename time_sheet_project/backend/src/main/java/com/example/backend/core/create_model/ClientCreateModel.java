package com.example.backend.core.create_model;

import lombok.Data;

@Data
public class ClientCreateModel {

    private String name;
    private String address;
    private String city;
    private String postalCode;

    private String countryName;

}
