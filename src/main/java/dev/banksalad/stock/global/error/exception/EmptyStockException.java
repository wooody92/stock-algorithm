package dev.banksalad.stock.global.error.exception;

public class EmptyStockException extends StockApiException {

    public EmptyStockException() {
    }

    public EmptyStockException(String message) {
        super(message);
    }
}
