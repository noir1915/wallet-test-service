package com.example.wallet_test_service.exception;

import org.springframework.http.HttpStatus;

public class InvalidOperationException extends RuntimeException implements CustomWalletException {
    public InvalidOperationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}