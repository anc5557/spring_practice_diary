package com.practice.diary.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// 몽고 DB User 모델
/**
 * users: {
 *  _id
 * username
 * password
 * email
 * first_name
 * last_name
 */
@Document(collection = "users") // MongoDB 컬렉션 이름을 명시
@Data
@NoArgsConstructor // 기본 생성자를 만들어줌
public class User {

  @Id // MongoDB의 Document ID
  private String id; // MongoDB는 주로 String 타입의 ID를 사용

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
}
