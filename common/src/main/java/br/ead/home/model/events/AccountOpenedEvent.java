package br.ead.home.model.events;

import br.ead.home.events.BaseEvent;
import br.ead.home.model.AccountType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountOpenedEvent extends BaseEvent {
    String accountHolder;
    AccountType accountType;
    Date createdAt;
    double openingBalance;
}
