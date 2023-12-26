package com.practice.diary.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryDto {

  private String title; // 일기 제목
  private String content; // 일기 내용
  private LocalDateTime date; // yyyy-MM-dd
  private String username; // 유저 아이디
  private String emotion; // 감정
}
