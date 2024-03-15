package pt.com.equadis.dto.form;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositForm(@Positive Long accountId, @Positive BigDecimal amount) {
}
