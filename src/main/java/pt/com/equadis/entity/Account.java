package pt.com.equadis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.com.equadis.dto.response.AccountDto;
import pt.com.equadis.error.WithdrawException;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Customer customer;

    private BigDecimal balance;

    public Account(Customer customer, BigDecimal initialBalance) {
        this.customer = customer;
        this.balance = initialBalance;
    }

    public AccountDto toDto() {
        return new AccountDto(id, balance);
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }


    public Long getId() {
        return id;
    }

    public void withdraw(BigDecimal amount) {
        if(amount.compareTo(this.balance) > 0)
            throw new WithdrawException("your account does not have enough money");

        this.balance = this.balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return this.balance;
    }
}
