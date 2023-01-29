# Junit Bank App

시큐리티+JWT 전체 그림으로 설명하고 코드 리뷰하기

### 기능
- 의존성 설정
- yml 설정
- 엔티티 생성
- 시큐리티 설정
- 시큐리티 테스트
- 레포지토리 생성
- 회원가입
- 회원가입 테스트
- JWT 인증, 인가 테스트

- 계좌목록보기_유저별 (숙제 - 코드를 올려두기)


### Jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;
```