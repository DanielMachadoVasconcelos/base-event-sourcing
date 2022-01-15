package br.ead.home.handlers;

import br.ead.home.model.BankAccount;
import br.ead.home.model.events.AccountClosedEvent;
import br.ead.home.model.events.AccountOpenedEvent;
import br.ead.home.model.events.FundsDepositedEvent;
import br.ead.home.model.events.FundsWithdrawnEvent;
import br.ead.home.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;


@Service
@AllArgsConstructor
public class AccountEventHandler implements EventHandler {

    BankAccountRepository repository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountType(event.getAccountType())
                .accountHolder(event.getAccountHolder())
                .createdAt(event.getCreatedAt())
                .balance(event.getOpeningBalance())
                .build();

        repository.save(bankAccount);
    }

    @Override
    public void on(AccountClosedEvent event) {
        var maybeABankAccount = repository.findById(event.getId());

        if (maybeABankAccount.isEmpty()) {
            return;
        }

        var bankAccount = maybeABankAccount.get();
        bankAccount.setClosedAt(Date.from(Instant.now()));
        repository.save(bankAccount);
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var maybeABankAccount = repository.findById(event.getId());

        if (maybeABankAccount.isEmpty()) {
            return ;
        }

        var bankAccount = maybeABankAccount.get();
        var currentBalance = bankAccount.getBalance();
        var latestBalance = currentBalance - event.getAmount();

        bankAccount.setBalance(latestBalance);
        repository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var maybeABankAccount = repository.findById(event.getId());

        if (maybeABankAccount.isEmpty()) {
            return ;
        }

        var bankAccount = maybeABankAccount.get();
        var currentBalance = bankAccount.getBalance();
        var latestBalance = currentBalance + event.getAmount();

        bankAccount.setBalance(latestBalance);
        repository.save(bankAccount);
    }
}
