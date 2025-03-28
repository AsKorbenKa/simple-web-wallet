package ru.example.simplewebwallet.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<ApiError> handleNotFound(NotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiError.builder()
                        .status("NOT_FOUND")
                        .reason("Искомый объект не найден.")
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleInsufficientFunds(InsufficientFundsException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiError.builder()
                        .status("UNPROCESSABLE_ENTITY")
                        .reason("Команды, содержащиеся в запросе, не могут быть выполнены.")
                        .message(e.getMessage())
                        .build()
                );
    }
}
