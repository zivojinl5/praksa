package com.example.backend.core.service;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.model.User;
import com.example.backend.core.req_model.ChangePasswordRequest;

public interface IUserService {

    Page<User> getAll(Pageable pageable);

    User getById(Long id);

    User getByUserName(String username);

    User update(Long id, User user);

    void delete(Long id);

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

}
