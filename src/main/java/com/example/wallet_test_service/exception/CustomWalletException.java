package com.example.wallet_test_service.exception;

import org.springframework.http.HttpStatus;


public interface CustomWalletException {
    String getMessage();
    HttpStatus getStatus();
}