package br.ead.home.producers;

import br.ead.home.events.BaseEvent;

public interface EventProducer {

    void producer(String topic, BaseEvent event);
}
