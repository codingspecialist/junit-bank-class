package shop.mtcoding.bank.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionEnum {
    // 출금,입금,이체는 INSERT 시에 사용 (Transaction History 만들때 사용)
    // ALL은 SELECT 시에만 사용
    WITHDRAW("출금"), DEPOSIT("입금"), TRANSFER("이체"), ALL("입출금내역");

    private String value;
}
