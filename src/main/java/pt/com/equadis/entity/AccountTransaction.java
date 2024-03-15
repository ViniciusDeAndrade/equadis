package pt.com.equadis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.com.equadis.dto.response.AccountTransactionDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class AccountTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Long accountId;

    private BigDecimal balance;

    private final LocalDateTime timestamp = LocalDateTime.now();

    public AccountTransaction(TransactionType transactionType, Long accountId, BigDecimal balance) {
        this.transactionType = transactionType;
        this.accountId = accountId;
        this.balance = balance;
    }

    public AccountTransactionDto toDto() {
        return new AccountTransactionDto(
                accountId,
                balance,
                id,
                transactionType.name(),
                timestamp
        );
    }
}
