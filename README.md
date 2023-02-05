# Junit Bank App

## 포스트맨 공유
- class-note에 bank.postman_collection.json 파일

## 화면설계
[ux-design](./class-note/ux-design.pdf)

## 테이블설계
[table-design](./class-note/table-design.pdf)

## 유효성검사
> [regex](./class-note/regex/regex.pdf)
> [validation](./class-note/regex/validation.png)

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
- 낮은 수 Long Value 테스트 (끝)
- cors expose 테스트 (끝)
- 컨트롤러 값 검증 테스트 (왜냐하면 서비스 단위 테스트만 하니까) (끝)
- 이체내역보기(동적쿼리) (Repo 끝)
- 계좌상세보기 (끝)

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

## 레포지토리, 컨트롤러 테스트시 주의점
```txt
    em.clear(); 를 통해서 영속성컨텍스트를 초기화 해줘야 한다.
```

## 통합테스트 기본 어노테이션 세팅
```java
@ActiveProfiles("test")
@Sql("classpath:db/teardown.sql")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
```

## 레포지토리 기본 어노테이션 세팅
```java
@ActiveProfiles("test")
@DataJpaTest
```
> 만약에 QueryDSL 빈이 필요하다면 추가하기 @Import(QueryDSLConfig.class)

## 서비스 기본 어노테이션 세팅
```java
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
```

```sql
create database metadb;
use metadb;

create table user_tb (
    id bigint auto_increment,
    username varchar(255) not null unique,
    password varchar(255) not null,
    fullname varchar(255) not null,
    email varchar(255) not null,
    role varchar(255) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    primary key (id)
);
create table account_tb (
    id bigint auto_increment,
    number bigint not null unique,
    balance bigint not null,
    password varchar(255) not null,
    user_id bigint,
    created_at timestamp not null,
    updated_at timestamp not null,
    primary key (id)
);
create index idx_account_number on account_tb (number);

create table transaction_tb (
    id bigint auto_increment,
    amount bigint not null,
    gubun varchar(255) not null, -- WITHDRAW, DEPOSIT, TRANSFER
    withdraw_account_balance bigint,
    deposit_account_balance bigint,
    deposit_account_id bigint,
    withdraw_account_id bigint,
    created_at timestamp not null,
    updated_at timestamp not null,
    primary key (id)
);
```

## 개발 더미 데이터 (통합 or 레포)
```java
 private void dataSetting() {
                User ssar = userRepository.save(newUser("ssar", "쌀"));
                User cos = userRepository.save(newUser("cos", "코스,"));
                User love = userRepository.save(newUser("love", "러브"));
                User admin = userRepository.save(newUser("admin", "관리자"));

                Account ssarAccount1 = accountRepository.save(newAccount(1111L, ssar));
                Account cosAccount = accountRepository.save(newAccount(2222L, cos));
                Account loveAccount = accountRepository.save(newAccount(3333L, love));
                Account ssarAccount2 = accountRepository.save(newAccount(4444L, ssar));

                Transaction withdrawTransaction1 = transactionRepository
                                .save(newWithdrawTransaction(ssarAccount1, accountRepository));
                Transaction depositTransaction1 = transactionRepository
                                .save(newDepositTransaction(cosAccount, accountRepository));
                Transaction transferTransaction1 = transactionRepository
                                .save(newTransferTransaction(ssarAccount1, cosAccount, accountRepository));
                Transaction transferTransaction2 = transactionRepository
                                .save(newTransferTransaction(ssarAccount1, loveAccount, accountRepository));
                Transaction transferTransaction3 = transactionRepository
                                .save(newTransferTransaction(cosAccount, ssarAccount1, accountRepository));
        }
```

## Junit 테스트시에 주의할 점
> Lombok 어노테이션 사용을 하지 않는다. Lombok이 compileOnly이기 때문에 runtime시에 작동 안한다.

## 통합테스트 MockUser 주입하는 법
> @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)

> default로 TestExecutionListener.beforeTestMethod로 설정되어 있음 @BeforeAll, @BeforeEach 실행전에 WithUserDetails가 실행되어서, DB에 User가 생기기전에 실행됨

## Security와 JWT를 함께 사용시 주의할 점
> JWT 인증, 인가 테스트를 따로 한다.

> 통합 테스트에서 인증체크는 세션값을 확인하면 된다.

## Security 설정 최신
```txt
AuthenticationManager 의존이 무한으로 의존하는 이슈가 있었다.
그래서 시큐리티 설정은 이제 @Configuration 클래스안에 @Bean으로 설정한다.
그리고 필터 설정은 전부 내부 클래스를 만들어서 AuthenticationManager를 주입받아서 필터를 설정한다.
```

## 서비스 테스트시에 주의할 점
> stub 실행시점은 service 메서드 동작시점이기 때문에, read일 때는 stub이 한개만 있어도 되지만, write일 때는 stub을 단계별로 만들고 깊은 복사를 해야 한다.

> DB에 영속화된 값이 아닌 더미데이터로 테스트하는 것이기 때문에 양방향 매핑시에는 양쪽으로 객체를 동기화 시켜줘야 한다.

## 서비스 테스트시에 참고할 어노테이션
```java
/*
 * Mock -> 진짜 객체를 추상화된 가짜 객체로 만들어서 Mockito환경에 주입함.
 * InjectMocks -> Mock된 가짜 객체를 진짜 객체 UserService를 만들어서 주입함
 * MockBean -> Mock객체들을 스프링 ApplicationContext에 주입함. (IoC컨테이너 주입)
 * Spy -> 진짜 객체를 만들어서 Mockito환경에 주입함.
 * SpyBean -> Spay객체들을 스프링 ApplicationContext에 주입함. (IoC컨테이너 주입)
 */
```

## Cors 정책 변경 공식 문서
https://developer.mozilla.org/en-US/docs/Glossary/CORS-safelisted_response_header