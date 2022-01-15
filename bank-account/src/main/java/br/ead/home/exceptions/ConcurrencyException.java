package br.ead.home.exceptions;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String errorMessage) {
        super(errorMessage);
    }
}
