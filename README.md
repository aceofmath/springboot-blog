# 스프링부트 블로그

## 원소스
- Blog : https://github.com/codingspecialist/Springboot-JPA-Blog.git
- OAuth : https://github.com/codingspecialist/-Springboot-Security-OAuth2.0-V3
- Swagger : https://github.com/stylehosting/example-spring

```
서비스 등록 방법
https://deeplify.dev/back-end/spring/oauth2-social-login
``` 

## 추가 내용

### kakao 인증 추가
- Naver와 유사
- registration, provider 추가
- 변수처리

### Swagger 추가
- application 수정(spring.mvc.hiddenmethod.filter.enabled = true)
- spring-boot-starter-parent 다운(2.6.4 → 2.4.2)


### 공공데이터포털 API 추가
- 한국천문연구원 특일 정보 > 공휴일 정보 조회
- 기상청 동네예보 통보문 조회서비스 > 육상정보조회
- 국립중앙의료원 > 약국정보조회서비스
- 건강보험심사평가원 > 병원평가정보서비스, 의료기관별상세정보서비스
