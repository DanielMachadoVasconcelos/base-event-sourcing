package br.ead.home.infrastructure;

import br.ead.home.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregatedId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregatedId);
}
