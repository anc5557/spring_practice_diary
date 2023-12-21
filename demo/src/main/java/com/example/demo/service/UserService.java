// src/main/java/com/example/demo/service/UserService.java
package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserProjection;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// 유저 서비스
// 사용자 등록, 로그인, 사용자 정보 조회, 사용자 정보 수정, 사용자 정보 삭제

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${jwt.secretKey}")
  private String secretKey;

  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  // jwt 토큰 발급
  public String generateJwtToken(User user) {
    // 토큰 만료 시간 설정 (예: 24시간)
    long EXPIRATION_TIME = 86400000; // 24시간 (밀리초 단위)
    Date issuedAt = new Date(); // 토큰 발급 시간
    Date expiresAt = new Date(issuedAt.getTime() + EXPIRATION_TIME); // 토큰 만료 시간

    // 토큰 생성
    String jwtToken = Jwts
      .builder()
      .claim("userId", user.getId()) // 토큰에 담을 정보 설정
      .setSubject(user.getUsername()) // 토큰 용도
      .setIssuedAt(issuedAt) // 토큰 발급 시간
      .setExpiration(expiresAt) // 토큰 만료 시간
      .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret 값 설정
      .compact();

    return jwtToken;
  }



  // 사용자 등록(회원가입)
  public User registerUser(User user) {
    // 필수 입력값 체크(username, password, email)
    if (
      user.getUsername() == null ||
      user.getPassword() == null ||
      user.getEmail() == null
    ) {
      throw new RuntimeException("필수 입력값이 없습니다.");
    }

    // 사용자명(username) 중복 체크
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new RuntimeException("사용자명이 중복됩니다.");
    }

    // 이메일(email) 형식 체크
    if (
      !user
        .getEmail()
        .matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    ) {
      throw new RuntimeException("이메일 형식이 올바르지 않습니다.");
    }

    // 비밀번호 형식 체크
    if (
      !user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$")
    ) {
      throw new RuntimeException("비밀번호 형식이 올바르지 않습니다.");
    }

    // 비밀번호 암호화
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    // 사용자 등록
    return userRepository.save(user);
  }

  // 로그인
  public String loginUser(String username, String password) {
    // 사용자명(username)으로 사용자 찾기
    User user = userRepository
      .findByUsername(username)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    // 비밀번호 일치 여부 확인
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    // 로그인 성공, jwt 토큰 발급, 성공 메시지 반환
    return generateJwtToken(user);
  }

  // 사용자 정보 조회
  public UserProjection getUserInfoFromToken(String token) {
    // 토큰에서 사용자 ID 추출
    Long userId = getUserIdFromToken(token);
    // 사용자 조회
    return userRepository
      .findProjectionById(userId)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
  }

  // 회원 정보 변경
  // email, firstName, lastName 필드만 변경 가능
  public User updateUserInfo(
    String token,
    String email,
    String firstName,
    String lastName
  ) {
    // 토큰에서 사용자 ID 추출
    Long userId = getUserIdFromToken(token);
    // 사용자 조회
    User existingUser = userRepository
      .findById(userId)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    // 제공된 값이 있는 경우에만 업데이트
    if (email != null && !email.isEmpty()) {
      existingUser.setEmail(email);
    }
    if (firstName != null && !firstName.isEmpty()) {
      existingUser.setFirstName(firstName);
    }
    if (lastName != null && !lastName.isEmpty()) {
      existingUser.setLastName(lastName);
    }

    // 변경된 정보 저장
    return userRepository.save(existingUser);
  }

  // 회원 탈퇴
  public void deleteUser(String token) {
    // 토큰에서 사용자 ID 추출
    Long userId = getUserIdFromToken(token);
    // 사용자 조회
    User existingUser = userRepository
      .findById(userId)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    // 사용자 삭제
    userRepository.delete(existingUser);
  }
}
