package br.ead.home.consumers;

import br.ead.home.handlers.EventHandler;
import br.ead.home.model.events.AccountClosedEvent;
import br.ead.home.model.events.AccountOpenedEvent;
import br.ead.home.model.events.FundsDepositedEvent;
import br.ead.home.model.events.FundsWithdrawnEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventConsumer implements EventConsumer {

    EventHandler eventHandler;

    @Override
    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(AccountOpenedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(AccountClosedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "FundsDepositedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(FundsDepositedEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }

    @Override
    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(FundsWithdrawnEvent event, Acknowledgment acknowledgment) {
        eventHandler.on(event);
        acknowledgment.acknowledge();
    }
}
