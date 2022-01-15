package br.ead.home.infrastructure;

import br.ead.home.events.BaseEvent;
import br.ead.home.producers.EventProducer;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventProducer implements EventProducer {

    KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void producer(String topic, BaseEvent event) {
        this.kafkaTemplate.send(topic, event);
    }
}
