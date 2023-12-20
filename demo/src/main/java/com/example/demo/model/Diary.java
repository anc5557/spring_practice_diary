// src/main/java/com/yourproject/model/Diary.java

package com.example.demo.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "diaries")
@Data
public class Diary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Column(length = 5000)
  private String content;

  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String emotion;
}
