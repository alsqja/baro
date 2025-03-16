# 바로 인턴 프로젝트

- **Github Repo**: [https://github.com/alsqja/baro](https://github.com/alsqja/baro)
- **배포 링크**: [http://ec2-54-180-147-103.ap-northeast-2.compute.amazonaws.com:8080](http://ec2-54-180-147-103.ap-northeast-2.compute.amazonaws.com:8080)
- **Swagger API 문서**: [http://ec2-54-180-147-103.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html](http://ec2-54-180-147-103.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html)
- **과제 요구사항 (노션)**: [https://teamsparta.notion.site/Java-1ad2dc3ef51481c49c27e8d618c1c671](https://teamsparta.notion.site/Java-1ad2dc3ef51481c49c27e8d618c1c671)

<br/>

## 프로젝트 소개
JWT 인증 방식을 적용한 Spring Boot 기반 REST API입니다. 회원가입 및 로그인 기능을 구현하고, Swagger를 이용해 API 문서를 제공합니다.

- **주요 기능**  
  - 회원가입 / 로그인 (JWT 발급, 유효성 검증)  
  - Swagger를 통한 API 명세 제공  
  - Spring Security 기반 권한 관리  

<br/>

## 기술 스택

- **Back-end**: 
  - Java 17
  - Spring Boot 3.4.x
  - Spring Security
  - Spring Validation
  - JJWT (JSON Web Token)
- **빌드/환경**:  
  - Gradle  
  - Amazon EC2 (Ubuntu)  
  - OpenJDK 17  

<br/>

## 프로젝트 구조 (간략 예시)

```
baro/
 ┣ src/
 ┃ ┣ main/
 ┃ ┃ ┣ java/com/example/baro
 ┃ ┃ ┃ ┣ config/      # JWT 및 Security 설정
 ┃ ┃ ┃ ┣ controller/  # REST API 컨트롤러
 ┃ ┃ ┃ ┣ model/       # 엔티티, dto
 ┃ ┃ ┃ ┣ service/     # 서비스 계층
 ┃ ┃ ┃ ┗ util/        # JWTProvider 등 유틸
 ┃ ┃ ┣ resources/
 ┃ ┃ ┃ ┗ application.properties
 ┃ ┗ test/
 ┣ build.gradle
 ┗ README.md
```

<br/>

## API 명세 (Swagger)

- **Swagger UI** 경로:  
  [http://ec2-54-180-147-103.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html](http://ec2-54-180-147-103.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html)  
- 회원가입 / 로그인 / JWT 토큰 발급 등에 대한 상세한 인터페이스가 제공됩니다.

<br/>

## 과제 요구사항

1. **JWT 기반 인증/인가**  
   - 회원가입 후 로그인 시 JWT를 발급하고, API 호출 시 토큰 검증을 수행합니다.
2. **Spring Security 도입**  
   - 비인가 사용자의 접근을 제한합니다.
3. **Swagger 문서화**  
   - API 상세 스펙을 Swagger UI로 확인할 수 있습니다.
4. 기타 배포, 예외 처리, 유효성 검사 등 요구사항 반영
