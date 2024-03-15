package pt.com.equadis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.com.equadis.dto.form.WithdrawForm;
import pt.com.equadis.dto.response.AccountDto;
import pt.com.equadis.dto.form.AccountForm;
import pt.com.equadis.dto.form.DepositForm;
import pt.com.equadis.dto.response.AccountTransactionDto;
import pt.com.equadis.entity.Account;
import pt.com.equadis.entity.AccountTransaction;
import pt.com.equadis.entity.TransactionType;
import pt.com.equadis.error.AccountNotFoundException;
import pt.com.equadis.error.CustomerNotFoundException;
import pt.com.equadis.repository.AccountRepository;
import pt.com.equadis.repository.CustomerRepository;
import pt.com.equadis.repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public AccountDto createAccount(AccountForm accountForm) {
        var customer = customerRepository
                .findById(accountForm.customerId())
                .orElseThrow(() ->
                        new CustomerNotFoundException("Could not found the customer with the id: " + accountForm.customerId())
                );

        var account = accountRepository.save(new Account(customer, accountForm.initialBalance()));

        return account.toDto();
    }

    @Override
    public AccountTransactionDto deposit(DepositForm depositForm) {
        var account = accountRepository
                .findById(depositForm.accountId())
                .orElseThrow(() ->
                        new AccountNotFoundException("Could not found the customer with the id: " + depositForm.accountId())
                );

        account.deposit(depositForm.amount());

        accountRepository.save(account);

        var transaction = new AccountTransaction(TransactionType.DEPOSIT, account.getId(), account.getBalance());

        transactionRepository.save(transaction);

        return transaction.toDto();
    }

    @Override
    public AccountTransactionDto withdraw(Long accountId, WithdrawForm withdrawForm) {
        var account = accountRepository
                .findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Could not found the customer with the id: " + accountId)
                );

        account.withdraw(withdrawForm.amount());
        accountRepository.save(account);

        var transaction = new AccountTransaction(TransactionType.WITHDRAW, account.getId(), account.getBalance());

        transactionRepository.save(transaction);

        return transaction.toDto();
    }

    @Override
    public List<AccountTransactionDto> getTransactions(Long accountId) {
        var transactions = transactionRepository.findAllByAccountId(accountId);

        if (transactions.isEmpty()) return List.of();

        return transactions.stream().map(AccountTransaction::toDto).collect(Collectors.toList());
    }
}
