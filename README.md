# js -> java 마이그레이션(진행중)

[토이프로젝트 : 다이어리](https://github.com/anc5557/Diary)의 Backend 부분을 마이그레이션 함

## 파일 구조

```md
src
└── main
    └── java
        └── com/practice/diary
                        ├── config
                        │   └── SecurityConfig.java
                        ├── constants
                        │   └── SecurityConstants.java
                        ├── controller
                        │   └── AuthController.java
                        ├── domain
                        │   ├── AuthenticationRequest.java
                        │   └── AuthenticationResponse.java 
                        ├── model
                        │   ├── Diary.java
                        │   └── User.java
                        ├── prop
                        │   └── jwtProp.java 
                        ├── repository
                        │   └── UserRepository.java
                        └── service
                            ├── JwtTokenProvider.java        
                            └── UserService.java            

```

### config

- SecurityConfig.java
  - Spring Security 설정

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
- UserRepository.java
  - 유저 CRUD

### service

- DiaryService.java
  - 다이어리 서비스
- UserService.java
  - 유저 서비스
- JwtTokenProvider
  - JWT 토큰 생성, 검증

### domain

- AuthenticationRequest.java
  - 로그인 요청
- AuthenticationResponse.java
  - 로그인 응답

### prop

- jwtProp.java
  - JWT 토큰 설정
