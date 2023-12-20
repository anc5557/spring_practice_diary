// src/main/java/com/yourproject/model/User.java

package com.example.demo.model;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor // 기본 생성자를 만들어줌
public class User {

  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성
  private Long id;

  private String username;
  private String password;
  private String email;

  @Column(name = "first_name") // DB에 저장될 때의 컬럼명
  private String firstName;

  @Column(name = "last_name") // DB에 저장될 때의 컬럼명
  private String lastName;
}
