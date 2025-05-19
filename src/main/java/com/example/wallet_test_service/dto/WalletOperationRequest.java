package com.example.wallet_test_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WalletOperationRequest {
    private UUID walletId;
    private String operationType; // DEPOSIT или WITHDRAW
    private Long amount;
}