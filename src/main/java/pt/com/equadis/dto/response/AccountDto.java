package pt.com.equadis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "account response dto", name = "Account DTO")
public record AccountDto(Long id, BigDecimal balance) {
}
