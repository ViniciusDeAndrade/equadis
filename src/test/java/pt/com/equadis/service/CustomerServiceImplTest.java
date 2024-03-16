package pt.com.equadis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.equadis.dto.form.CustomerForm;
import pt.com.equadis.dto.response.CustomerDto;
import pt.com.equadis.entity.Customer;
import pt.com.equadis.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    public static final String TESTE_NAME = "teste name";
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerForm customerForm;

    @Test
    void createCustomerTest() {

        given(repository.save(customerForm.toEntity()))
                .willReturn(this.mockEntityCustomer());

        CustomerDto expected = customerService.createCustomer(customerForm);

        assertEquals(TESTE_NAME, expected.name());
    }

    private Customer mockEntityCustomer() {
        return new Customer(TESTE_NAME);
    }
}