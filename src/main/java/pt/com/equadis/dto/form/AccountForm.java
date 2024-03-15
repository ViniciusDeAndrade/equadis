package pt.com.equadis.dto.form;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import pt.com.equadis.entity.Account;

import java.math.BigDecimal;

public record AccountForm(
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer=9, fraction=2)
        BigDecimal initialBalance,
        @Positive
        Long customerId) {

}
