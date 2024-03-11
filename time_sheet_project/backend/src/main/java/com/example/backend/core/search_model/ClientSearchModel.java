package com.example.backend.core.search_model;

import lombok.Data;

@Data
public class ClientSearchModel implements ISearchModel {
    private Character startingLetter;
    private String searchString;
    private int page = 0;
    private int size = 5;
    private String sortField;
    private String sortOrder; // ASC or DESC

}
