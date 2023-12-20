package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // (1) Spring Security 설정 클래스임을 명시합니다.
@EnableWebSecurity // (2) Spring Security를 활성화합니다.
public class SecurityConfig {

  private final String jwtSecretKey = "your_secret_key"; // 실제 환경에서는 환경변수나 설정 파일에서 불러오세요.

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) // (3) SecurityFilterChain을 빈으로 등록합니다.
    throws Exception {
    http
      .csrf(csrf -> csrf.disable()) 
      .authorizeHttpRequests(authorize -> 
        authorize
          .requestMatchers("/public/**")
          .permitAll()
          .anyRequest() 
          .authenticated()
      )
      .addFilter(new AuthTokenFilter(jwtSecretKey));

    return http.build();
  }
}
