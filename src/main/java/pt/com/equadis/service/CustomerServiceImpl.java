package pt.com.equadis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.com.equadis.dto.response.CustomerDto;
import pt.com.equadis.dto.form.CustomerForm;
import pt.com.equadis.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public CustomerDto createCustomer(CustomerForm customer) {
        var persisted = customerRepository.save(customer.toEntity());
        return persisted.toDto();
    }
}
