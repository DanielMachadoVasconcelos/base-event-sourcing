package br.ead.home.repositories;

import br.ead.home.events.BaseEvent;
import br.ead.home.model.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountHolder(String accountHolder);
    List<BaseEvent> findAllByBalanceGreaterThan(double balance);
    List<BaseEvent> findAllByBalanceLessThan(double balance);
}
