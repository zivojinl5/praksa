package com.example.backend.mapper;

import com.example.backend.core.model.Token;
import com.example.backend.data.entity.TokenEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class TokenMapper {

    private final ModelMapper modelMapper = new ModelMapper();

   

    public TokenEntity modelToEntity(Token token) {
        return modelMapper.map(token, TokenEntity.class);
    }

    public Token entityToModel(TokenEntity entity) {
        return modelMapper.map(entity, Token.class);
    }
    public Optional<TokenEntity> optionalModelToOptionalEntity(Optional<Token> tokenOptional) {
        return tokenOptional.map(token -> modelToEntity(token));
    }

    public Optional<Token> optionalEntityToOptionalModel(Optional<TokenEntity> entityOptional) {
        return entityOptional.map(entity -> entityToModel(entity));
    }

    public List<Token> entitiesToModels(List<TokenEntity> entities) {
        return entities.stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());
    }

    public List<TokenEntity> modelsToEntities(List<Token> models) {
        return models.stream()
                .map(model -> modelToEntity(model))
                .collect(Collectors.toList());
    }
}
