package com.example.backend.data.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.IUserCoreRepository;
import com.example.backend.core.model.User;
import com.example.backend.data.entity.UserEntity;
import com.example.backend.data.repository.IUserJPARepository;
import com.example.backend.mapper.UserMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class UserAdapter implements IUserCoreRepository {

    private final IUserJPARepository jPARepository;
    private final UserMapper mapper;

    @Override
    public Page<User> getAll(Pageable pageable) {
        Page<UserEntity> entitiesPage = jPARepository.findAll(
                pageable);

        return mapper.entitiesPageToModelsPage(entitiesPage);

    }

    @Override
    public User findById(Long id) {
        UserEntity entity = jPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapper.entityToModel(entity);

    }

    @Override
    public User save(User model) {
        UserEntity entity = mapper.modelToEntity(model);
        UserEntity createdEntity = jPARepository.save(entity);
        return mapper.entityToModel(createdEntity);
    }

    @Override
    public User update(Long id, User details) {
        UserEntity target = jPARepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        target = mapper.modelToEntityForPatch(details, target);
        target.setId(id);
        UserEntity updatedEntity = jPARepository.save(target);

        return mapper.entityToModel(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jPARepository.deleteById(id);
    }

    @Override
    public User findByUserName(String username) {
        UserEntity target = jPARepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        return mapper.entityToModel(target);

    }

}
