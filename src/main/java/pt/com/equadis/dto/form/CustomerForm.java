package pt.com.equadis.dto.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import pt.com.equadis.entity.Customer;

@Schema(
        name = "CustomerForm",
        description = "Form for creating new customer"
)
public record CustomerForm (
        @Schema(
                name = "name",
                description = "the name of the customer",
                example = "Marcos de Andrade"
        )
        @NotBlank String name
){

    public Customer toEntity() {
        return new Customer(name);
    }
}
