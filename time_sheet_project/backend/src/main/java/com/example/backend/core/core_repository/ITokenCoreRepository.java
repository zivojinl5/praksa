package com.example.backend.core.core_repository;

import java.util.List;
import java.util.Optional;

import com.example.backend.core.model.Token;

public interface ITokenCoreRepository {

    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);

    Token save(Token storedToken);

    void saveAll(List<Token> validUserTokens);
}
