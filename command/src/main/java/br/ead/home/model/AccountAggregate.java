package br.ead.home.model;

import br.ead.home.agregates.AggregateRoot;
import br.ead.home.commands.OpenAccountCommand;
import br.ead.home.model.events.AccountClosedEvent;
import br.ead.home.model.events.AccountOpenedEvent;
import br.ead.home.model.events.FundsDepositedEvent;
import br.ead.home.model.events.FundsWithdrawnEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;


@Data
@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getAccountType())
                .createdAt(Date.from(Instant.now()))
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!active) {
            throw new IllegalArgumentException("Founds cannot be deposit into a closed account!");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("The deposit amount must be greater than 0!");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!active) {
            throw new IllegalArgumentException("Founds cannot be withdraw from a closed account!");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("The withdraw amount must be greater than 0!");
        }

        raiseEvent(FundsWithdrawnEvent.builder()
                .id(id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!active) {
            throw new IllegalArgumentException("Cannot close an already closed account!");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
