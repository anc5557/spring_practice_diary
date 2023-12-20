// AuthTokenFilter.java
package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import java.io.IOException;
import java.util.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      String header = request.getHeader("Authorization");
      if (header != null && header.startsWith("Bearer ")) {
        String token = header.substring(7);
        Claims claims = Jwts
          .parser()
          .setSigningKey(jwtSecretKey)
          .parseClaimsJws(token)
          .getBody();
        String username = claims.getSubject();

        // 사용자 인증 로직
        // 예제에서는 단순화를 위해 사용자 정보 및 권한 정보를 직접 생성합니다.
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
          username,
          "",
          Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );
        authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );
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
