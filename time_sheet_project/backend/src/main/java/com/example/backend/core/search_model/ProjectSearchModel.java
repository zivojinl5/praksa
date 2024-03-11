package com.example.backend.core.search_model;

import lombok.Data;

@Data
public class ProjectSearchModel implements ISearchModel {
    private Character startingLetter;
    private String searchString;
}
