# Junit Bank App

시큐리티+JWT 전체 그림으로 설명하고 코드 리뷰하기

### 기능
- 의존성 설정 (끝)
- yml 설정 (끝)
- 엔티티 생성 (끝)
- 시큐리티 설정 (끝)
- 시큐리티 테스트 (끝)
- 레포지토리 생성 (끝)
- 유효성검사 테스트 (끝)
- 회원가입 (끝)
- 회원가입 테스트 (끝)
- JWT 인증, 인가 테스트 (끝)
- 계좌 등록 (끝)
- 계좌목록보기_유저별 (숙제 - 코드를 올려두기) (끝)
- 계좌삭제 (끝)
- 계좌입금 (끝)
- 계좌출금 (끝)
- 게좌이체 (끝)
- 낮은 수 Long Value 테스트
- admin 검증 테스트
- cors expose 테스트
- 컨트롤러 값 검증 테스트 (왜냐하면 서비스 단위 테스트만 하니까)
- 이체내역보기(동적쿼리)
- 계좌상세보기



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