package dev.banksalad.stock.global.error.exception;

public class InvalidSymbolException extends RuntimeException{

    public InvalidSymbolException() {
    }

    public InvalidSymbolException(String message) {
        super(message);
    }
}
