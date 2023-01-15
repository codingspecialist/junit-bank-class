# Junit Bank App

### 기능
- 의존성 설정
- yml 설정
- 엔티티 생성
- 시큐리티 설정
- 시큐리티 테스트
- 레포지토리 생성
- 회원가입
- 회원가입 테스트

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