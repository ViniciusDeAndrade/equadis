package pt.com.equadis.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(
        name = "WithdrawForm",
        description = "the form for withdraw operation"
)
public record WithdrawForm(
        @Schema(
                name = "amount",
                description = "the amount the customer wish to withdraw",
                example = "80.00"
        )
        @Positive BigDecimal amount) {

}
