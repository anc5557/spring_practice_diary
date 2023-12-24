package com.practice.diary.service;

import com.practice.diary.model.User;
import com.practice.diary.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  // UserRepository, PasswordEncoder, JwtTokenProvider를 주입받음
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  // 생성자
  public UserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    JwtTokenProvider jwtTokenProvider
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   *  로그인 서비스
   * @param username : 사용자 이름
   * @param password : 비밀번호
   * @return JWT 토큰
   * @throws AuthenticationException : 인증 예외
   */
  public String login(String username, String password)
    throws AuthenticationException {
    User user = userRepository.findByUsername(username); // DB에서 username으로 user를 찾음

    if (
      user == null || !passwordEncoder.matches(password, user.getPassword()) // 비밀번호가 일치하지 않으면
    ) {
      throw new BadCredentialsException("Invalid username or password");
    }

    return jwtTokenProvider.createToken(username); // JWT 토큰 생성
  }

  /**
   * 회원가입 서비스
   * @param newUser
   * @return user
   */
  public User register(User newUser) {
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    return userRepository.save(newUser);
  }

  /**
   * 사용자 정보 조회 서비스
   * @param username
   * @return user
   */
  public User getUserProfile(String username) {
    return userRepository.findByUsername(username);
  }

  /**
   * 사용자 정보 수정 서비스
   * @param username
   * @param newUser
   * @return user
   */
  public User updateUserInfo(String username, User newUser) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    user.setEmail(
      newUser.getEmail() != null ? newUser.getEmail() : user.getEmail()
    );
    user.setFirstName(
      newUser.getFirstName() != null
        ? newUser.getFirstName()
        : user.getFirstName()
    );
    user.setLastName(
      newUser.getLastName() != null ? newUser.getLastName() : user.getLastName()
    );

    return userRepository.save(user);
  }

  /**
   * 사용자 탈퇴 서비스
   * @param username
   * @return ok or bad request
   */
  public User deleteUser(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }

    userRepository.delete(user);
    return user;
  }
}
