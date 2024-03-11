package com.example.backend.web.controller;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.config.JwtService;
import com.example.backend.core.create_model.ActivityCreateModel;
import com.example.backend.core.model.Activity;
import com.example.backend.core.response_model.DayResponse;
import com.example.backend.core.response_model.MonthResponse;
import com.example.backend.core.search_model.ActivitySearchModel;
import com.example.backend.core.service.IActivityService;
import com.example.backend.mapper.ActivityMapper;
import com.example.backend.web.create_dto.ActivityCreateDTO;
import com.example.backend.web.dto.ActivityDTO;
import com.example.backend.web.response_dto.MonthResponseDTO;
import com.example.backend.web.search_dto.ActivitySearchDTO;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
        private final IActivityService service;
        private final JwtService jwtService;
        private final ActivityMapper mapper;

        @GetMapping("/{id}")
        public ResponseEntity<ActivityDTO> getById(@PathVariable("id") Long id) {
                Activity foundTimeSheetEntry = service.getById(id);
                ActivityDTO foundTimeSheetEntryDTO = mapper
                                .modelToDTO(foundTimeSheetEntry);
                return ResponseEntity.ok(foundTimeSheetEntryDTO);
        }

        @PostMapping
        public ResponseEntity<ActivityDTO> create(
                        @RequestBody ActivityCreateDTO createTimeSheetEntryDTO,
                        @RequestHeader("Authorization") String token) {
                             
                ActivityCreateModel timeSheetEntryCreateModel = mapper
                                .createDTOToCreateModel(createTimeSheetEntryDTO);

                                Long userId = jwtService.extractId(token);
                                timeSheetEntryCreateModel.setUserId(userId);
                Activity createdTimeSheetEntry = service
                                .create(timeSheetEntryCreateModel);
                ActivityDTO createdTimeSheetEntryDTO = (ActivityDTO) mapper
                                .modelToDTO(createdTimeSheetEntry);
                return new ResponseEntity<>(createdTimeSheetEntryDTO, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ActivityDTO> updateTimeSheetEntry(@PathVariable("id") Long id,
                        @RequestBody ActivityDTO details,
                        @RequestHeader("Authorization") String token) {
                Long userId = jwtService.extractId(token);

                Activity model = mapper.dTOToModel(details);
                model.setUserId(userId);
                Activity updated = service.update(id, model);

                ActivityDTO updatedDTO = mapper
                                .modelToDTO(updated);
                return ResponseEntity.ok(updatedDTO);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteByID(@PathVariable("id") Long id) {
                service.deleteById(id);
                return ResponseEntity.ok("Entry deleted");
        }

        @GetMapping("/day")
        public ResponseEntity<DayResponse> getAllByDateAndUserId(@RequestBody LocalDate date,
                        @RequestHeader("Authorization") String token) {
                {
                        Long userId = jwtService.extractId(token);

                        DayResponse response = service.getAllByDateAndUserId(date, userId);
                        return ResponseEntity.ok(response);
                }
        }

        @GetMapping("/month")
        public ResponseEntity<MonthResponseDTO> search(
                        @RequestBody ActivitySearchDTO searchDTO,
                        @RequestHeader("Authorization") String token) {

                Long userId = jwtService.extractId(token);
                searchDTO.setUserId(userId);

                ActivitySearchModel searchModel = mapper.searchDTOToSearchModel(searchDTO);
                MonthResponse response = service
                                .search(searchModel);
                MonthResponseDTO responseDTO = mapper
                                .monthResponseToMonthResponseDTO(response);

                return ResponseEntity.ok(responseDTO);

        }
}
