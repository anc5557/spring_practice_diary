package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  public SecurityConfig(
    PasswordEncoder passwordEncoder,
    UserDetailsService userDetailsService
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
  }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf
            .disable()) // CSRF 보호 기능 비활성화
          .authorizeRequestsCustomizer(authorizeRequests ->
            authorizeRequests
              .antMatchers("/login", "/signup")
              .permitAll() // 로그인, 회원가입 경로는 인증 없이 접근 허용
              .anyRequest()
              .authenticated() // 그 외 모든 요청은 인증 필요
          )
          .httpBasic(AbstractHttpConfigurer::disable) // 기본 HTTP 인증 비활성화
          .apply(new JwtConfigurer(userDetailsService, passwordEncoder)); // JWT 관련 설정 추가

      return http.build();
    }

  // JWT 인증 및 인가를 처리하기 위한 설정 클래스
  // 이 클래스는 별도로 구현해야 함
  public static class JwtConfigurer
    extends AbstractHttpConfigurer<JwtConfigurer, HttpSecurity> {
    // ... JWT 관련 설정 구현
  }
}
