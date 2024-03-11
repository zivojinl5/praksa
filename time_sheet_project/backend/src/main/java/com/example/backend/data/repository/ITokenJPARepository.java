package com.example.backend.data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.backend.core.model.Token;
import com.example.backend.data.entity.TokenEntity;

public interface ITokenJPARepository extends JpaRepository<TokenEntity, Long> {

  @Query("select t from TokenEntity t join t.user u where u.id = :id and (t.expired = false or t.revoked = false)")
  List<TokenEntity> findAllValidTokenByUser(Long id);
  

  Optional<TokenEntity> findByToken(String token);

  Optional<TokenEntity> save(Token storedToken);
}
