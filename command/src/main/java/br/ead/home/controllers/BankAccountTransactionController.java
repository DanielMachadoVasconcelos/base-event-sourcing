package br.ead.home.controllers;

import br.ead.home.commands.DepositFundsCommand;
import br.ead.home.commands.WithdrawFundsCommand;
import br.ead.home.infrastructure.CommandDispatcher;
import br.ead.home.model.dto.BaseResponse;
import br.ead.home.model.dto.OpenAccountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/bank-accounts/")
public class BankAccountTransactionController {

    CommandDispatcher commandDispatcher;

    @PostMapping(path = "{bank-account-id}/deposits")
    public BaseResponse depositFunds(
            @PathVariable(value = "bank-account-id") @NotBlank String bankAccountId,
            @RequestBody @Valid @NotNull DepositFundsCommand command) {
        command.setId(bankAccountId);
        commandDispatcher.send(command);
        return OpenAccountResponse.builder()
                .id(bankAccountId)
                .message("Deposit founds request completed successfully!")
                .build();
    }


    @PostMapping(path = "{bank-account-id}/withdraws")
    public BaseResponse withdrawFunds(
            @PathVariable(value = "bank-account-id") @NotBlank String bankAccountId,
            @RequestBody @Valid @NotNull WithdrawFundsCommand command) {
        command.setId(bankAccountId);
        commandDispatcher.send(command);
        return OpenAccountResponse.builder()
                .id(bankAccountId)
                .message("Withdraw founds request completed successfully!")
                .build();
    }
}
