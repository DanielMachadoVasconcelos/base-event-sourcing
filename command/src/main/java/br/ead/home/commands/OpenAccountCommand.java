package br.ead.home.commands;

import br.ead.home.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class OpenAccountCommand extends BaseCommand {

    @NotEmpty
    String accountHolder;

    @NotNull
    AccountType accountType;

    @Min(0L)
    double openingBalance;
}
