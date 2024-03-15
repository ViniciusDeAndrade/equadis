package pt.com.equadis.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountTransactionDto(
        Long accountId,
        BigDecimal balance,
        Long transactionId,
        String type,
        LocalDateTime timestamp) {

}
