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
                        │   └── DiaryController.java
                        ├── dto
                        │   └── DiaryDto.java
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
                        │   └── DiaryRepository.java
                        └── service
                            ├── JwtTokenProvider.java        
                            └── UserService.java            

```

### config

- SecurityConfig.java
  - Spring Security 설정

### controller

- 컨트롤러 : 클라이언트의 요청을 받아서 처리하는 부분
  - AuthController.java
    - 회원가입, 로그인, 로그아웃, 회원탈퇴
  - DiaryController.java
    - 다이어리 작성, 수정, 삭제, 조회

### model

- 모델 : 데이터베이스의 테이블과 매핑되는 부분
  - Diary.java
    - 다이어리 모델
  - User.java
    - 유저 모델

### repository

- Repository : 데이터베이스에 접근하는 부분
  - DiaryRepository.java
    - 다이어리 CRUD
  - UserRepository.java
    - 유저 CRUD

### service

- Service : 비즈니스 로직을 처리하는 부분
  - DiaryService.java
    - 다이어리 서비스
  - UserService.java
    - 유저 서비스
  - JwtTokenProvider
    - JWT 토큰 생성, 검증

### domain

- 도메인 : 로그인 요청, 응답
  - AuthenticationRequest.java
    - 로그인 요청
  - AuthenticationResponse.java
    - 로그인 응답

### prop

- prop : 상수값
  - jwtProp.java
    - JWT 토큰 설정

### util

- util : 유틸리티
  - TokenUtils.java
    - 토큰 생성, 검증

### dto

- dto : 데이터 전송 객체
  - DiaryDto.java
    - 다이어리 데이터 전송 객체