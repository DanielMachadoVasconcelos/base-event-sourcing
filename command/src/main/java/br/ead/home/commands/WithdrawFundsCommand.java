package br.ead.home.commands;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class WithdrawFundsCommand extends BaseCommand {

    @Positive
    private double amount;
}
