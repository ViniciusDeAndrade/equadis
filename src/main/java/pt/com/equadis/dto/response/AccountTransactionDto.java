package pt.com.equadis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Account transaction response dto", name = "Account DTO")
public record AccountTransactionDto(
        Long accountId,
        BigDecimal balance,
        Long transactionId,
        String type,
        LocalDateTime timestamp) {

}
