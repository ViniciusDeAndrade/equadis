package pt.com.equadis.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(
        name = "DepositForm",
        description = "form for creating a new deposit operation"
)
public record DepositForm(
        @Schema(
                name = "accountId",
                description = "id of the account",
                example = "2"
        )
        @Positive Long accountId,
        @Schema(
                name = "amount",
                description = "the amount the customer wish to deposit",
                example = "80.00"
        )
        @Positive BigDecimal amount) {
}
