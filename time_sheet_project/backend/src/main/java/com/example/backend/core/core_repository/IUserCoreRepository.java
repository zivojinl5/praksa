package com.example.backend.core.core_repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.backend.core.model.User;

public interface IUserCoreRepository {

    Page<User> getAll(Pageable pageable);

    User findById(Long id);

    User save(User user);

    User update(Long id, User user);

    void deleteById(Long id);

    User findByUserName(String username);

}
