package pt.com.equadis.dto.form;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawForm(@Positive BigDecimal amount) {

}
