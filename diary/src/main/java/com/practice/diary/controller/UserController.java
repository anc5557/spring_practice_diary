package com.practice.diary.controller;

import com.practice.diary.domain.AuthenticationRequest;
import com.practice.diary.domain.AuthenticationResponse;
import com.practice.diary.model.User;
import com.practice.diary.service.UserService;
import com.practice.diary.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

  private final UserService userService;
  private final TokenUtils tokenUtils;

  public UserController(UserService userService, TokenUtils tokenUtils) {
    this.userService = userService;
    this.tokenUtils = tokenUtils;
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
    String username = tokenUtils.getUsernameFromToken(header);
    User user = userService.updateUserInfo(username, newUser);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/delete")
  public ResponseEntity<?> deleteUser(
    @RequestHeader("Authorization") String header
  ) {
    String username = tokenUtils.getUsernameFromToken(header);
    userService.deleteUser(username);
    return ResponseEntity.ok("User deleted");
  }

  @GetMapping("/profile")
  public ResponseEntity<?> getUserProfile(
    @RequestHeader("Authorization") String header
  ) {
    String username = tokenUtils.getUsernameFromToken(header);
    User user = userService.getUserProfile(username);
    return ResponseEntity.ok(user);
  }
}
