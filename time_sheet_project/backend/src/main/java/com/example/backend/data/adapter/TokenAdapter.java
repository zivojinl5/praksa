package com.example.backend.data.adapter;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.ITokenCoreRepository;
import com.example.backend.core.model.Token;
import com.example.backend.data.entity.TokenEntity;
import com.example.backend.data.repository.ITokenJPARepository;
import com.example.backend.mapper.TokenMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class TokenAdapter implements ITokenCoreRepository {
    private final ITokenJPARepository jpaRepository;
    private final TokenMapper mapper;

    @Override
    public List<Token> findAllValidTokenByUser(Long id) {
        List<TokenEntity>entities = jpaRepository.findAllValidTokenByUser(id);
        return mapper.entitiesToModels(entities);

    }

    @Override
    public Optional<Token> findByToken(String token) {
        Optional<TokenEntity> entity = jpaRepository.findByToken(token);
        return  mapper.optionalEntityToOptionalModel(entity);

    }

    @Override
    public Token save(Token newToken) {
        TokenEntity newTokenEntity = mapper.modelToEntity(newToken);
        TokenEntity savedEntity = jpaRepository.save(newTokenEntity);
        Token savedModel = mapper.entityToModel(savedEntity);
        return savedModel;

    }

    @Override
    public void saveAll(List<Token> validUserTokens) {
        List<TokenEntity> entities = mapper.modelsToEntities(validUserTokens);
        jpaRepository.saveAll(entities);
    }

    

  
}