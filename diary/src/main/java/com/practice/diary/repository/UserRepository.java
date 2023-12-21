package com.practice.diary.repository;

import com.practice.diary.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// mongoDB에 접근하는 repository
@Repository
public interface UserRepository extends MongoRepository<User, String> {
  User findByUsername(String username);
}
