// AuthTokenFilter.java
package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthTokenFilter extends OncePerRequestFilter {

  private final String jwtSecretKey;

  public AuthTokenFilter(String jwtSecretKey) {
    this.jwtSecretKey = jwtSecretKey;
  }

  // JWT 토큰 검증 및 사용자 ID 추출
  private Long getUserIdFromToken(String token) {
    try {
      Claims claims = Jwts
        .parser()
        .setSigningKey(jwtSecretKey)
        .parseClaimsJws(token)
        .getBody();
      return claims.get("userId", Long.class);
    } catch (SignatureException e) {
      throw new RuntimeException("토큰 검증 실패: " + e.getMessage());
    }
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      // 헤더에서 JWT 토큰 추출
      String jwtToken = request.getHeader("Authorization");
      if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
        jwtToken = jwtToken.substring(7);
      }

      // JWT 토큰 검증 및 사용자 ID 추출
      Long userId = getUserIdFromToken(jwtToken);

      // JWT 토큰 검증을 통과한 경우
      if (userId != null) {
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userId, // 인증 토큰에 담을 사용자 ID
          null,
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        // 요청 정보 설정
        authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // SecurityContext에 인증 토큰 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (ExpiredJwtException | SignatureException e) {
      // JWT 오류 처리
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
