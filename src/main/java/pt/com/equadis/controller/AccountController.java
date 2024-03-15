package pt.com.equadis.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pt.com.equadis.dto.form.WithdrawForm;
import pt.com.equadis.dto.response.AccountDto;
import pt.com.equadis.dto.form.AccountForm;
import pt.com.equadis.dto.form.DepositForm;
import pt.com.equadis.dto.response.AccountTransactionDto;
import pt.com.equadis.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(
            @RequestBody @Valid AccountForm accountForm,
            UriComponentsBuilder uriComponentsBuilder
    ){
        var response = accountService.createAccount(accountForm);

        var uri = uriComponentsBuilder.path("v1/accounts/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountTransactionDto> deposit(
            @RequestBody @Valid DepositForm depositForm,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var dto = accountService.deposit(depositForm);

        var uri = uriComponentsBuilder
                .path("v1/transactions/{id}")
                .buildAndExpand(dto.transactionId())
                .toUri();


        return ResponseEntity.created(uri).body(dto);
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<AccountTransactionDto> withdraw(
            @PathVariable("accountId") Long accountId,
            @RequestBody @Valid WithdrawForm withdrawForm,
            UriComponentsBuilder uriComponentsBuilder
    ) {

        var dto = accountService.withdraw(accountId,withdrawForm);

        var uri = uriComponentsBuilder
                .path("v1/transactions/{id}")
                .buildAndExpand(dto.transactionId())
                .toUri();


        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping("{accountId}")
    public ResponseEntity<List<AccountTransactionDto>> getTransactions(@PathVariable("accountId") Long accountId) {
        List<AccountTransactionDto> transactions = this.accountService.getTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }
}
