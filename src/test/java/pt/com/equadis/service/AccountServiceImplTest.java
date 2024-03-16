package pt.com.equadis.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.equadis.dto.form.AccountForm;
import pt.com.equadis.dto.form.DepositForm;
import pt.com.equadis.dto.form.WithdrawForm;
import pt.com.equadis.dto.response.AccountTransactionDto;
import pt.com.equadis.entity.Account;
import pt.com.equadis.entity.AccountTransaction;
import pt.com.equadis.entity.Customer;
import pt.com.equadis.entity.TransactionType;
import pt.com.equadis.error.CustomerNotFoundException;
import pt.com.equadis.repository.AccountRepository;
import pt.com.equadis.repository.CustomerRepository;
import pt.com.equadis.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
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
    private TransactionRepository transactionRepository;

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
    void createAccountFailTest() {
        var accountForm = this.getAccountForm();
        given(customerRepository.findById(accountForm.customerId()))
                .willThrow(CustomerNotFoundException.class);

        assertThrows(CustomerNotFoundException.class, () -> accountService.createAccount(accountForm));
    }

    @Test
    void depositTest() {
        var account = getAccount();
        var transaction = this.getTransaction(TransactionType.DEPOSIT, account);
        given(accountRepository.findById(any(Long.class)))
                .willReturn(Optional.of(account));

        given(accountRepository.save(any(Account.class)))
                .willReturn(account);


        given(transactionRepository.save(any(AccountTransaction.class)))
                .willReturn(transaction);

        var expected = accountService.deposit(this.getDepositForm());

        assertEquals(expected.balance(), account.getBalance());
        assertTrue(expected.type().equals(TransactionType.DEPOSIT.name()));
    }


    @Test
    void withdrawTest() {
        var account = getAccount();
        var transaction = this.getTransaction(TransactionType.WITHDRAW, account);
        given(accountRepository.findById(any(Long.class)))
                .willReturn(Optional.of(account));

        given(accountRepository.save(any(Account.class)))
                .willReturn(account);

        given(transactionRepository.save(any(AccountTransaction.class)))
                .willReturn(transaction);

        var expected = accountService.withdraw(1L, this.getWithdrawForm());

        assertEquals(expected.balance().subtract(BigDecimal.ONE),
                account.getBalance().subtract(BigDecimal.ONE));
        assertTrue(expected.type().equals(TransactionType.WITHDRAW.name()));
    }

    private WithdrawForm getWithdrawForm() {
        return new WithdrawForm(BigDecimal.ONE);
    }

    @Test
    void getTransactionsTest() {
        var account = getAccount();
        given(transactionRepository.findAllByAccountId(any(Long.class)))
                .willReturn(List.of(getTransaction(TransactionType.DEPOSIT, account)));

        List<AccountTransactionDto> expected = this.accountService.getTransactions(2L);

        assertFalse(expected.isEmpty());

        expected.forEach(res -> {
            assertEquals(res.balance(), account.getBalance());
        });

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


    private DepositForm getDepositForm() {
        return new DepositForm(1L, BigDecimal.ZERO);
    }
    private AccountTransaction getTransaction(TransactionType transactionType, Account account) {
        return new AccountTransaction(transactionType, account.getId(), account.getBalance());
    }
}