package com.practice.diary.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// jwt 비밀키를 java 코드에서 관리하기 위한 클래스

// 해당 클래스는 Spring Boot의 `@ConfigurationProperties`
// 어노테이션을 사용하여, application.properties(속성 설정 파일) 로부터
// JWT 관련 프로퍼티를 관리하는 프로퍼티 클래스입니다.
@Data
@Component // Spring Bean으로 등록
@ConfigurationProperties("com.practice.diary") // com.practice.diary 경로 하위 속성들을 지정
public class jwtProp {

  // com.practice.diary.secretKey로 지정된 프로퍼티 값을 주입받는 필드
  // ✅ com.practice.diary.secret-key ➡ secretKey : {인코딩된 시크릿 키}
  private String secretKey; // 시크릿 키이기 때문에 다른 곳에서 접근 할 수 있도록 Data 어노테이션을 사용하여 getter, setter를 만들어줍니다.
}
