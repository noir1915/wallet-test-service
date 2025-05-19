package com.example.wallet_test_service.exception;

import org.springframework.http.HttpStatus;

public class WalletNotFoundException extends RuntimeException implements CustomWalletException {
    public WalletNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}