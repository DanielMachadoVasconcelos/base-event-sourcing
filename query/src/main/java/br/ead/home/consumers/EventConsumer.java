package br.ead.home.consumers;

import br.ead.home.model.events.AccountClosedEvent;
import br.ead.home.model.events.AccountOpenedEvent;
import br.ead.home.model.events.FundsDepositedEvent;
import br.ead.home.model.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consumer(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);
    void consumer(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);
    void consumer(@Payload FundsDepositedEvent event, Acknowledgment acknowledgment);
    void consumer(@Payload FundsWithdrawnEvent event, Acknowledgment acknowledgment);
}
