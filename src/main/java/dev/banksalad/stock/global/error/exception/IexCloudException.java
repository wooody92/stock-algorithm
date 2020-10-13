package dev.banksalad.stock.global.error.exception;

public class IexCloudException extends StockApiException {

    public IexCloudException() {
    }

    public IexCloudException(String message) {
        super(message);
    }
}
