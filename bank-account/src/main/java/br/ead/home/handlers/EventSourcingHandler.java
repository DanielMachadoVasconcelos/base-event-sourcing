package br.ead.home.handlers;

import br.ead.home.agregates.AggregateRoot;

public interface EventSourcingHandler<T> {

    void save(AggregateRoot aggregate);
    T getById(String id);
}
