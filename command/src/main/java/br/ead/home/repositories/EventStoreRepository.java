package br.ead.home.repositories;

import br.ead.home.events.EventModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventStoreRepository extends ElasticsearchRepository<EventModel, String> {
    List<EventModel> findByAggregatedIdentifier(String aggregatedIdentifier);
}
