package dev.banksalad.stock.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_SYMBOL(400, "Invalid Stock Symbol"),
    EMPTY_STOCK(404, "Not Found Stock Data"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
