package pt.com.equadis.dto.response;

import java.math.BigDecimal;

public record AccountDto(Long id, BigDecimal balance) {
}
