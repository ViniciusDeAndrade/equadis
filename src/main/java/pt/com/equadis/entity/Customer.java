package pt.com.equadis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.com.equadis.dto.response.CustomerDto;

@Entity
@NoArgsConstructor
@Getter
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public CustomerDto toDto() {
        return new CustomerDto(id, name);
    }
}
