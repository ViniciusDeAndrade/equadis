package pt.com.equadis.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import pt.com.equadis.dto.response.CustomerDto;
import pt.com.equadis.dto.form.CustomerForm;
import pt.com.equadis.service.CustomerService;

import java.net.URI;

@RestController
@RequestMapping("v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCostumer(
            @RequestBody @Valid CustomerForm customerForm,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var customer = customerService.createCustomer(customerForm);

        URI uri = uriComponentsBuilder
                .path("v1/customers/{id}")
                .buildAndExpand(customer.id())
                .toUri();

        return ResponseEntity.created(uri).body(customer);
    }
}
