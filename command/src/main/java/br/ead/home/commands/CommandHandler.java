package br.ead.home.commands;

public interface CommandHandler {
    void handler(OpenAccountCommand command);
    void handler(CloseAccountCommand command);
    void handler(DepositFundsCommand command);
    void handler(WithdrawFundsCommand command);
}
