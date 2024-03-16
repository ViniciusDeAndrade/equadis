package pt.com.equadis.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import pt.com.equadis.entity.Account;

import java.math.BigDecimal;

@Schema(
        description = "Account input dto(form)",
        name = "Account Form"
)
public record AccountForm(
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer=9, fraction=2)
        @Schema(
                name = "inicialBalance",
                description = "the customer must deposit when opening an account",
                example = "60.00"
        )
        BigDecimal initialBalance,
        @Positive
        @Schema(
                name = "customerId",
                description = "to create an account the customer should have been already created. Pass customer id here",
                example = "8"
        )
        Long customerId) {

}
