# 기술 스택
- Java 21
- Spring Boot 3.4.5 Version
- Spring Security
- JWT
- Redis

# 기능
- 회원가입
  - /users/signup
  - Info : 회원가입, 비밀번호
- 로그인
  - /users/login
  - 이메일과 비밀번호를 통해 인증
- Access Token 재발급
  - /refresh

# Test
Redis를 사용해 Refresh Token 관리하기
- Access Token을 재발급 받기 위해서는 클라이언트가 Refresh Token을 가지고 있어야 하는데 Refresh Token을 Redis로 어떻게 관리할 것인지 테스트 진행

## Token
- Access Token 만료 : 180000 (3분)
- Refresh Token 만료 : 300000 (5분)