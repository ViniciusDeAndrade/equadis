package pt.com.equadis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.equadis.dto.form.AccountForm;
import pt.com.equadis.entity.Account;
import pt.com.equadis.entity.Customer;
import pt.com.equadis.repository.AccountRepository;
import pt.com.equadis.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private static final String CUSTOMER_NAME = "name";
    private static final BigDecimal BALANCE = BigDecimal.TEN;
    @InjectMocks
    private AccountServiceImpl accountService;
    
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private Customer customer;

    @Test
    void createAccountTest() {
        var accountForm = this.getAccountForm();
        var account = getAccount();

        given(customerRepository.findById(accountForm.customerId()))
                .willReturn(opCustomer());

        given(accountRepository.save(any(Account.class)))
                .willReturn(account);

        var expected = accountService.createAccount(accountForm);
        assertEquals(expected.balance(), BALANCE);
    }

    @Test
    void depositTest() {
    }

    @Test
    void withdrawTest() {
    }

    @Test
    void getTransactionsTest() {
    }

    private Account getAccount() {
        return new Account(opCustomer().get(), BALANCE);
    }

    private Optional<Customer> opCustomer() {
        return Optional.of(customer);
    }

    private AccountForm getAccountForm(){
        return new AccountForm(BALANCE, 2L);
    }
}