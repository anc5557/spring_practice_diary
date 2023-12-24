package com.practice.diary.util;

import com.practice.diary.service.JwtTokenProvider;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

  private final JwtTokenProvider jwtTokenProvider;

  public TokenUtils(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public String getUsernameFromToken(String header) {
    String token = jwtTokenProvider.resolveToken(header);
    if (!jwtTokenProvider.validateToken(token)) {
      throw new IllegalArgumentException("Invalid token");
    }
    return jwtTokenProvider.getUsername(token);
  }
}
