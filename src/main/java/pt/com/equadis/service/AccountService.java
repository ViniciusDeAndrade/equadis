package pt.com.equadis.service;

import org.springframework.transaction.annotation.Transactional;
import pt.com.equadis.dto.form.WithdrawForm;
import pt.com.equadis.dto.response.AccountDto;
import pt.com.equadis.dto.form.AccountForm;
import pt.com.equadis.dto.form.DepositForm;
import pt.com.equadis.dto.response.AccountTransactionDto;

import java.util.List;

public interface AccountService {

    @Transactional
    AccountDto createAccount(AccountForm account);

    @Transactional
    AccountTransactionDto deposit(DepositForm depositForm);

    @Transactional
    AccountTransactionDto withdraw(Long accountId, WithdrawForm withdrawForm);

    List<AccountTransactionDto> getTransactions(Long accountId);
}
