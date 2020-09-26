package dev.banksalad.stock.global.error.exception;

public class IexCloudException extends RuntimeException {

    public IexCloudException() {
    }

    public IexCloudException(String message) {
        super(message);
    }
}
