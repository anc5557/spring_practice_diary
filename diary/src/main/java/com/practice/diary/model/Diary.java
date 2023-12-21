package com.practice.diary.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "diaries") // MongoDB 컬렉션 이름을 명시
public class Diary {

  @Id
  private String id; // MongoDB는 주로 String 타입의 ID를 사용

  private String title;
  private String content;
  private LocalDateTime date;
  private String userId; // MongoDB에는 @ManyToOne 같은 관계 매핑 어노테이션이 없으므로, 직접 참조를 관리해야 함
  private String emotion;
}
