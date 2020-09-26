package dev.banksalad.stock.global.error.exception;

public class NullProfitException extends RuntimeException {

    public NullProfitException() {
    }

    public NullProfitException(String message) {
        super(message);
    }
}
