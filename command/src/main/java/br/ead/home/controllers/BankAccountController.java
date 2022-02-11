package br.ead.home.controllers;

import br.ead.home.commands.CloseAccountCommand;
import br.ead.home.commands.OpenAccountCommand;
import br.ead.home.infrastructure.CommandDispatcher;
import br.ead.home.model.dto.BaseResponse;
import br.ead.home.model.dto.OpenAccountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/bank-accounts")
public class BankAccountController {

    CommandDispatcher commandDispatcher;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse openAccount(@RequestBody @Valid @NotNull OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        commandDispatcher.send(command);
        return OpenAccountResponse.builder()
                .id(id)
                .message("Bank Account creation request completed successfully!")
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/{bank-account-id}/close")
    public BaseResponse closeAccount(@PathVariable("bank-account-id") @NotBlank String bankAccountId) {
        commandDispatcher.send(CloseAccountCommand.builder()
                .id(bankAccountId)
                .build());
        return OpenAccountResponse.builder()
                .id(bankAccountId)
                .message("Bank Account close request completed successfully!")
                .build();
    }
}
