package com.practice.diary.controller;

import com.practice.diary.domain.AuthenticationRequest;
import com.practice.diary.domain.AuthenticationResponse;
import com.practice.diary.model.User;
import com.practice.diary.service.JwtTokenProvider;
import com.practice.diary.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthController(
    UserService userService,
    JwtTokenProvider jwtTokenProvider
  ) {
    this.userService = userService;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest authReq) {
    try {
      String token = userService.login(
        authReq.getUsername(),
        authReq.getPassword()
      );
      return ResponseEntity.ok(new AuthenticationResponse(token));
    } catch (AuthenticationException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User newUser) {
    User user = userService.register(newUser);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/update-info")
  public ResponseEntity<?> updateUserInfo(
    @RequestBody User newUser,
    @RequestHeader("Authorization") String header
  ) {
    String token = jwtTokenProvider.resolveToken(header);

    if (!jwtTokenProvider.validateToken(token)) {
      return ResponseEntity.badRequest().body("Invalid token");
    }

    String username = jwtTokenProvider.getUsername(token);
    User user = userService.updateUserInfo(username, newUser);
    return ResponseEntity.ok(user);
  }
}
