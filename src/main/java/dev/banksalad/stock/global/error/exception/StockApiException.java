package dev.banksalad.stock.global.error.exception;

public class StockApiException extends RuntimeException {

    public StockApiException() {
    }

    public StockApiException(String message) {
        super(message);
    }
}
