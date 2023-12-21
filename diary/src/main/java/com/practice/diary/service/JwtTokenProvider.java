package com.practice.diary.service;

import com.practice.diary.constants.SecurityConstants;
import com.practice.diary.prop.jwtProp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenProvider {

  private final jwtProp jwtProp;

  public JwtTokenProvider(jwtProp jwtProp) {
    this.jwtProp = jwtProp;
  }

  public String createToken(String username) {
    // 서명에 사용할 키 생성 (새로운 방식)
    byte[] signingKey = jwtProp.getSecretKey().getBytes();

    // JWT 토큰 생성
    String jwt = Jwts
      .builder()
      .signWith(Keys.hmacShaKeyFor(signingKey), Jwts.SIG.HS512) // 서명에 사용할 키와 알고리즘 설정
      .header()
      .add("typ", SecurityConstants.TOKEN_TYPE) // 헤더 설정
      .and()
      .expiration(new Date(System.currentTimeMillis() + 864000000)) // 토큰 만료 시간 설정 (10일)
      .claim("uid", username) // 클레임 설정: 사용자 아이디
      .compact(); // 최종적으로 토큰 생성

    return jwt;
  }

  public String getUsername(String token) {
    // 서명에 사용할 키 생성
    byte[] signingKey = jwtProp.getSecretKey().getBytes();

    // 토큰 파싱
    Jws<Claims> parsedToken = Jwts
      .parser()
      .verifyWith(Keys.hmacShaKeyFor(signingKey))
      .build()
      .parseSignedClaims(token);

    // 사용자 이름 반환
    return parsedToken.getPayload().get("uid").toString();
  }

  public boolean validateToken(String token) {
    // 토큰 유효성 검증

    // 서명에 사용할 키 생성
    byte[] signingKey = jwtProp.getSecretKey().getBytes();

    // 토큰 파싱
    Jws<Claims> parsedToken = Jwts
      .parser()
      .verifyWith(Keys.hmacShaKeyFor(signingKey))
      .build()
      .parseSignedClaims(token);

    // 토큰 만료 시간 검증
    Date expiration = parsedToken.getPayload().getExpiration();
    if (expiration.before(new Date())) {
      return false;
    }

    return true;
  }

  public String resolveToken(String bearerToken) {
    // 헤더에서 토큰 추출
    if (
      bearerToken != null &&
      bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)
    ) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
