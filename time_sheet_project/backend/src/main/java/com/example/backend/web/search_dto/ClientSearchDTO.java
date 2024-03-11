package com.example.backend.web.search_dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ClientSearchDTO implements ISearchDTO {
    private Character startingLetter;
    private String searchString;
    private int page = 0;
    private int size = 5;
    private String sortField;
    private String sortOrder; // ASC or DESC
}