package pt.com.equadis.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import pt.com.equadis.dto.response.CustomerDto;
import pt.com.equadis.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("v1/accounts")
@Tag(name = "Account", description = "Account API - Group of operation in accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Could not create the account because of wrong input")
    })
    public ResponseEntity<AccountDto> createAccount(
            @RequestBody @Valid AccountForm accountForm,
            UriComponentsBuilder uriComponentsBuilder
    ){
        var response = accountService.createAccount(accountForm);

        var uri = uriComponentsBuilder.path("v1/accounts/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/deposit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "deposit was done", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransactionDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Could not deposit because the account was not found")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Withdraw was done", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransactionDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Could not withdraw because the account was not found")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "all transactions by account id was retrieved", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AccountTransactionDto.class))
            })
    })
    public ResponseEntity<List<AccountTransactionDto>> getTransactions(@PathVariable("accountId") Long accountId) {
        List<AccountTransactionDto> transactions = this.accountService.getTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }
}
