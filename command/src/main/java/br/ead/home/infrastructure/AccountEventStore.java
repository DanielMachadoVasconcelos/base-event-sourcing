package br.ead.home.infrastructure;

import br.ead.home.events.BaseEvent;
import br.ead.home.events.EventModel;
import br.ead.home.exceptions.AggregateNotFoundException;
import br.ead.home.exceptions.ConcurrencyException;
import br.ead.home.model.AccountAggregate;
import br.ead.home.repositories.EventStoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountEventStore implements EventStore {

    private EventStoreRepository repository;
    private AccountEventProducer producer;

    @Override
    public void saveEvents(String aggregatedId, Iterable<BaseEvent> events, int expectedVersion) {

        var eventStream = repository.findByAggregatedIdentifier(aggregatedId);
        int latestVersion = eventStream.stream()
                .map(EventModel::getVersion)
                .max(Comparator.naturalOrder())
                .orElse(-1);

        if (expectedVersion != 1 && latestVersion != expectedVersion) {
            var errorMessage = MessageFormat.format("Mismatch on the expected version. Current version is {0}", latestVersion);
            throw new ConcurrencyException(errorMessage);
        }

        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .createdAt(Date.from(Instant.now()))
                    .aggregatedIdentifier(aggregatedId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = repository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                producer.producer(event.getClass().getSimpleName(), event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregatedId) {
        var eventStream = repository.findByAggregatedIdentifier(aggregatedId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException(MessageFormat.format("Incorrect account ID provided! No bank account found for ID {0}", aggregatedId));
        }
        return eventStream.stream()
                .map(EventModel::getEventData)
                .sorted(Comparator.comparing(BaseEvent::getVersion))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}


