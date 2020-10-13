package dev.banksalad.stock.global.error.exception;

public class InvalidSymbolException extends StockApiException {

    public InvalidSymbolException() {
    }

    public InvalidSymbolException(String message) {
        super(message);
    }
}
