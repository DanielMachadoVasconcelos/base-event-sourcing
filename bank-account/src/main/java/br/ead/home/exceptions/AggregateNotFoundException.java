package br.ead.home.exceptions;

public class AggregateNotFoundException extends RuntimeException{
    public AggregateNotFoundException(String message) {
        super(message);
    }
}
