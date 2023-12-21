package com.example.demo.controller;

import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  // 로그인 API
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    try {
      String token = userService.loginUser(
        loginRequest.getUsername(),
        loginRequest.getPassword()
      );
      return ResponseEntity.ok(
        new LoginResponse(token, "로그인에 성공하였습니다.")
      );
    } catch (RuntimeException e) {
      return ResponseEntity
        .badRequest()
        .body(new ErrorResponse("로그인에 실패하였습니다. " + e.getMessage()));
    }
  }

  // 로그인 요청을 위한 내부 클래스
  @Getter
  @Setter
  static class LoginRequest {

    private String username;
    private String password;
  }

  // 로그인 응답을 위한 내부 클래스
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  static class LoginResponse {

    private String token;
    private String message;
  }

  // 에러 응답을 위한 별도의 클래스
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  static class ErrorResponse {

    private String message;
  }
}
