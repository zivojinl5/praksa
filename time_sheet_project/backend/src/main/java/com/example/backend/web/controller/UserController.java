package com.example.backend.web.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.backend.core.model.User;
import com.example.backend.core.req_model.ChangePasswordRequest;
import com.example.backend.core.service.IUserService;

import com.example.backend.mapper.UserMapper;
import com.example.backend.web.dto.UserDTO;
import com.example.backend.web.req_dto.ChangePasswordRequestDTO;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/teamMembers")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final IUserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> pageUsers = service.getAll(pageable);
        Page<UserDTO> pageUserDTOS = mapper.mapModelsPageDTOsPage(pageUsers);

        return ResponseEntity.ok(pageUserDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
        User foundTeamMember = service.getById(id);
        UserDTO foundTeamMemberDTO = (UserDTO) mapper.modelToDTO(foundTeamMember);
        return ResponseEntity.ok(foundTeamMemberDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Long id,
            @RequestBody UserDTO details) {
        User user = (com.example.backend.core.model.User) mapper.dTOToModel(details);
        user.setId(id);
        User updatedUser = service.update(id, user);

        UserDTO updatedUserDTO = mapper.modelToDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok("User deleted");
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequestDTO requestDTO,
            Principal connectedUser) {
        ChangePasswordRequest request = mapper.ChangePassRegDTOToChangePassReq(requestDTO);
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
