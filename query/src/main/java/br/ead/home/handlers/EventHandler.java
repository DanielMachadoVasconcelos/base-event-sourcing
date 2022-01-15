package br.ead.home.handlers;

import br.ead.home.model.events.AccountClosedEvent;
import br.ead.home.model.events.AccountOpenedEvent;
import br.ead.home.model.events.FundsDepositedEvent;
import br.ead.home.model.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(AccountClosedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(FundsDepositedEvent event);
}
