package pt.com.equadis.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customer", description = "Customer API")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    @Operation(summary = "Creates a new costumer", description = "this operation creates a new costumer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Could not create the customer because of wrong input")
    })

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
