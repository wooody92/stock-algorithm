package dev.banksalad.stock.global.error;

import dev.banksalad.stock.global.error.exception.EmptyStockException;
import dev.banksalad.stock.global.error.exception.IexCloudException;
import dev.banksalad.stock.global.error.exception.InvalidSymbolException;
import dev.banksalad.stock.global.error.exception.NullProfitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchNullProfitException(NullProfitException e) {
        log.error("NullProfitException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchInvalidSymbolException(InvalidSymbolException e) {
        log.info("InvalidSymbolException", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_SYMBOL);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchEmptyStockException(EmptyStockException e) {
        log.info("EmptyStockException", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.EMPTY_STOCK);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> catchIexCloudException(IexCloudException e) {
        log.error("IexCloudException", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
