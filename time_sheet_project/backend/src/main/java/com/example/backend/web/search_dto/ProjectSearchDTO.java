package com.example.backend.web.search_dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectSearchDTO implements ISearchDTO {
    @NotBlank(message = "Starting letter can't be null or empty")
    private Character startingLetter;

    private String searchString;

}
