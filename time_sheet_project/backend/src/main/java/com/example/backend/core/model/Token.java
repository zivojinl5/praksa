package com.example.backend.core.model;

import com.example.backend.enums.TokenType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Token {
  public Long id;

  public String token;
  public boolean revoked;
  public boolean expired;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public User user;

  public Token(String token, boolean revoked, boolean expired, TokenType tokenType, User user) {
    this.token = token;
    this.revoked = revoked;
    this.expired = expired;
    this.tokenType = tokenType;
    this.user = user;
}


}
