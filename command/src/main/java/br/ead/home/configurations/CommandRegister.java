package br.ead.home.configurations;

import br.ead.home.commands.*;
import br.ead.home.infrastructure.CommandDispatcher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CommandRegister {

    CommandDispatcher commandDispatcher;
    CommandHandler commandHandler;

    @EventListener(ApplicationReadyEvent.class)
    public void registerHandlers() {
        log.debug("Registering the Account commands handlers to the Commander Dispatcher!");
        commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handler);
        commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handler);
    }
}
