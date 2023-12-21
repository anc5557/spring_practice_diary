package com.practice.diary.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthenticationResponse {

  public String token; // JWT 토큰

  public AuthenticationResponse(String token) {
    this.token = token;
  }
}
