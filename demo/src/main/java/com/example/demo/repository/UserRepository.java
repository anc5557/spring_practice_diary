package com.example.demo.repository;

import com.example.demo.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
  // 사용자명으로 사용자 찾기
  Optional<User> findByUsername(String username);

  // pk로 사용자 찾기(User 객체에서 password 필드는 제외)
  Optional<UserProjection> findProjectionById(Long id);
}
