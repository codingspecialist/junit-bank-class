package shop.mtcoding.bank.domain.transaction;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.bank.domain.account.Account;

@NoArgsConstructor // 스프링이 User 객체생성할 때 빈생성자로 new를 하기 때문!!
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction_tb")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private Account withdrawAccount; // null 허용됨

    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne(fetch = FetchType.LAZY)
    private Account depositAccount; // null 허용됨

    @Column(nullable = false)
    private Long amount;

    private Long withdrawAccountBalance; // 1111 계좌 -> 1000원 -> 500원 -> 200원
    private Long depositAccountBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionEnum gubun; // WITHDRAW, DEPOSIT, TRANSFER, ALL

    // 계좌가 사라져도 로그는 남아야 한다.
    private String sender; // 1111계좌 or ATM
    private String reciver; // 2222계좌 or ATM
    private String tel; // ATM -> 1111 입금 (ATM기로 무통장 입금 - 전화번호는 좀 남겨나야 함)

    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount,
            Long withdrawAccountBalance, Long depositAccountBalance, TransactionEnum gubun, String sender,
            String reciver, String tel, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.withdrawAccount = withdrawAccount;
        this.depositAccount = depositAccount;
        this.amount = amount;
        this.withdrawAccountBalance = withdrawAccountBalance;
        this.depositAccountBalance = depositAccountBalance;
        this.gubun = gubun;
        this.sender = sender;
        this.reciver = reciver;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}

/**
 * withdrawAccount = null, depositAccount = 2222계좌 (ATM->2222) (입금내역)
 * withdrawAccount = 1111계좌, depositAccount = 2222계좌 (1111->2222) (이체내역)
 * withdrawAccount = 1111계좌, depositAccount = null (1111->ATM) (출금내역)
 */