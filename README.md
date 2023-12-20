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
#### config

