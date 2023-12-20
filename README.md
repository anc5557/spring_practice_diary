# js -> java 마이그레이션

[토이프로젝트 : 다이어리](https://github.com/anc5557/Diary)의 Backend 부분을 마이그레이션 함(진행중)

## 파일 구조

```md
- src
  - main
    - java/com/example/demo
            - config
              - WebConfig.java
            - controller
              - AuthController.java
            - model
              - Diary.java
              - User.java
            - repository
              - DiaryRepository.java
              - UserProjection.java
              - UserRepository.java
            - security
              - AuthTokenFilter.java
              - SecurityConfig.java
            - service
              - DiaryService.java
              - UserService.java
            - DemoApplication.java
    - resources
      - application.properties(.gitignore)
```

### config

- WebConfig.java
  - CORS 설정

### controller

- AuthController.java
  - 회원가입, 로그인, 로그아웃, 회원탈퇴
- DiaryController.java
  - 다이어리 작성, 수정, 삭제, 조회

### model

- Diary.java
  - 다이어리 모델
- User.java
  - 유저 모델

### repository

- DiaryRepository.java
  - 다이어리 CRUD
- UserProjection.java
  - 유저 모델의 일부만 반환
- UserRepository.java
  - 유저 CRUD

### security

- AuthTokenFilter.java
  - JWT 토큰 인증
- SecurityConfig.java
  - Spring Security 설정
  
### service

- DiaryService.java
  - 다이어리 서비스
- UserService.java
  - 유저 서비스
