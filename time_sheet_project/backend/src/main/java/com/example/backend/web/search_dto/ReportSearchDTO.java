package com.example.backend.web.search_dto;

import lombok.Data;

@Data
public class ReportSearchDTO implements ISearchDTO {
    private Long teamMemberId;
    private Long clientid;
    private Long projectid;
    private Long categoryid;
    private Long startDateid;
    private Long endDateid;
}
