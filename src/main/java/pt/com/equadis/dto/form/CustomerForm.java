package pt.com.equadis.dto.form;

import jakarta.validation.constraints.NotBlank;
import pt.com.equadis.entity.Customer;

public record CustomerForm (@NotBlank String name){

    public Customer toEntity() {
        return new Customer(name);
    }
}
