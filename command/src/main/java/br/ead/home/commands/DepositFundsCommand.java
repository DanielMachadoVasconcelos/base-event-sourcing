package br.ead.home.commands;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class DepositFundsCommand extends BaseCommand {
    @Min(0L)
    private double amount;
}
