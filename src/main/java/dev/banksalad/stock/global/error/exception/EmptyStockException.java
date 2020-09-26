package dev.banksalad.stock.global.error.exception;

public class EmptyStockException extends RuntimeException{

    public EmptyStockException() {
    }

    public EmptyStockException(String message) {
        super(message);
    }
}
