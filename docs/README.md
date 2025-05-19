# Redis를 사용해 JWT 관리하기
- CrudRepository를 상속받아 구현
- Redis Template 사용 안 함

## 기술 스택
- Java 21
- Spring Boot 3.4.5 Version
- Spring Security
- JWT
- Redis

### 의존성
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-data-redis'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	compileOnly 'io.jsonwebtoken:jjwt-api:0.12.3'
	runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
	runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'
	runtimeOnly 'com.mysql:mysql-connector-j'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

## 기능
- 회원가입
  - /auth/signup
  - 이메일과 비밀번호만 입력
- 로그인
  - /auth/login
  - 이메일과 비밀번호를 통해 인증
- 로그아웃
  - /auth/logout
- Access Token 재발급
  - /auth/refresh
  - Refresh Token을 요청 값으로 보내고, Access Token을 재발급 받음
 
  ## 보완
  - https://github.com/jiyeon0926/rht-auth-redis-crud
