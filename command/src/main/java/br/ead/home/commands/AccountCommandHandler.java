package br.ead.home.commands;

import br.ead.home.handlers.EventSourcingHandler;
import br.ead.home.model.AccountAggregate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.*;

@Service
@AllArgsConstructor
public class AccountCommandHandler implements CommandHandler {

    EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Override
    public void handler(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(CloseAccountCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.closeAccount();
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(DepositFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.depositFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }

    @Override
    public void handler(WithdrawFundsCommand command) {
        var aggregate = eventSourcingHandler.getById(command.getId());

        checkState(command.getAmount() < aggregate.getBalance(), "Withdraw declined due to insufficient founds!");

        aggregate.withdrawFunds(command.getAmount());
        eventSourcingHandler.save(aggregate);
    }
}
