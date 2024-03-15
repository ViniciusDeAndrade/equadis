package pt.com.equadis.service;

import org.springframework.transaction.annotation.Transactional;
import pt.com.equadis.dto.response.CustomerDto;
import pt.com.equadis.dto.form.CustomerForm;

public interface CustomerService {

    @Transactional
    CustomerDto createCustomer(CustomerForm customer);
}
